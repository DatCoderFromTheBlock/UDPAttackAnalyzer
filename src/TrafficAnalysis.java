import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class TrafficAnalysis {

	private static HashMap<SourceDestination, ArrayList<Packet>> packets;
	private final static double MAX_ACCEPTABLE_TIME_DIFF = 3;
	private final static double PACKET_THRESHHOLD = 0.5;

	public static void main(String argv[]) throws Exception {

		packets = new HashMap<SourceDestination, ArrayList<Packet>>();

		final String dir = System.getProperty("user.dir");
		TarArchiveInputStream tarInput = new TarArchiveInputStream(new GzipCompressorInputStream(
				new FileInputStream(dir + "\\src\\SYN_attack_B_testing_trace.csv.tar.gz")));
		TarArchiveEntry file = tarInput.getNextTarEntry();

		// Printing CSV contents
		BufferedReader br = new BufferedReader(new InputStreamReader(tarInput)); // Read directly from tarInput

		String line;
		int counter = 0;
		List<Integer> list = new ArrayList<Integer>();

		br.readLine();
		while ((line = br.readLine()) != null) {
			// PREPARES THE DATA TO CREATE OBJECTS THAT REPRESENT THE PACKETS
			String[] lineRaw = line.split(",");
			for (int i = 0; i < lineRaw.length; i++) {
				lineRaw[i] = lineRaw[i].replace("\"", "");
			}

			// IF SOURCE IP & DESTINATION IP ALREADY EXITS IN DATA STRUCTURE
			Packet packet = new Packet(lineRaw);
			SourceDestination sD = new SourceDestination(packet.getSourceIP(), packet.getDestIP());
			if (packets.containsKey(sD)) {
				packets.get(sD).add(packet);
			} else {
				ArrayList<Packet> newPackets = new ArrayList<Packet>();
				newPackets.add(packet);
				packets.put(new SourceDestination(packet.getSourceIP(), packet.getDestIP()), newPackets);
			}
		}
		
	
		
		br.close();

		Iterator iterator = packets.entrySet().iterator();
		
		
		int sum = 0;
		int counter1 = 0;
		
		while (iterator.hasNext()) {
			HashMap.Entry me = (HashMap.Entry) iterator.next();
			SourceDestination keyValue = (SourceDestination) me.getKey();
			// SourceDestination keyValue = new SourceDestination("52.207.72.2",
			// "192.168.1.230");

			ArrayList<Packet> possibleSYNFlood = new ArrayList<Packet>();
			double timeDiff = 0;

			Utils utils = new Utils();

			

			for (int i = 0; i < packets.get(keyValue).size(); i++) {
				Packet packet = packets.get(keyValue).get(i);

				if (packet.getProtocol().equals("TCP")) {
					
					if(i != 0)
						timeDiff = packet.getTime() - packets.get(keyValue).get(i - 1).getTime();
					if (timeDiff < MAX_ACCEPTABLE_TIME_DIFF &&(utils.getTcpReqFlags(packet).contains(TcpRequestType.SYN)
							|| utils.getTcpReqFlags(packet).contains(TcpRequestType.RST))
							&& !utils.getTcpReqFlags(packet).contains(TcpRequestType.ACK)
							) {
						possibleSYNFlood.add(packet);
					}
					
				}

			}

			if (!possibleSYNFlood.isEmpty()) {
				for (int j = 1; j < possibleSYNFlood.size(); j++) {
					System.out.println(utils.getTcpReqFlags(possibleSYNFlood.get(j)) + ","
							+ possibleSYNFlood.get(j).getTime() + ","
							+ new BigDecimal(possibleSYNFlood.get(j).getTime() - possibleSYNFlood.get(j - 1).getTime())
									.setScale(2, RoundingMode.HALF_UP).doubleValue() + "\n");
				}
			}
			counter1 += possibleSYNFlood.size();
		}
		System.out.println(counter1);
	}
}
