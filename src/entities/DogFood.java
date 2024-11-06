package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DogFood {
    private int x, y;
    private int width = 190;  // Adjust size as needed
    private int height = 190; // Adjust size as needed
    private BufferedImage dogFoodImg;
    private Rectangle hitbox;
    
    public DogFood(int gameWidth, int gameHeight) {
        // Load image directly
        try {
            dogFoodImg = ImageIO.read(new File("res/DogFood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Position in center
        x = gameWidth/2 - width/2;
        y = gameHeight/2 - height/2;
        
        // Create hitbox
        hitbox = new Rectangle(x, y, width, height);
    }
    
    public void render(Graphics g) {
        g.drawImage(dogFoodImg, x, y, width, height, null);
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }
}
