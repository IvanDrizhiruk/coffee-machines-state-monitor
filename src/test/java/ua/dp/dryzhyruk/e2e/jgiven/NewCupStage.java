package ua.dp.dryzhyruk.e2e.jgiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
class NewCupStage extends Stage<NewCupStage> {

    @Autowired
    private TestRestTemplate restTemplate;

    @ScenarioState
    private String coffeeMachineId;
    @ScenarioState
    private CupDTO newCup;
    @ScenarioState
    private ResponseEntity<Void> answer;

    public NewCupStage coffeeMachineId(String coffeeMachineId) {
        this.coffeeMachineId = coffeeMachineId;

        return self();
    }
    public NewCupStage cup(CupDTO cup) {
        this.newCup = cup;

        return self();
    }

    public NewCupStage registerNewCoffeeCup() {
        String cupProducedRequestUrl = "/event-registrar/coffee-machine/" + coffeeMachineId + "/cup-produced";
        answer = this.restTemplate.postForEntity(cupProducedRequestUrl, newCup, Void.class);

        return self();
    }

    public void answerIs(HttpStatus expectedHttpStatus) {
        assertThat(answer.getStatusCode()).isEqualTo(expectedHttpStatus);
    }
}