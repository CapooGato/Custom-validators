package psk.pd.lab2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    private counter cnt;

    @BeforeEach
    void setUp() {
        cnt = new counter();
    }

    @Test
    void shouldStartFromOne() {
        assertEquals(1, cnt.increment());
    }

    @Test
    void shouldIncrementSequentially() {
        assertEquals(1, cnt.increment());
        assertEquals(2, cnt.increment());
        assertEquals(3, cnt.increment());
    }

    @Test
    void shouldHandleMultipleIncrements() {
        for (int i = 1; i <= 100; i++) {
            assertEquals(i, cnt.increment());
        }
    }

    @Test
    void shouldBeThreadSafe() throws InterruptedException {
        int threadCount = 10;
        int incrementsPerThread = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    cnt.increment();
                }
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        assertEquals(threadCount * incrementsPerThread, cnt.increment() - 1);
    }
}
