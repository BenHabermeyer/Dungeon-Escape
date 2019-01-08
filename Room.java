//Room class provides a 'node' data structuer with information about the rooms
//Each room has a certain gameboard space associated with it, along with links to
//other rooms via cardinal directions. 
//

import java.awt.*;


public class Room {
    
    //top left corner
    private int posX;
    private int posY;
    private boolean visibility;
    
    public static int size = 130;
    public static int doorWidth = 50;
    public static int doorHeight = 5;
    public static int space = (size - doorWidth) / 2;
       
    private Room north;
    private Room south;
    private Room east;
    private Room west;    
    
    public Room(int x, int y) {
        posX = x;
        posY = y;
        visibility = false;
    }
    
    //~~~~~~~~~~Setters~~~~~~~~~~~~~~~~~
    public void setVisibility() {
        visibility = true;
    }
    
    public void setEast(Room r) {
        east = r;
    }
    
    public void setWest(Room r) {
        west = r;
    }
    
    public void setSouth(Room r) {
        south = r;
    }
    
    public void setNorth(Room r) {
        north = r;
    }
   
    //~~~~~~~~~~Getters~~~~~~~~~~~~~~~~~
    public int getX() {
        return posX;
    }
    
    public int getY() {
        return posY;
    }
    
    public static int getSize() {
        return size;
    }
    
    public boolean getVisibility() {
        return visibility;
    }
    
    public Room getEast() {
        return east;
    }

    public Room getWest() {
        return west;
    }
   
    public Room getNorth() {
        return  north;
    }
    
    public Room getSouth() {
        return south;
    }
    
    public int getIndX() {
        return (int) Math.floor(posX / size);
    }
   
    public int getIndY() {
        return (int) Math.floor(posY / size);
    }
    
    
    public void draw(Graphics g) {
        if (visibility) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(posX, posY, size, size);
            g.setColor(Color.BLACK);
            g.drawRect(posX, posY, size, size);
            g.setColor(Color.ORANGE);
            if (north != null) {
                g.fillRect(posX + space, posY, doorWidth, doorHeight);
            }
            if (south != null) {
                g.fillRect(posX + space, posY + size - doorHeight, doorWidth, doorHeight);
            }
            if (east != null) {
                g.fillRect(posX + size - doorHeight, posY + space, doorHeight, doorWidth);
            }
            if (west != null) {
                g.fillRect(posX, posY + space, doorHeight, doorWidth);
            }
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(posX, posY, size, size);
        }
    }    
    
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        } else {
            Room p = (Room) o;
            boolean b = (this.getX() == p.getX() && this.getY() == p.getY());
            return b;
        }
    } 
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = prime + size;
        result = result * prime + this.getX();
        result = result * prime + this.getY();
        return result;
    }    
    
}
