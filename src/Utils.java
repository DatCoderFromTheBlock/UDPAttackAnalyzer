import java.math.BigInteger;
import java.util.ArrayList;

public class Utils {
	
	/**
	 * Finds all the {@code TcpRequestType} flags a packet has
	 * 
	 * @requires packet protocol to be TCP
	 * @param p packet
	 * @return tcp flags of a packet
	 */
	public static ArrayList<TcpRequestType> getTcpReqFlags(Packet p) {
		ArrayList<TcpRequestType> flags = new ArrayList<>();
		int[] bin = decodeFlags(p.getFlags());
		if (bin[5] == 1)
			flags.add(TcpRequestType.FIN);
		if (bin[4] == 1)
			flags.add(TcpRequestType.SYN);
		if (bin[3] == 1)
			flags.add(TcpRequestType.RST);
		if (bin[2] == 1)
			flags.add(TcpRequestType.PSH);
		if (bin[1] == 1)
			flags.add(TcpRequestType.ACK);
		if (bin[0] == 1)
			flags.add(TcpRequestType.URG);
		return flags;

	}

	/**
	 * Returns an array representing TCP flags
	 * <p>
	 * a[0] = URG
	 * <p>
	 * a[1] = ACK
	 * <p>
	 * a[2] = PSH
	 * <p>
	 * a[3] = RST
	 * <p>
	 * a[4] = SYN
	 * <p>
	 * a[5] = FIN
	 * 
	 * 
	 * @param flags
	 * @return
	 */
	private static int[] decodeFlags(String flags) {
		StringBuilder binS = new StringBuilder(hexToBin(flags));
		int[] bin = new int[6];
		int binI = Integer.parseInt(binS.toString());
		int index = 5;
		while (binI != 0) {
			bin[index] = binI % 10;
			binI /= 10;
			index--;
		}
		return bin;
	}

	/**
	 * Convert a hexadecimal String to a binary String
	 * 
	 * @param s hexadecimal string
	 * @return binary String
	 */
	private static String hexToBin(String s) {
		return new BigInteger(s.substring(2), 16).toString(2);
	}

	public static Packet createPacket(String s) {
		String[] packet = s.split(",");
		for (int i = 0; i < packet.length; i++) {
			packet[i] = packet[i].replace("\"", "");
		}
		return new Packet(packet);
	}
}
