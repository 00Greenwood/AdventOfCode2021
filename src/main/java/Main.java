import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Create an object for each Day.
        Vector<Day> days = new Vector<Day>();
        days.add(new DayZero());
        days.add(new DayOne());
        days.add(new DayTwo());
        days.add(new DayThree());
        days.add(new DayFour());
        days.add(new DayFive());
        days.add(new DaySix());
        days.add(new DaySeven());
        days.add(new DayEight());
        days.add(new DayNine());
        days.add(new DayTen());
        days.add(new DayEleven());

        Vector<Thread> threads = new Vector<>();
        // Create a thread for each day.
        for (Day day: days) {
            Thread thread = new Thread(day);
            thread.start();
            threads.add(thread);
        }

        // Wait for each thread to finish.
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        for (Day day: days) {
            day.printSolutions();
        }
    }
}
