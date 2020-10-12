package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.impl;

import org.springframework.stereotype.Repository;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoffeeMachineStateStorageInMemory implements CoffeeMachineStateStorage {

    private final Map<String, CoffeeMachineStateEntity> coffeeMachinesStates = new ConcurrentHashMap<>();

    @Override
    public CoffeeMachineStateEntity find(String coffeeMachineId) {
        return coffeeMachinesStates.get(coffeeMachineId);
    }

    @Override
    public List<CoffeeMachineStateEntity> findAll() {
        return new ArrayList<>(coffeeMachinesStates.values());
    }

    @Override
    public void update(String coffeeMachineId, CoffeeMachineStateEntity coffeeMachineState) {
        coffeeMachinesStates.put(coffeeMachineId, coffeeMachineState);
    }
}
