package gamestates;

import java.awt.Rectangle;


public enum GameState {

	PLAYING, MENU, MAP_SELECTION, QUIT;
	
	public static GameState state = MENU; 
	
	

	GameState() {

	}

	
	public void checkPugAttack(Rectangle attackBox) {
		// Check collision between attackBox and zombies
		// Add your zombie collision logic here
	}
}
