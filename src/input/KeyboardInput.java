package input;

import gamestates.GameState;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
public class KeyboardInput implements KeyListener{
	private GamePanel gamePanel;
	
	public KeyboardInput(GamePanel game) {
		this.gamePanel = game;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case MAP_SELECTION:
			gamePanel.getGame().getMapSelection().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameState.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case MAP_SELECTION:
			gamePanel.getGame().getMapSelection().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		}
	}
	
}
