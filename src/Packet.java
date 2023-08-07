
public class Packet {

	private final int no;
	private final float time;
	private final String sourceIP;
	private final String destIP;
	private final String sourcePort;
	private final String destPort;
	private final String protocol;
	private final String icmp;
	private final int length;
	private final String flags;
	

	public Packet(String[] packet) {
		this.no = Integer.parseInt(packet[0]);
		this.time = Float.parseFloat(packet[1]);
		this.sourceIP = packet[2];
		this.destIP = packet[3];
		this.sourcePort = packet[4];
		this.destPort = packet[5];
		this.protocol = packet[6];
		this.icmp = packet[7];
		this.length = Integer.parseInt(packet[8]);
		this.flags = packet[9];
	}

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @return the sourceIP
	 */
	public String getSourceIP() {
		return sourceIP;
	}

	/**
	 * @return the destIP
	 */
	public String getDestIP() {
		return destIP;
	}

	/**
	 * @return the sourcePort
	 */
	public String getSourcePort() {
		return sourcePort;
	}

	/**
	 * @return the destPort
	 */
	public String getDestPort() {
		return destPort;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @return the icmp
	 */
	public String getIcmp() {
		return icmp;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the flags
	 */
	public String getFlags() {
		return flags;
	}

	@Override
	public String toString() {
		return "Packet [no=" + no + ", time=" + time + ", sourceIP=" + sourceIP + ", destIP=" + destIP
				+ ", sourcePort=" + sourcePort + ", destPort=" + destPort + ", protocol=" + protocol + ", icmp=" + icmp
				+ ", length=" + length + ", flags=" + flags + "]";
	}

}
