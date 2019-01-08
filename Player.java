//Static image of knight character

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends GameObj {
    public static final String IMG_FILE = "files/knight4.png";
    public static final int SIZE = 36;
    public static final int INIT_POS_X = 306;
    public static final int INIT_POS_Y = 306;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;

    //public Player(int courtWidth, int courtHeight) {
    public Player(Dungeon d) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, d.getCurrentRoom().getX(), 
              d.getCurrentRoom().getY(), d.getCurrentRoom().getX() + Room.getSize(), d.getCurrentRoom().getY() + Room.getSize(), d);
        
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
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
