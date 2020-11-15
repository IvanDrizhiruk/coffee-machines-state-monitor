package ua.dp.dryzhyruk.e2e.spring.clients;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;

public class EventRegistrarClient {

    private final TestRestTemplate restTemplate;

    public EventRegistrarClient(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public  ResponseEntity<Void> getResponseEntity(String coffeeMachineId, CupDTO cup) {
        String cupProducedRequestUrl = "/event-registrar/coffee-machine/" + coffeeMachineId + "/cup-produced";
        return this.restTemplate.postForEntity(cupProducedRequestUrl, cup, Void.class);
    }
}
