package entities;

import gamestates.Playing;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class ZombieManager {
    private Playing playing;
    private ArrayList<Zombie> zombies;
    private boolean gameOver = false;
    private Random random = new Random();

    private int spawnTimer = 0;
    private final int SPAWN_INTERVAL = 120; 
    private int zombiesLeftToSpawn;
    private int zombiesRightToSpawn;
    private int score = 0;
    private final int POINTS_PER_KILL = 10;
    private int dogFoodHealth = 100; 
    private long lastDamageTime = 0;  
    private final int DAMAGE_PER_SECOND = 10;  

    public ZombieManager(Playing playing) {
        this.playing = playing;
        zombies = new ArrayList<>();
        initializeSpawnCounts();
        spawnFirstZombie();
    }

    private void initializeSpawnCounts() {
        zombiesLeftToSpawn = random.nextInt(3) + 3; 
        zombiesRightToSpawn = random.nextInt(3) + 3; 
    }

    private void spawnFirstZombie() {
        if (random.nextBoolean() && zombiesLeftToSpawn > 0) {
            int xOffset = random.nextInt(800) + 100;
            zombies.add(new Zombie(-xOffset, 420, true));
            zombiesLeftToSpawn--;
        } else if (zombiesRightToSpawn > 0) {
            int xOffset = random.nextInt(800) + 1000;
            zombies.add(new Zombie(xOffset, 420, false));
            zombiesRightToSpawn--;
        }
    }

    public void update() {
        zombies.removeIf(zombie -> !zombie.isAlive());
        

        if (zombiesLeftToSpawn > 0 || zombiesRightToSpawn > 0) {
            spawnTimer++;
            if (spawnTimer >= SPAWN_INTERVAL) {
                spawnTimer = 0;
                if (random.nextBoolean() && zombiesLeftToSpawn > 0) {
                    // Spawn left zombie
                    int xOffset = random.nextInt(800) + 100;
                    zombies.add(new Zombie(-xOffset, 420, true));
                    zombiesLeftToSpawn--;
                } else if (zombiesRightToSpawn > 0) {
                    // Spawn right zombie
                    int xOffset = random.nextInt(800) + 1000;
                    zombies.add(new Zombie(xOffset, 420, false));
                    zombiesRightToSpawn--;
                }
            }
        }

        long currentTime = System.currentTimeMillis();
        for (Zombie zombie : zombies) {
            if (zombie.isAlive()) {
                zombie.update();
                if (dogFoodHealth > 0 && zombie.getHitbox().intersects(playing.getDogFood().getHitbox())) {
                    if (currentTime - lastDamageTime >= 1000) { 
                        dogFoodHealth -= DAMAGE_PER_SECOND;
                        lastDamageTime = currentTime;
                        
                        if (dogFoodHealth <= 0) {
                            dogFoodHealth = 0;
                            gameOver = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        for (Zombie zombie : zombies) {
            zombie.render(g);
        }

        drawTextWithBorder(g, "Score : " + score, 50, 50, Color.WHITE);

        Color healthColor = dogFoodHealth > 30 ? Color.GREEN : Color.RED;
        drawTextWithBorder(g, "Dog Food : " + dogFoodHealth + "%", 50, 100, healthColor);

        if (gameOver) {
            drawTextWithBorder(g, "GAME OVER!", 550, 300, Color.RED);
        }
        
        if (areAllZombiesDead()) {
            drawTextWithBorder(g, "YOU WIN!", 550, 300, Color.GREEN);
        }
    }

    private void drawTextWithBorder(Graphics g, String text, int x, int y, Color textColor) {
        if (text.contains("GAME OVER") || text.contains("WIN")) {
            g.setFont(new Font("Arial", Font.BOLD, 40));  
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 25)); 
        }
        
        g.setColor(Color.BLACK);
        g.drawString(text, x-2, y-2);
        g.drawString(text, x-2, y+2);
        g.drawString(text, x+2, y-2);
        g.drawString(text, x+2, y+2);
        
        g.setColor(textColor);
        g.drawString(text, x, y);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void checkPugAttack(Rectangle attackBox) {
        for (Zombie zombie : zombies) {
            if (zombie.isAlive() && attackBox.intersects(zombie.getHitbox())) {
                zombie.hit();
                if (!zombie.isAlive()) {
                    score += POINTS_PER_KILL;
                }
            }
        }
    }

    public boolean areAllZombiesDead() {
        for (Zombie zombie : zombies) {
            if (zombie.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public int getScore() {
        return score;
    }
}
