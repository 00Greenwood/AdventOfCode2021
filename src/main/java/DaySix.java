import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class DaySix extends Day<BigInteger> {

    public DaySix() {
        super("6");
    }

    private Map<Integer, BigInteger> getTestInput() {
        String test_input = "3,4,3,1,2";
        Map<Integer, BigInteger> fish = new HashMap<>();
        for (String string : test_input.split(",")) {
            Integer number = Integer.valueOf(string);
            fish.put(number, fish.getOrDefault(number, BigInteger.ZERO).add(BigInteger.ONE));
        }
        return fish;
    }

    private Map<Integer, BigInteger> getInput() {
        Map<Integer, BigInteger> fish = new HashMap<>();
        try {
            File file = new File("src/main/resources/DaySix.txt");
            Scanner scanner = new Scanner(file).useDelimiter(",");
            while (scanner.hasNext()) {
                Integer number = Integer.valueOf(scanner.next());
                fish.put(number, fish.getOrDefault(number, BigInteger.ZERO).add(BigInteger.ONE));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fish;
    }

    private BigInteger runSimulation(Map<Integer, BigInteger> fish, Integer number_of_days) {
        for (int day = 1; day <= number_of_days ; day++) {
            BigInteger number_of_new_fish = fish.getOrDefault(0,BigInteger.ZERO);
            for (int key = 1; key <= 8; key++) {
                fish.put(key - 1, fish.getOrDefault(key, BigInteger.ZERO));
            }
            fish.put(6, fish.get(6).add(number_of_new_fish));
            fish.put(8, number_of_new_fish);
        }
        BigInteger count = BigInteger.ZERO;
        for (Integer key: fish.keySet()) {
            count = count.add(fish.get(key));
        }
        return count;
    }

    public void runSolutionOneTest() {
        BigInteger number_of_fish = runSimulation(getTestInput(), 80);
    }

    public void runSolutionOne() {
        solution_one = runSimulation(getInput(), 80);
    }

    public void runSolutionTwoTest() {
        BigInteger number_of_fish = runSimulation(getTestInput(), 256);
    }

    public void runSolutionTwo() {
        solution_two = runSimulation(getInput(), 256);
    }


}
