package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Cup;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.List;

class N5_ErrorGuessing_CoffeeMachinesEventRegisterServicesTest {

    @Test
    void minimumValueForStateShouldBeZero() {
        //given
        CoffeeMachineConfigurationStorageMock configurationStorage = new CoffeeMachineConfigurationStorageMock();
        CoffeeMachineStateStorageMock stateStorage = new CoffeeMachineStateStorageMock();

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
        Assertions.assertEquals(expectedUpdatedCoffeeMachineId, stateStorage.getLastUpdatedCoffeeMachineId());
        Assertions.assertEquals(expectedUpdatedCoffeeMachineState, stateStorage.getLastUpdatedCoffeeMachineState());
    }

    private static class CoffeeMachineConfigurationStorageMock implements CoffeeMachineConfigurationStorage {

        @Override
        public CoffeeMachineConfiguration find(String coffeeMachineId) {
            return null;
        }

        @Override
        public List<CoffeeMachineConfiguration> findAll() {
            return null;
        }
    }

    @Getter
    private static class CoffeeMachineStateStorageMock implements CoffeeMachineStateStorage {

        private String lastUpdatedCoffeeMachineId;
        private CoffeeMachineStateEntity lastUpdatedCoffeeMachineState;


        @Override
        public CoffeeMachineStateEntity find(String coffeeMachineId) {
            return CoffeeMachineStateEntity.builder()
                    .coffeeMachineId(coffeeMachineId)
                    .coffeeBeansLeftForNPortions(0)
                    .milkLeftForNPortions(0)
                    .placeInTrashContainerLeftForNPortions(0)
                    .build();
        }

        @Override
        public List<CoffeeMachineStateEntity> findAll() {
            return null;
        }

        @Override
        public void update(String coffeeMachineId, CoffeeMachineStateEntity newCoffeeMachineState) {
            this.lastUpdatedCoffeeMachineId = coffeeMachineId;
            this.lastUpdatedCoffeeMachineState = newCoffeeMachineState;
        }
    }
}