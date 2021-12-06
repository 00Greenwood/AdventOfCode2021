import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DayFour extends Day<Integer> {

    public DayFour() {
        super("4");
    }

    private Pair<Vector<Integer>, Vector<BingoBoard>> getTestInput() {
        String test_input = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n\n22 13 17 11  0\n 8  2 23  4 24\n21  9 14 16  7\n 6 10  3 18  5\n 1 12 20 15 19\n\n 3 15  0  2 22\n 9 18 13 17  5\n19  8  7 25 23\n20 11 10 24  4\n14 21 16 12  6\n\n14 21 17 24  4\n10 16 15  9 19\n18  8 23 26 20\n22 11 13  6  5\n 2  0 12  3  7";
        Pair<Vector<Integer>, Vector<BingoBoard>> numbers_and_boards = new Pair<>(new Vector<>(), new Vector<>());
        String[] test_inputs = test_input.split("\n\n");
        for (String number : test_inputs[0].split(",")) {
            numbers_and_boards.first.add(Integer.valueOf(number));
        }
        for (int i = 1; i < test_inputs.length; i++) {
            numbers_and_boards.second.add(new BingoBoard(test_inputs[i]));
        }
        return numbers_and_boards;
    }

    private Pair<Vector<Integer>, Vector<BingoBoard>> getInput() {
        Pair<Vector<Integer>, Vector<BingoBoard>> numbers_and_boards = new Pair<>(new Vector<>(), new Vector<>());
        try {
            File file = new File("src/main/resources/DayFour.txt");
            Scanner scanner = new Scanner(file).useDelimiter("\r\n\r\n");
            for (String number : scanner.next().split(",")) {
                numbers_and_boards.first.add(Integer.valueOf(number));
            }
            while (scanner.hasNext()) {
                numbers_and_boards.second.add(new BingoBoard(scanner.next()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return numbers_and_boards;
    }

    private Integer playBingoToWin(Vector<Integer> numbers, Vector<BingoBoard> boards) {
        for (Integer number : numbers) {
            for (BingoBoard board : boards) {
                board.mark_number(number);
                if (board.has_bingo()) {
                    return board.calculate_score() * number;
                }
            }
        }
        return 0;
    }

    private Integer playBingoToLose(Vector<Integer> numbers, Vector<BingoBoard> boards) {
        for (Integer number : numbers) {
            Vector<BingoBoard> boards_to_remove = new Vector<>();
            for (BingoBoard board : boards) {
                board.mark_number(number);
                if (board.has_bingo()) {
                    boards_to_remove.add(board);
                }
            }
            boards.removeAll(boards_to_remove);
            if (boards.size() == 0) {
                return boards_to_remove.firstElement().calculate_score() * number;
            }
        }
        return 0;
    }

    public void runSolutionOneTest() {
        Pair<Vector<Integer>, Vector<BingoBoard>> input = getTestInput();
        Integer score = playBingoToWin(input.first, input.second);
    }

    public void runSolutionOne() {
        Pair<Vector<Integer>, Vector<BingoBoard>> input = getInput();
        solution_one = playBingoToWin(input.first, input.second);
    }

    public void runSolutionTwoTest() {
        Pair<Vector<Integer>, Vector<BingoBoard>> input = getTestInput();
        Integer score = playBingoToLose(input.first, input.second);
    }

    public void runSolutionTwo() {
        Pair<Vector<Integer>, Vector<BingoBoard>> input = getInput();
        solution_two = playBingoToLose(input.first, input.second);
    }


}
