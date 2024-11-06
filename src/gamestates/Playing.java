package gamestates;

import entities.DogFood;
import entities.Pug;
import entities.ZombieManager;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import levels.Levels;
import main.Game;

public class Playing extends State implements Statemethods {
    private Pug pug;
    private ZombieManager zombieManager;
    private Levels levels;
    private int gameWidth, gameHeight;
    private DogFood dogFood;
    

    public Playing(Game game) {
        super(game);
        this.gameWidth = game.GAME_WIDTH;
        this.gameHeight = game.GAME_HEIGHT;
        initClasses();
    }   

    private void initClasses() {
        levels = new Levels();
        dogFood = new DogFood(gameWidth, 1150);
        pug = new Pug(200, 550, this);
        zombieManager = new ZombieManager(this);

        
    }

  

    @Override
    public void update() {
        pug.update();
        zombieManager.update();
    
    }

    @Override
    public void draw(Graphics g) {
        levels.drawBackground(g, gameWidth, gameHeight);
        dogFood.render(g);
        pug.render(g);
        zombieManager.render(g);
        
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void moveMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                pug.setLeft(true);
                break;
            case KeyEvent.VK_D:
                pug.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                pug.attack();
                zombieManager.checkPugAttack(pug.getAttackBox());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_A:
                pug.setLeft(false);
                break;
            case KeyEvent.VK_D:
                pug.setRight(false);
                break;
        }
    }

    public Pug getPug(){
        return pug;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public DogFood getDogFood() {
        return dogFood;
    }
}
