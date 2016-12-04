package cs3500.music.view;

import cs3500.music.model.Model;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

/**
 * A composite of the Midi and Gui views
 */
public class CompositeViewImpl implements View {

    /**
     * Views that make up the composite view
     */
    private View midiView;
    private GuiView guiView;

    /**
     * Create a new composite view
     *
     * @param score Piece of music to represent
     */
    public CompositeViewImpl(Model score, EventListener e) {
        midiView = new MidiViewImpl(score);
        guiView = new GuiViewImpl(score);
        guiView.addKeyHandler((KeyListener) e);
        guiView.addMouseHandler((MouseListener) e);
    }

    /**
     * Calls the display methods and shows a representation of the music
     */
    @Override
    public void display() {
        midiView.display();
        guiView.display();
    }

    /**
     * Sets current beat that is being played for the view
     */
    public void setCurrentBeat(int beat) {
        midiView.setCurrentBeat(beat);
        guiView.setCurrentBeat(beat);
    }

    /**
     * Pause or unpause the music
     */
    @Override
    public void pause() {
        midiView.pause();
        guiView.pause();
    }
}
