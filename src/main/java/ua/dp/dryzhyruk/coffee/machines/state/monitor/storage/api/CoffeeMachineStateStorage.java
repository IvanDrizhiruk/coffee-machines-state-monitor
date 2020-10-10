package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

public interface CoffeeMachineStateStorage {

    CoffeeMachineState find(String coffeeMachineId);

    void update(String coffeeMachineId, CoffeeMachineState newCoffeeMachineState);
}
