import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

public class MyThread<T, R> implements Runnable {
    private T input;
    private R result;
    private final CountDownLatch latch;
    private Function<T, R> function;

    MyThread(CountDownLatch latch, Function function) {
        this.latch = latch;
        this.function = function;
    }

    R getResult() {
        return result;
    }

    public void run() {
        result = function.apply(input);
        latch.countDown();
    }
}
