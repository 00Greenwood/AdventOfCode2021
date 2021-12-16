package Days;

import Utility.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Day13 extends Day<Integer> {

    public Day13() {
        super("13");
    }

    private void getTestInput(Vector<Pair<Integer, Integer>> points, Vector<Pair<String, Integer>> folds) {
        String test_input = """
                6,10
                0,14
                9,10
                0,3
                10,4
                4,11
                6,0
                6,12
                4,1
                0,13
                10,12
                3,4
                3,0
                8,4
                1,10
                2,14
                8,10
                9,0
                                
                fold along y=7
                fold along x=5
                """;
        String[] points_and_folds = test_input.split("\n\n");
        for (String point : points_and_folds[0].split("\n")) {
            String[] x_and_y = point.split(",");
            points.add(new Pair<>(Integer.valueOf(x_and_y[0]), Integer.valueOf(x_and_y[1])));
        }
        for (String point : points_and_folds[1].split("\n")) {
            String[] direction_and_line = point.split("=");
            String direction = direction_and_line[0].substring(direction_and_line[0].length() - 1);
            folds.add(new Pair<>(direction, Integer.valueOf(direction_and_line[1])));
        }
    }

    private void getInput(Vector<Pair<Integer, Integer>> points, Vector<Pair<String, Integer>> folds) {
        Vector<Integer> input = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file).useDelimiter("\r\n\r\n");
            for (String point : scanner.next().split("\r\n")) {
                String[] x_and_y = point.split(",");
                points.add(new Pair<>(Integer.valueOf(x_and_y[0]), Integer.valueOf(x_and_y[1])));
            }
            for (String point : scanner.next().split("\r\n")) {
                String[] direction_and_line = point.split("=");
                String direction = direction_and_line[0].substring(direction_and_line[0].length() - 1);
                folds.add(new Pair<>(direction, Integer.valueOf(direction_and_line[1])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void fold(Vector<Pair<Integer, Integer>> points, Pair<String, Integer> fold) {
        for (Pair<Integer, Integer> point : points) {
            switch (fold.first) {
                case "x" -> point.first = point.first >= fold.second ? 2 * fold.second - point.first : point.first;
                case "y" -> point.second = point.second >= fold.second ? 2 * fold.second - point.second : point.second;
            }
        }
        // Remove invalid points.
        Vector<Pair<Integer, Integer>> points_to_remove = new Vector<>();
        for (Pair<Integer, Integer> point : points) {
            if (point.first < 0 || point.second < 0) {
                points_to_remove.add(point);
            }
        }
        // Remove duplicate points.
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).equals(points.get(j))) {
                    points_to_remove.add(points.get(i));
                }
            }
        }
        points.removeAll(points_to_remove);
    }

    public void runSolutionOneTest() {
        Vector<Pair<Integer, Integer>> points = new Vector<>();
        Vector<Pair<String, Integer>> folds = new Vector<>();
        getTestInput(points, folds);
        fold(points, folds.get(0));
        Integer number_of_points = points.size();
    }

    public void runSolutionOne() {
        Vector<Pair<Integer, Integer>> points = new Vector<>();
        Vector<Pair<String, Integer>> folds = new Vector<>();
        getInput(points, folds);
        fold(points, folds.get(0));
        solution_one = points.size();
    }

    public void runSolutionTwoTest() {
        Vector<Pair<Integer, Integer>> points = new Vector<>();
        Vector<Pair<String, Integer>> folds = new Vector<>();
        getTestInput(points, folds);
        for (Pair<String, Integer> fold : folds) {
            fold(points, fold);
        }
        Integer number_of_points = points.size();
    }

    public void runSolutionTwo() {
        Vector<Pair<Integer, Integer>> points = new Vector<>();
        Vector<Pair<String, Integer>> folds = new Vector<>();
        getInput(points, folds);
        for (Pair<String, Integer> fold : folds) {
            fold(points, fold);
        }
        solution_two = points.size();
    }
}
