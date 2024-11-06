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

public class MapSelection extends State implements Statemethods {
    private BufferedImage[] mapPreviews;
    private BufferedImage selectBg;
    private int selectedMap = 0;
    private final int NUM_MAPS = 3;

    public MapSelection(Game game) {
        super(game);
        loadMapPreviews();
        loadBackground();
    }

    private void loadMapPreviews() {
        mapPreviews = new BufferedImage[NUM_MAPS];
        try {
            for (int i = 0; i < NUM_MAPS; i++) {
                mapPreviews[i] = ImageIO.read(new File("res/map" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBackground() {
        try {
            selectBg = ImageIO.read(new File("res/selectbg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        // Draw background first
        if (selectBg != null) {
            g.drawImage(selectBg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        // Draw title
        drawTextWithBorder(g, "Select Map", 
            Game.GAME_WIDTH / 2 - 100,
            50,
            Color.WHITE,
            40);

        // Draw map preview
        if (mapPreviews[selectedMap] != null) {
            g.drawImage(mapPreviews[selectedMap], 
                Game.GAME_WIDTH / 2 - 200,
                100,
                400,
                250,
                null);
        }

        // Draw map name
        drawTextWithBorder(g, "Map " + (selectedMap + 1), 
            Game.GAME_WIDTH / 2 - 50,
            400,
            Color.WHITE,
            30);

        // Draw instructions
        drawTextWithBorder(g, "← →  to select map", 
            Game.GAME_WIDTH / 2 - 100,
            450,
            Color.YELLOW,
            20);

        drawTextWithBorder(g, "SPACE BAR to start", 
            Game.GAME_WIDTH / 2 - 70,
            490,
            Color.YELLOW,
            20);
    }

    private void drawTextWithBorder(Graphics g, String text, int x, int y, Color textColor, int fontSize) {
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        
        // Draw black border
        g.setColor(Color.BLACK);
        g.drawString(text, x-1, y-1);
        g.drawString(text, x-1, y+1);
        g.drawString(text, x+1, y-1);
        g.drawString(text, x+1, y+1);
        
        // Draw main text
        g.setColor(textColor);
        g.drawString(text, x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                selectedMap = (selectedMap - 1 + NUM_MAPS) % NUM_MAPS;
                System.out.println("Selected map: " + selectedMap);
                break;
            case KeyEvent.VK_RIGHT:
                selectedMap = (selectedMap + 1) % NUM_MAPS;
                System.out.println("Selected map: " + selectedMap);
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Selected map:  " + selectedMap);
                game.getPlaying().setCurrentMap(selectedMap);
                GameState.state = GameState.PLAYING;
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Left arrow region
        if (e.getX() < Game.GAME_WIDTH / 4) {
            selectedMap = (selectedMap - 1 + NUM_MAPS) % NUM_MAPS;
        } 
        // Right arrow region
        else if (e.getX() > Game.GAME_WIDTH * 3/4) {
            selectedMap = (selectedMap + 1) % NUM_MAPS;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameState.state = GameState.PLAYING;
    }

    // Other required methods
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
} 