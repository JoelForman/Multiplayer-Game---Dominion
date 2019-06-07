import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public class Player extends GameObject{

	private float width = 64, height = 64;
	
	private int speed = 8;
	
	private float gravity = 0.25f;
	private final float MAX_SPEED = 1000;
	
	//texture number
	private int type;
	
	private Handler handler;
	
	String username;
	
	Texture tex = Game.getInstance();
	
	private Animation playerWalk;
	
	public Player(float x, float y, int type, String username, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.type = type;
		this.username = username;
		
		playerWalk = new Animation(10, tex.player[0]);
	}

	public void tick(LinkedList<GameObject> object) {
		
		if(handler != null){
			// updates player position or movement
			x += velX;
			y += velY;
			
			if(right)
				velX = getSpeed();
			else if(left)
				velX = -getSpeed();
			else
				velX = 0;
			
			
			if(isShooting)
				projectiles.add(new Projectile((int)x+26, (int)y+32, getFacing(), 12));
				
			
			// adds gravity if player is not on the ground
			if(falling || jumping){
				velY += gravity;
				
				if(velY > MAX_SPEED)
					velY = MAX_SPEED;
			}
		
		
			Collision(object);
			
			Packet02Move packet = new Packet02Move(this.getUsername(), (int)this.x, (int)this.y);
			packet.writeData(Game.game.socketClient);
		}
		playerWalk.runAnimation();
		
	}
	
	////////////////// COLLISION DETECTION ////////////////
	
	public int getSpeed() {

		return this.speed;
		
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public void Collision(LinkedList<GameObject> object){
		
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.Bullet){
				
				//TOP
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY() + 64;
					velY = 0;
				}
				
				//BOTTOM
				if(getBoundsBot().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				}
				else
					falling = true;
				
				//RIGHT
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - 64;
				}
				
				//LEFT
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + 64;
				}
				
			}
			
		}
		
	}
	
	
	////////////////// DRAWS PLAYER ////////////////////

	public void render(Graphics g) {
		
//		g.setColor(Color.blue);
//		g.fillRect((int)x, (int)y, (int)width, (int)height);
		
		Graphics2D g2d = (Graphics2D) g;
		
//		g2d.setColor(Color.RED);
//		g2d.draw(getBoundsBot());
//		g2d.draw(getBoundsLeft());
//		g2d.draw(getBoundsRight());
//		g2d.draw(getBoundsTop());
		
		if(velX != 0)
			playerWalk.drawAnimation(g, (int)x, (int)y);
		
		g.drawImage(tex.player[type], (int)x, (int)y, null);

		
	}

	/////////////////////// COLLISION BOXES ////////////////////////
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	
	public Rectangle getBoundsBot() {
		return new Rectangle((int) ((int)x+(width/2)-(width/2)/2), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int)y, (int)width/2, (int)height/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int)x+width-5), (int)y+5, (int)5, (int)height-10);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
	}

	public String getUsername(){
		return this.username;
	}
	
}
