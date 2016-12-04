package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.*;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Mock MidiView
 */
public class MidiViewTest {

    private Score s1 = Score.builder().build();
    private Note n1 = new Note(0, 5, 1, 0, 70);
    private Note n2 = new Note(1, 2, 1, 17, 70);
    private Note n3 = new Note(2, 4, 1, 11, 70);
    private Note n4 = new Note(9, 15, 1, 50, 70);
    private Note n5 = new Note(0, 4, 1, 127, 70);
    private Note n6 = new Note(33, 37, 1, 66, 70);

    private void initData() {
        s1 = Score.builder().build();
    }

    @Test
    public void testMidiDebug() throws Exception {
        initData();
        s1.addNotes(n1, n2, n3, n4, n5, n6);
        MidiViewImpl midi = new MidiViewImpl(s1, true);
        midi.display();
        assertEquals(midi.getDebug(),
                "[-112, 0, 70] : -1\n" +
                        "[-128, 0, 70] : 1000000\n" +
                        "[-112, 127, 70] : -1\n" +
                        "[-128, 127, 70] : 800000\n" +
                        "[-112, 17, 70] : -1\n" +
                        "[-128, 17, 70] : 200000\n" +
                        "[-112, 11, 70] : -1\n" +
                        "[-128, 11, 70] : 400000\n" +
                        "[-112, 50, 70] : -1\n" +
                        "[-128, 50, 70] : 1200000\n" +
                        "[-112, 66, 70] : -1\n" +
                        "[-128, 66, 70] : 800000\n" +
                        "End of song");
    }
}
