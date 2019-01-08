//Static image of key to collect

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Key extends GameObj implements Comparable<Key> {
    public static final String IMG_FILE = "files/key.png";
    public static final int SIZE = 24;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private int value;

    private static BufferedImage img;
    private Room r;

    public Key(Room r, Dungeon d, int num) {
        super(INIT_VEL_X, INIT_VEL_Y, r.getX() + (int) (Math.random() * (Room.getSize() - SIZE)),
              r.getY() + (int) (Math.random() * (Room.getSize() - SIZE)), SIZE, SIZE, r.getX(), 
              r.getY(), r.getX() + Room.getSize(), r.getY() + Room.getSize(), d);
        value = num;
        this.r = r;
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    public int getNum() {
        return this.value;
    }
    

    @Override
    public void draw(Graphics g) {
        if (r.getVisibility()) {
            g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);            
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        } else {
            Key p = (Key) o;
            boolean b = (this.getNum() == p.getNum() && this.getPx() == p.getPx() && this.getPy() == p.getPy());
            return b;
        }
    } 
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + value;
        result = result * prime + this.getPx();
        result = result * prime + this.getPy();
        return result;
    }    
    
    public int compareTo(Key k) {
        return Integer.compare(k.getNum(), this.getNum());
    }
}
