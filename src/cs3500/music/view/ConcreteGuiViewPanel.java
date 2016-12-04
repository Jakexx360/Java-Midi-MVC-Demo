package cs3500.music.view;

import cs3500.music.model.Model;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;

/**
 * The Panel that composes the visual interface of the music player
 */
public class ConcreteGuiViewPanel extends JPanel {

    /**
     * Score of music to display
     */
    private final Model score;

    /**
     * Current beat being played
     */
    private int currentBeat = 0;

    /**
     * Creates a new Panel with the given Model
     *
     * @param score Piece of music to display
     */
    public ConcreteGuiViewPanel(Model score) {
        this.score = score;
    }

    /**
     * Constants for drawing
     */
    private static final int COLUMN_WIDTH = 80;
    private static final int NOTE_WIDTH = COLUMN_WIDTH / 4;
    private static final int LEFT_ALIGN = 25;

    /**
     * Puts together the graphical interface
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Some constants
        String[] noteRange = score.getNoteRange();
        int songLength = score.getSongLength();
        int minNote = score.getMinNote();
        int noteRangeLength = noteRange.length;
        // Add notes to the view
        for (int i = 0; i < songLength; i++) {
            Note[] noteArray = score.getNotes(i);
            for (Note n : noteArray) {
                for (int j = 0; j < n.getEndTime() - n.getStartTime(); j++) {
                    if (j == 0) {
                        g.setColor(Color.BLACK); // Add the head of the Note
                    } else {
                        g.setColor(Color.GREEN); // Add the tail of the Note
                    }
                    g.fillRect(LEFT_ALIGN + (n.getStartTime() + j) * NOTE_WIDTH,
                            2 + (noteRange.length * NOTE_WIDTH) - NOTE_WIDTH * (n.getPitch() - minNote),
                            NOTE_WIDTH, NOTE_WIDTH);
                }
            }
        }
        // Create grid and headers
        g.setColor(Color.BLACK);
        for (int i = 0; i < noteRangeLength; i++) {
            // Draw column headers
            g.drawString(noteRange[noteRangeLength - 1 - i], 0, 35 + (NOTE_WIDTH * i));
            for (int j = 0; j < Math.ceil(songLength / 4.00); j++) {
                // Draw grid
                g.drawRect(LEFT_ALIGN + (COLUMN_WIDTH * j), 22 + (NOTE_WIDTH * i),
                        COLUMN_WIDTH, NOTE_WIDTH);
                if (j % 4 == 0) {
                    // Draw beat count
                    g.drawString("" + j * 4, 26 + (COLUMN_WIDTH * j), NOTE_WIDTH);
                }
            }
        }
        // Draw playback line
        g.setColor(Color.RED);
        g.drawLine(LEFT_ALIGN + (NOTE_WIDTH * currentBeat), 22,
                LEFT_ALIGN + (NOTE_WIDTH * currentBeat), 22 + (noteRange.length * NOTE_WIDTH));
    }

    /**
     * Sets the current beat to move playback arrow
     *
     * @param beat Current beat of playback
     */
    public void setCurrentBeat(int beat) {
        this.currentBeat = beat;
        repaint();
    }

    /**
     * Set the size of the view
     *
     * @return The dimensions of the window
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20 * score.getSongLength() + COLUMN_WIDTH,
                score.getNoteRange().length * 20 + 22);
    }
}
