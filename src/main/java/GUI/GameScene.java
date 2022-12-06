package GUI;

import Controls.CollisionCheck;
import Controls.KeyHandler;
import Controls.MouseHandler;
import Entity.Bomb;
import Entity.Mob;
import Entity.Player;
import Objects.SuperObject;

import java.awt.*;
import java.util.ArrayList;

public class GameScene extends Scene {
    public static CollisionCheck cCheck;
    public static SuperObject[] Object = new SuperObject[10];

    Pause pause;
    GameOver gameOver;

    KeyHandler keyH;
    MouseHandler mouseH;

    static Player player;
    Bomb bomb;
    static ArrayList<Bomb> bombList;
    static ArrayList<Mob> mobList = new ArrayList<>(3);


    AssetSetter aSetter = new AssetSetter(this);
    TileManager tileM;

    public GameScene(KeyHandler keyH, MouseHandler mouseH) {
        this.keyH = keyH;
        this.mouseH = mouseH;

        player = new Player(keyH, 1);
        cCheck = new CollisionCheck();
        tileM = new TileManager();

        aSetter.setMob();
        aSetter.setItems();

        bomb = new Bomb(keyH);
        bombList = bomb.getBombList();

        pause = new Pause(false, keyH);
        gameOver = new GameOver(mouseH);
    }

    @Override
    public void update(double dt) {
        pause.pauseGame();
        gameOver.checkAlive(player.state);

        if (!pause.isPaused) {
            //Game is running
            player.update();
            for (Mob value : mobList) {
                value.update();
                //cCheck.checkMob(player,mobList);
            }
            bomb.update(player.x, player.y);
            bombList = bomb.getBombList();

        }  // Do nothing

        //Game over
        if (!gameOver.isAlive) {
            gameOver.update(dt);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Draw Map
        tileM.draw(g2);

        //Draw player
        player.draw(g2);

        //Draw Items
        for (SuperObject superObject : Object) {
            if (superObject != null) {
                superObject.draw(g2);
            }
        }

        //Draw Bomb
        if (bombList != null) {
            for (Bomb b : bombList) {
                b.draw(g2);
            }
        }

        //Draw mob
        for (Mob value : mobList) {
                value.draw(g2);
        }

        //Draw if the game is paused
        if (pause.isPaused) {
            Overlay.getInstance().draw(g2);
            pause.draw(g2);
        }
        if (!gameOver.isAlive) {
            Overlay.getInstance().draw(g2);
            gameOver.draw(g2);
        }
    }
    public static ArrayList<Bomb> getBombList() {
        return bombList;
    }
    public static Player getPlayer(){
        return player;
    }
    public static ArrayList<Mob> getMobList(){
        return mobList;
    }

}