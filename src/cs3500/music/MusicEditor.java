package cs3500.music;

import cs3500.music.controller.Controller;
import cs3500.music.model.Score;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ViewFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Main class for the music player
 */
public final class MusicEditor {
    public static void main(String[] args) {
        try {
            // Create an instance of a View using the given program arguments, then display it
            if (args.length == 1) {
                new Controller(MusicReader.parseFile(new FileReader(args[0]), Score.builder()));
            } else {
                new ViewFactory().getView(MusicReader.parseFile(new FileReader(args[0]), Score.builder()),
                        args[1]).display();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file path: " + args[0]);
        }
    }
}