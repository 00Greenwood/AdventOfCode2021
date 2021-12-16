package Utility;

import java.math.BigInteger;
import java.util.Vector;

public class Packet {
    public Integer version = null;
    public Integer type_id = null;
    public BigInteger literal_value = BigInteger.ZERO;

    public Integer lengthTypeID = null;
    public Integer length = null;
    public Vector<Packet> sub_packets = new Vector<>();
}
