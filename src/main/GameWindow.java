package main;

import javax.swing.JFrame;

public class GameWindow{
	private JFrame jFrame;
	
	public GameWindow(GamePanel gamePanel) {
		
		jFrame = new JFrame();
		
		jFrame.setTitle("A pug fights with zombies to protect his dog food, which the zombies are trying to steal.");
		jFrame.setDefaultCloseOperation(3);
		jFrame.add(gamePanel);
		jFrame.setLocationRelativeTo(null);
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setVisible(true);
	}
}
