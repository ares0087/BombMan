package Entity;

import Controls.CollisionCheck;
import Controls.KeyHandler;
import GUI.GameScene;
import Variables.Constant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    CollisionCheck cCheck = new CollisionCheck();

    public Player(KeyHandler keyH) {
        this.keyH = keyH;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefault();
        getPlayerImage();
    }

    @Override
    public void setDefault() {
        x = 48 + Constant.tileSize;
        y = 32 + Constant.tileSize;
        speed = 2;
        direction = "down";
        state = 1;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up3.png")));
            up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_up4.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down3.png")));
            down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_down4.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left3.png")));
            left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_left4.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right3.png")));
            right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/player_right4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double dt) {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            cCheck.checkTile(this);

            int objIndex = GameScene.cCheck.checkObject(this, true);
            pickUpObject(objIndex);
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNum != 4) {
                    spriteNum++;
                } else
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }
    public void pickUpObject (int i){
        if (i != 999){
            String objName = GameScene.Object[i].name;
            switch (objName){
                case "BlastRadius":
                    Bomb.bombSize += 1;
                    GameScene.Object[i] = null;
                    break;
                case "SpeedIncrease":
                    speed += 1;
                    GameScene.Object[i] = null;
                    break;
            }
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage img = null;
        Image img1 = null;
        switch (direction) {
            case "up" -> img = getBufferedImage(up1, up2, up3, up4);
            case "down" -> img = getBufferedImage(down1, down2, down3, down4);
            case "left" -> img = getBufferedImage(left1, left2, left3, left4);
            case "right" -> img = getBufferedImage(right1, right2, right3, right4);
        }
        if (state == 0) {
            URL url = Objects.requireNonNull(getClass().getResource("/Player/player_die.gif"));
            ImageIcon icon = new ImageIcon(url);
            img1 = icon.getImage();
            //img = getBufferedImage(die1, die2, die3, die4);
            speed = 0;
        }
        if (img1 == null) {
            g2.drawImage(img, x, y, Constant.original_tile_size * Constant.scale,
                    Constant.original_tile_size * Constant.scale, null);
        } else {
            g2.drawImage(img1, x, y, Constant.original_tile_size * Constant.scale,
                    Constant.original_tile_size * Constant.scale, null);
            //Window.getWindow().changeState(0);
        }
    }

    private BufferedImage getBufferedImage(BufferedImage img1,
                                           BufferedImage img2,
                                           BufferedImage img3,
                                           BufferedImage img4) {
        if (spriteNum == 1) {
            return img1;
        }
        if (spriteNum == 2) {
            return img2;
        }
        if (spriteNum == 3) {
            return img3;
        }
        if (spriteNum == 4) {
            return img4;
        }
        return null;
    }
}
