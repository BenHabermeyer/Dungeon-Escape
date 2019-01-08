import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import org.junit.Test;
import java.util.*;

public class GameTest {
    
    @Test
    public void testDungeonCreateTopLeft() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(0, 0);
        assertNull("top left north is null", r.getNorth());
        assertNull("top left west is null", r.getWest());
        assertNotNull("top left south isn't null", r.getSouth());
        assertNotNull("top left east isn't null", r.getEast());           
    }
    
    @Test
    public void testDungeonCreateBottomLeft() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(4, 0);
        assertNotNull("top left north isn't null", r.getNorth());
        assertNull("top left west is null", r.getWest());
        assertNull("top left south is null", r.getSouth());
        assertNotNull("top left east isn't null", r.getEast());           
    }
    
    @Test
    public void testDungeonCreateMiddle() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(2, 2);
        assertNotNull("top left north isn't null", r.getNorth());
        assertNotNull("top left west isn't null", r.getWest());
        assertNotNull("top left south isn't null", r.getSouth());
        assertNotNull("top left east isn't null", r.getEast());           
    }
    
    @Test
    public void testDungeonAdjacentRoomEqual() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(2, 0);
        Room s = d.getRoomFromInd(2, 1);
        Room t = d.getRoomFromInd(2, 2);
        assertTrue("adjacent rooms equal", r.getEast().equals(t.getWest()));
        assertTrue("left equal to middle", r.getEast().equals(s));
        assertTrue("right equal to middle", t.getWest().equals(s));
        assertFalse("not adjacent rooms not equal", r.getEast().equals(s.getEast()));
    }
    
    @Test
    public void testDungeonCoords() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(2, 3);
        assertEquals("top left coordinates x", r.getX(), 390);
        assertEquals("top left coordinates y", r.getY(), 260);
    }
    
    @Test
    public void testRoomPointerCoords() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(0, 0);
        Room s = r.getEast();
        assertEquals("coords x", s.getX(), 130);
        assertEquals("coords y", s.getY(), 0);
        assertEquals("prev coords x", s.getWest().getX(), r.getX());
        assertEquals("prev coords x", s.getWest().getY(), r.getY());
    }
    
    @Test
    public void testRoomEquals() {
        Room r = new Room(5, 5);
        Room s = new Room(5, 5);
        assertTrue("rooms with same coordinates are same", r.equals(s));
    }
    
    @Test
    public void testAllRoomsSize() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        List<Room> l = d.allRooms();
        Room r = d.getRoomFromInd(2, 2);
        int x = l.size();
        assertEquals("contains all rooms except start", 24, x);
        assertFalse("does not contain start", l.contains(r));
    }
    
    @Test
    public void testRandRoom() {
        Room r = new Room(130, 360);
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r);
        Room s = GameCourt.randRoom(rooms, false);
        assertTrue("room found is only one added", s.equals(r));
        assertEquals("size of list is 1", rooms.size(), 1);
    }
    
    @Test
    public void testRandRoomRemove() {
        Room r = new Room(130, 360);
        Room s = new Room(0, 0);
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(r);
        rooms.add(s);
        Room t = GameCourt.randRoom(rooms, true);
        assertTrue("room found one of those added", (t.equals(r) || t.equals(s)));
        assertEquals("size of list is 1", rooms.size(), 1);
    }
    
    @Test
    public void testEmptyDungeon() {
        Dungeon d  = new Dungeon(650, 650, 0.0);
        List<Room> l = d.allRooms();
        Room r = d.getRoomFromInd(2, 2);
        assertNotNull("Starting room exists", r);
        assertEquals("4 adjacent rooms besides middle", 4, l.size());
    }
    
    @Test
    public void testRoomsUnequal() {
        Room r = new Room(0, 0);
        Room s = new Room(130, 130);
        assertFalse("Rooms with different coordinates are not equal", s.equals(r));        
    }
    
    @Test
    public void testStartingRoom() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Room r = d.getRoomFromInd(2, 2);
        Room s = d.getCurrentRoom();
        assertEquals("Starting room is at center", r, s);
    }
    
    @Test
    public void testAdvanceMovesRoom() {
        Dungeon d = new Dungeon(650, 650, 1.0);
        Player knight = new Player(d);
        assertEquals("knight starts in middle", d.getRoom(knight.getPx(), knight.getPy()), d.getRoomFromInd(2, 2));
        assertEquals("current room is starting room", d.getCurrentRoom(), d.getRoomFromInd(2, 2));
        knight.advance(Direction.UP, true);
        assertFalse("current room is changes", d.getCurrentRoom().equals(d.getRoomFromInd(2, 2)));
    }
    
}