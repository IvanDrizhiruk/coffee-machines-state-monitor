package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CoffeeCup {

    private int coffeePortions;
    private boolean withMilk;
}