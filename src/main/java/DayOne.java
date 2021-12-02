import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayOne extends Day {

    public DayOne() {
        super("1");
    }

    private Vector<Integer> getTestInput() {
        String test_input = "199\n200\n208\n210\n200\n207\n240\n269\n260\n263";

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
        Vector<Integer> depths = getTestInput();
        calculateIncreases(depths);
    }

    public void runSolutionOne() {
        Vector<Integer> depths = getInput();
        solution_one = String.valueOf(calculateIncreases(depths));
    }

    public void runSolutionTwoTest() {
        Vector<Integer> depths = getTestInput();
        calculateSumIncreases(depths);
    }

    public void runSolutionTwo() {
        Vector<Integer> depths = getInput();
        solution_two = String.valueOf(calculateSumIncreases(depths));
    }


}
