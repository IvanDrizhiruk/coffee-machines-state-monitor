package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.impl;

import org.springframework.stereotype.Repository;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CoffeeMachineStateStorageInMemory implements CoffeeMachineStateStorage {

    private final Map<String, CoffeeMachineState> coffeeMachineStates = new ConcurrentHashMap<>();

    @Override
    public CoffeeMachineState find(String coffeeMachineId) {
        return coffeeMachineStates.get(coffeeMachineId);
    }

    @Override
    public List<CoffeeMachineState> findAll() {
        return new ArrayList<>(coffeeMachineStates.values());
    }

    @Override
    public void update(String coffeeMachineId, CoffeeMachineState coffeeMachineState) {
        coffeeMachineStates.put(coffeeMachineId, coffeeMachineState);
    }
}
