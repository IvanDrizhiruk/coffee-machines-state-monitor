package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

class N4_Cause_Effect_StatusCalculatorTest {

    @Test
    public void statusShouldBeWarningIfCriticalLevelIsZero() {
        //given

        int warningLevel = 10;
        int criticalLevel = 0;
        int nPortionsLeft = 0;

        Status expected = Status.WARNING;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void statusShouldBeOkIfAllLevelsIsZero() {
        //given

        int warningLevel = 0;
        int criticalLevel = 0;
        int nPortionsLeft = 0;

        Status expected = Status.OK;

        //when
        Status actual = new StatusCalculator(warningLevel, criticalLevel)
                .calculateState(nPortionsLeft);

        //then
        Assertions.assertEquals(expected, actual);
    }
}