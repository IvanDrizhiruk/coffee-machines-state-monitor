package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMachineConfigurationEntity {

    private final String coffeeMachineId;

    private final int maxNumberCoffeeBeansPortions;
    private final int coffeeBeansWarningNPortionLeft;
    private final int coffeeBeansCriticalNPortionLeft;

    private final int maxNumberMilkPortions;
    private final int milkWarningNPortionLeft;
    private final int milkCriticalNPortionLeft;

    private final int maxNumberPlacesInTrashContainer;
    private final int placesInTrashContainerWarningNPortionLeft;
    private final int PlacesInTrashContainerCriticalNPortionLeft;
}
