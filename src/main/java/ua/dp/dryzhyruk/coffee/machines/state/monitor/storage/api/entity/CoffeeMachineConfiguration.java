package ua.dp.dryzhyruk.coffee.machines.state.monitor.storage.api.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoffeeMachineConfiguration {

    private int maxNumberCoffeeBeansPortions;
    private int maxNumberMilkPortions;
    private int maxNumberPlacesInTrashContainer;
}
