package main;

import entities.Pug;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import input.KeyboardInput;
import java.awt.Graphics;
import levels.Levels;

public class Game implements Runnable{
	
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
	private Levels level;
	
	private Pug pug;
	
	public Game() {
		 initClasses();
		    gamePanel = new GamePanel(this);
		    gamePanel.addKeyListener(new KeyboardInput(gamePanel)); 
		    gameWindow = new GameWindow(gamePanel);
		    gamePanel.requestFocus();
		    startGameLoop();
	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void update() {
		switch(GameState.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		default:
			break;
		}
	}
	
	void render(Graphics g) {
		switch(GameState.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);

			break;
		default:
			break;
		}
	}
	

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 /FPS_SET;
		double timePerUpdate = 1000000000.0 /UPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		while(true) {
			
			now = System.nanoTime();
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(now - lastFrame >= timePerFrame) {
				gamePanel.repaint();
				lastFrame = now;
				frames++;
			}
			
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				frames = 0;
				updates = 0;
			}
		}
		
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
}