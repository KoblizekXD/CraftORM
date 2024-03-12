package lol.koblizek.craftorm.util;

public class OrmExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler previous;

    public OrmExceptionHandler(Thread.UncaughtExceptionHandler previous) {
        this.previous = previous;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        previous.uncaughtException(t, e);
    }

    public static void set() {
        Thread.setDefaultUncaughtExceptionHandler(new OrmExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()));
    }
}
