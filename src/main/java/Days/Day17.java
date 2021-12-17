package Days;

import Utility.Area;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Day17 extends Day<Integer> {

    public Day17() {
        super("17");
    }

    private Area getTestInput() {
        return new Area(20, -5, 30, -10);
    }

    private Area getInput() {
        return new Area(29, -194, 73, -248);
    }

    private Integer findHighestY(Area area) {
        int max_velocity_x = Math.abs(area.right()) + 1;
        int max_velocity_y = Math.abs(area.bottom()) + 1;
        int highest_y = 0;

        for (int i = 0; i <= max_velocity_x; i++) {
            for (int j = 0; j <= max_velocity_y; j++) {
                highest_y = Math.max(launchProbe(area, i, j), highest_y);
            }
        }

        return highest_y;

    }

    private Integer numberOfSuccesses(Area area) {
        int max_velocity_x = Math.abs(area.right()) + 1;
        int max_velocity_y = Math.abs(area.bottom()) + 1;
        int successes = 0;

        for (int i = 0; i <= max_velocity_x; i++) {
            for (int j = -max_velocity_y; j <= max_velocity_y; j++) {
                successes += launchProbe(area, i, j) > Integer.MIN_VALUE ? 1 : 0;
            }
        }

        return successes;
    }

    private Integer launchProbe(Area area, Integer velocity_x, Integer velocity_y) {
        Integer highest_y = 0;
        Integer position_x = 0;
        Integer position_y = 0;
        while (true) {
            if (area.hit(position_x, position_y)) {
                return highest_y;
            }
            if (area.missed(position_x, position_y)) {
                return Integer.MIN_VALUE;
            }

            highest_y = position_y > highest_y ? position_y : highest_y;
            position_x += velocity_x;
            position_y += velocity_y;
            velocity_x += velocity_x > 0 ? -1 : velocity_x < 0 ? 1 : 0;
            velocity_y -= 1;
        }
    }

    public void runSolutionOneTest() {
        Integer highest_y = findHighestY(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = findHighestY(getInput());
    }

    public void runSolutionTwoTest() {
        Integer test = numberOfSuccesses(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = numberOfSuccesses(getInput());
    }


}
