import java.util.Scanner;
import java.util.Vector;

public class BingoBoard {
    private Vector<Vector<Pair<Integer, Boolean>>> bingo_board = new Vector<>();
    private Integer width = 0;
    private Integer height = 0;

    public BingoBoard(String input) {
        for (String line : input.split("\n")) {
            Vector<Pair<Integer, Boolean>> row = new Vector<>();
            Scanner scanner = new Scanner(line);
            while (scanner.hasNext()) {
                row.add(new Pair<Integer, Boolean>(Integer.valueOf(scanner.next()), false));
            }
            bingo_board.add(row);
        }
        width = bingo_board.size();
        height = bingo_board.firstElement().size();
    }

    public void mark_number(Integer number) {
        for (Vector<Pair<Integer, Boolean>> row : bingo_board) {
            for (Pair<Integer, Boolean> element : row) {
                if (element.first.equals(number)) {
                    element.second = true;
                    return;
                }
            }
        }
    }

    public Boolean has_bingo() {
        for (int i = 0; i < width; i++) {
            Boolean is_row_complete = true;
            for (int j = 0; j < height; j++) {
                is_row_complete &= bingo_board.get(i).get(j).second;
            }
            if (is_row_complete) {
                return true;
            }
        }
        for (int i = 0; i < width; i++) {
            Boolean is_column_complete = true;
            for (int j = 0; j < height; j++) {
                is_column_complete &= bingo_board.get(j).get(i).second;
            }
            if (is_column_complete) {
                return true;
            }
        }
        return false;
    }

    public Integer calculate_score() {
        Integer score = 0;
        for (Vector<Pair<Integer, Boolean>> row : bingo_board) {
            for (Pair<Integer, Boolean> element : row) {
                if (!element.second) {
                    score += element.first;
                }
            }
        }
        return score;
    }

}
