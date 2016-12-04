package cs3500.music.view;

import cs3500.music.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Frame that contains the Panel representing the music player interface
 */
public class GuiViewImpl extends JFrame implements GuiView {

    /**
     * Score of music to represent
     */
    final private Model score;

    /**
     * Current beat being played
     */
    private int currentBeat = 0;

    /**
     * State of being paused
     */
    private boolean isPaused = true;

    /**
     * Content panel for the JFrame
     */
    private ConcreteGuiViewPanel panel;
    private JScrollPane scrollPane;

    /**
     * Creates new GuiView
     *
     * @param score Music to represent visually
     */
    public GuiViewImpl(Model score) {
        this.score = score;
        panel = new ConcreteGuiViewPanel(score);
        scrollPane = new JScrollPane(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Vertical scroll behavior
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setUnitIncrement(10);
        InputMap im = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
        im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
        // Horizontal scroll behavior
        JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
        horizontal.setUnitIncrement(10);
        im = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
        im.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");
        // Put together frame
        add(scrollPane, BorderLayout.CENTER);
        this.pack();
    }

    /**
     * Calls the display methods and shows a representation of the music
     */
    public void display() {
        this.setVisible(true);
        if (!isPaused && currentBeat < score.getSongLength()) {
            this.currentBeat++;
            scroll();
        }
        panel.setCurrentBeat(currentBeat);
    }

    /**
     * Sets current beat that is being played for the view
     */
    public void setCurrentBeat(int beat) {
        if (beat < score.getSongLength()) {
            this.currentBeat = beat;
        } else if (beat < 0) {
            this.currentBeat = 0;
        } else {
            this.currentBeat = score.getSongLength();
        }
        panel.setCurrentBeat(currentBeat);
        scroll();
    }


    /**
     * Scroll the view based on the current beat
     */
    public void scroll() {
        if (currentBeat % 64 == 0 || currentBeat >= score.getSongLength()) {
            scrollPane.getViewport().setViewPosition(new Point(20 * currentBeat, 0));
        }
    }

    /**
     * Pause or unpause the music
     */
    public void pause() {
        this.isPaused = !isPaused;
    }

    /**
     * Set the size of the view
     *
     * @return The dimension of the frame
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20 * score.getSongLength() + 50, score.getNoteRange().length * 25);
    }

    /**
     * Adds a key listener to the GUI
     *
     * @param k KeyListener to add
     */
    public void addKeyHandler(KeyListener k) {
        this.addKeyListener(k);
    }

    /**
     * Adds a mouse listener to the GUI
     *
     * @param m MouseListener to add
     */
    public void addMouseHandler(MouseListener m) {
        this.panel.addMouseListener(m);
    }

    /**
     * Removes a key listener from the GUI
     *
     * @param k KeyListener to remove
     */
    public void removeKeyHandler(KeyListener k) {
        this.removeKeyListener(k);
    }

    /**
     * Removes a mouse listener from the GUI
     *
     * @param m MouseListener to remove
     */
    public void removeMouseHandler(MouseListener m) {
        this.removeMouseListener(m);
    }
}
