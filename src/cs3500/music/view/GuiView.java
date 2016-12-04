package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Sub-interface of View
 */
public interface GuiView extends View {
    /**
     * Adds a key listener to the GUI
     *
     * @param k KeyListener to add
     */
    void addKeyHandler(KeyListener k);

    /**
     * Adds a mouse listener to the GUI
     *
     * @param m MouseListener to add
     */
    void addMouseHandler(MouseListener m);

    /**
     * Removes a key listener from the GUI
     *
     * @param k KeyListener to remove
     */
    void removeKeyHandler(KeyListener k);

    /**
     * Removes a mouse listener from the GUI
     *
     * @param m MouseListener to remove
     */
    void removeMouseHandler(MouseListener m);

    /**
     * Scroll the view based on
     */
    void scroll();
}
