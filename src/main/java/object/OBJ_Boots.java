package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {
    public OBJ_Boots()
    {
        name = "boots";
        try{
            image = ImageIO.read (getClass ().getResourceAsStream ("boots.png"  ));
        } catch (IOException e) {
            throw new RuntimeException ( e );
        }
    }

}
