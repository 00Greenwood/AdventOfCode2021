import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class DaySixteen extends Day<BigInteger> {

    public DaySixteen() {
        super("16");
    }

    private String hexadecimalToBinary(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    private String getTestInput() {
        String test_input = "9C005AC2F8F0";
        return hexadecimalToBinary(test_input);
    }

    private String getInput() {
        String input = "";
        try {
            File file = new File("src/main/resources/DaySixteen.txt");
            Scanner scanner = new Scanner(file);
            input = scanner.nextLine();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hexadecimalToBinary(input);
    }

    private Pair<BigInteger, Integer> decodeLiteralValue(String binary) {
        StringBuilder value = new StringBuilder();
        int index = 0;
        String sub_binary;
        do {
            sub_binary = binary.substring(index, index + 5);
            value.append(sub_binary.substring(1));
            index += 5;
        } while (sub_binary.startsWith("1"));

        return new Pair<>(new BigInteger(value.toString(), 2), index);
    }

    private Integer decodeSubPackets(Packet packet, String binary) {
        Integer index = 0;
        switch (packet.lengthTypeID) {
            case 0 -> { // Length is a 15-bit number representing the number of bits in the sub-packets
                while (index < packet.length) {
                    Pair<Packet, Integer> packet_and_index = decodeBinaryToPacket(binary.substring(index));
                    packet.sub_packets.add(packet_and_index.first);
                    index += packet_and_index.second;
                }
            }
            case 1 -> { // Length is an 11-bit number representing the number of sub-packets
                for (int i = 0; i < packet.length; i++) {
                    Pair<Packet, Integer> packet_and_index = decodeBinaryToPacket(binary.substring(index));
                    packet.sub_packets.add(packet_and_index.first);
                    index += packet_and_index.second;
                }
            }
        }
        return index;
    }

    private Integer decodeLengthAndSubPackets(Packet packet, String binary) {
        // Length Type ID
        String lengthTypeID = binary.substring(6, 7);
        packet.lengthTypeID = Integer.valueOf(lengthTypeID, 2);

        // Either length of 15 bits or 11 bits.
        int sub_string_length = 7 + (packet.lengthTypeID.equals(0) ? 15 : 11);
        String length = binary.substring(7, sub_string_length);
        packet.length = Integer.valueOf(length, 2);
        return sub_string_length + decodeSubPackets(packet, binary.substring(sub_string_length));
    }

    private Pair<Packet, Integer> decodeBinaryToPacket(String binary) {
        Packet packet = new Packet();
        // Version
        String version = binary.substring(0, 3);
        packet.version = Integer.valueOf(version, 2);
        // Type ID
        String typeID = binary.substring(3, 6);
        packet.type_id = Integer.valueOf(typeID, 2);

        int index = 0;
        switch (packet.type_id) {
            case 4 -> { // Literal Value
                Pair<BigInteger, Integer> value_and_index = decodeLiteralValue(binary.substring(6));
                packet.literal_value = value_and_index.first;
                index = 6 + value_and_index.second;
            }

            case 0 -> { // Sum
                index = decodeLengthAndSubPackets(packet, binary);
                for (Packet sub_packet : packet.sub_packets) {
                    packet.literal_value = packet.literal_value.add(sub_packet.literal_value);
                }
            }
            case 1 -> { // Product
                index = decodeLengthAndSubPackets(packet, binary);
                packet.literal_value = BigInteger.ONE;
                for (Packet sub_packet : packet.sub_packets) {
                    packet.literal_value = packet.literal_value.multiply(sub_packet.literal_value);
                }
            }
            case 2 -> { // Minimum
                index = decodeLengthAndSubPackets(packet, binary);
                Vector<BigInteger> values = new Vector<>();
                for (Packet sub_packet : packet.sub_packets) {
                    values.add(sub_packet.literal_value);
                }
                packet.literal_value = Collections.min(values);
            }
            case 3 -> { // Maximum
                index = decodeLengthAndSubPackets(packet, binary);
                Vector<BigInteger> values = new Vector<>();
                for (Packet sub_packet : packet.sub_packets) {
                    values.add(sub_packet.literal_value);
                }
                packet.literal_value = Collections.max(values);
            }
            case 5 -> { // Greater than
                index = decodeLengthAndSubPackets(packet, binary);
                BigInteger first = packet.sub_packets.firstElement().literal_value;
                BigInteger second = packet.sub_packets.lastElement().literal_value;
                packet.literal_value = first.compareTo(second) > 0 ? BigInteger.ONE : BigInteger.ZERO;
            }
            case 6 -> { // Less than
                index = decodeLengthAndSubPackets(packet, binary);
                BigInteger first = packet.sub_packets.firstElement().literal_value;
                BigInteger second = packet.sub_packets.lastElement().literal_value;
                packet.literal_value = first.compareTo(second) < 0 ? BigInteger.ONE : BigInteger.ZERO;
            }
            case 7 -> { // Equal to
                index = decodeLengthAndSubPackets(packet, binary);
                BigInteger first = packet.sub_packets.firstElement().literal_value;
                BigInteger second = packet.sub_packets.lastElement().literal_value;
                packet.literal_value = first.compareTo(second) == 0 ? BigInteger.ONE : BigInteger.ZERO;
            }
        }
        return new Pair<>(packet, index);
    }

    private Integer sumVersions(Packet packet) {
        Integer version_sum = packet.version;
        for (Packet sub_packet : packet.sub_packets) {
            version_sum += sumVersions(sub_packet);
        }
        return version_sum;
    }

    private Integer sumVersions(String binary) {
        Pair<Packet, Integer> packet_and_index = decodeBinaryToPacket(binary);
        return sumVersions(packet_and_index.first);
    }

    public void runSolutionOneTest() {
        Integer version_sum = sumVersions(getTestInput());
    }

    public void runSolutionOne() {
        solution_one = BigInteger.valueOf(sumVersions(getInput()));
    }

    public void runSolutionTwoTest() {
        Pair<Packet, Integer> packet_and_index = decodeBinaryToPacket(getTestInput());
        BigInteger value = packet_and_index.first.literal_value;
    }

    public void runSolutionTwo() {
        Pair<Packet, Integer> packet_and_index = decodeBinaryToPacket(getInput());
        solution_two = packet_and_index.first.literal_value;
    }


}
