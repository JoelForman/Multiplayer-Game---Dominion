import java.net.InetAddress;
import java.util.LinkedList;

public class PlayerMP extends Player{
	
	public InetAddress ipAddress;
	public int port;
	String username;
	
	public PlayerMP(float x, float y, int type, String username, Handler handler, ObjectId id, InetAddress ipAddress, int port) {
		super(x, y, type, username, handler, id);
		this.ipAddress = ipAddress;
		this.port = port;
		this.username = username;
		
	}
	
	public PlayerMP(float x, float y, int type, String username, ObjectId id, InetAddress ipAddress, int port) {
		super(x, y, type, username, null, id);
		this.ipAddress = ipAddress;
		this.port = port;
		this.username = username;
		
	}
	
	@Override
	public void tick(LinkedList<GameObject> object){
		super.tick(object);
	}

}