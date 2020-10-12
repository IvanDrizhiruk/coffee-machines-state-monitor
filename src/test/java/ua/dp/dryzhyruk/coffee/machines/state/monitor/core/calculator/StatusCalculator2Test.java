package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

class StatusCalculator2Test {

    @Test
    public void statusShouldBeOkIfValueLesOfAllConfigurations() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 10;

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
        int nPortionsLeft = 9;

        Status expected = Status.WARNING;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeWarningIfValueLesOfAllConfigurationsCase2() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = 3;

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
        int nPortionsLeft = 2;

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
}