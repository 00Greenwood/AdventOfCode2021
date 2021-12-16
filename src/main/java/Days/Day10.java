package Days;

import Utility.SyntaxComplete;
import Utility.SyntaxError;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Day10 extends Day<BigInteger> {

    public Day10() {
        super("10");
    }

    private Vector<Map<Integer, Character>> getTestInput() {
        String test_input = """
                [({(<(())[]>[[{[]{<()<>>
                [(()[<>])]({[<{<<[]>>(
                {([(<{}[<>[]}>{[]{[(<()>
                (((({<>}<{<{<>}{[]{[]{}
                [[<[([]))<([[{}[[()]]]
                [{[{({}]{}}([{[{{{}}([]
                {<[[]]>}<{[{[{[]{()[[[]
                [<(<(<(<{}))><([]([]()
                <{([([[(<>()){}]>(<<{{
                <{([{{}}[<[[[<>{}]]]>[]]
                """;
        Vector<Map<Integer, Character>> syntax_lines = new Vector<>();
        for (String line : test_input.split("\n")) {
            Map<Integer, Character> map = new TreeMap<>();
            int i = 0;
            for (char ch : line.toCharArray()) {
                map.put(i++, ch);
            }
            syntax_lines.add(map);
        }
        return syntax_lines;
    }

    private Vector<Map<Integer, Character>> getInput() {
        Vector<Map<Integer, Character>> syntax_lines = new Vector<>();
        try {
            File file = new File("src/main/resources/Day"+ id +".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Map<Integer, Character> map = new TreeMap<>();
                int i = 0;
                for (char ch : scanner.nextLine().toCharArray()) {
                    map.put(i++, ch);
                }
                syntax_lines.add(map);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return syntax_lines;
    }

    private void removeCaseIfComplete(Map<Integer, Character> syntax, Integer current_key, Integer next_key, Character to_match) throws SyntaxComplete, SyntaxError {
        String closing_syntax = "\\)|\\]|\\}|\\>";
        if (syntax.get(next_key).toString().matches(closing_syntax)) {
            if (syntax.get(next_key).equals(to_match)) {
                syntax.remove(current_key);
                syntax.remove(next_key);
                throw new SyntaxComplete();
            } else {
                throw new SyntaxError();
            }
        }
    }

    private Integer calculateFirstErrorsScore(Vector<Map<Integer, Character>> syntax_lines) {
        int score = 0;
        for (Map<Integer, Character> syntax_line : syntax_lines) {
            score += calculateFirstErrorScore(syntax_line);
        }
        return score;
    }

    private Integer calculateFirstErrorScore(Map<Integer, Character> syntax) {
        try {
            while (syntax.size() > 1) {
                Vector<Integer> keys = new Vector<>(syntax.keySet());
                for (int index = 0; index < keys.size(); index++) {
                    Integer current_key = keys.get(index);
                    Integer next_key = keys.get(index + 1);
                    try {
                        removeSyntaxIfComplete(syntax, current_key, next_key);
                    } catch (SyntaxComplete e) {
                        break;
                    } catch (SyntaxError e) {
                        int score = 0;
                        switch (syntax.get(next_key)) {
                            case ')' -> score = 3;
                            case ']' -> score = 57;
                            case '}' -> score = 1197;
                            case '>' -> score = 25137;
                        }
                        return score;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return 0;
    }

    private void removeSyntaxIfComplete(Map<Integer, Character> syntax, Integer current_key, Integer next_key) throws SyntaxComplete, SyntaxError {
        switch (syntax.get(current_key)) {
            case '(' -> removeCaseIfComplete(syntax, current_key, next_key, ')');
            case '[' -> removeCaseIfComplete(syntax, current_key, next_key, ']');
            case '{' -> removeCaseIfComplete(syntax, current_key, next_key, '}');
            case '<' -> removeCaseIfComplete(syntax, current_key, next_key, '>');
        }
    }

    private BigInteger calculateMiddleCompletionScore(Vector<Map<Integer, Character>> syntax_lines) {
        Vector<BigInteger> scores = new Vector<>();
        for (Map<Integer, Character> syntax_line : syntax_lines) {
            try {
                scores.add(calculateCompletionScore(syntax_line));
            } catch (SyntaxError ignored) {
            }
        }
        Collections.sort(scores);
        return scores.get((scores.size() - 1) / 2);
    }

    private BigInteger calculateCompletionScore(Map<Integer, Character> syntax) throws SyntaxError {
        try {
            while (syntax.size() > 1) {
                Vector<Integer> keys = new Vector<>(syntax.keySet());
                for (int index = 0; index < keys.size(); index++) {
                    Integer current_key = keys.get(index);
                    Integer next_key = keys.get(index + 1);
                    try {
                        removeSyntaxIfComplete(syntax, current_key, next_key);
                    } catch (SyntaxComplete e) {
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        Vector<Character> values = new Vector<>(syntax.values());
        Collections.reverse(values);
        BigInteger score = BigInteger.ZERO;
        for (Character ch : values) {
            score = score.multiply(BigInteger.valueOf(5));
            switch (ch) {
                case '(' -> score = score.add(BigInteger.valueOf(1));
                case '[' -> score = score.add(BigInteger.valueOf(2));
                case '{' -> score = score.add(BigInteger.valueOf(3));
                case '<' -> score = score.add(BigInteger.valueOf(4));
            }
        }
        return score;
    }

    public void runSolutionOneTest() {
        Integer test = calculateFirstErrorsScore(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = BigInteger.valueOf(calculateFirstErrorsScore(getInput()));
    }

    public void runSolutionTwoTest() {
        BigInteger test = calculateMiddleCompletionScore(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = calculateMiddleCompletionScore(getInput());
    }


}
