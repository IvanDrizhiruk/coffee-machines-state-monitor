package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CoffeeMachineStateEntity {

    private final String coffeeMachineId;

    private final int coffeeBeansLeftForNPortions;
    private final int milkLeftForNPortions;
    private final int placeInTrashContainerLeftForNPortions;
}
