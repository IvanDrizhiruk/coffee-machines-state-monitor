package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

class N5_ErrorGuessing_Mockito_CoffeeMachinesEventRegisterServicesTest {

    @Test
    void minimumValueForStateShouldBeZero() {
        //given
        CoffeeMachineConfigurationStorage configurationStorage = Mockito.mock(CoffeeMachineConfigurationStorage.class);
        CoffeeMachineStateStorage stateStorage = Mockito.mock(CoffeeMachineStateStorage.class);

        CoffeeMachineStateEntity stateEntity = CoffeeMachineStateEntity.builder()
                .coffeeMachineId("#1")
                .coffeeBeansLeftForNPortions(0)
                .milkLeftForNPortions(0)
                .placeInTrashContainerLeftForNPortions(0)
                .build();

        Mockito.when(stateStorage.find("#1")).thenReturn(stateEntity);


        String coffeeMachineId = "#1";
        Cup cup = Cup.builder()
                .coffeePortions(2)
                .withMilk(true)
                .build();

        String expectedUpdatedCoffeeMachineId = "#1";
        CoffeeMachineStateEntity expectedUpdatedCoffeeMachineState = CoffeeMachineStateEntity.builder()
                .coffeeMachineId(coffeeMachineId)
                .coffeeBeansLeftForNPortions(0)
                .milkLeftForNPortions(0)
                .placeInTrashContainerLeftForNPortions(0)
                .build();

        //when
        CoffeeMachinesEventRegisterServices registerServices = new CoffeeMachinesEventRegisterServices(
                configurationStorage,
                stateStorage);

        registerServices.registerNewMadeCup(coffeeMachineId, cup);

        //then
        Mockito.verify(stateStorage).update(expectedUpdatedCoffeeMachineState);
    }
}