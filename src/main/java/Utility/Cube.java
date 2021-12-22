package Utility;

import java.math.BigInteger;
import java.util.Vector;

public class Cube {
    private final Integer left;
    private final Integer right;
    private final Integer down;
    private final Integer up;
    private final Integer back;
    private final Integer front;

    private final Vector<Cube> sub_cubes = new Vector<>();

    public Cube(Integer left, Integer right, Integer down, Integer up, Integer back, Integer front) {
        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
        this.back = back;
        this.front = front;
    }

    public void intersect(Cube to_remove) {
        if (!doesIntersect(to_remove)) {
            return; // Does not intersect.
        }

        Cube intersection = new Cube(
                Math.max(left, to_remove.left),
                Math.min(right, to_remove.right),
                Math.max(down, to_remove.down),
                Math.min(up, to_remove.up),
                Math.max(back, to_remove.back),
                Math.min(front, to_remove.front)
        );

        for (Cube sub_cube : sub_cubes) {
            sub_cube.intersect(intersection);
        }
        sub_cubes.add(intersection);
    }

    public boolean doesIntersect(Cube other) {
        return doesIntersect(this, other) || doesIntersect(other, this);
    }

    public static boolean doesIntersect(Cube one, Cube two) {
        Boolean contains_left_bottom_back =
                one.left >= two.left &&
                        one.left <= two.right &&
                        one.down >= two.down &&
                        one.down <= two.up &&
                        one.back >= two.back &&
                        one.back <= two.front;
        Boolean contains_right_up_front =
                one.right >= two.left &&
                        one.right <= two.right &&
                        one.up >= two.down &&
                        one.up <= two.up &&
                        one.front >= two.back &&
                        one.front <= two.front;

        return contains_left_bottom_back || contains_right_up_front;
    }

    public Vector<Cube> subCubes() {
        return sub_cubes;
    }

    public BigInteger volume() {
        BigInteger volume = BigInteger.valueOf(left - right).abs().multiply(
                BigInteger.valueOf(down - up).abs()).multiply(
                BigInteger.valueOf(back - front).abs());
        for (Cube sub_cube : sub_cubes) {
            volume = volume.subtract(sub_cube.volume());
        }
        return volume;
    }
}
