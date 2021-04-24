package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;

import java.util.List;

public interface CoffeeMachineConfigurationStorage {

    CoffeeMachineConfigurationEntity find(String coffeeMachineId);

    List<CoffeeMachineConfigurationEntity> findAll();

    void save(CoffeeMachineConfigurationEntity configuration);

    void removeAll();
}
