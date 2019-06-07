
public enum PacketTypes {
	INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02);
	
	private int packetId;
	private PacketTypes(int packetId){
		this.packetId = packetId;
	}
	
	public int getId(){
		return packetId;
	}
}
