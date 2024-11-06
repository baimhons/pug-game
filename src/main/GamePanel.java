package main;

import input.KeyboardInput;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
	private Game game;
	
	public GamePanel(Game game) {
		this.game = game;
		
		setPanelSize();
		addKeyListener(new KeyboardInput(this));
		}
	
	private void setPanelSize() {
		Dimension size = new Dimension(1280, 720); 
		setMinimumSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
	}
	public void updateGame() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}
}
