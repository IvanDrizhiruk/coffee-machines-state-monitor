package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cup {

    private int coffeePortions;
    private boolean withMilk;
}
