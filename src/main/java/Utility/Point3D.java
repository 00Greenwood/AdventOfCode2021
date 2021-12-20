package Utility;

import java.util.Objects;

public class Point3D {
    public int x;
    public int y;
    public int z;

    public Point3D(String input) {
        String[] x_y_z = input.split(",");
        x = Integer.parseInt(x_y_z[0]);
        y = Integer.parseInt(x_y_z[1]);
        z = Integer.parseInt(x_y_z[2]);
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int distance(Point3D other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }

    public boolean equals(Point3D other) {
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
