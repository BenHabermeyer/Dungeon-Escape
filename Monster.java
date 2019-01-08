//Static image of skeleton character

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Monster extends GameObj {
    public static final String IMG_FILE = "files/skull.png";
    public static final int SIZE = 16;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;
    private Room r;

    public Monster(Room r, Dungeon d) {
        super(INIT_VEL_X, INIT_VEL_Y, r.getX() + (3 * SIZE) + (int) (Math.random() * (Room.getSize() - (7 * SIZE))),
              r.getY() + (3 * SIZE) + (int) (Math.random() * (Room.getSize() - (7 * SIZE))), SIZE, SIZE, r.getX(), 
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
    
    public Room monsterRoom() {
        return r;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        } else {
            Monster p = (Monster) o;
            boolean b = (this.getPx() == p.getPx() && this.getPy() == p.getPy());
            return b;
        }
    } 
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + this.getPx();
        result = result * prime + this.getPy();
        return result;
    }

    @Override
    public void draw(Graphics g) {
        if (r.getVisibility()){
            g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
        }
    }
    
    
}
