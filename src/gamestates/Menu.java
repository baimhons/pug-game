package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Game;

public class Menu extends State implements Statemethods {
	private BufferedImage backgroundImg;
	private int flashTimer = 0;
	private final int FLASH_INTERVAL = 100; // Adjust this to control flash speed
	private boolean showText = true;

	public Menu(Game game) {
		super(game);
		loadBackground();
	}

	private void loadBackground() {
		try {
			backgroundImg = ImageIO.read(new File("res/bg.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// Update flash timer
		flashTimer++;
		if (flashTimer >= FLASH_INTERVAL) {
			flashTimer = 0;
			showText = !showText; // Toggle text visibility
		}
	}

	@Override
	public void draw(Graphics g) {
		// Draw background first
		if (backgroundImg != null) {
			g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		}

		// Draw flashing title
		if (showText) {
			drawTextWithBorder(g, "Press Enter to Start", 
				Game.GAME_WIDTH / 2 - 150,
				Game.GAME_HEIGHT / 2 - 100,
				Color.WHITE,
				30);
		}

		// Draw controls header (always visible)
		drawTextWithBorder(g, "Controls:", 
			Game.GAME_WIDTH / 2 - 75,
			Game.GAME_HEIGHT / 2,
			Color.YELLOW,
			25);

		// Draw control instructions (always visible)
		drawTextWithBorder(g, "A - Move Left", 
			Game.GAME_WIDTH / 2 - 100,
			Game.GAME_HEIGHT / 2 + 50,
			Color.WHITE,
			20);

		drawTextWithBorder(g, "D - Move Right", 
			Game.GAME_WIDTH / 2 - 100,
			Game.GAME_HEIGHT / 2 + 85,
			Color.WHITE,
			20);

		drawTextWithBorder(g, "Space - Attack", 
			Game.GAME_WIDTH / 2 - 100,
			Game.GAME_HEIGHT / 2 + 120,
			Color.WHITE,
			20);
	}

	private void drawTextWithBorder(Graphics g, String text, int x, int y, Color textColor, int fontSize) {
		g.setFont(new Font("Arial", Font.BOLD, fontSize));
		
		// Draw black border
		g.setColor(Color.BLACK);
		g.drawString(text, x-1, y-1);  // Made border thinner for smaller text
		g.drawString(text, x-1, y+1);
		g.drawString(text, x+1, y-1);
		g.drawString(text, x+1, y+1);
		
		// Draw main text
		g.setColor(textColor);
		g.drawString(text, x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			GameState.state = GameState.MAP_SELECTION;
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
