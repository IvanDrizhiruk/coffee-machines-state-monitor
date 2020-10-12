package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeMachineConfiguration {

    private final String coffeeMachineId;

    private final int maxNumberCoffeeBeansPortions;
    private final double coffeeBeansWarningLevel;
    private final double coffeeBeansCriticalLevel;

    private final int maxNumberMilkPortions;
    private final double milkWarningLevel;
    private final double milkCriticalLevel;

    private final int maxNumberPlacesInTrashContainer;
    private final double placesInTrashContainerWarningLevel;
    private final double PlacesInTrashContainerCriticalLevel;
}
