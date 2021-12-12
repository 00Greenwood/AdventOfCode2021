import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayZero extends Day<Integer> {

    public DayZero() {
        super("0");
    }

    private Vector<Integer> getTestInput() {
        String test_input = "1";
        Vector<Integer> inputs = new Vector<>();
        inputs.add(Integer.valueOf(test_input));
        return inputs;
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
