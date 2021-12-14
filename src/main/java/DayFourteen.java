import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DayFourteen extends Day<BigInteger> {

    public DayFourteen() {
        super("14");
    }

    private Polymer getTestInput() {
        String test_input = """
                NNCB
                                
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C
                """;
        String[] template_and_pairs = test_input.split("\n\n");
        return new Polymer(template_and_pairs[0], template_and_pairs[1]);
    }

    private Polymer getInput() {
        Polymer polymer = null;
        try {
            File file = new File("src/main/resources/DayFourteen.txt");
            Scanner scanner = new Scanner(file).useDelimiter("\r\n\r\n");
            polymer = new Polymer(scanner.next(), scanner.next());
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return polymer;
    }

    private void runPolymerInsertionStep(Polymer polymer) {
        // Get a list of the changes as they happen simultaneously.
        Map<String, BigInteger> pair_changes = new HashMap<>();
        for (String pair : polymer.pair_quantity.keySet()) {
            BigInteger new_quantity = polymer.pair_quantity.get(pair);
            if (new_quantity.equals(BigInteger.ZERO)) {
                continue; // Nothing to add for this pair.
            }
            // Count the new material.
            Character to_insert = polymer.map.get(pair);
            changeMap(polymer.quantity, to_insert, new_quantity);
            // Remove previous pairs.
            changeMap(pair_changes, pair, BigInteger.ZERO.subtract(new_quantity));
            // Add new pairs.
            String first = pair.substring(0, 1) + to_insert;
            String second = polymer.map.get(pair) + pair.substring(1, 2);
            changeMap(pair_changes, first, new_quantity);
            changeMap(pair_changes, second, new_quantity);
        }

        // Update the polymer.
        for (String pair : pair_changes.keySet()) {
            changeMap(polymer.pair_quantity, pair, pair_changes.get(pair));
        }
    }

    private void changeMap(Map<String, BigInteger> map, String key, BigInteger value_to_add) {
        map.put(key, map.getOrDefault(key, BigInteger.ZERO).add(value_to_add));
    }

    private void changeMap(Map<Character, BigInteger> map, Character key, BigInteger value_to_add) {
        map.put(key, map.getOrDefault(key, BigInteger.ZERO).add(value_to_add));
    }

    private BigInteger runPolymerCreation(Polymer polymer, Integer number_of_iterations) {
        for (int i = 0; i < number_of_iterations; i++) {
            runPolymerInsertionStep(polymer);
        }

        BigInteger max = BigInteger.ZERO;
        BigInteger min = polymer.quantity.values().iterator().next();
        for (Character ch : polymer.quantity.keySet()) {
            BigInteger quantity = polymer.quantity.get(ch);
            min = quantity.min(min);
            max = quantity.max(max);
        }
        return max.subtract(min);
    }

    public void runSolutionOneTest() {
        Polymer polymer = getTestInput();
        BigInteger max_minus_min = runPolymerCreation(polymer, 10);
    }

    public void runSolutionOne() {
        Polymer polymer = getInput();
        solution_one = runPolymerCreation(polymer, 10);
    }

    public void runSolutionTwoTest() {
        Polymer polymer = getTestInput();
        BigInteger max_minus_min = runPolymerCreation(polymer, 40);
    }

    public void runSolutionTwo() {
        Polymer polymer = getInput();
        solution_two = runPolymerCreation(polymer, 40);
    }


}
