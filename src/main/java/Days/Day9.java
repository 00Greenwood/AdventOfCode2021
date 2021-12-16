package Days;

import Utility.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Day9 extends Day<Integer> {

    public Day9() {
        super("9");
    }

    private Vector<Vector<Integer>> getTestInput() {
        String test_input = """
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678""";
        Vector<Vector<Integer>> row = new Vector<>();
        for (String line : test_input.split("\n")) {
            Vector<Integer> column = new Vector<>();
            for (String number : line.split("")) {
                column.add(Integer.valueOf(number));
            }
            row.add(column);
        }
        return row;
    }

    private Vector<Vector<Integer>> getInput() {
        Vector<Vector<Integer>> row = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Vector<Integer> column = new Vector<>();
                for (String number : scanner.nextLine().split("")) {
                    column.add(Integer.valueOf(number));
                }
                row.add(column);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return row;
    }

    private boolean isMinimum(Integer level, Vector<Vector<Integer>> height_map, Pair<Integer, Integer> point) {
        Integer x = point.first;
        Integer y = point.second;
        return level < getOrNine(height_map, new Pair<>(x - 1, y)) &&
                level < getOrNine(height_map, new Pair<>(x, y - 1)) &&
                level < getOrNine(height_map, new Pair<>(x + 1, y)) &&
                level < getOrNine(height_map, new Pair<>(x, y + 1));
    }


    private Integer getOrNine(Vector<Vector<Integer>> height_map, Pair<Integer, Integer> point) {
        try {
            return height_map.get(point.first).get(point.second);
        } catch (IndexOutOfBoundsException e) {
            return 9;
        }
    }

    private Integer calculateDangerLevel(Vector<Vector<Integer>> height_map) {
        Map<Integer, Map<Integer, Integer>> minimum_levels = getMinimumLevels(height_map);
        int danger_level = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> row : minimum_levels.entrySet()) {
            for (Map.Entry<Integer, Integer> column : row.getValue().entrySet()) {
                danger_level += column.getValue() + 1;
            }
        }
        return danger_level;
    }

    private Map<Integer, Map<Integer, Integer>> getMinimumLevels(Vector<Vector<Integer>> height_map) {
        Map<Integer, Map<Integer, Integer>> minimum_levels = new HashMap<>();
        for (int i = 0; i < height_map.size(); i++) {
            for (int j = 0; j < height_map.get(i).size(); j++) {
                Integer level = height_map.get(i).get(j);
                if (isMinimum(level, height_map, new Pair<>(i, j))) {
                    minimum_levels.putIfAbsent(i, new HashMap<>());
                    minimum_levels.get(i).put(j, level);
                }
            }
        }
        return minimum_levels;
    }

    private Integer calculateBasinArea(Vector<Vector<Integer>> height_map) {
        Vector<Integer> basin_areas = new Vector<>();

        Map<Integer, Map<Integer, Integer>> minimum_levels = getMinimumLevels(height_map);
        for (Integer i : minimum_levels.keySet()) {
            for (Integer j : minimum_levels.get(i).keySet()) {
                int basin_area = 0;

                Queue<Pair<Integer, Integer>> points_to_check = new LinkedBlockingDeque<>();
                Set<Pair<Integer, Integer>> points_checked = new HashSet<>();
                points_to_check.add(new Pair<>(i, j));
                while (points_to_check.size() > 0) {
                    ++basin_area;

                    Pair<Integer, Integer> point = points_to_check.poll();
                    Integer x = point.first;
                    Integer y = point.second;
                    Integer level = height_map.get(x).get(y);

                    checkAndAddToQueue(height_map, new Pair<>(x - 1, y), level, points_to_check, points_checked);
                    checkAndAddToQueue(height_map, new Pair<>(x, y - 1), level, points_to_check, points_checked);
                    checkAndAddToQueue(height_map, new Pair<>(x + 1, y), level, points_to_check, points_checked);
                    checkAndAddToQueue(height_map, new Pair<>(x, y + 1), level, points_to_check, points_checked);

                    points_checked.add(point);
                }
                basin_areas.add(basin_area);
            }
        }
        Collections.sort(basin_areas, Collections.reverseOrder());
        return basin_areas.get(0) * basin_areas.get(1) * basin_areas.get(2);
    }

    private void checkAndAddToQueue(
            Vector<Vector<Integer>> height_map,
            Pair<Integer, Integer> point,
            Integer level,
            Queue<Pair<Integer, Integer>> points_to_check,
            Set<Pair<Integer, Integer>> points_checked
    ) {
        Integer level_to_check = getOrNine(height_map, point);
        if (level_to_check > level && level_to_check < 9) {
            for (Pair<Integer, Integer> point_to_check : points_to_check) {
                if (point_to_check.equals(point)) {
                    return;
                }
            }
            for (Pair<Integer, Integer> point_checked : points_checked) {
                if (point_checked.equals(point)) {
                    return;
                }
            }
            points_to_check.add(point);
        }
    }

    public void runSolutionOneTest() {
        Integer danger_level = calculateDangerLevel(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = calculateDangerLevel(getInput());
    }

    public void runSolutionTwoTest() {
        Integer area_of_basin = calculateBasinArea(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculateBasinArea(getInput());
    }


}
