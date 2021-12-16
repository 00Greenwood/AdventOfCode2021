package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Day7 extends Day<Integer> {

    public Day7() {
        super("7");
    }

    private Vector<Integer> getTestInput() {
        String test_input = "16,1,2,0,4,2,7,1,2,14";
        Vector<Integer> crabs = new Vector<>();
        for (String string : test_input.split(",")) {
            crabs.add(Integer.valueOf(string));
        }
        return crabs;
    }

    private Vector<Integer> getInput() {
        Vector<Integer> crabs = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file).useDelimiter(",");
            while (scanner.hasNext()) {
                crabs.add(Integer.valueOf(scanner.next()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return crabs;
    }

    private Integer calculateMinimumFuelConstant(Vector<Integer> crabs) {
        Integer average_position = -1;
        Integer total_fuel = Integer.MAX_VALUE;
        while (++average_position < Collections.max(crabs)) {
            int new_total_fuel = 0;
            for (Integer position : crabs) {
                new_total_fuel += Math.abs(average_position - position);
            }
            if (new_total_fuel > total_fuel) {
                break;
            }
            total_fuel = new_total_fuel;
        }
        return total_fuel;
    }

    private Integer calculateMinimumFuelLinear(Vector<Integer> crabs) {
        Integer average_position = -1;
        int total_fuel = Integer.MAX_VALUE;
        while (++average_position < Collections.max(crabs)) {
            int new_total_fuel = 0;
            for (Integer position : crabs) {
                int difference = Math.abs(average_position - position);
                new_total_fuel += 0.5 * difference * (difference + 1);
            }
            if (new_total_fuel > total_fuel) {
                break;
            }
            total_fuel = new_total_fuel;
        }
        return total_fuel;
    }

    public void runSolutionOneTest() {
        Integer total_fuel = calculateMinimumFuelConstant(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculateMinimumFuelConstant(getInput());
    }

    public void runSolutionTwoTest() {
        Integer total_fuel = calculateMinimumFuelLinear(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculateMinimumFuelLinear(getInput());
    }


}
