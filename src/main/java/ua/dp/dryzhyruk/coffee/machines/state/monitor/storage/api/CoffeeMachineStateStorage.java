package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineStateEntity;

import java.util.List;

public interface CoffeeMachineStateStorage {

    CoffeeMachineStateEntity find(String coffeeMachineId);

    List<CoffeeMachineStateEntity> findAll();

    void update(CoffeeMachineStateEntity newCoffeeMachineState);

    void save(CoffeeMachineStateEntity newCoffeeMachineState);

    void removeAll();
}
