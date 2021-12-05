import java.util.Vector;

public class Line {
    private final Pair<Integer, Integer> start;
    private final Pair<Integer, Integer> end;
    private final Vector<Pair<Integer, Integer>> points;
    private final Boolean is_vertical;
    private final Boolean is_horizontal;

    public Line(String input) {
        String[] numbers = input.split(",| -> ");
        start = new Pair<>(Integer.valueOf(numbers[0]), Integer.valueOf(numbers[1]));
        end = new Pair<>(Integer.valueOf(numbers[2]), Integer.valueOf(numbers[3]));

        Integer horizontal_difference = end.first - start.first;
        Integer vertical_difference = end.second - start.second;

        is_vertical = Math.abs(vertical_difference) > 0 && horizontal_difference == 0;
        is_horizontal = Math.abs(horizontal_difference) > 0 && vertical_difference == 0;

        Pair<Integer, Integer> increment = new Pair<>(
                horizontal_difference.equals(0) ? 0 : horizontal_difference / Math.abs(horizontal_difference),
                vertical_difference.equals(0) ? 0 : vertical_difference / Math.abs(vertical_difference));

        points = new Vector<>();
        Pair<Integer, Integer> point = new Pair<>(start.first, start.second);
        while (!point.equals(end)) {
            points.add(point);
            point = new Pair<>(point.first + increment.first, point.second + increment.second);
        }
        points.add(end);
    }

    public Vector<Pair<Integer, Integer>> getPoints() {
        return points;
    }

    public Boolean isVerticalOrHorizontal() {
        return is_vertical || is_horizontal;
    }
}
