import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class DayFive extends Day<Integer> {

    public DayFive() {
        super("5");
    }

    private Vector<Line> getTestInput() {
        String test_input = """
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2
                """;
        Vector<Line> lines = new Vector<>();
        for (String line : test_input.split("\n")) {
            lines.add(new Line(line));
        }
        return lines;
    }

    private Vector<Line> getInput() {
        Vector<Line> lines = new Vector<>();
        try {
            File file = new File("src/main/resources/DayFive.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(new Line(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }

    private Integer countIntersections(Vector<Line> lines, Boolean consider_diagonal) {
        Map<Integer, Map<Integer, Integer>> sea_floor = new HashMap<>();
        for (Line line : lines) {
            if (!line.isVerticalOrHorizontal() && !consider_diagonal) {
                continue;
            }
            for (Pair<Integer, Integer> point : line.getPoints()) {
                sea_floor.putIfAbsent(point.first, new HashMap<>());
                sea_floor.get(point.first).putIfAbsent(point.second, 0);
                sea_floor.get(point.first).put(point.second, sea_floor.get(point.first).get(point.second) + 1);
            }
        }

        Integer count = 0;
        for (Integer first_key : sea_floor.keySet()) {
            for (Integer second_key : sea_floor.get(first_key).keySet()) {
                if (sea_floor.get(first_key).get(second_key) > 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public void runSolutionOneTest() {
        Integer number_of_intersections = countIntersections(getTestInput(), false);
    }

    public void runSolutionOne() {
        solution_one = countIntersections(getInput(), false);
    }

    public void runSolutionTwoTest() {
        Integer number_of_intersections = countIntersections(getTestInput(), true);
    }

    public void runSolutionTwo() {
        solution_two = countIntersections(getInput(), true);
    }


}
