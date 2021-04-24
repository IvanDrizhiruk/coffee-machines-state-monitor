package ua.dp.sys.gears.core.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Distance {

    private final String unit;
    private final BigDecimal value;
}
