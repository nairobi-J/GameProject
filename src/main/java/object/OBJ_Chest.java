package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest()
    {
        name = "chest";
        try{
            image = ImageIO.read (getClass ().getResourceAsStream ("chest.png"  ));
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
    }

}
