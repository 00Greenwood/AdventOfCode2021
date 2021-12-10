import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayOne extends Day<Integer> {

    public DayOne() {
        super("1");
    }

    private Vector<Integer> getTestInput() {
        String test_input = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
        """;

        Vector<Integer> depths = new Vector<>();
        for (String number : test_input.split("\n")) {
            depths.add(Integer.valueOf(number));
        }
        return depths;
    }

    private Vector<Integer> getInput() {
        Vector<Integer> depths = new Vector<>();
        try {
            File file = new File("src/main/resources/DayOne.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                depths.add(Integer.valueOf(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return depths;
    }

    private Integer calculateIncreases(Vector<Integer> depths) {
        Integer count = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                count++;
            }
        }
        return count;
    }

    private Integer calculateSumIncreases(Vector<Integer> depths) {
        Integer count = 0;
        for (int i = 0; i < depths.size() - 3; i++) {
            int first = depths.get(i) + depths.get(i + 1) + depths.get(i + 2);
            int second = depths.get(i + 1) + depths.get(i + 2) + depths.get(i + 3);
            if (second > first) {
                count++;
            }
        }
        return count;
    }

    public void runSolutionOneTest() {
        Integer number_of_increases = calculateIncreases(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculateIncreases(getInput());
    }

    public void runSolutionTwoTest() {
        Integer number_of_sum_increases = calculateSumIncreases(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculateSumIncreases(getInput());
    }


}
