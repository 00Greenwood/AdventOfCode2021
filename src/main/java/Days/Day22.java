package Days;

import Utility.Cube;
import Utility.CubeInstruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Day22 extends Day<BigInteger> {

    public Day22() {
        super("22");
    }

    private Vector<CubeInstruction> getTestInput() {
        String test_input = """
                on x=-20..26,y=-36..17,z=-47..7
                on x=-20..33,y=-21..23,z=-26..28
                on x=-22..28,y=-29..23,z=-38..16
                on x=-46..7,y=-6..46,z=-50..-1
                on x=-49..1,y=-3..46,z=-24..28
                on x=2..47,y=-22..22,z=-23..27
                on x=-27..23,y=-28..26,z=-21..29
                on x=-39..5,y=-6..47,z=-3..44
                on x=-30..21,y=-8..43,z=-13..34
                on x=-22..26,y=-27..20,z=-29..19
                off x=-48..-32,y=26..41,z=-47..-37
                on x=-12..35,y=6..50,z=-50..-2
                off x=-48..-32,y=-32..-16,z=-15..-5
                on x=-18..26,y=-33..15,z=-7..46
                off x=-40..-22,y=-38..-28,z=23..41
                on x=-16..35,y=-41..10,z=-47..6
                off x=-32..-23,y=11..30,z=-14..3
                on x=-49..-5,y=-3..45,z=-29..18
                off x=18..30,y=-20..-8,z=-3..13
                on x=-41..9,y=-7..43,z=-33..15
                on x=-54112..-39298,y=-85059..-49293,z=-27449..7877
                on x=967..23432,y=45373..81175,z=27513..53682
                """;
        Vector<CubeInstruction> instructions = new Vector<>();
        for (String line : test_input.split("\n")) {
            instructions.add(new CubeInstruction(line));
        }
        return instructions;
    }

    private Vector<CubeInstruction> getInput() {
        Vector<CubeInstruction> instructions = new Vector<>();
        try {
            File file = new File("src/main/resources/Day" + id + ".txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(new CubeInstruction(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return instructions;
    }

    private Integer countOnCubes(HashMap<String, Boolean> reactor) {
        int count = 0;
        for (String key : reactor.keySet()) {
            count += reactor.get(key) ? 1 : 0;
        }
        return count;
    }

    private Cube initializeReactorWithLimit(Vector<CubeInstruction> instructions) {
        Cube reactor = new Cube(-50,50,-50,50,-50,50);
        for (CubeInstruction instruction : instructions) {
            if (instruction.turn_on) {
                reactor.intersect(instruction.cube);
            } else {
                for (Cube sub_cubes : reactor.subCubes()) {
                    sub_cubes.intersect(instruction.cube);
                }
            }
        }
        return reactor;
    }

    public void runSolutionOneTest() {
        Vector<CubeInstruction> instructions = getTestInput();
        Cube reactor = initializeReactorWithLimit(instructions);
        BigInteger number_of_on_cubes = reactor.volume().negate();
    }

    public void runSolutionOne() {
        Vector<CubeInstruction> instructions = getInput();
        Cube reactor = initializeReactorWithLimit(instructions);
        //solution_one = countOnCubes(reactor);
    }

    public void runSolutionTwoTest() {
        Integer test = 0;//getTestInput().get(0);
    }

    public void runSolutionTwo() {
        solution_two = BigInteger.ZERO;//getInput().get(0);
    }


}
