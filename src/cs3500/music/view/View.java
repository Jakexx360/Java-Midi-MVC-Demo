package cs3500.music.view;

/**
 * Represents the visual aspect of a Music Editor
 */
public interface View {

    /**
     * Calls the display methods and shows a representation of the music
     */
    void display();

    /**
     * Sets current beat that is being played for the view
     */
    void setCurrentBeat(int beat);

    /**
     * Pause or unpause the music
     */
    void pause();
}
