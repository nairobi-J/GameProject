package Entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues ();
        getPlayerImage();

    }
    public void setDefaultValues()
    {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        x = 100;
        y = 100;
        speed = 2;
        direction = "down";

    }
    public void getPlayerImage() {
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (1).png" ));
            up2 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (2).png" ));
            down1 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (3).png" ));
            down2 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (4).png" ));
            right1 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (5).png" ));
            right2 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (6).png" ));
            left1 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (7).png" ));
            left2 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (8).png" ));
            //up1 = ImageIO.read(getClass().getResourceAsStream ( "New Piskel-1.png (1).png" ));

        } catch (IOException e) {
            throw new RuntimeException ( e );
        }


    }

    public void update()
    {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed|| keyH.rightPressed)
        {
            if(keyH.upPressed)
                direction = "up";
            else if(keyH.downPressed)
                direction = "down";
            else if(keyH.rightPressed)
                direction = "right";
            else if(keyH.leftPressed)
                direction = "left";

            collisionOn = false;
            gp.ccheck.checkTile(this);
            //ck obj col
           int objIndex =  gp.ccheck.checkObject ( this, true );
           pickUpObject(objIndex);
            if(collisionOn == false)
            {
                switch (direction)
                {
                    case "up": worldY -= speed;
                        break;
                    case "down": worldY += speed;
                        break;
                    case "left": worldX -= speed;
                        break;
                    case "right": worldX += speed;
                        break;

                }
            }
            spriteCounter++;
            if(spriteCounter > 5)
            {
                if(spriteNum == 1)
                    spriteNum = 2;
                else if(spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;

            }}
    }
    public void pickUpObject(int i)

    {
        if(i != 999)
        {
           // gp.obj[i] = null;
            String objectName = gp.obj[i].name;
            switch(objectName)
            {
                case "key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage ( "congo!! you got a KEYYY!!!");
                    System.out.println (hasKey);

                    break;
                case  "door":
                    if(hasKey > 0)
                    {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage ( "ye!!You opened the Door!!" );
                    }
                    else
                    {
                        gp.ui.showMessage ( "OPPSS! you need a Key" );
                    }
                    System.out.println (hasKey);
                        break;
                case "chest":
                    break;
                case "boots":
                    speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage ( "speed upp" );

                    break;
            }
        }

    }


    public void draw(Graphics2D g2)
    {
//      g2.setColor ( Color.white);
//      g2.fillRect(x, y,gp.tileSize,gp.tileSize );
        //g2.drawImage(image, 5, 10, 100, 100, null );
        BufferedImage image  = null;
        switch(direction)
        {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;

                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                break;

        }

        g2.drawImage(image,screenX, screenY,gp.tileSize, gp.tileSize, null );






    }



}
