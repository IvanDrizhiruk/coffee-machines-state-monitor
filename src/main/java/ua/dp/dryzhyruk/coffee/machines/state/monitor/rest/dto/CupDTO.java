package ua.dp.dryzhyruk.coffee.machines.state.monitor.rest.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CupDTO {

    private final int coffeePortions;
    private final  boolean withMilk;
}