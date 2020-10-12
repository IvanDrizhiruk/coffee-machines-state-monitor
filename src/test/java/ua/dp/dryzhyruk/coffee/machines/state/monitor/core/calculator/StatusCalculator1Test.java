package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

class StatusCalculator1Test {

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
}