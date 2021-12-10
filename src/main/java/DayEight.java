import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;

public class DayEight extends Day<Integer> {

    public DayEight() {
        super("8");
    }

    private Vector<String> getTestInput() {
        String test_input = """
                be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
                edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
                fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
                fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
                aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
                fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
                dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
                bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
                egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
                gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
                """;
        Vector<String> lines = new Vector<>();
        Collections.addAll(lines, test_input.split("\n"));
        return lines;
    }

    private Vector<String> getInput() {
        Vector<String> lines = new Vector<>();
        try {
            File file = new File("src/main/resources/DayEight.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }

    private Integer findNumberOf1478s(Vector<String> lines) {
        Integer count = 0;
        for (String line : lines) {
            String[] split_string = line.split(" \\| ");
            for (String digit : split_string[1].split(" ")) {
                switch (digit.length()) {
                    case 2 -> count++; // 1
                    case 4 -> count++; // 4
                    case 3 -> count++; // 7
                    case 7 -> count++; // 8
                }
            }
        }
        return count;
    }

    private Integer sumDecodedDisplay(Vector<String> lines) {
        Vector<Integer> decodedDisplay = decodeDisplay(lines);
        Integer sum = 0;
        for (Integer digit : decodedDisplay) {
            sum += digit;
        }
        return sum;
    }

    private Vector<Integer> decodeDisplay(Vector<String> lines) {
        Vector<Integer> numbers = new Vector<>();
        for (String line : lines) {
            String[] split_string = line.split(" \\| ");
            Map<Integer, String> encode_map = createEncodeMap(split_string[0]);
            StringBuilder display = new StringBuilder();
            for (String digit : split_string[1].split(" ")) {
                for (Map.Entry<Integer, String> entry : encode_map.entrySet()) {
                    if (digit.length() == entry.getValue().length() &&
                            digit.matches(createContainsRegex(entry.getValue()))
                    ) {
                        display.append(entry.getKey());
                        break;
                    }
                }

            }
            numbers.add(Integer.valueOf(display.toString()));
        }
        return numbers;
    }

    private Map<Integer, String> createEncodeMap(String all_digits) {
        Map<Integer, String> encode_map = new HashMap<>();

        String[] digits = all_digits.split(" ");
        // First iteration, find 1, 4, 7 and 8
        for (String digit : digits) {
            switch (digit.length()) {
                case 2 -> encode_map.put(1, digit);
                case 4 -> encode_map.put(4, digit);
                case 3 -> encode_map.put(7, digit);
                case 7 -> encode_map.put(8, digit);
            }
        }

        String seven_regex = createContainsRegex(encode_map.get(7));
        String four_regex = createContainsRegex(encode_map.get(4));
        String one = encode_map.get(1);
        String one_regex = MessageFormat.format("{0}|{1}", one.charAt(0), one.charAt(1));
        String four_minus_one = encode_map.get(4).replaceAll(one_regex, "");
        String four_minus_one_regex = createContainsRegex(four_minus_one);

        // Second iteration, find 0, 2, 3, 5, 6 and 9
        for (String digit : digits) {
            switch (digit.length()) {
                case 5 -> {
                    if (digit.matches(seven_regex)) {
                        encode_map.put(3, digit);
                    } else if (digit.matches(four_minus_one_regex)) {
                        encode_map.put(5, digit);
                    } else {
                        encode_map.put(2, digit);
                    }
                }
                case 6 -> {
                    if (digit.matches(four_regex)) {
                        encode_map.put(9, digit);
                    } else if (digit.matches(seven_regex)) {
                        encode_map.put(0, digit);
                    } else {
                        encode_map.put(6, digit);
                    }
                }
            }
        }
        return encode_map;
    }

    private String createContainsRegex(String input) {
        StringBuilder output = new StringBuilder();
        for (char ch : input.toCharArray()) {
            output.append("(?=.*").append(ch).append(")");
        }
        output.append(".+");
        return output.toString();
    }

    public void runSolutionOneTest() {
        Integer total_1478s = findNumberOf1478s(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = findNumberOf1478s(getInput());
    }

    public void runSolutionTwoTest() {
        Integer sum = sumDecodedDisplay(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = sumDecodedDisplay(getInput());
    }


}
