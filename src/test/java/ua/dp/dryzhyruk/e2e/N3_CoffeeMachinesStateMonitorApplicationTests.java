package ua.dp.dryzhyruk.e2e;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.CoffeeMachinesStateMonitorApplication;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CupDTO;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = CoffeeMachinesStateMonitorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class N3_CoffeeMachinesStateMonitorApplicationTests {

    @Autowired
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage;
    @Autowired
    private CoffeeMachineStateStorage coffeeMachineStateStorage;

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

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

    @Test
    public void shouldCorrectlyCountAddedCookies() {

        String coffeeMachineId = "1";

        CupDTO cup = CupDTO.builder()
                .coffeePortions(1)
                .withMilk(true)
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(cup)
                .when()
                .post("/event-registrar/coffee-machine/" + coffeeMachineId + "/cup-produced")
                .then()
                .assertThat()
                .statusCode(204);

        CoffeeMachineStateDTO state = RestAssured.get("/coffee-machine/" + coffeeMachineId + "/status")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(CoffeeMachineStateDTO.class);

        RestAssured.get("/coffee-machine/" + coffeeMachineId + "/status")
                .then()
                .assertThat()
                .statusCode(200)
                .body("", Matchers.aMapWithSize(8),
                        "coffeeMachineId", Matchers.equalTo(coffeeMachineId),
                        "coffeeBeansLeftForNPortions", Matchers.equalTo(99),
                        "coffeeBeansStatus", Matchers.equalTo(Status.OK.toString()),
                        "milkLeftForNPortions", Matchers.equalTo(29),
                        "milkStatus", Matchers.equalTo(Status.OK.toString()),
                        "placeInTrashContainerLeftForNPortions", Matchers.equalTo(99),
                        "placesInTrashContainerStatus", Matchers.equalTo(Status.OK.toString()),
                        "timeOfCalculation", Matchers.notNullValue());

//        CoffeeMachineStateDTO expectedCoffeeMachineStateDTO = CoffeeMachineStateDTO.builder()
//                .coffeeMachineId(coffeeMachineId)
//                .coffeeBeansLeftForNPortions(99)
//                .coffeeBeansStatus(Status.OK)
//                .milkLeftForNPortions(29)
//                .milkStatus(Status.OK)
//                .placeInTrashContainerLeftForNPortions(99)
//                .placesInTrashContainerStatus(Status.OK)
//                .build();
//
//        assertThat(state)
//                .usingRecursiveComparison()
//                .ignoringFields("timeOfCalculation")
//                .isEqualTo(expectedCoffeeMachineStateDTO);
    }
}
