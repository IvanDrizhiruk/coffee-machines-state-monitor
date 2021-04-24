package ua.dp.sys.gears.core.model;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
public class ConvertingConfiguration {

    private final int scale;
    private final Map<String, BigDecimal> unitsInMillimetres;
}
