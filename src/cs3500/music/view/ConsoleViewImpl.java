package cs3500.music.view;

import cs3500.music.model.Model;

/**
 * Prints the visual design of the music player to the console
 */
public final class ConsoleViewImpl implements View {

    private Model score;

    /**
     * Creates a new ConsoleViewImpl with a given piece of music
     *
     * @param score Music to display in the console
     */
    public ConsoleViewImpl(Model score) {
        this.score = score;
    }

    /**
     * Displays all the NOTES in the music on the screen
     */
    public void display() {
        System.out.println(score);
    }

    /**
     * Sets current beat that is being played for the view
     */
    @Override
    public void setCurrentBeat(int beat) {
        // Do nothing
    }

    /**
     * Pause or unpause the music
     */
    @Override
    public void pause() {
        // Do nothing
    }
}
