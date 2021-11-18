import java.util.*;

public class Main {
    public static void main(String[] args) {
        Vector<Thread> threads = new Vector<Thread>();
        // Create a thread for each day.
        threads.add(new Thread(new DayZero()));
        // Start each thread.
        for (Thread thread: threads) {
            thread.start();
        }
        // Wait for each thread to finish.
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
