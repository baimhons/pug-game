package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Levels {
    private BufferedImage[] backgrounds;
    private int currentLevel = 0;

    // Add this field at the class level
    private final int[] LEVEL_CENTERS = {500, 500, 500};

    // Add constructor to initialize backgrounds
    public Levels() {
        loadBackgrounds();
    }

    private void loadBackgrounds() {
        backgrounds = new BufferedImage[3];
        
        try {
            // Load all three maps
            InputStream is1 = getClass().getResourceAsStream("/map1.PNG");
            InputStream is2 = getClass().getResourceAsStream("/map2.PNG");
            InputStream is3 = getClass().getResourceAsStream("/map3.PNG");

            backgrounds[0] = ImageIO.read(is1);
            backgrounds[1] = ImageIO.read(is2);
            backgrounds[2] = ImageIO.read(is3);

            is1.close();
            is2.close();
            is3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLevel(int level) {
        if (level >= 0 && level < backgrounds.length && backgrounds[level] != null) {
            currentLevel = level;
        }
    }

    public void drawBackground(Graphics g, int panelWidth, int panelHeight) {
        if (currentLevel >= 0 && currentLevel < backgrounds.length) {
            // วาดภาพพื้นหลังและปรับขนาดให้พอดีกับขนาดของ GamePanel
            g.drawImage(backgrounds[currentLevel], 0, 0, panelWidth, panelHeight, null);
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getLevelCenterX() {
        return LEVEL_CENTERS[currentLevel];
    }
}
