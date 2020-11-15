package ua.dp.dryzhyruk.e2e.test.rest.clients;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;

public class CoffeeMachineStatesClient {

    private final TestRestTemplate restTemplate;

    public CoffeeMachineStatesClient(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<CoffeeMachineStateDTO> getStatus(String coffeeMachineId) {
        String coffeeMachineStatusRequestUrl = "/coffee-machine/" + coffeeMachineId + "/status";
        return restTemplate.getForEntity(coffeeMachineStatusRequestUrl, CoffeeMachineStateDTO.class);
    }
}
