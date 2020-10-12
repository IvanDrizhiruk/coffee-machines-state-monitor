package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;

import java.util.List;

public interface CoffeeMachineConfigurationStorage {

    CoffeeMachineConfiguration find(String coffeeMachineId);

    List<CoffeeMachineConfiguration> findAll();
}
