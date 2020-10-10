package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;

public interface CoffeeMachineConfigurationStorage {

    CoffeeMachineConfiguration find(String coffeeMachineId);
}
