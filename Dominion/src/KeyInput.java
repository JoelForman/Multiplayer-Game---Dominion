import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter{

	Handler handler;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.getObjects().size(); i++){
			GameObject tempObject = handler.getObjects().get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				
				if(key == KeyEvent.VK_D){
					tempObject.setRight(true); //tempObject.setVelX(5);
					tempObject.setLeft(false);
				}
				if(key == KeyEvent.VK_A){
					tempObject.setLeft(true); //tempObject.setVelX(-5);
					tempObject.setRight(false);
				}
				if(key == KeyEvent.VK_W && !tempObject.isJumping()){
					tempObject.setJumping(true);
					tempObject.setVelY(-10);
				}
				if(key == KeyEvent.VK_ENTER){
					tempObject.setShooting(true);
				}
				
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
		
	}
	
	public void keyReleased(KeyEvent e){
		
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.getObjects().size(); i++){
			GameObject tempObject = handler.getObjects().get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				
				if(key == KeyEvent.VK_D) tempObject.setRight(false); //tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) tempObject.setLeft(false); //tempObject.setVelX(0);
				//if(key == KeyEvent.VK_CONTROL) tempObject.setHeight(tempObject.getHeight());
				if(key == KeyEvent.VK_ENTER){
					tempObject.setShooting(false);
				}
			}
		}
		
	}
	
}
