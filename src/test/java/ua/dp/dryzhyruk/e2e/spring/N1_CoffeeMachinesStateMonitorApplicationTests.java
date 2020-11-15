package ua.dp.dryzhyruk.e2e.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.CoffeeMachinesStateMonitorApplication;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = CoffeeMachinesStateMonitorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class N1_CoffeeMachinesStateMonitorApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    @Autowired
    private CoffeeMachineStateStorage coffeeMachineStateStorage;


    @BeforeEach
    void beforeEach() {
        CoffeeMachineStateEntity coffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId("1")
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(30)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        coffeeMachineStateStorage.save(coffeeMachineState);

        CoffeeMachineConfigurationEntity coffeeMachineConfiguration = CoffeeMachineConfigurationEntity.builder()
                .coffeeMachineId("1")
                .maxNumberCoffeeBeansPortions(100)
                .coffeeBeansWarningNPortionLeft(30)
                .coffeeBeansCriticalNPortionLeft(10)
                .maxNumberMilkPortions(30)
                .milkWarningNPortionLeft(15)
                .milkCriticalNPortionLeft(5)
                .maxNumberPlacesInTrashContainer(100)
                .placesInTrashContainerWarningNPortionLeft(30)
                .PlacesInTrashContainerCriticalNPortionLeft(10)
                .build();

        coffeeMachineConfigurationStorage.save(coffeeMachineConfiguration);
    }

    @AfterEach
    void afterEach() {
        coffeeMachineStateStorage.removeAll();
        coffeeMachineConfigurationStorage.removeAll();
    }

    @Test
    public void registerNewCapAndCheckStatus() {
        String coffeeMachineId = "1";
        String cupProducedRequestUrl = "/event-registrar/coffee-machine/" + coffeeMachineId + "/cup-produced";

        //when
        CupDTO cup = CupDTO.builder()
                .coffeePortions(1)
                .withMilk(true)
                .build();

        ResponseEntity<Void> entity = this.restTemplate.postForEntity(cupProducedRequestUrl, cup, Void.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


        //when
        CoffeeMachineStateEntity coffeeMachineState = coffeeMachineStateStorage.find("1");
        CoffeeMachineStateEntity expectedCoffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId("1")
                .coffeeBeansLeftForNPortions(99)
                .milkLeftForNPortions(29)
                .placeInTrashContainerLeftForNPortions(99)
                .build();

        //then
        assertThat(coffeeMachineState).isEqualTo(expectedCoffeeMachineState);


        //when
        String coffeeMachineStatusRequestUrl = "/coffee-machine/" + coffeeMachineId + "/status";
        ResponseEntity<CoffeeMachineStateDTO> coffeeMachineStateDTO = this.restTemplate.getForEntity(coffeeMachineStatusRequestUrl, CoffeeMachineStateDTO.class);

        //then
        CoffeeMachineStateDTO expectedCoffeeMachineStateDTO = CoffeeMachineStateDTO.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(99)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(29)
                .milkStatus(Status.OK)
                .placeInTrashContainerLeftForNPortions(99)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        assertThat(coffeeMachineStateDTO.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(coffeeMachineStateDTO.getBody())
                .usingRecursiveComparison()
                .ignoringFields("timeOfCalculation")
                .isEqualTo(expectedCoffeeMachineStateDTO);
    }
}
