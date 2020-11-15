package ua.dp.dryzhyruk.e2e.jgiven;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.CoffeeMachinesStateMonitorApplication;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@SpringBootTest(
        classes = CoffeeMachinesStateMonitorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class N4_CoffeeMachinesStateMonitorApplicationTests {

    @Autowired
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    @Autowired
    private CoffeeMachineStateStorage coffeeMachineStateStorage;

    @Autowired
    private NewCupStage newCupStage;
    @Autowired
    private CoffeeMachineStateStage coffeeMachineStateStage;


    @BeforeEach
    void beforeEach() {
        //TODO move it to configuration of app
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
    void registerNewCapAndCheckStatus() {

        String coffeeMachineId = "1";

        CupDTO cup = CupDTO.builder()
                .coffeePortions(1)
                .withMilk(true)
                .build();

        newCupStage
                .given()
                    .coffeeMachineId(coffeeMachineId)
                    .cup(cup)
                .when()
                    .registerNewCoffeeCup()
                .then()
                    .answerIs(HttpStatus.NO_CONTENT);

        CoffeeMachineStateDTO expectedCoffeeMachineState = CoffeeMachineStateDTO.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(99)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(29)
                .milkStatus(Status.OK)
                .placeInTrashContainerLeftForNPortions(99)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        coffeeMachineStateStage
                .given().coffeeMachineId(coffeeMachineId)
                .when().getCoffeeMachineState()
                .then().answerIs(HttpStatus.OK, expectedCoffeeMachineState);
    }
}
