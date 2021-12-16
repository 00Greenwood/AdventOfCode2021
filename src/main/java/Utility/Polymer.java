package Utility;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Polymer {
    public Map<Character, BigInteger> quantity = new HashMap<>();
    public Map<String, Character> map = new HashMap<>();
    public Map<String, BigInteger> pair_quantity = new HashMap<>();

    public Polymer(String template, String pairs) {
        for (String pair : pairs.split("\n")) {
            String[] first_and_second = pair.split(" -> ");
            map.put(first_and_second[0], first_and_second[1].charAt(0));
            pair_quantity.put(first_and_second[0], BigInteger.ZERO);
        }
        for (char ch : template.toCharArray()) {
            quantity.put(ch, quantity.getOrDefault(ch, BigInteger.ZERO).add(BigInteger.ONE));
        }
        for (int i = 0; i < template.length() - 1; i++) {
            String pair = template.substring(i, i + 2);
            pair_quantity.put(pair, pair_quantity.get(pair).add(BigInteger.ONE));
        }
    }
}
