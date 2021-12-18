package Days;

import Utility.SnailNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Vector;

public class Day18 extends Day<BigInteger> {

    public Day18() {
        super("18");
    }

    private Vector<SnailNumber> getTestInput() {
        String test_input = """
                [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                """;
        Vector<SnailNumber> snailNumbers = new Vector<>();
        for (String line : test_input.split("\n")) {
            snailNumbers.add(new SnailNumber(null, line));
        }
        return snailNumbers;
    }

    private Vector<Integer> getInput() {
        Vector<Integer> input = new Vector<>();
        try {
            File file = new File("src/main/resources/Day" + id + ".txt");
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

    private SnailNumber addSnailNumbers(Vector<SnailNumber> snailNumbers) {
        SnailNumber sum = new SnailNumber(snailNumbers.get(0), snailNumbers.get(1));
        System.out.println(sum.toString(0));
        sum.reduce();
        for (int i = 2; i < snailNumbers.size(); i++) {
            sum = new SnailNumber(sum, snailNumbers.get(i));
            sum.reduce();
        }
        return sum;
    }

    public void runSolutionOneTest() {
        SnailNumber sum = addSnailNumbers(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = BigInteger.ZERO;//getInput().get(0);
    }

    public void runSolutionTwoTest() {
        Integer test = 0;//getTestInput().get(0);
    }

    public void runSolutionTwo() {
        solution_two = BigInteger.ZERO;//getInput().get(0);
    }


}
