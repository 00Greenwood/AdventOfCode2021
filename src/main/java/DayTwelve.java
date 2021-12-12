import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayTwelve extends Day<Integer> {

    public DayTwelve() {
        super("12");
    }

    private Map<String, Cave> getSmallTestInput() {
        String test_input = """
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """;
        Map<String, Cave> caves = new HashMap<>();
        for (String line : test_input.split("\n")) {
            createCavePair(caves, line);
        }
        return caves;
    }

    private Map<String, Cave> getMediumTestInput() {
        String test_input = """
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """;
        Map<String, Cave> caves = new HashMap<>();
        for (String line : test_input.split("\n")) {
            createCavePair(caves, line);
        }
        return caves;
    }

    private Map<String, Cave> getBigTestInput() {
        String test_input = """
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW
                """;
        Map<String, Cave> caves = new HashMap<>();
        for (String line : test_input.split("\n")) {
            createCavePair(caves, line);
        }
        return caves;
    }

    private Map<String, Cave> getInput() {
        Map<String, Cave> caves = new HashMap<>();
        try {
            File file = new File("src/main/resources/DayTwelve.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                createCavePair(caves, scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return caves;
    }

    private void createCavePair(Map<String, Cave> caves, String cave_pair) {
        String[] ids = cave_pair.split("-");
        caves.putIfAbsent(ids[0], createCave(ids[0]));
        caves.putIfAbsent(ids[1], createCave(ids[1]));
        caves.get(ids[0]).link(caves.get(ids[1]));
    }

    private Cave createCave(String id) {
        if (id.toUpperCase().equals(id)) {
            return new BigCave(id);
        }
        return new SmallCave(id);
    }

    private Integer findAllSmallCavePaths(Map<String, Cave> caves) {
        Vector<Vector<Cave>> paths = new Vector<>();
        Cave start = caves.get("start");
        Vector<Cave> start_path = new Vector<>();
        generateSmallCavePath(start, start_path, paths);
        return paths.size();
    }

    private void generateSmallCavePath(Cave current_cave, Vector<Cave> current_path, Vector<Vector<Cave>> paths) {
        if (current_cave.getId().equals("end")) {
            current_path.add(current_cave);
            paths.add(current_path);
            return; // End reached.
        }

        if (current_cave.isSmall()) {
            for (Cave previous_cave : current_path) {
                if (previous_cave.equals(current_cave)) {
                    return; // Only visit small cases once.
                }
            }
        }

        current_path.add(current_cave);
        for (Cave next_cave : current_cave.getLinks()) {
            Vector<Cave> next_path = new Vector<>(current_path);
            generateSmallCavePath(next_cave, next_path, paths);
        }
    }

    private Integer findAllSmallCavePathsWithTwoVisits(Map<String, Cave> caves) {
        Vector<Vector<Cave>> paths = new Vector<>();
        Cave start = caves.get("start");
        Vector<Cave> start_path = new Vector<>();
        generateSmallCavePathWithTwoVists(start, start_path, paths);
        return paths.size();
    }

    private void generateSmallCavePathWithTwoVists(Cave current_cave, Vector<Cave> current_path, Vector<Vector<Cave>> paths) {
        if (current_cave.getId().equals("end")) {
            current_path.add(current_cave);
            paths.add(current_path);
            return; // End reached.
        }

        if (current_cave.getId().equals("start")) {
            for (Cave previous_cave : current_path) {
                if (previous_cave.equals(current_cave)) {
                    return; // Only the start once.
                }
            }
        }

        if (current_cave.isSmall()) {
            int total_visits = 1;
            Set<Cave> small_caves = new HashSet<>();
            small_caves.add(current_cave);
            for (Cave previous_cave : current_path) {
                if (previous_cave.isSmall()) {
                    small_caves.add(previous_cave);
                    total_visits++;
                }
            }

            if (total_visits > small_caves.size() + 1) {
                return; // Second visit used previously;
            }
        }

        current_path.add(current_cave);
        for (Cave next_cave : current_cave.getLinks()) {
            Vector<Cave> next_path = new Vector<>(current_path);
            generateSmallCavePathWithTwoVists(next_cave, next_path, paths);
        }
    }

    public void runSolutionOneTest() {
        Integer number_of_paths = findAllSmallCavePaths(getSmallTestInput());
        number_of_paths = findAllSmallCavePaths(getMediumTestInput());
        number_of_paths = findAllSmallCavePaths(getBigTestInput());
    }

    public void runSolutionOne() {
        solution_one = findAllSmallCavePaths(getInput());
    }

    public void runSolutionTwoTest() {
        Integer number_of_paths = findAllSmallCavePathsWithTwoVisits(getSmallTestInput());
        number_of_paths = findAllSmallCavePathsWithTwoVisits(getMediumTestInput());
        number_of_paths = findAllSmallCavePathsWithTwoVisits(getBigTestInput());
    }

    public void runSolutionTwo() {
        solution_two = findAllSmallCavePathsWithTwoVisits(getInput());
    }


}
