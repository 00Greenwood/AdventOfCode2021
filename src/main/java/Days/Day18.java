package Days;

import Utility.SnailNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Vector;

public class Day18 extends Day<Integer> {

    public Day18() {
        super("18");
    }

    private Vector<SnailNumber> getTestInput() {
        String test_input = """
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
                """;
        Vector<SnailNumber> snailNumbers = new Vector<>();
        for (String line : test_input.split("\n")) {
            snailNumbers.add(new SnailNumber(null, line));
        }
        return snailNumbers;
    }

    private Vector<SnailNumber> getInput() {
        Vector<SnailNumber> snailNumbers = new Vector<>();
        try {
            File file = new File("src/main/resources/Day" + id + ".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                snailNumbers.add(new SnailNumber(null, scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return snailNumbers;
    }

    private SnailNumber addSnailNumbers(Vector<SnailNumber> snailNumbers) {
        SnailNumber sum = new SnailNumber(snailNumbers.get(0), snailNumbers.get(1));
        sum.reduce();
        for (int i = 2; i < snailNumbers.size(); i++) {
            sum = new SnailNumber(sum, snailNumbers.get(i));
            sum.reduce();
        }
        return sum;
    }

    private Integer findMaximumMagnitude(Vector<SnailNumber> snailNumbers) {
        int maximum_magnitude = 0;
        for (int i = 0; i < snailNumbers.size(); i++) {
            for (int j = i + 1; j < snailNumbers.size(); j++) {
                // x + y
                SnailNumber sum = new SnailNumber(snailNumbers.get(i).copy(), snailNumbers.get(j).copy());
                sum.reduce();
                maximum_magnitude = Math.max(maximum_magnitude, sum.magnitude());
                // y + x
                sum = new SnailNumber(snailNumbers.get(j).copy(), snailNumbers.get(i).copy());
                sum.reduce();
                maximum_magnitude = Math.max(maximum_magnitude, sum.magnitude());
            }
        }
        return maximum_magnitude;
    }

    public void runSolutionOneTest() {
        SnailNumber sum = addSnailNumbers(getTestInput());
        Integer magnitude = sum.magnitude();
    }

    public void runSolutionOne() {
        SnailNumber sum = addSnailNumbers(getInput());
        solution_one = sum.magnitude();
    }

    public void runSolutionTwoTest() {
        Integer maximum_magnitude = findMaximumMagnitude(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = findMaximumMagnitude(getInput());
    }


}
