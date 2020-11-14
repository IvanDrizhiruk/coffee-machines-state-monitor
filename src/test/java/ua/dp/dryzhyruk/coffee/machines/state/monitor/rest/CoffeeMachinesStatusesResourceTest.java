package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesStatusService;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateDTO;

import java.time.Clock;

class CoffeeMachinesStatusesResourceTest {

    @Test
    void getAllCoffeeMachinesStatus() {

        //given
        String coffeeMachineId = "#1";

        Clock clock = Clock.systemDefaultZone();
        CoffeeMachinesStatusService coffeeMachinesStatusService = Mockito.mock(CoffeeMachinesStatusService.class);

        CoffeeMachineState coffeeMachineState = CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(30)
                .milkStatus(Status.OK)
                .placeInTrashContainerLeftForNPortions(100)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        Mockito.when(coffeeMachinesStatusService.getCoffeeMachinesStatus(coffeeMachineId))
                .thenReturn(coffeeMachineState);

        CoffeeMachineStateDTO expected = CoffeeMachineStateDTO.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(30)
                .milkStatus(Status.OK)
                .placeInTrashContainerLeftForNPortions(100)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        //when
        CoffeeMachineStateDTO actual = new CoffeeMachinesStatusesResource(clock, coffeeMachinesStatusService)
                .getCoffeeMachinesStatus(coffeeMachineId);

        //then
//        Assertions.assertEquals(expected, actual);
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("timeOfCalculation")
                .isEqualTo(expected);
    }
}