import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayTwo extends Day<Integer> {

    public DayTwo() {
        super("2");
    }

    private Vector<Pair<String, Integer>> getTestInput() {
        String test_input = """
                forward 5
                down 5
                forward 8
                up 3
                down 8
                forward 2
                """;
        Vector<Pair<String, Integer>> directions = new Vector<>();
        for (String direction : test_input.split("\n")) {
            String[] split_direction = direction.split(" ");
            directions.add(new Pair<>(split_direction[0], Integer.valueOf(split_direction[1])));
        }
        return directions;
    }

    private Vector<Pair<String, Integer>> getInput() {
        Vector<Pair<String, Integer>> directions = new Vector<>();
        try {
            File file = new File("src/main/resources/DayTwo.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] split_direction = scanner.nextLine().split(" ");
                directions.add(new Pair<>(split_direction[0], Integer.valueOf(split_direction[1])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return directions;
    }

    private Integer calculateDistance(Vector<Pair<String, Integer>> directions) {
        Pair<Integer, Integer> distance = new Pair<>(0, 0);
        for (Pair<String, Integer> direction : directions) {
            switch (direction.first) {
                case "forward" -> distance.first += direction.second;
                case "up" -> distance.second -= direction.second;
                case "down" -> distance.second += direction.second;
            }
        }
        return distance.first * distance.second;
    }

    private Integer calculateDistanceWithAim(Vector<Pair<String, Integer>> directions) {
        Pair<Integer, Integer> distance = new Pair<>(0, 0);
        Integer aim = 0;
        for (Pair<String, Integer> direction : directions) {
            switch (direction.first) {
                case "forward" -> {
                    distance.first += direction.second;
                    distance.second += aim * direction.second;
                }
                case "up" -> aim -= direction.second;
                case "down" -> aim += direction.second;
            }
        }
        return distance.first * distance.second;
    }

    public void runSolutionOneTest() {
        Integer distance = calculateDistance(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculateDistance(getInput());
    }

    public void runSolutionTwoTest() {
        Integer distance_with_aim = calculateDistanceWithAim(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculateDistanceWithAim(getInput());
    }


}
