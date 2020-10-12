package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

public class StateCalculator {

    private final int maxNumber;
    private final double warningLevel;
    private final double criticalLevel;

    public StateCalculator(int maxNumber, double warningLevel, double criticalLevel) {

        this.maxNumber = maxNumber;
        this.warningLevel = warningLevel;
        this.criticalLevel = criticalLevel;
    }

    public Status calculateState(int nPortionsLeft) {
        double percentage = 1 - (nPortionsLeft * 1.0d) / maxNumber;

        if (percentage > criticalLevel) {
            return Status.CRITICAL;
        } else if (percentage > warningLevel) {
            return Status.WARNING;
        } else {
            return Status.OK;
        }
    }
}
