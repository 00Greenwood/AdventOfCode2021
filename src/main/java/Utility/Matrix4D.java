package Utility;

import java.util.Arrays;
import java.util.Vector;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix4D {
    private final int[][] m = new int[4][4];

    public Matrix4D() {
        // Create the identity matrix.
        m[0][0] = 1;
        m[1][1] = 1;
        m[2][2] = 1;
        m[3][3] = 1;
    }

    public static Matrix4D rotation(Double alpha, Double beta, Double gamma) {
        Matrix4D matrix = new Matrix4D();
        matrix.m[0][0] = (int) round(cos(alpha) * cos(beta));
        matrix.m[0][1] = (int) round(cos(alpha) * sin(beta) * sin(gamma) - sin(alpha) * cos(gamma));
        matrix.m[0][2] = (int) round(cos(alpha) * sin(beta) * cos(gamma) + sin(alpha) * sin(gamma));
        matrix.m[1][0] = (int) round(sin(alpha) * cos(beta));
        matrix.m[1][1] = (int) round(sin(alpha) * sin(beta) * sin(gamma) + cos(alpha) * cos(gamma));
        matrix.m[1][2] = (int) round(sin(alpha) * sin(beta) * cos(gamma) - cos(alpha) * sin(gamma));
        matrix.m[2][0] = (int) round(-sin(beta));
        matrix.m[2][1] = (int) round(cos(beta) * sin(gamma));
        matrix.m[2][2] = (int) round(cos(beta) * cos(gamma));
        return matrix;
    }

    public void set(int i, int j, int value) {
        m[i][j] = value;
    }

    public int get(int i, int j) {
        return m[i][j];
    }

    public int sumElements(Matrix4D other, int row, int col) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += m[row][i] * other.m[i][col];
        }
        return sum;
    }

    public Matrix4D multiply(Matrix4D other) {
        Matrix4D output = new Matrix4D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                output.m[i][j] = sumElements(other, i, j);
            }
        }
        return output;
    }

    public int sumElements(Point3D other, int row) {
        int sum = 0;
        sum += m[row][0] * other.x;
        sum += m[row][1] * other.y;
        sum += m[row][2] * other.z;
        sum += m[row][3];
        return sum;
    }

    public Point3D multiply(Point3D other) {
        return new Point3D(
                sumElements(other, 0),
                sumElements(other, 1),
                sumElements(other, 2)
        );
    }

    public boolean equals(Matrix4D other) {
        return Arrays.deepEquals(m, other.m);
    }
}
