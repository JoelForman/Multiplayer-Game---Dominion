
public abstract class Packet {
	
	public byte packetId;
	
	public Packet(int packetId){
		this.packetId = (byte) packetId;
	}
	
	
	public abstract void writeData(GameClient client);
	
	public abstract void writeData(GameServer server);

	public String readData(byte[] data){
		String message = new String(data).trim();
		return message.substring(2);
	}
	
	public abstract byte[] getData();
	
	public static PacketTypes lookupPacket(String packetId){
		try{
			return lookupPacket(Integer.parseInt(packetId));
		}catch(NumberFormatException e){
			return PacketTypes.INVALID;
		}
	}
	
	public static PacketTypes lookupPacket(int id){
		for(PacketTypes p : PacketTypes.values()){
			if(p.getId() == id)
				return p;
		}
		
		return PacketTypes.INVALID;
	}
}

