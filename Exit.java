//Static image of chest to escape

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Exit extends GameObj {
    public static final String IMG_FILE = "files/door.png";
    public static final int SIZE = 40;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;
    private Room r;

    public Exit(Room r, Dungeon d) {
        super(INIT_VEL_X, INIT_VEL_Y, r.getX() - SIZE / 2 + Room.getSize() / 2,
              r.getY() - SIZE / 2 + Room.getSize() / 2, SIZE, SIZE, r.getX(), 
              r.getY(), r.getX() + Room.getSize(), r.getY() + Room.getSize(), d);
        
        this.r = r;        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        if (r.getVisibility()) {
            g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);            
        }
    }
}
