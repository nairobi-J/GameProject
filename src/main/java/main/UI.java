package main;

import object.OBJ_key;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    
    // Restart button variables
    private Rectangle restartButton;
    private boolean restartHover = false;
    private final Color buttonColor = new Color(70, 120, 200);
    private final Color hoverColor = new Color(100, 150, 230);

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_key key = new OBJ_key();
        keyImage = key.image;
        
        // Initialize restart button
        int buttonWidth = 200;
        int buttonHeight = 50;
        restartButton = new Rectangle(
            gp.screenWidth/2 - buttonWidth/2,
            gp.screenHeight/2 + gp.tileSize*4,
            buttonWidth,
            buttonHeight
        );
        
        // Add mouse listener
        gp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameFinished && restartButton.contains(e.getPoint())) {
                    restartGame();
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                restartHover = gameFinished && restartButton.contains(e.getPoint());
                gp.repaint(); // Refresh UI to show hover effect
            }
        });
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    
    private void restartGame() {
        // Reset game state
        gameFinished = false;
        
        // Reset player
        gp.player.worldX = gp.tileSize * 10; // Starting X
        gp.player.worldY = gp.tileSize * 10; // Starting Y
        gp.player.hasKey = 0;
        gp.player.direction = "down";

        
        for (int i = 0; i < gp.obj.length; i++) {
        gp.obj[i] = null;
    }
        // Reset objects using Asset class
        gp.asset.setObject();
        
        // Restart game thread if needed
        if (gp.gameThread == null) {
            gp.gameThread = new Thread(gp);
            gp.gameThread.start();
        }
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            // Draw congratulations message
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            
            String text = "Brave!! You found the TREASURE!!";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth/2 - textLength/2;
            int y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);
            
            g2.setFont(arial_80B);
            g2.setColor(Color.BLACK);
            text = "CONGRATULATIONS";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);
            
            // Draw restart button
            g2.setFont(arial_40);
            Color buttonFill = restartHover ? hoverColor : buttonColor;
            g2.setColor(buttonFill);
            g2.fill(restartButton);
            g2.setColor(Color.white);
            g2.draw(restartButton);
            
            text = "Play Again";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = restartButton.x + (restartButton.width - textLength)/2;
            y = restartButton.y + (restartButton.height + g2.getFontMetrics().getAscent())/2;
            g2.drawString(text, x, y);
            
        } else {
            // Normal game UI
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x = " + gp.player.hasKey, 74, 50);
            
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
                messageCounter++;
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}