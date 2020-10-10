package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CoffeeMachineState {

    private int coffeeBeansLeftForNPortions;
    private int milkLeftForNPortions;
    private int placeInTrashContainerLeftForNPortions;
}
