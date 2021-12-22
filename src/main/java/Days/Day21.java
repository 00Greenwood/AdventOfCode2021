package Days;

import Utility.Player;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day21 extends Day<BigInteger> {

    public Day21() {
        super("21");
    }

    private Integer playDiracDeterministicDice(Player one, Player two) {

        LinkedList<Player> turn_order = new LinkedList<>();
        turn_order.add(one);
        turn_order.add(two);

        int dice = 0; // Treat as 0 to 99;
        int dice_rolls = 0;

        for (int turn = 0; turn < 500; turn++) {
            Player player = turn_order.poll();
            turn_order.add(player);
            dice = ((9 * turn + 3) % 100) + 3;
            dice_rolls += 3;
            player.move(dice);
            if (player.score() >= 1000) {
                Player other_player = turn_order.peek();
                return other_player.score() * dice_rolls;
            }
        }

        return 0;
    }

    private BigInteger playDiracQuantumDice(Player one, Player two) {
        Map<Integer, BigInteger> dice_roll_freq = new HashMap<>();
        for (int dice_one = 1; dice_one <= 3; dice_one++) {
            for (int dice_two = 1; dice_two <= 3; dice_two++) {
                for (int dice_three = 1; dice_three <= 3; dice_three++) {
                    int dice_score = dice_one + dice_two + dice_three;
                    dice_roll_freq.put(dice_score, dice_roll_freq.getOrDefault(dice_score, BigInteger.ZERO).add(BigInteger.ONE));
                }
            }
        }

        BigInteger one_wins = BigInteger.ZERO;
        BigInteger two_wins = BigInteger.ZERO;

        for (int turn = 0; turn < 50; turn++) {
            one.quantumMove(dice_roll_freq);
            one.countAndRemoveWins(two);
            two.quantumMove(dice_roll_freq);
            two.countAndRemoveWins(one);
        }

        return BigInteger.ZERO;
    }

    public void runSolutionOneTest() {
        Integer end_score = playDiracDeterministicDice(new Player(4), new Player(8));
    }

    public void runSolutionOne() {
        Integer end_score = playDiracDeterministicDice(new Player(2), new Player(10));
        solution_one = BigInteger.valueOf(end_score);
    }

    public void runSolutionTwoTest() {
        BigInteger end_score = playDiracQuantumDice(new Player(4), new Player(8));
    }

    public void runSolutionTwo() {
        solution_two = BigInteger.ZERO;
    }


}
