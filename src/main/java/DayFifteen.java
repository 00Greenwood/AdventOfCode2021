import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class DayFifteen extends Day<Integer> {

    public DayFifteen() {
        super("15");
    }

    private Map<Integer, Map<Integer, Integer>> getTestInput() {
        String test_input = """
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581
                """;
        Map<Integer, Map<Integer, Integer>> risk_map = new HashMap<>();
        int i = 0;
        for (String row : test_input.split("\n")) {
            int j = 0;
            for (String risk_level : row.split("")) {
                risk_map.putIfAbsent(i, new HashMap<>());
                risk_map.get(i).put(j++, Integer.valueOf(risk_level));
            }
            i++;
        }
        return risk_map;
    }

    private Map<Integer, Map<Integer, Integer>> getInput() {
        Map<Integer, Map<Integer, Integer>> risk_map = new HashMap<>();
        try {
            File file = new File("src/main/resources/DayFifteen.txt");
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNextLine()) {
                int j = 0;
                for (String risk_level : scanner.nextLine().split("")) {
                    risk_map.putIfAbsent(i, new HashMap<>());
                    risk_map.get(i).put(j++, Integer.valueOf(risk_level));
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return risk_map;
    }

    private Integer calculateRisk(Map<Integer, Map<Integer, Integer>> risk_map) {
        Map<Integer, Map<Integer, Integer>> total_risk_map = new HashMap<>();
        Queue<Pair<Integer, Integer>> points_checked = new LinkedBlockingDeque<>();
        checkAndAddToQueue(risk_map, total_risk_map, new Pair<>(0, 0), -risk_map.get(0).get(0), points_checked);

        while (points_checked.size() > 0) {
            Pair<Integer, Integer> point = points_checked.poll();
            Integer x = point.first;
            Integer y = point.second;
            Integer current_total_risk_level = total_risk_map.get(x).get(y);

            checkAndAddToQueue(risk_map, total_risk_map, new Pair<>(x, y + 1), current_total_risk_level, points_checked);
            checkAndAddToQueue(risk_map, total_risk_map, new Pair<>(x + 1, y), current_total_risk_level, points_checked);
            checkAndAddToQueue(risk_map, total_risk_map, new Pair<>(x, y - 1), current_total_risk_level, points_checked);
            checkAndAddToQueue(risk_map, total_risk_map, new Pair<>(x - 1, y), current_total_risk_level, points_checked);
        }

        return total_risk_map.get(total_risk_map.size() - 1).get(total_risk_map.size() - 1);
    }

    private void checkAndAddToQueue(
            Map<Integer, Map<Integer, Integer>> risk_map,
            Map<Integer, Map<Integer, Integer>> total_risk_map,
            Pair<Integer, Integer> point_to_check,
            Integer risk_level,
            Queue<Pair<Integer, Integer>> points_checked) {
        Integer x = point_to_check.first;
        Integer y = point_to_check.second;
        try {
            risk_level += risk_map.get(x).get(y);
        } catch (NullPointerException ignored) {
            return; // X or Y does not exist in the Risk Map.
        }

        total_risk_map.putIfAbsent(x, new HashMap<>());
        if (risk_level < total_risk_map.get(x).getOrDefault(y, Integer.MAX_VALUE)) {
            total_risk_map.get(x).put(y, risk_level);
            points_checked.add(point_to_check);
        }
    }

    private Map<Integer, Map<Integer, Integer>> scaleRiskMap(Map<Integer, Map<Integer, Integer>> risk_map) {
        Map<Integer, Map<Integer, Integer>> scaled_risk_map = new HashMap<>();
        int size_of_map = risk_map.size();
        for (Integer x : risk_map.keySet()) {
            for (Integer y : risk_map.get(x).keySet()) {
                for (int i = 0; i < 5; i++) {
                    scaled_risk_map.putIfAbsent(size_of_map * i + x, new HashMap<>());
                    for (int j = 0; j < 5; j++) {
                        int risk_level = risk_map.get(x).get(y) + i + j;
                        scaled_risk_map.get(size_of_map * i + x).put(size_of_map * j + y, risk_level > 9 ? risk_level - 9 : risk_level);
                    }
                }
            }
        }
        return scaled_risk_map;
    }

    public void runSolutionOneTest() {
        Integer total_risk = calculateRisk(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculateRisk(getInput());
    }

    public void runSolutionTwoTest() {
        Map<Integer, Map<Integer, Integer>> scaled_risk_map = scaleRiskMap(getTestInput());
        Integer total_risk = calculateRisk(scaled_risk_map);
    }

    public void runSolutionTwo() {
        Map<Integer, Map<Integer, Integer>> scaled_risk_map = scaleRiskMap(getInput());
        solution_two = calculateRisk(scaled_risk_map);
    }


}
