import java.util.ArrayList;

public class Ataque {
    private double frequencia;
    private double threshold;
    private ArrayList<Packet> packets;


    public Ataque (double frequencia, double threshold, Packet pacote){
        this.frequencia = frequencia;
        this.threshold = threshold;
        packets.add(pacote);
    }
    public double getFrequencia(){
        return frequencia;
    }
    public double getThreshold(){
        return threshold;
    }
    public ArrayList<Packet> getPackets(){
        return packets;
    }
    public void addPacket(Packet packet) {
    	packets.add(packet);
    }
    public double minValue() {
    	return frequencia - threshold;
    }
    
    public double maxValue() {
    	return frequencia + threshold;
    }
    
}