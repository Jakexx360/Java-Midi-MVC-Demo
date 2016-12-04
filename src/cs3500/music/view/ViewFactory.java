package cs3500.music.view;

import cs3500.music.model.Model;

/**
 * A Factory to generate a View based on given information
 */
public class ViewFactory {
    /**
     * Generates a View of the specified type with the given Model
     *
     * @param score Piece of music to create a View with
     * @param view  String of view type
     * @return A View object of the specified type
     * @throws IllegalArgumentException if the given view type is invalid
     */
    public View getView(Model score, String view) {
        if (view.equalsIgnoreCase("CONSOLE")) {
            return new ConsoleViewImpl(score);
        } else if (view.equalsIgnoreCase("VISUAL")) {
            return new GuiViewImpl(score);
        } else if (view.equalsIgnoreCase("MIDI")) {
            return new MidiViewImpl(score);
        } else {
            throw new IllegalArgumentException("Invalid view specified: " + view);
        }
    }
}
