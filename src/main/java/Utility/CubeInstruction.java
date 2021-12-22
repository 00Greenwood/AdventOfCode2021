package Utility;

public class CubeInstruction {
    public Boolean turn_on;
    public Cube cube;

    public CubeInstruction(String input) {
        String[] inputs = input.split("\sx=|,y=|,z=|\\.\\.");
        turn_on = inputs[0].equals("on");
        cube = new Cube(
                Integer.valueOf(inputs[1]),
                Integer.valueOf(inputs[2]),
                Integer.valueOf(inputs[3]),
                Integer.valueOf(inputs[4]),
                Integer.valueOf(inputs[5]),
                Integer.valueOf(inputs[6])
        );
    }
}
