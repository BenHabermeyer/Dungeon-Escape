/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Dungeon Escape");
        frame.setLocation(300, 0);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        final JLabel stopwatch = new JLabel("Time Remaining: 30");
        status_panel.add(stopwatch);

        // Main playing area
        final GameCourt court = new GameCourt(status, stopwatch);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });        
        final JButton start = new JButton("Start Game");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.start();
            }
        });
        final String text = "Brave Cowboy Knight, You have been trapped in a dungeon!\n"
                            + "\n"
                            + "To escape, follow these instructions closely:\n"
                            + "1) Navigate the dungeon using the arrow keys\n"
                            + "2) Rooms with 'light' on the sides have doors - enter to discover a new room\n"
                            + "3) Within the dungeon are 3 keys in different rooms - collect all 3 to escape\n"
                            + "4) Find the exit door - only when you have all 3 keys will you be able to open it\n"
                            + "5) Beware of skeletons - one touch is deadly, but they cannot leave their room!\n"
                            + "6) As if there wasn't enough pressure, you must escape in under 60 seconds!\n"
                            + "\n"
                            + "The dungeon layout is randomly generated, but all the rooms are accessible\n"
                            + "The number of rooms and skeletons will vary proportionally depending on each unique game\n"
                            + "Press Start Game to start the timer and begin the game\n"
                            + "Follow the dialogue on the bottom of the game window to monitor progress\n"
                            + "Press the Reset button to randomly generate a new game layout\n"
                            + "Clicking the instructions at any point pauses the game, press start game again to resume\n"
                            + "\n"
                            + "Good Luck!\n";
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.setPlaying(false);
                JOptionPane.showMessageDialog(null, text);
            }
        });           
        
        control_panel.add(instructions);
        control_panel.add(start);
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}