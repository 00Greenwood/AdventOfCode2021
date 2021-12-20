package Utility;

import java.util.*;

public class Beacon {
    private Point3D position;
    private Set<Integer> distances = new HashSet<>();

    public Beacon(String input){
        position = new Point3D(input);
    }

    public Integer distance(Beacon other) {
        return position.distance(other.position);
    }

    public void calculateDistances(Vector<Beacon> beacons) {
        for (Beacon beacon: beacons) {
            if (beacon.equals(this)) {
                continue;
            }
            distances.add(distance(beacon));
        }
    }

    public Set<Integer> distances() {
        return distances;
    }

    public Point3D position() {
        return position;
    }

    public void transform(Matrix4D transformation) {
        position = transformation.multiply(position);
    }
}
