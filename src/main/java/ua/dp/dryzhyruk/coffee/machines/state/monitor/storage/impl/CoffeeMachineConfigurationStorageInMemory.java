package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.impl;

import org.springframework.stereotype.Repository;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfigurationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoffeeMachineConfigurationStorageInMemory implements CoffeeMachineConfigurationStorage {

    private final Map<String, CoffeeMachineConfigurationEntity> coffeeMachinesConfigurations = new ConcurrentHashMap<>();

    public CoffeeMachineConfigurationStorageInMemory() {
        //TODO load from json file
    }

    @Override
    public CoffeeMachineConfigurationEntity find(String coffeeMachineId) {
        return coffeeMachinesConfigurations.get(coffeeMachineId);
    }

    @Override
    public List<CoffeeMachineConfigurationEntity> findAll() {
        return new ArrayList<>(coffeeMachinesConfigurations.values());
    }

    @Override
    public void save(CoffeeMachineConfigurationEntity configuration) {
        coffeeMachinesConfigurations.put(configuration.getCoffeeMachineId(), configuration);
    }

    @Override
    public void removeAll() {
        coffeeMachinesConfigurations.clear();
    }
}
