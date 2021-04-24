package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
    public void update(CoffeeMachineStateEntity coffeeMachineState) {
        if (null == coffeeMachinesStates.get(coffeeMachineState.getCoffeeMachineId())) {

            String errorMessage = "State for coffee machine with id " + coffeeMachineState.getCoffeeMachineId() + " absent";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        coffeeMachinesStates.put(coffeeMachineState.getCoffeeMachineId(), coffeeMachineState);
    }

    @Override
    public void save(CoffeeMachineStateEntity newCoffeeMachineState) {
        if (null != coffeeMachinesStates.get(newCoffeeMachineState.getCoffeeMachineId())) {

            String errorMessage = "State for coffee machine with id " + newCoffeeMachineState.getCoffeeMachineId() + " already exist";
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        coffeeMachinesStates.put(newCoffeeMachineState.getCoffeeMachineId(), newCoffeeMachineState);
    }

    @Override
    public void removeAll() {
        coffeeMachinesStates.clear();
    }
}
