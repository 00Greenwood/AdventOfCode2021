package Utility;

import java.util.Vector;

public class Scanner {
    private final Vector<Beacon> beacons = new Vector<>();
    private Point3D position = new Point3D(0,0,0);

    public Scanner(String points) {
        for (String point : points.split("\r?\n")) {
            if (point.isEmpty()) {
                continue;
            }
            beacons.add(new Beacon(point));
        }
        for (Beacon beacon: beacons) {
            beacon.calculateDistances(beacons);
        }
    }

    public Vector<Beacon> beacons(){
        return beacons;
    }

    public void transform(Matrix4D transformation) {
        position = transformation.multiply(position);
        for (Beacon beacon: beacons) {
            beacon.transform(transformation);
        }
    }

    @Override
    public String toString() {
        return position.toString();
    }

    public Integer distance(Scanner other){
        return position.distance(other.position);
    }
}
