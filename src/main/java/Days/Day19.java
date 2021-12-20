package Days;

import Utility.Beacon;
import Utility.Matrix4D;
import Utility.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day19 extends Day<Integer> {

    private final Vector<Matrix4D> rotation_to_directions = new Vector<>();
    private final Vector<Matrix4D> rotation_around_axes = new Vector<>();

    public Day19() {
        super("19");

        for (int i = -2; i < 2; i++) {
            rotation_to_directions.add(Matrix4D.rotation(i * Math.PI / 2, 0.0, 0.0));
        }
        rotation_to_directions.add(Matrix4D.rotation(0.0, Math.PI / 2, 0.0));
        rotation_to_directions.add(Matrix4D.rotation(0.0, -Math.PI / 2, 0.0));

        for (int i = -2; i < 2; i++) {
            rotation_around_axes.add(Matrix4D.rotation(0.0, 0.0, i * Math.PI / 2));
        }
    }

    private Vector<Utility.Scanner> getTestInput() {
        String test_input = """
                --- scanner 0 ---
                404,-588,-901
                528,-643,409
                -838,591,734
                390,-675,-793
                -537,-823,-458
                -485,-357,347
                -345,-311,381
                -661,-816,-575
                -876,649,763
                -618,-824,-621
                553,345,-567
                474,580,667
                -447,-329,318
                -584,868,-557
                544,-627,-890
                564,392,-477
                455,729,728
                -892,524,684
                -689,845,-530
                423,-701,434
                7,-33,-71
                630,319,-379
                443,580,662
                -789,900,-551
                459,-707,401

                --- scanner 1 ---
                686,422,578
                605,423,415
                515,917,-361
                -336,658,858
                95,138,22
                -476,619,847
                -340,-569,-846
                567,-361,727
                -460,603,-452
                669,-402,600
                729,430,532
                -500,-761,534
                -322,571,750
                -466,-666,-811
                -429,-592,574
                -355,545,-477
                703,-491,-529
                -328,-685,520
                413,935,-424
                -391,539,-444
                586,-435,557
                -364,-763,-893
                807,-499,-711
                755,-354,-619
                553,889,-390

                --- scanner 2 ---
                649,640,665
                682,-795,504
                -784,533,-524
                -644,584,-595
                -588,-843,648
                -30,6,44
                -674,560,763
                500,723,-460
                609,671,-379
                -555,-800,653
                -675,-892,-343
                697,-426,-610
                578,704,681
                493,664,-388
                -671,-858,530
                -667,343,800
                571,-461,-707
                -138,-166,112
                -889,563,-600
                646,-828,498
                640,759,510
                -630,509,768
                -681,-892,-333
                673,-379,-804
                -742,-814,-386
                577,-820,562

                --- scanner 3 ---
                -589,542,597
                605,-692,669
                -500,565,-823
                -660,373,557
                -458,-679,-417
                -488,449,543
                -626,468,-788
                338,-750,-386
                528,-832,-391
                562,-778,733
                -938,-730,414
                543,643,-506
                -524,371,-870
                407,773,750
                -104,29,83
                378,-903,-323
                -778,-728,485
                426,699,580
                -438,-605,-362
                -469,-447,-387
                509,732,623
                647,635,-688
                -868,-804,481
                614,-800,639
                595,780,-596

                --- scanner 4 ---
                727,592,562
                -293,-554,779
                441,611,-461
                -714,465,-776
                -743,427,-804
                -660,-479,-426
                832,-632,460
                927,-485,-438
                408,393,-506
                466,436,-512
                110,16,151
                -258,-428,682
                -393,719,612
                -211,-452,876
                808,-476,-593
                -575,615,604
                -485,667,467
                -680,325,-822
                -627,-443,-432
                872,-547,-609
                833,512,582
                807,604,487
                839,-516,451
                891,-625,532
                -652,-548,-490
                30,-46,-14
                """;
        Vector<Utility.Scanner> scanners = new Vector<>();
        for (String scanner : test_input.split("--- scanner \\d* ---")) {
            if (scanner.isEmpty()) {
                continue;
            }
            scanners.add(new Utility.Scanner(scanner));
        }
        return scanners;
    }

    private Vector<Utility.Scanner> getInput() {
        Vector<Utility.Scanner> scanners = new Vector<>();
        try {
            File file = new File("src/main/resources/Day" + id + ".txt");
            java.util.Scanner scanner = new java.util.Scanner(file).useDelimiter("--- scanner \\d* ---");
            while (scanner.hasNext()) {
                scanners.add(new Utility.Scanner(scanner.next()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return scanners;
    }

    private Map<Beacon, Beacon> findCommonBeacons(Utility.Scanner first, Utility.Scanner second) {
        Map<Beacon, Beacon> common_beacons = new HashMap<>();
        for (Beacon first_beacon : first.beacons()) {
            for (Beacon second_beacon : second.beacons()) {
                if (countCommonDistances(first_beacon, second_beacon) >= 10) {
                    common_beacons.put(first_beacon, second_beacon);
                    break;
                }
            }
        }
        return common_beacons;
    }

    private Integer countCommonDistances(Beacon first, Beacon second) {
        Set<Integer> distances = new HashSet<>(first.distances());
        distances.retainAll(second.distances());
        return distances.size();
    }

    private Boolean checkTransformation(Map<Beacon, Beacon> common_beacons, Matrix4D transformation) {
        for (Map.Entry<Beacon, Beacon> beacon_pair : common_beacons.entrySet()) {
            Point3D transformed_point = transformation.multiply(beacon_pair.getValue().position());
            if (!beacon_pair.getKey().position().equals(transformed_point)) {
                return false;
            }
        }
        return true;
    }

    private Matrix4D findTransformation(Map<Beacon, Beacon> common_beacons) {
        Map.Entry<Beacon, Beacon> first_entry = common_beacons.entrySet().iterator().next();
        Point3D first_position = first_entry.getKey().position();
        Point3D second_position = first_entry.getValue().position();
        Matrix4D translation_to_origin = new Matrix4D();
        translation_to_origin.set(0, 3, -second_position.x);
        translation_to_origin.set(1, 3, -second_position.y);
        translation_to_origin.set(2, 3, -second_position.z);

        Matrix4D translation_from_origin = new Matrix4D();
        translation_from_origin.set(0, 3, first_position.x);
        translation_from_origin.set(1, 3, first_position.y);
        translation_from_origin.set(2, 3, first_position.z);

        for (Matrix4D rotation_to_direction : rotation_to_directions) {
            for (Matrix4D rotation_around_axis : rotation_around_axes) {
                Matrix4D transformation = translation_from_origin
                        .multiply(rotation_to_direction)
                        .multiply(rotation_around_axis)
                        .multiply(translation_to_origin);
                if (checkTransformation(common_beacons, transformation)) {
                    return transformation;
                }
            }
        }
        return new Matrix4D();
    }

    private Boolean isBeaconInVector(Beacon beacon, Vector<Beacon> beacons) {
        for (Beacon current_beacon : beacons) {
            if (beacon.position().equals(current_beacon.position())) {
                return true;
            }
        }
        return false;
    }

    private Integer countUniqueBeacons(Vector<Utility.Scanner> scanners) {
        Vector<Utility.Scanner> transformed_scanners = transformAllScanners(scanners);

        Vector<Beacon> beacons = new Vector<>();
        for (Utility.Scanner scanner : transformed_scanners) {
            for (Beacon beacon_to_add : scanner.beacons()) {
                if (!isBeaconInVector(beacon_to_add, beacons)) {
                    beacons.add(beacon_to_add);
                }
            }
        }
        return beacons.size();
    }

    private Integer findLargestDistance(Vector<Utility.Scanner> scanners) {
        Vector<Utility.Scanner> transformed_scanners = transformAllScanners(scanners);
        Integer distance = 0;
        for (Utility.Scanner first : transformed_scanners) {
            for (Utility.Scanner second : transformed_scanners){
                distance = Math.max(first.distance(second), distance);
            }
        }
        return distance;
    }

    private Vector<Utility.Scanner> transformAllScanners(Vector<Utility.Scanner> scanners) {
        Vector<Utility.Scanner> transformed_scanners = new Vector<>();
        Vector<Utility.Scanner> to_remove = new Vector<>();
        transformed_scanners.add(scanners.get(0));
        scanners.remove(scanners.get(0));

        int index = 0;
        while (!scanners.isEmpty()) {
            Utility.Scanner transformed = transformed_scanners.get(index++);
            for (Utility.Scanner scanner : scanners) {
                Map<Beacon, Beacon> common_beacons = findCommonBeacons(transformed, scanner);
                if (common_beacons.size() >= 12) {
                    Matrix4D transformation = findTransformation(common_beacons);
                    scanner.transform(transformation);
                    to_remove.add(scanner);
                }
            }
            transformed_scanners.addAll(to_remove);
            scanners.removeAll(to_remove);
            to_remove.clear();
        }
        return transformed_scanners;
    }

    public void runSolutionOneTest() {
        Integer number_of_beacons = countUniqueBeacons(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = countUniqueBeacons(getInput());
    }

    public void runSolutionTwoTest() {
        Integer distance = findLargestDistance(getTestInput());
    }

    public void runSolutionTwo() {
        solution_two = findLargestDistance(getInput());
    }


}
