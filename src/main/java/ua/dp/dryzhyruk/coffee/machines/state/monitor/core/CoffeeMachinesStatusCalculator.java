package ua.dp.dryzhyruk.coffee.machines.state.monitor.core;

import org.springframework.stereotype.Service;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto.CoffeeMachineStateExtended;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity.CoffeeMachineState;

@Service
public class CoffeeMachinesStatusCalculator {

    public CoffeeMachineStateExtended calculate(CoffeeMachineState coffeeMachineState) {
        return CoffeeMachineStateExtended.builder()
                .coffeeMachineName(coffeeMachineState.toString())
                .build();
    }
}
