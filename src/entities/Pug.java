package entities;

import gamestates.Playing;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;
import static utils.Constants.PlayerConstants.ATTACK;
import static utils.Constants.PlayerConstants.GetSpriteAmount;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;

public class Pug extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 37;
	private int playerAction = IDLE;
	private boolean left, right, attacking;
	private boolean moving = false;
	private boolean facingRight = true;
	private float playerSpeed = 1.75f;
	private boolean hasAttacked = false; // New flag to ensure `x` is only updated once per attack
	private Rectangle attackBox;
	private boolean isAttacking = false;
	private int attackCooldown = 0;
	private final int ATTACK_DURATION = 20;  // Frames the attack hitbox stays active
	private Playing gameState;
	private Game game;
	
	public Pug(float x, float y, Playing gameState) {
		super(x, y);
		this.gameState = gameState;
		loadAnimations();
		attackBox = new Rectangle((int)x + 100, (int)y + 50, 100, 100);
	}
	
	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
		updateAttackBox();
		handleAttack();
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (facingRight) {
			g2d.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 192, 126, null);
		} else {
			g2d.drawImage(animations[playerAction][aniIndex], 
				(int) x + 192, (int) y,    // Add width to x position
				-192, 126,                  // Negative width to flip horizontally
				null);
		}
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/Pug.png");
		
		try {
			BufferedImage img = ImageIO.read(is);
			animations = new BufferedImage[3][4];
			
			for (int j = 0; j < animations.length; j++) {
				for (int i = 0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i * 192, j * 126, 192, 126);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}
	
	public void setAnimation() {	
		int startAni = playerAction;
		
		if (attacking) {
			playerAction = ATTACK;
		} else if (moving) {
			playerAction = RUNNING;
		} else {
			playerAction = IDLE;
		}
		
		if (startAni != playerAction) {
			resetAniTick();
		}
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		hasAttacked = false; // Reset after animation resets
	}

	public void setAttack(boolean attacking) {
		this.attacking = attacking;
		if (attacking) {
			resetAniTick();
		}
	}
	
	private void updatePos() {
		moving = false;
		if (attacking && hasAttacked) {
			x += 15;
			checkAttackHit();
			hasAttacked = false;
		}
		
		float newX = x;
		
		if (left && !right) {
			newX -= playerSpeed;
			moving = true;
			facingRight = false;
		} else if (right && !left) {
			newX += playerSpeed;
			moving = true;
			facingRight = true;
		}
		
		if (newX >= 0 && newX <= Game.GAME_WIDTH - 192) {
			x = newX;
		}
	}

	private void checkAttackHit() {
		if (attacking) {
			updateAttackBox();
		}
	}

	private void updateAttackBox() {
		if (facingRight) {
			attackBox.x = (int)x + 50;
			attackBox.width = 60;
		} else {
			attackBox.x = (int)x - 50;
			attackBox.width = 60;
		}
		attackBox.y = (int)y + 20;
		attackBox.height = 40;
	}

	// Getters and setters for left and right movement
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	private void handleAttack() {
		if (attacking) {
			isAttacking = true;
			attackCooldown = ATTACK_DURATION;
			checkAttackHit();
		}
		
		if (attackCooldown > 0) {
			attackCooldown--;
			if (attackCooldown <= 0) {
				isAttacking = false;
			}
		}
	}

	public Rectangle getAttackBox() {
		return isAttacking ? attackBox : null;
	}

	public void attack() {
		if (!isAttacking && !attacking) {  // Only start attack if not already attacking
			setAttack(true);
			hasAttacked = true;
		}
	}
}

