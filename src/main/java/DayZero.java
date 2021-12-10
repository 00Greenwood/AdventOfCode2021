import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayZero extends Day<Integer> {

    public DayZero() {
        super("0");
    }

    private Vector<Integer> getTestInput() {
        Vector<Integer> test_input = new Vector<>();
        test_input.add(1);
        return test_input;
    }

    private Vector<Integer>  getInput() {
        Vector<Integer> input = new Vector<>();
        try {
            File file = new File("src/main/resources/DayZero.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                input.add(Integer.valueOf(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return input;
    }

    public void runSolutionOneTest() {
        Integer test = getTestInput().get(0);
    }

    public void runSolutionOne() {
        solution_one = getInput().get(0);
    }

    public void runSolutionTwoTest() {
        Integer test = getTestInput().get(0);
    }

    public void runSolutionTwo() {
        solution_two = getInput().get(0);
    }


}
