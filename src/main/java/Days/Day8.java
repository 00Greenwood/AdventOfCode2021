package Days;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 extends Day<Integer> {

    public Day8() {
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
            File file = new File("src/main/resources/Day"+ id +".txt");
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
            Map<Integer, Set<Character>> encode_map = createEncodeMap(split_string[0]);
            StringBuilder display = new StringBuilder();
            for (String string_digit : split_string[1].split(" ")) {
                Set<Character> digit = createDigit(string_digit);
                for (Map.Entry<Integer, Set<Character>> entry : encode_map.entrySet()) {
                    if (digit.size() == entry.getValue().size() &&
                            digit.containsAll(entry.getValue())
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

    private Set<Character> createDigit(String digit) {
        Set<Character> set = new TreeSet<>();
        char[] charArray = digit.toCharArray();
        for (char ch : charArray) {
            set.add(ch);
        }
        return set;
    }

    private Map<Integer, Set<Character>> createEncodeMap(String all_digits) {
        Map<Integer, Set<Character>> encode_map = new HashMap<>();

        String[] digits = all_digits.split(" ");
        // First iteration, find 1, 4, 7 and 8
        for (String digit : digits) {
            switch (digit.length()) {
                case 2 -> encode_map.put(1, createDigit(digit));
                case 4 -> encode_map.put(4, createDigit(digit));
                case 3 -> encode_map.put(7, createDigit(digit));
                case 7 -> encode_map.put(8, createDigit(digit));
            }
        }

        Set<Character> seven = encode_map.get(7);
        Set<Character> four = encode_map.get(4);
        Set<Character> four_minus_one = new TreeSet<>(encode_map.get(4));
        four_minus_one.removeAll(encode_map.get(1));

        // Second iteration, find 0, 2, 3, 5, 6 and 9
        for (String string_digit : digits) {
            Set<Character> digit = createDigit(string_digit);
            switch (string_digit.length()) {
                case 5 -> {
                    if (digit.containsAll(seven)) {
                        encode_map.put(3, digit);
                    } else if (digit.containsAll(four_minus_one)) {
                        encode_map.put(5, digit);
                    } else {
                        encode_map.put(2, digit);
                    }
                }
                case 6 -> {
                    if (digit.containsAll(four)) {
                        encode_map.put(9, digit);
                    } else if (digit.containsAll(seven)) {
                        encode_map.put(0, digit);
                    } else {
                        encode_map.put(6, digit);
                    }
                }
            }
        }
        return encode_map;
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
