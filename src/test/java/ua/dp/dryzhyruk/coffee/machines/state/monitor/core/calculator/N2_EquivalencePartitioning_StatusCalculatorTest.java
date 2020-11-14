package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

class N2_EquivalencePartitioning_StatusCalculatorTest {

    @Test
    public void statusShouldBeOkIfValueLesOfAllConfigurations() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 15;

        Status expected = Status.OK;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeWarningIfValueLesOfAllConfigurationsCase1() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 7;

        Status expected = Status.WARNING;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeCriticalIfValueLesOfAllConfigurationsCase1() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 1;

        Status expected = Status.CRITICAL;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeCriticalIfValueLesOfAllConfigurationsCase2() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 0;

        Status expected = Status.CRITICAL;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void exceptionShouldBeExceptionIfValueIsNegative() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = -3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            new StatusCalculator(warningLevel, criticalLevel)
                    .calculateState(nPortionsLeft);

            //then exception
        });
    }
}