package ua.dp.sys.gears.parser.entities;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class RequestedDistance {

    private final String unit;
    private final BigDecimal value;
}
