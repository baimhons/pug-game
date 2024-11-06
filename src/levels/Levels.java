package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class Levels {
    private BufferedImage[] backgrounds;
    private int currentLevel = 0; // ค่าเริ่มต้นเป็นด่านที่ 1 (ด่าน 0)

    // Add center position for each level
    private final int[] LEVEL_CENTERS = {500, 500, 500}; // Adjust these values for each level's center

    private Random random = new Random();

    public Levels() {
        loadBackgrounds();
        // Randomly set initial level to 0 or 1
        currentLevel = random.nextInt(2); // This generates 0 or 1 randomly
    }

    private void loadBackgrounds() {
        backgrounds = new BufferedImage[3]; // กำหนดให้มี 3 ด่าน

        // โหลดภาพพื้นหลังสำหรับแต่ละด่าน
        try {
            InputStream is1 = getClass().getResourceAsStream("/map1.png");
           InputStream is2 = getClass().getResourceAsStream("/map2.PNG");
//            InputStream is3 = getClass().getResourceAsStream("/map3.png");

            backgrounds[0] = ImageIO.read(is1);
           backgrounds[1] = ImageIO.read(is2);
//            backgrounds[2] = ImageIO.read(is3);

            is1.close();
           is2.close();
//            is3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBackground(Graphics g, int panelWidth, int panelHeight) {
        if (currentLevel >= 0 && currentLevel < backgrounds.length) {
            // วาดภาพพื้นหลังและปรับขนาดให้พอดีกับขนาดของ GamePanel
            g.drawImage(backgrounds[currentLevel], 0, 0, panelWidth, panelHeight, null);
        }
    }

    public void setLevel(int level) {
        // ตร��จสอบว่าด่านที่เลือกอยู่ในช่วงที่มีอยู่หรือไม่
        if (level >= 0 && level < backgrounds.length) {
            currentLevel = level;
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getLevelCenterX() {
        return LEVEL_CENTERS[currentLevel];
    }
}
