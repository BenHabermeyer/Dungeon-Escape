/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Graphics;

/** 
 * An object in the game. 
 *
 * Game objects exist in the game court. They have a position, velocity, size and bounds. Their
 * velocity controls how they move; their position should always be within their bounds.
 */
public abstract class GameObj {
    /*
     * Current position of the object (in terms of graphics coordinates)
     *  
     * Coordinates are given by the upper-left hand corner of the object. This position should
     * always be within bounds.
     *  0 <= px <= maxX 
     *  0 <= py <= maxY 
     */
    private int px; 
    private int py;

    /* Size of object, in pixels. */
    private int width;
    private int height;

    /* Velocity: number of pixels to move every time move() is called. */
    private int vx;
    private int vy;

    /* 
     * Upper bounds of the area in which the object can be positioned. Maximum permissible x, y
     * positions for the upper-left hand corner of the object.
     */
    private int maxX;
    private int maxY;
    
    //lower bounds - lowest possible x, y positions for the upper-left hand corner
    private int minX;
    private int minY;
    
    private Dungeon d;
    
    
    /**
     * Constructor
     */
    public GameObj(int vx, int vy, int px, int py, int width, int height, int cornerX, int cornerY,
                   int courtWidth, int courtHeight, Dungeon d) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;
        this.minX = cornerX;
        this.minY = cornerY;
        this.d = d;

        // take the width and height into account when setting the bounds for the upper left corner
        // of the object.
        // change theseeeeeeeeeeeeeeeeeeeeeeeeeeeee
        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
    }

    /*** GETTERS **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }
    
    public int getVx() {
        return this.vx;
    }
    
    public int getVy() {
        return this.vy;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getMaxX() {
        return maxX;
    }
    
    public int getMaxY() {
        return maxY;
    }

    
    public int getMinX() {
        return minX;
    }

    
    public int getMinY() {
        return minY;
    }


    /*** SETTERS **********************************************************************************/
    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    
    public void setMaxX(int x) {
        this.maxX = x;
    }
    
    public void setMinX(int x) {
        this.minX = x;
    }
    
    public void setMaxY(int y) {
        this.maxY = y;
    }
    
    public void setMinY(int y) {
        this.minY = y;
    }

    /*** UPDATES AND OTHER METHODS ****************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area designated for the object.
     * (i.e. Object cannot go outside of the active area the user defines for it).
     */ 
    private void clip() {
        this.px = Math.min(Math.max(this.px, this.minX), this.maxX);
        this.py = Math.min(Math.max(this.py, this.minY), this.maxY);
    }

    /**
     * Moves the object by its velocity.  Ensures that the object does not go outside its bounds by
     * clipping.
     */
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

        clip();
    }

    /**
     * Determine whether this game object is currently intersecting another object.
     * 
     * Intersection is determined by comparing bounding boxes. If the bounding boxes overlap, then
     * an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(GameObj that) {
        return (this.px + 3 * this.width / 4 >= that.px + that.width / 2
            && this.py + 3 * this.height / 4 >= that.py + that.height / 2
            && that.px + that.width / 2 >= this.px  + this.width / 4
            && that.py + that.height / 2 >= this.py + this.height / 4);
    }


    /**
     * Determine whether this game object will intersect another in the next time step, assuming
     * that both objects continue with their current velocity.
     * 
     * Intersection is determined by comparing bounding boxes. If the  bounding boxes (for the next
     * time step) overlap, then an intersection is considered to occur.
     * 
     * @param that The other object
     * @return Whether an intersection will occur.
     */
    public boolean willIntersect(GameObj that) {
        int thisNextX = this.px + this.vx;
        int thisNextY = this.py + this.vy;
        int thatNextX = that.px + that.vx;
        int thatNextY = that.py + that.vy;
    
        return (thisNextX + this.width / 2 >= thatNextX
            && thisNextY + this.height / 2 >= thatNextY
            && thatNextX + that.width / 2 >= thisNextX 
            && thatNextY + that.height / 2 >= thisNextY);
    }


    /**
     * Update the velocity of the object in response to hitting an obstacle in the given direction.
     * If the direction is null, this method has no effect on the object.
     *
     * @param d The direction in which this object hit an obstacle
     */
    public void bounce(Direction d) {
        if (d == null) return;
        
        switch (d) {
        case UP:
            this.vy = Math.abs(this.vy);
            break;  
        case DOWN:
            this.vy = -Math.abs(this.vy);
            break;
        case LEFT:
            this.vx = Math.abs(this.vx);
            break;
        case RIGHT:
            this.vx = -Math.abs(this.vx);
            break;
        }
    }

    /**
     * Determine whether the game object will hit a wall in the next time step. If so, return the
     * direction of the wall in relation to this game object.
     *  
     * @return Direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (this.px + this.vx < this.minX) {
            return Direction.LEFT;
        } else if (this.px + this.vx > this.maxX) {
           return Direction.RIGHT;
        }

        if (this.py + this.vy < this.minY) {
            return Direction.UP;
        } else if (this.py + this.vy > this.maxY) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }
    
    //isDoor called with hitWall to determine if player is in line with a door or wall in a room
    public boolean isDoor() {
        if (this.px > d.getCurrentRoom().getX() + Room.space - Player.SIZE / 2 && 
            this.px <= d.getCurrentRoom().getX() + Room.space + Room.doorWidth - Player.SIZE / 2) {
            return true;
        } else if (this.py > d.getCurrentRoom().getY() + Room.space - Player.SIZE / 2&& 
                   this.py <= d.getCurrentRoom().getY() + Room.space + Room.doorWidth - Player.SIZE / 2) {
            return true;
        } else {
            return false;
        }
    }
    
    // call with knight - if there is a non-null room in the next direction move by that much
    // make current room next room, make current room visible, move character, make character limits min/max change
    public void advance(Direction e, boolean door) {
        if (e == null) {
            return;
        } else if (door == false) {
            return;
        }
        Room r = d.getCurrentRoom();
        switch (e) {
        case UP:
            if (r.getNorth() != null) {
                d.setCurrentRoom(r.getNorth());
                Room s = d.getCurrentRoom();
                s.setVisibility();
                py = py - height;
                maxX = s.getX() + Room.getSize() - width;
                maxY = s.getY() + Room.getSize() - height;
                minX = s.getX();
                minY = s.getY();
            }
            break;  
        case DOWN:
            if (r.getSouth() != null) {
                d.setCurrentRoom(r.getSouth());
                Room s = d.getCurrentRoom();
                s.setVisibility();
                py = py + height;
                maxX = s.getX() + Room.getSize() - width;
                maxY = s.getY() + Room.getSize() - height;
                minX = s.getX();
                minY = s.getY();
            }
            break;
        case LEFT:
            if (r.getWest() != null) {
                d.setCurrentRoom(r.getWest());
                Room s = d.getCurrentRoom();
                s.setVisibility();
                px = px - width;
                maxX = s.getX() + Room.getSize() - width;
                maxY = s.getY() + Room.getSize() - height;
                minX = s.getX();
                minY = s.getY();
            }
            break;
        case RIGHT:
            if (r.getEast() != null) {
                d.setCurrentRoom(r.getEast());
                Room s = d.getCurrentRoom();
                s.setVisibility();
                px = px + width;
                maxX = s.getX() + Room.getSize() - width;
                maxY = s.getY() + Room.getSize() - height;
                minX = s.getX();
                minY = s.getY();
            }
            break;
        }        
    }

    /**
     * Determine whether the game object will hit another object in the next time step. If so,
     * return the direction of the other object in relation to this game object.
     * 
     * @param that The other object
     * @return Direction of impending object, null if all clear.
     */
    public Direction hitObj(GameObj that) {
        if (this.willIntersect(that)) {
            double dx = that.px + that.width / 2 - (this.px + this.width / 2);
            double dy = that.py + that.height / 2 - (this.py + this.height / 2);

            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
            double diagTheta = Math.atan2(this.height / 2, this.width / 2);

            if (theta <= diagTheta) {
                return Direction.RIGHT;
            } else if (theta > diagTheta && theta <= Math.PI - diagTheta) {
                // Coordinate system for GUIs is switched
                if (dy > 0) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }

    /**
     * Default draw method that provides how the object should be drawn in the GUI. This method does
     * not draw anything. Subclass should override this method based on how their object should
     * appear.
     * 
     * @param g The <code>Graphics</code> context used for drawing the object. Remember graphics
     * contexts that we used in OCaml, it gives the context in which the object should be drawn (a
     * canvas, a frame, etc.)
     */
    public abstract void draw(Graphics g);
}