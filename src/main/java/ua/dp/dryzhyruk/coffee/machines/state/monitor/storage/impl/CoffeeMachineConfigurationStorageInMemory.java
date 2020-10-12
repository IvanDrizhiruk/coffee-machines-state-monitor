package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.impl;

import org.springframework.stereotype.Repository;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineConfigurationStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoffeeMachineConfigurationStorageInMemory implements CoffeeMachineConfigurationStorage {

    private final Map<String, CoffeeMachineConfiguration> coffeeMachinesConfigurations = new ConcurrentHashMap<>();

    public CoffeeMachineConfigurationStorageInMemory() {
        //TODO load from json file
    }

    @Override
    public CoffeeMachineConfiguration find(String coffeeMachineId) {
        return coffeeMachinesConfigurations.get(coffeeMachineId);
    }

    @Override
    public List<CoffeeMachineConfiguration> findAll() {
        return new ArrayList<>(coffeeMachinesConfigurations.values());
    }
}
