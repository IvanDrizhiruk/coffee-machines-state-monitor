package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto;

import lombok.Builder;
import lombok.Value;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

import java.time.LocalDateTime;

@Value
@Builder
public class CoffeeMachineStateDTO {

    private final String coffeeMachineId;

    private final int coffeeBeansLeftForNPortions;
    private final Status coffeeBeansStatus;

    private final int milkLeftForNPortions;
    private final Status milkStatus;

    private final int placeInTrashContainerLeftForNPortions;
    private final Status placesInTrashContainerStatus;

    private final LocalDateTime timeOfCalculation;
}
