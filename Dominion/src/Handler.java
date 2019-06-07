import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class Handler {

	private LinkedList<GameObject> object = new LinkedList<GameObject>();
	public LinkedList<String[][]> levels = new LinkedList<String[][]>();
	
	private GameObject tempObject;
	
	private int currentLevel = 0;
	
	/////////////////////// UPDATES EACH OBJECT/////////////////////
	public void tick(){
		
		for(int i = 0; i < getObjects().size(); i++){
			
			tempObject = getObjects().get(i);
			
			//if(tempObject.getId() == ObjectId.Bullet)
				//object.remove(tempObject);
			
			tempObject.tick(getObjects());
		}
		
	}
	
	public synchronized LinkedList<GameObject> getObjects(){
		return this.object;
	}
	
	///////////////////// RENDERS EACH OBJECT /////////////////////
	public void render(Graphics g){
		for(int i = 0; i < getObjects().size(); i++){
			tempObject = getObjects().get(i);
			
			tempObject.render(g);
		}
	}
	
	public void render(GameObject object, Graphics g){
		
		object.render(g);
		
	}
	
	public void addObject(GameObject object){
		this.getObjects().add(object);
	}
	
	public void removeObject(GameObject object){
		this.getObjects().remove(object);
	}
	
	///////////////////////// INITIALIZES EACH LEVEL ////////////////////////
	public void createLevel(){
		
		String[][] level1 = {
				{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "P", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "1", "1", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0"},
				{"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"}
				};
		
			levels.add(level1);
	}
	
	/////////////////////////////// DRAWS EACH ARRAY OF LEVELS //////////////////////
	public void drawLevel(int level){
		
		for(int rows = 0; rows < levels.get(level).length; rows++){
			for(int columns = 0; columns < levels.get(level)[0].length; columns++){
				
				if(levels.get(level)[rows][columns].equals("1"))
					addObject(new Block(columns*64, rows*64, 0, ObjectId.Block));
				if(levels.get(level)[rows][columns].equals("P"))
					addObject(new Player(columns*64, rows*96, 0, "Latte", this, ObjectId.Player));
				
			}
		}
		
	}
	
	public void setLevel(int level){
		
		currentLevel = level;
		
	}
	
	public int getLevel(){
		
		return currentLevel;
		
	}

	public void removePlayerMP(String username) {
		int index = 0;
		for(GameObject g: getObjects()){
			if(g instanceof PlayerMP && ((PlayerMP)g).getUsername().equals(username)){
				break;
			}
			index++;
		}
		this.getObjects().remove(index);
	}
	
	private int getPlayerMPIndex(String username){
		int index = 0;
		for(GameObject g : getObjects()){
			if(g instanceof PlayerMP && ((PlayerMP)g).getUsername().equals(username)){
				break;
			}
		index++;
		}
		return index;
	}
	
	public void movePlayer(String username, int x, int y){
		int index = getPlayerMPIndex(username);
		this.getObjects().get(index).x = x;
		this.getObjects().get(index).y = y;
	}
	
}
