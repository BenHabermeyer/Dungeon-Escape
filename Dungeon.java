//dungeon is a linked data structure containing rooms
//
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Dungeon {
    
    private Room current;
    private Room[][] layout = new Room[5][5];
    private double maze;
    //total sizes
    private int boardWidth;
    private int boardHeight;
    // width and height - should be 130
    private int sizeX;
    private int sizeY;
    
    // width and height should be size of canvas, randomness is a double proportion
    public Dungeon(int width, int height, double randomness) {
        boardHeight = height;
        boardWidth = width;
        sizeX = boardWidth / 5;
        sizeY = boardHeight / 5;
        maze = randomness;
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                double z = Math.random();
                if (z <= maze) {
                    Room r = new Room(sizeX * j, sizeY * i);
                    add(i, j, r, layout);
                }
            }
        }
        
        //instantiate adjacent rooms
        if (layout[2][2] == null) {
            Room r = new Room(sizeX * 2, sizeY * 2);
            add(2, 2, r, layout);
        }
        if (layout[2][1] == null) {
            Room r = new Room(sizeX * 1, sizeY * 2);
            add(2, 1, r, layout);
        }
        if (layout[2][3] == null) {
            Room r = new Room(sizeX * 3, sizeY * 2);
            add(2, 3, r, layout);
        }
        if (layout[1][2] == null) {
            Room r = new Room(sizeX * 2, sizeY * 1);
            add(1, 2, r, layout);
        }
        if (layout[3][2] == null) {
            Room r = new Room(sizeX * 2, sizeY * 3);
            add(3, 2, r, layout);
        }
        current = layout[2][2];  
        //remove bad rooms to percolate
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j ++) {
                check(i, j);
            }
        }
        //add rooms to middle edges if there is a corner room
        if (layout[0][0] != null) {
            Room r = new Room(sizeX * 1, sizeY * 1);
            add(1, 1, r, layout);
        }
        if (layout[0][4] != null) {
            Room r = new Room(sizeX * 3, sizeY * 1);
            add(1, 3, r, layout);
        }        
        if (layout[4][0] != null) {
            Room r = new Room(sizeX * 1, sizeY * 3);
            add(3, 1, r, layout);
        }
        if (layout[4][4] != null) {
            Room r = new Room(sizeX * 3, sizeY * 3);
            add(3, 3, r, layout);
        }        
        current.setVisibility();
    }
    
    //adds room to layout and updates pointers to the next rooms
    private void add(int i, int j, Room r, Room[][] rs) {
        rs[i][j] = r;
        if (i - 1 >= 0 && layout[i-1][j] != null) {
            Room s = layout[i-1][j];
            r.setNorth(s);
            s.setSouth(r);
        }
        if (i + 1 < 5 && layout[i+1][j] != null) {
            Room s = layout[i+1][j];
            r.setSouth(s);
            s.setNorth(r);
        }
        if (j - 1 >= 0 && layout[i][j-1] != null) {
            Room s = layout[i][j-1];
            r.setWest(s);
            s.setEast(r);
        }
        if (j + 1 < 5 && layout[i][j+1] != null) {
            Room s = layout[i][j+1];
            r.setEast(s);
            s.setWest(r);
        }
    }
    
    //removes pointers from room and makes null
    private void remove(int i, int j, Room r, Room[][] rs) {
        if (i - 1 >= 0 && layout[i-1][j] != null) {
            Room s = layout[i-1][j];
            s.setSouth(null);
        }
        if (i + 1 < 5 && layout[i+1][j] != null) {
            Room s = layout[i+1][j];
            s.setNorth(null);
        }
        if (j - 1 >= 0 && layout[i][j-1] != null) {
            Room s = layout[i][j-1];
            s.setEast(null);
        }
        if (j + 1 < 5 && layout[i][j+1] != null) {
            Room s = layout[i][j+1];
            s.setWest(null);
        }
        rs[i][j] = null;
    }
    
    
    //checks to see if room is reachable and if isn't deletes it
    private void check(int i, int j) {
        Room r = layout[i][j];
        if (r == null) {
            return;
        }
        int total = getNumCardinal(r);
        if (total > 1) {
            return;
        } else if (total == 0) {
            remove(i, j, r, layout);
        } else if (total == 1 && nextNumCardinal(r) == 1) {
            remove(i, j, r, layout);
        }
    }
    
    // call this method on a room with only one next pointer and get number of pointers next room has
    private int nextNumCardinal(Room r) {
        if (r.getNorth() != null) {
            return getNumCardinal(r.getNorth());
        } else if (r.getSouth() != null) {
            return getNumCardinal(r.getSouth());
        } else if (r.getWest() != null) {
            return getNumCardinal(r.getWest());
        } else if (r.getEast() != null) {
            return getNumCardinal(r.getEast());
        } else {
            return 0;
        }
    }
    
    // sum of how many next pointers are not null   
    private int getNumCardinal(Room r) {
        int sum = 0;
        if (r.getNorth() != null) {
            sum++;
        } if (r.getSouth() != null) {
            sum++;
        } if (r.getEast() != null) {
            sum++;
        } if (r.getWest() != null) {
            sum++;
        }
        return sum;
    }
    
    public Room getCurrentRoom() {
        return current;
    }
    
    public void setCurrentRoom(Room r) {
        current = r;
    }    
    
    //returns collection containing the non-null rooms with which gameobjects can reside
    public List<Room> allRooms() {
        List<Room> h = new ArrayList<Room>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (layout[i][j] != null && (i != 2 || j != 2)) {
                    h.add(layout[i][j]);
                }
            }
        }
        return h;
    }
    
    //give current room given object positions on canvas - better way to do this given sizes and boardWidth and boardHeight
    public Room getRoom(int px, int py) {
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j ++) {
                if (130 * j <= px && 130 * (j + 1) > px && 130 * i <= py && 130 * (i + 1) > py) {
                    return layout[i][j];                    
                }
            }
        }
        return null;
    }
    
    public Room getRoomFromInd(int i, int j) {
        return layout[i][j];
    }
    
    public void draw(Graphics g) {
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j ++) {
                if (layout[i][j] != null) {
                    Room r = layout[i][j];
                    r.draw(g);
                }
            }
        }
    }
    
}