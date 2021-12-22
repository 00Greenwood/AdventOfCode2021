package Utility;

import java.math.BigInteger;
import java.util.*;

public class Player {
    private Integer position;
    private Integer score = 0;
    private HashMap<Integer, BigInteger> quantum_position = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, BigInteger>> quantum_scores = new HashMap<>();
    private BigInteger wins = BigInteger.ZERO;

    public Player(Integer starting_square) {
        // Board is treated as 0 to 9;
        position = starting_square - 1;
        // Quantum is treated as 1 to 10;
        quantum_position.put(starting_square, BigInteger.ONE);
        HashMap<Integer, BigInteger> quantum_score = new HashMap<>();
        quantum_score.put(0, BigInteger.ONE);
        quantum_scores.put(starting_square, quantum_score);
    }

    public Integer score() {
        return score;
    }

    public void move(Integer number_of_places_to_move) {
        // Board is treated as 0 to 9;
        position += number_of_places_to_move;
        position %= 10;
        score += position + 1;
    }

    public void quantumMove(Map<Integer, BigInteger> dice_roll_freq) {
        HashMap<Integer, BigInteger> new_quantum_position = new HashMap<>();
        HashMap<Integer, HashMap<Integer, BigInteger>> new_quantum_scores = new HashMap<>();

        // Quantum is treated as 1 to 10;
        for (Integer position : quantum_position.keySet()) {
            BigInteger position_freq = quantum_position.getOrDefault(position, BigInteger.ZERO);
            for (Integer dice_roll : dice_roll_freq.keySet()){
                Integer new_position = ((position + dice_roll - 1) % 10) + 1;
                BigInteger dice_freq = dice_roll_freq.get(dice_roll);
                BigInteger current_freq =  new_quantum_position.getOrDefault(new_position, BigInteger.ZERO);
                BigInteger new_position_freq = position_freq.multiply(dice_freq);
                new_quantum_position.put(new_position, current_freq.add(new_position_freq));
                HashMap<Integer, BigInteger> current_scores =  quantum_scores.get(position);
                HashMap<Integer, BigInteger> current_new_scores =  quantum_scores.getOrDefault(new_position, new HashMap<>());
                for (Integer score : current_scores.keySet()) {
                    Integer new_score = score + new_position;
                    BigInteger current_new_score_freq = current_scores.getOrDefault(new_score, BigInteger.ZERO);
                    current_new_scores.put(new_score, current_new_score_freq.add(new_position_freq));
                }
                new_quantum_scores.put(new_position, current_new_scores);
            }
        }
        quantum_position = new_quantum_position;
        quantum_scores = new_quantum_scores;
    }

    public void countAndRemoveWins(Player other) {
        for (Integer position : quantum_scores.keySet()) {
            HashMap<Integer, BigInteger> quantum_score = quantum_scores.get(position);
            Set<Integer> to_remove = new HashSet<>();
            for (Integer score : quantum_score.keySet()) {
                if (score >= 21) {
                    // Remove score later.
                    to_remove.add(score);
                    // Add to the number of wins.
                    BigInteger score_freq = quantum_score.get(score);
                    wins = wins.add(score_freq);
                    // Remove winning positions.
                    BigInteger new_position_freq = quantum_position.get(position).subtract(score_freq);
                    quantum_position.put(position, new_position_freq);
                }
            }
            // Remove all the scores above 21 now.
            to_remove.forEach(quantum_score::remove);
        }
    }
}
