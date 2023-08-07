
public class SourceDestination {
	public String sourceIP;
	public String destinationIP;
	
	public SourceDestination(String sourceIP, String destinationIP) {
		this.sourceIP = sourceIP;
		this.destinationIP = destinationIP;
	}

	public String getSourceIP() {
		return sourceIP;
	}
	public String getDestinationIP() {
		return destinationIP;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        SourceDestination sd = (SourceDestination) o;
        return (sourceIP.equals(sd.sourceIP) 
          && destinationIP.equals(sd.destinationIP));
    }
	
	@Override
	public int hashCode() {
	    return (int) sourceIP.hashCode() * destinationIP.hashCode();
	}
}
