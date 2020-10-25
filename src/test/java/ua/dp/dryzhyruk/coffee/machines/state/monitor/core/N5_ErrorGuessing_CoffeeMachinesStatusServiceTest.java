package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.CoffeeMachineState;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.List;

class N5_ErrorGuessing_CoffeeMachinesStatusServiceTest {

    @Test
    void getAllCoffeeMachinesStatus() {

        //given
        CoffeeMachineStateStorage coffeeMachineStateStorage = new CoffeeMachineStateStorageMock();
        CoffeeMachineConfigurationStorage coffeeMachineConfigurationStorage = new CoffeeMachineConfigurationStorageMock();
        CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator = new CoffeeMachinesStatusCalculator();

        String coffeeMachineId = "#1";

        CoffeeMachineState expected = CoffeeMachineState.builder()
                .build();

        //when
        CoffeeMachinesStatusService coffeeMachinesStatusService = new CoffeeMachinesStatusService(
                coffeeMachineStateStorage,
                coffeeMachineConfigurationStorage,
                coffeeMachinesStatusCalculator);


        CoffeeMachineState actual = coffeeMachinesStatusService.getCoffeeMachinesStatus(coffeeMachineId);

        //then
        Assertions.assertEquals(expected, actual);
    }



    private static class CoffeeMachineStateStorageMock implements CoffeeMachineStateStorage {

        @Override
        public CoffeeMachineStateEntity find(String coffeeMachineId) {
            return null;
        }

        @Override
        public List<CoffeeMachineStateEntity> findAll() {
            return null;
        }

        @Override
        public void update(String coffeeMachineId, CoffeeMachineStateEntity newCoffeeMachineState) {

        }
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
}