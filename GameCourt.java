/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private Player knight; // the Black Square, keyboard control
    private Monster skull; // the Golden Snitch, bounces
    private Exit chest; // escape doesn't move
    private Set<Key> keys;
    private Set<Monster> monsters;
    private List<Room> rooms;
    
    //create a container of roooms that are visible - for each room, draw it
    //call paintcomponent and include draw method
    //create an array to represent the current game board

    public boolean playing = false; // whether the game is running
    private int keysRemaining;
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel stopwatch;
    private int timeRemaining = 60;
    private int numMonsters = 5;

    // Game constants
    public static final int COURT_WIDTH = 650;
    public static final int COURT_HEIGHT = 650;
    public static final int PLAYER_VELOCITY = 3;
    
    //Dungeon time baby
    private Dungeon d;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status, JLabel stopwatch) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.DARK_GRAY);

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    knight.setVx(-PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    knight.setVx(PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    knight.setVy(PLAYER_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    knight.setVy(-PLAYER_VELOCITY);
                }
            }

            public void keyReleased(KeyEvent e) {
                knight.setVx(0);
                knight.setVy(0);
            }
        });

        this.status = status;
        this.stopwatch = stopwatch;
    }
    
    Timer gameclock = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playing == true) {
                    tick2();
                }
            }
    });

    public void start() {
        playing = true;
        status.setText("Keys Remaining: " + keysRemaining);
        stopwatch.setText("Time Remaining: " + timeRemaining);
        gameclock.start();
        requestFocusInWindow();
    }
    
    void tick2() {
        if (timeRemaining > 0 && playing == true) {
            timeRemaining--;
        } else if (timeRemaining == 0) {
            playing = false;
            status.setText("Time's Up!");
        }
        stopwatch.setText("Time Remaining: " + timeRemaining);
    }
    
    //returns a random room from the list of rooms that are not null or the starting room
    public static Room randRoom(List<Room> s, boolean ownRoom) {
        int len = s.size();
        int ind = (int) (Math.random() * len);
        if (ownRoom) {
            return s.remove(ind);
        } else {
            return s.get(ind);            
        }
    }
    
    public void setPlaying (boolean b) {
        playing = b;
    }
    
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        d = new Dungeon(COURT_WIDTH, COURT_HEIGHT, 0.7);
        rooms = d.allRooms();
        numMonsters = (1 + (rooms.size() / 4));
        
        knight = new Player(d);
        chest = new Exit(randRoom(rooms, true), d);
        monsters = new HashSet<Monster>();
        for (int i = 0; i < numMonsters; i++) {
            skull = new Monster(randRoom(rooms, false), d);
            monsters.add(skull);
        }
        Key key1 = new Key(randRoom(rooms, true), d, 1);
        Key key2 = new Key(randRoom(rooms, true), d, 2);
        Key key3 = new Key(randRoom(rooms, true), d, 3);
        keys = new HashSet<Key>();
        keys.add(key1);
        keys.add(key2);
        keys.add(key3);
        
        playing = false;
        repaint();
        keysRemaining = keys.size();
        timeRemaining = 60;
        status.setText("Press start if you dare!");
        stopwatch.setText("Time Remaining: " + timeRemaining);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            knight.move();
            
            for (Monster skull : monsters) {
                skull.move();
                if (skull.monsterRoom().equals(d.getCurrentRoom())) {
                    if (knight.getPx() < skull.getPx()) {
                        skull.setVx(-1);
                    } else {
                        skull.setVx(1);
                    } 
                    if (knight.getPy() < skull.getPy()) {
                        skull.setVy(-1);
                    } else {
                    skull.setVy(1);
                    } 
                } else {
                    skull.setVx(0);
                    skull.setVy(0);
                }
                skull.bounce(skull.hitWall());
                skull.bounce(skull.hitObj(chest));
            }

            
            knight.advance(knight.hitWall(), knight.isDoor());
                        
            for (Key k : keys) {
                skull.bounce(skull.hitObj(k));  
            }
            if (keysRemaining > 0) {
                knight.bounce(knight.hitObj(chest));
            } else {
                if (knight.intersects(chest)) {
                    playing = false;
                    status.setText("You escaped!");
                }
            }
            
            // check for the game end conditions
            for (Monster skull : monsters) {
                if (knight.intersects(skull)) {
                    playing = false;
                    status.setText("You died!");
                }   
            }
            
            Set<Key> delete = new HashSet<Key>();
            for (Key k : keys) {
                if (knight.intersects(k)) {
                    keysRemaining--;
                    delete.add(k);
                    if (keysRemaining > 0) {
                        status.setText("Keys Remaining: " + keysRemaining);
                    } else {
                        status.setText("Escape the Dungeon!");
                    }
                }
            }
            for (Key l : delete) {
                keys.remove(l);
            }
            
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        d.draw(g);
        knight.draw(g);
        chest.draw(g);
        for (Monster skull : monsters) {
            skull.draw(g);
        }
        for (Key k : keys) {
            k.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}