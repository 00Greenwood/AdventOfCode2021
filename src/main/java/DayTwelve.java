import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

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

    private Integer findAllSmallCavePaths(Map<String, Cave> caves, Boolean allow_two_visits) {
        Vector<CavePath> paths = new Vector<>();
        Cave start = caves.get("start");
        CavePath start_path = new CavePath();
        generateSmallCavePath(start, start_path, paths, allow_two_visits);
        return paths.size();
    }

    private void generateSmallCavePath(Cave current_cave, CavePath current_path, Vector<CavePath> paths, Boolean allow_two_visits) {
        if (!current_path.add(current_cave)) {
            return; // Add failed, skip!
        }

        if (current_cave.getId().equals("end")) {
            paths.add(current_path);
            return; // End reached.
        }

        if (!current_path.isValid(allow_two_visits)) {
            return; // Path no longer valid.
        }

        for (Cave next_cave : current_cave.getLinks()) {
            CavePath next_path = current_path.copy();
            generateSmallCavePath(next_cave, next_path, paths, allow_two_visits);
        }
    }

    public void runSolutionOneTest() {
        Integer number_of_paths = findAllSmallCavePaths(getSmallTestInput(), false);
        number_of_paths = findAllSmallCavePaths(getMediumTestInput(), false);
        number_of_paths = findAllSmallCavePaths(getBigTestInput(), false);
    }

    public void runSolutionOne() {
        solution_one = findAllSmallCavePaths(getInput(), false);
    }

    public void runSolutionTwoTest() {
        Integer number_of_paths = findAllSmallCavePaths(getSmallTestInput(), true);
        number_of_paths = findAllSmallCavePaths(getMediumTestInput(), true);
        number_of_paths = findAllSmallCavePaths(getBigTestInput(), true);
    }

    public void runSolutionTwo() {
        solution_two = findAllSmallCavePaths(getInput(), true);
    }


}
