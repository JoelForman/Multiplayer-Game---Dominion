
public class Camera {

	private float x, y;
	GameObject player;
	
	public Camera(float x, float y, GameObject player){
		this.x = x;
		this.y = y;
		this.player = player;
	}
	
	public void tick() {
		
		x = -player.getX() + Game.WIDTH/2;
			
	}
	
	public GameObject getPlayer() {
		return player;
	}

	public void setPlayer(GameObject player) {
		this.player = player;
	}

	public void setX(float x){
		this.x = x;
	}
	public float getX(){
		return x;
	}
	public void setY(float x){
		this.y = y;
	}
	public float getY(){
		return y;
	}
	
}
