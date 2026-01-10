package game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.crop.CropManager;
import game.crop.CropType;
import game.entity.Player;
import game.object.FigureHead;
import game.object.GameObject;
import game.object.House;
import game.object.ObjectManager;
import game.object.Shop;
import game.object.Well;
import game.tile.SoilState;
import game.tile.TileManager;
import game.time.GameTime;
import game.ui.UI;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16;
	final int scale = 2;
	//SYSTEM
	public MouseHandler mouseH = new MouseHandler();
	public KeyHandler keyH = new KeyHandler();
	
	
	public final int tileSize = originalTileSize* scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	public SoilState[][] soilState;
	public BufferedImage dirtImg;
	public BufferedImage seedlingImg;
	public CropManager cropM;
	public GameTime gameTime;
	public UI ui;
	public GameObject[] objects;
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	int FPS = 60;
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public ObjectManager objectManager;
	public TileManager tileM = new TileManager(this);
	
	public  Player player = new Player(this,keyH);
	// CONSTRUCTOR
	public GamePanel() {
		this.setPreferredSize(new Dimension (screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		
		objects = new GameObject[50];
		gameTime = new GameTime();
		ui = new UI(this);
		cropM = new CropManager(this);
		objectManager = new ObjectManager(this);
		initSoil();
		loadSoilImages();
		loadCropImages();
		setupObjects();
	}
	
	public void setupObjects() {
		objects[0] = new Well(this,24,16);
		objects[1] = new House(this,6,3);
		objects[2] = new Shop(this,28,8);
		objects[3] = new FigureHead(this,37,16);
		objects[4] = new FigureHead(this,37,31);
	}
	
	private void initSoil() {
		soilState = new SoilState[maxWorldCol][maxWorldRow];
		for(int c = 0; c < maxWorldCol; c++) {
			for(int r =0; r< maxWorldRow;r++) {
				soilState[c][r] = SoilState.EMPTY;
			}
		}
	}	

	private void loadSoilImages() {
		try {
			dirtImg = ImageIO.read(getClass().getResourceAsStream("/tileMaps/dirt_plant_1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadCropImages() {
		try {
			seedlingImg = ImageIO.read(getClass().getResourceAsStream("/crop/seedling.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		CropType.CARROT.setImages(loadCrop("Carrot",2));
		CropType.TOMATO.setImages(loadCrop("Tomato",2));
		CropType.RICE.setImages(loadCrop("Rice",2));
		CropType.SUNFLOWER.setImages(loadCrop("Sunflower",2));
	}
	
	public BufferedImage[] loadCrop(String name,int stages) {
		BufferedImage[] imgs = new BufferedImage[stages];
		imgs[0] = seedlingImg;
		for(int i = 1; i < stages; i++) {
			try {
				imgs[i] = ImageIO.read(getClass().getResourceAsStream("/crop/"+name+"_"+i+".png"));
		}catch(Exception e) {
			e.printStackTrace();
		}}
		return imgs;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		this.requestFocusInWindow();
	}
	
	@Override
	public void addNotify() {
	    super.addNotify();
	    requestFocusInWindow();
	}
	
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount =0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/ drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update(delta);
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Fps:" +drawCount);
				drawCount =0;
				timer =0;
			}
		}
	}
	
	public void update(double delta) {
		gameTime.update(delta);
		player.update();
		cropM.update();	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		for(GameObject obj: objects) {
			if(obj != null) {
				obj.draw(g2,this);
			}
		}
		cropM.draw(g2);
		player.draw(g2);
		ui.draw(g2);
		
		g2.dispose();
	}
}