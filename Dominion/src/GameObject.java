import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class GameObject {

	protected float x, y, width, height;
	protected ObjectId id;
	protected float velX = 0, velY = 0;
	protected boolean falling = true;
	protected boolean jumping = false;
	protected boolean left = false;
	protected boolean right = false;
	protected int facing = 1;
	protected boolean isShooting = false;
	
	protected ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	

	public GameObject(float x, float y, ObjectId id){
		
		this.x = x;
		this.y = y;
		this.id = id;
		
	}
	
	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public void setRight(boolean b){
		right = b;
		if(b) facing = 1;
	}
	
	
	public void setLeft(boolean b){
		left = b;
		if(b) facing = -1;
	}
	
	
	public float getX() {
		
		return x;
		
	}

	public int getFacing(){
		return facing;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getY() {
		
		return y;
		
	}

	
	public void setX(float x) {

		this.x = x;
		
	}

	
	public void setY(float y) {
		
		this.y = y;
		
	}

	
	public float getVelX() {

		return velX;
		
	}

	
	public float getVelY() {
		
		return velY;
		
	}

	
	public void setVelX(float velX) {

		this.velX = velX;
		
	}

	
	public void setVelY(float velY) {
		
		this.velY = velY;
		
	}

	
	public ObjectId getId() {
		return id;
	}
	
	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setShooting(boolean b) {
		
		isShooting = b;
		
	}
	
	public void isShooting(){
		
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
}
