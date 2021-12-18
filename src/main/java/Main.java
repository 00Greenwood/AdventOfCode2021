import Days.*;

import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        // Create an object for each Day.
        Vector<Day> days = new Vector<Day>();
        days.add(new Day0());
        days.add(new Day1());
        days.add(new Day2());
        days.add(new Day3());
        days.add(new Day4());
        days.add(new Day5());
        days.add(new Day6());
        days.add(new Day7());
        days.add(new Day8());
        days.add(new Day9());
        days.add(new Day10());
        days.add(new Day11());
        days.add(new Day12());
        days.add(new Day13());
        days.add(new Day14());
        days.add(new Day15());
        days.add(new Day16());
        days.add(new Day17());
        days.add(new Day18());

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
