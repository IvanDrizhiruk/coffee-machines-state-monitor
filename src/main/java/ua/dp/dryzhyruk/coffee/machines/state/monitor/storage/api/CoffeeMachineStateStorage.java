package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

import java.util.List;

public interface CoffeeMachineStateStorage {

    CoffeeMachineState find(String coffeeMachineId);

    List<CoffeeMachineState> findAll();

    void update(String coffeeMachineId, CoffeeMachineState newCoffeeMachineState);
}
