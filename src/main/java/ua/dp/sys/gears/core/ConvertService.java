package ua.dp.sys.gears.core;

import ua.dp.sys.gears.core.model.ConvertingConfiguration;
import ua.dp.sys.gears.core.model.Distance;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConvertService {

    private final ConvertingConfiguration configuration;

    public ConvertService(ConvertingConfiguration configuration) {
        this.configuration = configuration;
    }

    public Distance convert(Distance distance, String convertToUnit) {
        BigDecimal distanceInMillimeters = convertToMilimeters(distance);
        BigDecimal convertedValue = convertMillimetersToUnitAndScale(distanceInMillimeters, convertToUnit);

        return new Distance(convertToUnit, convertedValue);
    }

    private BigDecimal convertToMilimeters(Distance distance) {
        BigDecimal unitInMillimeters = unitsOfMeasurementsInMillimeters(distance.getUnit());
        return unitInMillimeters.multiply(distance.getValue());
    }

    private BigDecimal convertMillimetersToUnitAndScale(BigDecimal distanceInMillimeters, String convertToUnit) {
        BigDecimal unitInMillimeters = unitsOfMeasurementsInMillimeters(convertToUnit);
        return distanceInMillimeters.divide(unitInMillimeters, configuration.getScale(), RoundingMode.HALF_DOWN);
    }

    public BigDecimal unitsOfMeasurementsInMillimeters(String unit) {
        BigDecimal unitInMillimetres = configuration.getUnitsInMillimetres().get(unit);
        if (unitInMillimetres == null) {
            throw new IllegalArgumentException("Unsupported unit : " + unit
                    + "Supported units are: " + configuration.getUnitsInMillimetres().keySet());
        }

        return unitInMillimetres;
    }
}
