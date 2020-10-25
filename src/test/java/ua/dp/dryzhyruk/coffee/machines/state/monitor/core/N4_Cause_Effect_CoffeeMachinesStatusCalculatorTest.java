package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

class N4_Cause_Effect_CoffeeMachinesStatusCalculatorTest {

    @Test
    public void statusShouldBeOkForMaxValue() {

        //given
        String coffeeMachineId = "#1";

        CoffeeMachineStateEntity coffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(30)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        CoffeeMachineConfiguration coffeeMachineConfiguration = CoffeeMachineConfiguration.builder()
                .coffeeMachineId(coffeeMachineId)
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

        CoffeeMachineState expected = CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(30)
                .milkStatus(Status.OK)
                .placeInTrashContainerLeftForNPortions(100)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        //when
        CoffeeMachineState actual = new CoffeeMachinesStatusCalculator()
                .calculate(coffeeMachineState, coffeeMachineConfiguration);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeCalculatedSeparately() {

        //given
        String coffeeMachineId = "#1";

        CoffeeMachineStateEntity coffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(20)
                .milkLeftForNPortions(3)
                .placeInTrashContainerLeftForNPortions(90)
                .build();

        CoffeeMachineConfiguration coffeeMachineConfiguration = CoffeeMachineConfiguration.builder()
                .coffeeMachineId(coffeeMachineId)
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

        CoffeeMachineState expected = CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(20)
                .coffeeBeansStatus(Status.WARNING)
                .milkLeftForNPortions(3)
                .milkStatus(Status.CRITICAL)
                .placeInTrashContainerLeftForNPortions(90)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        //when
        CoffeeMachineState actual = new CoffeeMachinesStatusCalculator()
                .calculate(coffeeMachineState, coffeeMachineConfiguration);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldBePossibleToReceiveWarningForZeroValue() {

        //given
        String coffeeMachineId = "#1";

        CoffeeMachineStateEntity coffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(0)
                .milkLeftForNPortions(0)
                .placeInTrashContainerLeftForNPortions(0)
                .build();

        CoffeeMachineConfiguration coffeeMachineConfiguration = CoffeeMachineConfiguration.builder()
                .coffeeMachineId(coffeeMachineId)
                .maxNumberCoffeeBeansPortions(100)
                .coffeeBeansWarningNPortionLeft(10)
                .coffeeBeansCriticalNPortionLeft(0)
                .maxNumberMilkPortions(30)
                .milkWarningNPortionLeft(5)
                .milkCriticalNPortionLeft(0)
                .maxNumberPlacesInTrashContainer(100)
                .placesInTrashContainerWarningNPortionLeft(10)
                .PlacesInTrashContainerCriticalNPortionLeft(0)
                .build();

        CoffeeMachineState expected = CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(0)
                .coffeeBeansStatus(Status.WARNING)
                .milkLeftForNPortions(0)
                .milkStatus(Status.WARNING)
                .placeInTrashContainerLeftForNPortions(0)
                .placesInTrashContainerStatus(Status.WARNING)
                .build();

        //when
        CoffeeMachineState actual = new CoffeeMachinesStatusCalculator()
                .calculate(coffeeMachineState, coffeeMachineConfiguration);

        //then
        Assertions.assertEquals(expected, actual);
    }
}