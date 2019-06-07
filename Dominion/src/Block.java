

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Block extends GameObject{

	Texture tex = Game.getInstance();
	private int type;
	
	public Block(float x, float y, int type, ObjectId id) {
		super(x, y, id);
		this.type = type;
		
	}

	
	public void tick(LinkedList<GameObject> object) {
		
		
	}

	public void render(Graphics g) {
		
//		g.setColor(Color.RED);
//		g.drawRect((int)x, (int)y, 64, 64);
		
		g.drawImage(tex.block[type], (int)x, (int)y, null);
		
	}


	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 64, 64);
	}


}
