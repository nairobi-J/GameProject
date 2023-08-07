package main;

import object.OBJ_key;

public class Asset {
    GamePanel gp;
    public Asset ( GamePanel gp)
    {
        this.gp = gp;
    }
    public void  setObject()
    {
        gp.obj[0] = new OBJ_key;
        gp.obj[0].worldX = 23*gp.tileSize;
        gp.obj[0].worldY = 7*gp.tileSize;

        gp.obj[1] = new OBJ_key;
        gp.obj[1].worldX = 23*gp.tileSize;
        gp.obj[1].worldY = 7*gp.tileSize;
    }
}
