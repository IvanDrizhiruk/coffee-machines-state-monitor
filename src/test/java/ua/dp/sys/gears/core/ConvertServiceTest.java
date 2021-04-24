package ua.dp.sys.gears.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.sys.gears.core.model.ConvertingConfiguration;
import ua.dp.sys.gears.core.model.Distance;

import java.math.BigDecimal;
import java.util.HashMap;


class ConvertServiceTest {


    @Test
    public void shouldConvertIntegerNumbers() {
        //given
        int scale = 2;
        HashMap<String, BigDecimal> unitsInMillimetres = new HashMap<>();
        unitsInMillimetres.put("sm", BigDecimal.valueOf(10));
        unitsInMillimetres.put("m", BigDecimal.valueOf(1000));

        ConvertingConfiguration configuration = new ConvertingConfiguration(scale, unitsInMillimetres);

        Distance distanceForConvention = new Distance("m", BigDecimal.valueOf(0.5));
        String convertTo = "sm";

        Distance expected = new Distance("sm", new BigDecimal("50.00"));

        //when
        ConvertService convertService = new ConvertService(configuration);
        Distance actual = convertService.convert(distanceForConvention, convertTo);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertDecimalNumbers() {
        //given
        int scale = 2;
        HashMap<String, BigDecimal> unitsInMillimetres = new HashMap<>();
        unitsInMillimetres.put("km", BigDecimal.valueOf(10 * 100 * 1000));
        unitsInMillimetres.put("yd", BigDecimal.valueOf(914.4));

        ConvertingConfiguration configuration = new ConvertingConfiguration(scale, unitsInMillimetres);

        Distance distanceForConvention = new Distance("yd", BigDecimal.valueOf(102));
        String convertTo = "km";

        Distance expected = new Distance("km", new BigDecimal("0.09"));

        //when
        ConvertService convertService = new ConvertService(configuration);
        Distance actual = convertService.convert(distanceForConvention, convertTo);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void exceptionShouldHappenedInCaseUnsupportedUnitInDistance() {
        //given
        int scale = 2;
        HashMap<String, BigDecimal> unitsInMillimetres = new HashMap<>();
        unitsInMillimetres.put("km", BigDecimal.valueOf(10 * 100 * 1000));

        ConvertingConfiguration configuration = new ConvertingConfiguration(scale, unitsInMillimetres);

        Distance distanceForConvention = new Distance("yd", BigDecimal.valueOf(102));
        String convertTo = "km";

        Distance expected = new Distance("km", new BigDecimal("0.09"));

        //then exception
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    //when
                    ConvertService convertService = new ConvertService(configuration);
                    Distance actual = convertService.convert(distanceForConvention, convertTo);
                });
    }

    @Test
    public void exceptionShouldHappenedInCaseUnsupportedUnitInConvertTo() {
        //given
        int scale = 2;
        HashMap<String, BigDecimal> unitsInMillimetres = new HashMap<>();
        unitsInMillimetres.put("yd", BigDecimal.valueOf(914.4));

        ConvertingConfiguration configuration = new ConvertingConfiguration(scale, unitsInMillimetres);

        Distance distanceForConvention = new Distance("yd", BigDecimal.valueOf(102));
        String convertTo = "km";

        Distance expected = new Distance("km", new BigDecimal("0.09"));

        //then exception
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    //when
                    ConvertService convertService = new ConvertService(configuration);
                    Distance actual = convertService.convert(distanceForConvention, convertTo);
                });
    }
}