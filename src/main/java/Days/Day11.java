package Days;

import Utility.Octopus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Day11 extends Day<Integer> {

    public Day11() {
        super("11");
    }

    private Vector<Vector<Octopus>> getTestInput() {
        String test_input = """
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526
                """;
        Vector<Vector<Octopus>> octopuses = new Vector<>();
        for (String line : test_input.split("\n")) {
            Vector<Octopus> row = new Vector<>();
            for (String number : line.split("")) {
                row.add(new Octopus(Integer.valueOf(number)));
            }
            octopuses.add(row);
        }
        linkOctopuses(octopuses);
        return octopuses;
    }

    private Vector<Vector<Octopus>> getInput() {
        Vector<Vector<Octopus>> octopuses = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Vector<Octopus> row = new Vector<>();
                for (String number : scanner.nextLine().split("")) {
                    row.add(new Octopus(Integer.valueOf(number)));
                }
                octopuses.add(row);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        linkOctopuses(octopuses);
        return octopuses;
    }

    private void linkOctopuses(Vector<Vector<Octopus>> octopuses) {
        for (int j = 0; j <= 9; j++) {
            for (int i = 0; i <= 9; i++) {
                Octopus octopus = octopuses.get(i).get(j);
                octopus.link(getOrNull(octopuses, i + 1, j - 1));
                octopus.link(getOrNull(octopuses, i + 1, j));
                octopus.link(getOrNull(octopuses, i + 1, j + 1));
                octopus.link(getOrNull(octopuses, i, j + 1));
                octopus.link(getOrNull(octopuses, i - 1, j + 1));
                octopus.link(getOrNull(octopuses, i - 1, j));
                octopus.link(getOrNull(octopuses, i - 1, j - 1));
                octopus.link(getOrNull(octopuses, i, j - 1));
            }
        }
    }

    private Octopus getOrNull(Vector<Vector<Octopus>> octopuses, Integer x, Integer y) {
        try {
            return octopuses.get(x).get(y);
        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }

    private Integer countFlashes(Vector<Vector<Octopus>> octopuses) {
        Integer flashes = 0;
        for (int i = 1; i <= 100; i++) {
            for (Vector<Octopus> row : octopuses) {
                for (Octopus octopus : row) {
                    octopus.increaseEnergy();
                }
            }
            for (Vector<Octopus> row : octopuses) {
                for (Octopus octopus : row) {
                    flashes += octopus.didFlash();
                }
            }
        }
        return flashes;
    }

    private Integer findSynchronization(Vector<Vector<Octopus>> octopuses) {
        int flashes = 0;
        int step = 0;
        while (flashes < 100) {
            step++;
            for (Vector<Octopus> row : octopuses) {
                for (Octopus octopus : row) {
                    octopus.increaseEnergy();
                }
            }
            flashes = 0;
            for (Vector<Octopus> row : octopuses) {
                for (Octopus octopus : row) {
                    flashes += octopus.didFlash();
                }
            }
        }
        return step;
    }

    public void runSolutionOneTest() {
        Integer flashes = countFlashes(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = countFlashes(getInput());
    }

    public void runSolutionTwoTest() {
        Integer step = findSynchronization(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = findSynchronization(getInput());
    }


}
