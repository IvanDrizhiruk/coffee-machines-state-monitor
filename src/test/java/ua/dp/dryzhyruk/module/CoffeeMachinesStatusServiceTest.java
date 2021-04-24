package ua.dp.dryzhyruk.module;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.CoffeeMachinesStatusService;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator.CoffeeMachinesStatusCalculator;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

class CoffeeMachinesStatusServiceTest {

    private CoffeeMachineStateStorage coffeeMachineStateStorageMock;
    private CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorageMock;
    private CoffeeMachinesStatusService coffeeMachinesStatusService;

    @BeforeEach
    void beforeEach() {
        coffeeMachineStateStorageMock = Mockito.mock(CoffeeMachineStateStorage.class);
        coffeeMachineConfigurationStorageMock = Mockito.mock(CoffeeMachineConfigurationStorage.class);

        CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator = new CoffeeMachinesStatusCalculator();

        coffeeMachinesStatusService = new CoffeeMachinesStatusService(
                coffeeMachineStateStorageMock,
                coffeeMachineConfigurationStorageMock,
                coffeeMachinesStatusCalculator);
    }

    @Test
    void getAllCoffeeMachinesStatus() {
        //given
        String coffeeMachineId = "#1";

        CoffeeMachineStateEntity coffeeMachineStateEntity = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .milkLeftForNPortions(3)
                .placeInTrashContainerLeftForNPortions(100)
                .build();

        Mockito.when(coffeeMachineStateStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineStateEntity);

        CoffeeMachineConfigurationEntity coffeeMachineConfiguration = CoffeeMachineConfigurationEntity.builder()
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
        Mockito.when(coffeeMachineConfigurationStorageMock.find(coffeeMachineId))
                .thenReturn(coffeeMachineConfiguration);

        CoffeeMachineState expected = CoffeeMachineState.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(100)
                .coffeeBeansStatus(Status.OK)
                .milkLeftForNPortions(3)
                .milkStatus(Status.CRITICAL)
                .placeInTrashContainerLeftForNPortions(100)
                .placesInTrashContainerStatus(Status.OK)
                .build();

        //when
        CoffeeMachineState actual = coffeeMachinesStatusService
                .getCoffeeMachinesStatus(coffeeMachineId);

        //then
        Assertions.assertEquals(expected, actual);
    }
}