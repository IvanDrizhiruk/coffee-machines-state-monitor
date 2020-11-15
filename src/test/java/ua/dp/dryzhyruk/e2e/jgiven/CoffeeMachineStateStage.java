package ua.dp.dryzhyruk.e2e.jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
class CoffeeMachineStateStage extends Stage<CoffeeMachineStateStage> {

    @Autowired
    private TestRestTemplate restTemplate;

    @ScenarioState
    private String coffeeMachineId;
    @ScenarioState
    private ResponseEntity<CoffeeMachineStateDTO> answer;

    public CoffeeMachineStateStage coffeeMachineId(String coffeeMachineId) {
        this.coffeeMachineId = coffeeMachineId;

        return self();
    }

    public CoffeeMachineStateStage getCoffeeMachineState() {
        String coffeeMachineStatusRequestUrl = "/coffee-machine/" + coffeeMachineId + "/status";
        answer = this.restTemplate.getForEntity(coffeeMachineStatusRequestUrl, CoffeeMachineStateDTO.class);

        return self();
    }

    public void answerIs(HttpStatus expectedHttpStatus, CoffeeMachineStateDTO expectedCoffeeMachineState) {
        assertThat(answer.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(answer.getBody())
                .usingRecursiveComparison()
                .ignoringFields("timeOfCalculation")
                .isEqualTo(expectedCoffeeMachineState);
    }
}