package ua.dp.dryzhyruk.coffee.machines.state.monitor.core.calculator;

import ua.dp.dryzhyruk.coffee.machines.state.monitor.core.model.Status;

public class StatusCalculator {

    private final double warningNPortionLeft;
    private final double criticalNPortionLeft;

    public StatusCalculator(int warningNPortionLeft, int criticalNPortionLeft) {
        this.warningNPortionLeft = warningNPortionLeft;
        this.criticalNPortionLeft = criticalNPortionLeft;
    }

    public Status calculateState(int nPortionsLeft) {
        checkIsValidNPortionsLeft(nPortionsLeft);

        if (nPortionsLeft < criticalNPortionLeft) {
            return Status.CRITICAL;
        } else if (nPortionsLeft < warningNPortionLeft) {
            return Status.WARNING;
        } else {
            return Status.OK;
        }
    }

    private void checkIsValidNPortionsLeft(int nPortionsLeft) {
        if (nPortionsLeft < 0) {
            throw new IllegalArgumentException("nPortionsLeft can not be negative");
        }
    }
}
