import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Projectile{
	
	private float x, y;
	private int velX, speed;
	private boolean visible;
	
	Handler handler = new Handler();
	
	public Projectile(float x, float y, int velX, int speed) {
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.speed = speed;
		visible = true;
	}

	public void tick(LinkedList<GameObject> object) {
		x += velX * speed;
		
		if(x > Game.level.getWidth() * 64 || x < 0)
			setVisible(false);
		
		Collision(object);
	}
	
	public void Collision(LinkedList<GameObject> object){
		
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ObjectId.Block){
				
				if(getBounds().intersects(tempObject.getBounds())){
					setVisible(false);
					//object.remove(tempObject);
					
				}
				
			}
			
		}
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 16, 10);

	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 10);
	}

}