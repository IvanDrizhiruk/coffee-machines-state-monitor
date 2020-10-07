package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMachinesStatus {

    private String coffeeMachineName;
}
