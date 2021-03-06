package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Day3 extends Day<Integer> {

    public Day3() {
        super("3");
    }

    private Vector<String> getTestInput() {
        String test_input = """
                00100
                11110
                10110
                10111
                10101
                01111
                00111
                11100
                10000
                11001
                00010
                01010
                """;
        Vector<String> binary = new Vector<>();
        for (String line : test_input.split("\n")) {
            binary.add(line);
        }
        return binary;
    }

    private Vector<String> getInput() {
        Vector<String> binary = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                binary.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return binary;
    }

    private Integer calculatePowerLevel(Vector<String> binary) {
        Vector<Integer> common_bits = new Vector<>();

        for (int i = 0; i < binary.firstElement().length(); i++) {
            Integer frequency = 0;
            for (String bits : binary) {
                switch (bits.charAt(i)) {
                    case '1' -> frequency++;
                    case '0' -> frequency--;
                }
            }
            common_bits.add(frequency);
        }
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for (Integer common_bit : common_bits) {
            gamma.append(common_bit > 0 ? '1' : '0');
            epsilon.append(common_bit > 0 ? '0' : '1');
        }
        return Integer.parseInt(String.valueOf(gamma), 2) * Integer.parseInt(String.valueOf(epsilon), 2);
    }

    private Integer calculate02Level(Vector<String> binary) {
        for (int i = 0; i < binary.firstElement().length(); i++) {
            Integer frequency = 0;
            for (String bits : binary) {
                switch (bits.charAt(i)) {
                    case '1' -> frequency++;
                    case '0' -> frequency--;
                }
            }
            final char common_bit = frequency >= 0 ? '1' : '0';
            final int index = i;
            binary.removeIf((bits) -> (bits.charAt(index) != common_bit));

            if (binary.size() == 1) break;
        }
        return Integer.parseInt(binary.firstElement(), 2);
    }

    private Integer calculateC02Level(Vector<String> binary) {
        for (int i = 0; i < binary.firstElement().length(); i++) {
            Integer frequency = 0;
            for (String bits : binary) {
                switch (bits.charAt(i)) {
                    case '1' -> frequency++;
                    case '0' -> frequency--;
                }
            }
            final char common_bit = frequency >= 0 ? '0' : '1';
            final int index = i;
            binary.removeIf((bits) -> (bits.charAt(index) != common_bit));

            if (binary.size() == 1) break;
        }
        return Integer.parseInt(binary.firstElement(), 2);
    }

    public void runSolutionOneTest() {
        Integer power_level = calculatePowerLevel(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculatePowerLevel(getInput());
    }

    public void runSolutionTwoTest() {
        Integer life_support_level = calculate02Level(getTestInput()) * calculateC02Level(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculate02Level(getInput()) * calculateC02Level(getInput());
    }


}
