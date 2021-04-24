package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMachineLineServiceDTO {

    private final boolean coffeeBeansFilled;
    private final boolean milkFilled;
    private final boolean trashContainerCleaned;
}
