package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusCalculator3Test {

    @Test
    public void statusShouldBeOkIfValueLesOfAllConfigurations() {
        //given

        int warningLevel = 10;
        int criticalLevel = 3;
        int nPortionsLeft = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            new StatusCalculator(warningLevel, criticalLevel)
                    .calculateState(nPortionsLeft);

            //then exception
        });
    }
}