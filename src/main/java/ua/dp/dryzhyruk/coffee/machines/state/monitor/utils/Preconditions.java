package ua.dp.dryzhyruk.coffee.machines.state.monitor.utils;

public class Preconditions {

    public static <T> void requireNonNull(T object) {
        if (null == object) {
            throw new IllegalArgumentException();
        }
    }
}
