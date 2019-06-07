import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 3278250411764951060L;
	
	private boolean running = false;
	public boolean debug = false;
	private Thread thread;
	
	public static int WIDTH, HEIGHT;
	
	public static BufferedImage level = null;//, clouds = null;
	
	public WindowHandler windowhandler;
	
	public Handler handler;
	Camera cam;
	public static Texture tex = new Texture();
	public static Window window;
	
	public static Game game;
	
	Player player;
	
	public GameClient socketClient;
	public GameServer socketServer;
	
	public Game(){
		
	}
	
	public static void main(String[] args){
		
		window = new Window(1280, 768, "Dominion V0.1.1", new Game());
		
	}
	
	private void init(){
		
		game = this;
		
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png"); //loads the level image
		//clouds = loader.loadImage("/bkg_clouds.png"); //loads background
		
		handler = new Handler();
		
		//tex = new Texture();
		
		loadImageLevel(level);
		
		player = new PlayerMP(100, 0, 0, JOptionPane.showInputDialog(this, "Enter your username: "), handler, ObjectId.Player, null, -1);
		cam = new Camera(0, 0, player);
		
		windowhandler = new WindowHandler(this);
		
		handler.addObject(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), (int)player.x, (int)player.y);
		if(socketServer != null){
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		//socketClient.sendData("ping".getBytes());
		
		//handler.createLevel();
		//handler.drawLevel(handler.getLevel());
		
		this.addKeyListener(new KeyInput(handler));
		
		loginPacket.writeData(socketClient);
	}
	
	public synchronized void start(){
		
		if(running)
			return;
		
		if(JOptionPane.showConfirmDialog(this, "Do you want to run this server?") == 0){
			socketServer = new GameServer(this);
			socketServer.start();
			
		}
		
		socketClient = new GameClient(this, "2602:306:c427:3920:159f:ae9c:23df:9f98");
		socketClient.start();
		
		running = true;
		thread = new Thread(this);
		thread.start();
		

		
	}
	
	/////////////////// GAME LOOP ////////////////////////
	public void run() {
		
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames + " TICKS: " + updates);
				window.setTitle("Dominion V0.1.1   FPS: " + frames);
				frames = 0;
				updates = 0;
				
			}
		}
	}

	private void tick(){
		
		handler.tick();
		for(int i = 0; i < handler.getObjects().size(); i++){
			if(handler.getObjects().get(i).getId() == ObjectId.Player){
				cam.tick();
			}
			ArrayList<Projectile> projectiles = handler.getObjects().get(i).getProjectiles();
			for (int j = 0; j < projectiles.size(); j++) {
				Projectile p = projectiles.get(j);
				if (p.isVisible() == true) {
					p.tick(handler.getObjects());
				} else {
					projectiles.remove(j);
				}
			}
		}
		
		
		
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			
			this.createBufferStrategy(3);
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		/////////////////////////////////////////////////////
		/*
		 * Draw Everything Here
		 */
		
		
		g.setColor(new Color(25, 191, 224));
		g.fillRect(0,  0,  getWidth(), getHeight());
		
		g2d.translate(cam.getX(), cam.getY());
		
	//	for(GameObject object: handler.object){
			Iterator<GameObject> iter = handler.getObjects().iterator();

			while (iter.hasNext()) {
			    GameObject str = iter.next();
				//	if(object.getX() < -cam.getX() + Game.WIDTH && object.getX() > -cam.getX()-64){
				handler.render(str, g);
				//  }
				
				ArrayList<Projectile> projectiles = str.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = projectiles.get(i);
					p.render(g);
				}
			}
			

			
		//}

		
		//handler.render(g);
			
		g2d.translate(-cam.getX(), -cam.getY());
		
			
		
		////////////////////////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	
	private void loadImageLevel(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx < h; xx++){
			for(int yy = 0; yy < w; yy++){
				int pixel = image.getRGB(xx, yy);
				int alpha = (pixel >> 32) & 0xff;
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				

				
				//if(red == 0 && green == 255 && blue == 0)handler.addObject(new BackGroundImage(xx*64, yy*64, 2, ObjectId.BackGroundImage));

				//draws different textured blocks for green, red, and white pixels
				if(red == 255 && green == 255 && blue == 255) handler.addObject(new Block(xx*64, yy*64, 0, ObjectId.Block));
				if(red == 255 && green == 0 && blue == 0) handler.addObject(new Block(xx*64, yy*64, 1, ObjectId.Block));
				
				//Draws player for each blue pixel
				//if(red == 0 && green == 0 && blue == 255) handler.addObject(player);
			}
		}
	}
	
	public static  Texture getInstance(){
		return tex;

	}
	
}
