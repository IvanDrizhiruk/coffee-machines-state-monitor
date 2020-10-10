package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateExtended;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.CoffeeMachineStateStorage;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeMachinesStatusService {

    private final CoffeeMachineStateStorage coffeeMachineStateStorage;
    private final CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator;

    @Autowired
    public CoffeeMachinesStatusService(
            CoffeeMachineStateStorage coffeeMachineStateStorage,
            CoffeeMachinesStatusCalculator coffeeMachinesStatusCalculator) {
        this.coffeeMachineStateStorage = coffeeMachineStateStorage;
        this.coffeeMachinesStatusCalculator = coffeeMachinesStatusCalculator;
    }

    public List<CoffeeMachineStateExtended> getAllCoffeeMachinesStatus() {
        List<CoffeeMachineState> coffeeMachineStates = coffeeMachineStateStorage.findAll();

        return coffeeMachineStates.stream()
                .map(coffeeMachineState -> coffeeMachinesStatusCalculator.calculate(coffeeMachineState))
                .collect(Collectors.toList());
    }

    public CoffeeMachineStateExtended getCoffeeMachinesStatus(String coffeeMachineId) {
        CoffeeMachineState coffeeMachineState = coffeeMachineStateStorage.find(coffeeMachineId);

        CoffeeMachineStateExtended coffeeMachineStates = coffeeMachinesStatusCalculator.calculate(coffeeMachineState);

        return coffeeMachineStates;
    }
}
