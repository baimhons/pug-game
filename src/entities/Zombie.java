package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import static utils.Constants.PlayerConstants.*;

public class Zombie extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 42;
    private int zombieAction = RUNNING;
    private boolean moving = true;
    private float zombieSpeed = 0.15f;
    private boolean spawnFromLeft;
    private boolean reachedCenter = false;
    private float moveSpeed = 2.5f;
    private boolean facingLeft;
    private int health = 100;
    private boolean alive = true;
    private Rectangle hitbox;
    private int hitCount = 0;
    private final int HITS_TO_DIE = 5;

    public Zombie(float x, float y, boolean spawnFromLeft) {
        super(x, y);
        this.spawnFromLeft = spawnFromLeft;
        this.zombieSpeed = spawnFromLeft ? zombieSpeed : -zombieSpeed;
        hitbox = new Rectangle((int)x + 50, (int)y + 50, 92, 152);
        loadAnimations();
    }

    public Zombie(float x, float y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    public void update() {
        updateAnimationTick();
        updatePos();
        updateHitbox();
    }

    public void render(Graphics g) {
        if (!alive) return; // Don't render dead zombies
        
        BufferedImage image = animations[zombieAction][aniIndex];
        if (spawnFromLeft) {
            // Flip horizontally for zombies spawning from left (facing right)
            g.drawImage(image, (int) x + 192, (int) y, -192, 252, null);
        } else {
            // Draw normally for zombies spawning from right (facing left)
            g.drawImage(image, (int) x, (int) y, 192, 252, null);
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/Zombie.png");
        
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[2][4];
            
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 192, j * 1, 192, 252);
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
            
            if (aniIndex >= GetSpriteAmount(zombieAction)) {
                aniIndex = 0;
            }
        }
    }

    private void updatePos() {
        // Update the position based on the spawn direction
        if (!reachedCenter) {
            // Always move towards center (500)
            if (spawnFromLeft) {
                x += zombieSpeed;  // Move right
            } else {
                x += zombieSpeed;  // Move left
            }
            
            // Check if the zombie has reached the center (assumed to be at x = 520)
            if ((spawnFromLeft && x >= 480) || (!spawnFromLeft && x <= 575)) {
                reachedCenter = true;
            }
        }
    }

    private void updateHitbox() {
        hitbox.x = (int)x + 50;
        hitbox.y = (int)y + 50;
    }

    public boolean hasReachedCenter() {
        return reachedCenter;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void hit() {
        hitCount++;
        if (hitCount >= HITS_TO_DIE) {
            setAlive(false);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
