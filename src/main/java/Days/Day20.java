package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20 extends Day<Integer> {

    public Day20() {
        super("20");
    }

    private void getTestInput(Map<Integer, Map<Integer, Integer>> image, Integer[] algorithm) {
        String test_input = """
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                                
                #..#.
                #....
                ##..#
                ..#..
                ..###
                """;
        String[] algorithm_and_image = test_input.split("\n\n");
        int index = 0;
        for (char ch : algorithm_and_image[0].toCharArray()) {
            algorithm[index++] = ch == '#' ? 1 : 0;
        }

        int row = 0;
        for (String line : algorithm_and_image[1].split("\n")) {
            int column = 0;
            image.putIfAbsent(row, new HashMap<>());
            for (char ch : line.toCharArray()) {
                image.get(row).put(column++, ch == '#' ? 1 : 0);
            }
            row++;
        }
    }

    private void getInput(Map<Integer, Map<Integer, Integer>> image, Integer[] algorithm) {
        try {
            File file = new File("src/main/resources/Day" + id + ".txt");
            Scanner scanner = new Scanner(file);

            int index = 0;
            for (char ch : scanner.nextLine().toCharArray()) {
                algorithm[index++] = ch == '#' ? 1 : 0;
            }

            int row = 0;
            while (scanner.hasNextLine()) {
                int column = 0;
                image.putIfAbsent(row, new HashMap<>());
                for (char ch : scanner.nextLine().toCharArray()) {
                    image.get(row).put(column++, ch == '#' ? 1 : 0);
                }
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private Integer getOrDefault(Map<Integer, Map<Integer, Integer>> image, Integer row, Integer column, Integer default_value) {
        image.putIfAbsent(row, new HashMap<>());
        image.get(row).putIfAbsent(column, default_value);
        return image.get(row).get(column);
    }

    private Map<Integer, Map<Integer, Integer>> enhance(Map<Integer, Map<Integer, Integer>> image, Integer[] algorithm, Integer steps) {
        Integer[] default_values = calculateDefaultValues(algorithm);
        for (int step = 1; step <= steps; step++) {
            Map<Integer, Map<Integer, Integer>> enhanced_image = new HashMap<>();
            int min = Collections.min(image.keySet());
            int max = Collections.max(image.keySet());
            for (int row = min - 1; row <= max + 1; row++) {
                for (int column = min - 1; column <= max + 1; column++) {
                    Integer enhanced_pixel_index = calculateEnhancedPixelIndex(image, default_values[step % 2], row, column);
                    enhanced_image.putIfAbsent(row, new HashMap<>());
                    enhanced_image.get(row).put(column, algorithm[enhanced_pixel_index]);
                }
            }
            image = enhanced_image;
        }
        return image;
    }

    private Integer calculateEnhancedPixelIndex(Map<Integer, Map<Integer, Integer>> image, Integer default_value, int row, int column) {
        StringBuilder binary = new StringBuilder();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                binary.append(getOrDefault(image, row + i, column + j, default_value));
            }
        }
        return Integer.valueOf(binary.toString(), 2);
    }

    private Integer[] calculateDefaultValues(Integer[] algorithm) {
        return new Integer[]{algorithm[0], algorithm[Integer.valueOf(String.valueOf(algorithm[0]).repeat(9), 2)]};
    }

    private Integer countLightPixels(Map<Integer, Map<Integer, Integer>> image) {
        int min = Collections.min(image.keySet());
        int max = Collections.max(image.keySet());
        int sum = 0;
        for (int row = min; row <= max; row++) {
            for (int column = min; column <= max; column++) {
                sum += image.get(row).get(column);
            }
        }
        return sum;
    }

    private void print(Map<Integer, Map<Integer, Integer>> image) {
        int min = Collections.min(image.keySet());
        int max = Collections.max(image.keySet());
        for (int row = min; row <= max; row++) {
            for (int column = min; column <= max; column++) {
                System.out.print(image.get(row).get(column) > 0 ? "#" : ".");
            }
            System.out.print("\n");
        }
    }

    public void runSolutionOneTest() {
        Map<Integer, Map<Integer, Integer>> image = new HashMap<>();
        Integer[] algorithm = new Integer[512];
        getTestInput(image, algorithm);
        image = enhance(image, algorithm, 2);
        Integer number_of_pixels = countLightPixels(image);
    }

    public void runSolutionOne() {
        Map<Integer, Map<Integer, Integer>> image = new HashMap<>();
        Integer[] algorithm = new Integer[512];
        getInput(image, algorithm);
        image = enhance(image, algorithm, 2);
        solution_one = countLightPixels(image);
    }

    public void runSolutionTwoTest() {
        Map<Integer, Map<Integer, Integer>> image = new HashMap<>();
        Integer[] algorithm = new Integer[512];
        getTestInput(image, algorithm);
        image = enhance(image, algorithm, 50);
        Integer number_of_pixels = countLightPixels(image);
    }

    public void runSolutionTwo() {
        Map<Integer, Map<Integer, Integer>> image = new HashMap<>();
        Integer[] algorithm = new Integer[512];
        getInput(image, algorithm);
        image = enhance(image, algorithm, 50);
        solution_two = countLightPixels(image);
    }


}
