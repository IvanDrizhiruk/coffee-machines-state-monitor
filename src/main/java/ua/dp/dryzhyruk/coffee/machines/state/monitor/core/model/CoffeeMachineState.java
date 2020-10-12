package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMachineState {

    private final String coffeeMachineId;

    private final int coffeeBeansLeftForNPortions;
    private final Status coffeeBeansStatus;

    private final int milkLeftForNPortions;
    private final Status milkStatus;

    private final int placeInTrashContainerLeftForNPortions;
    private final Status placesInTrashContainerStatus;
}
