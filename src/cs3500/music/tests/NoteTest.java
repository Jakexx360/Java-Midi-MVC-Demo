package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.Model;
import cs3500.music.model.Note;

import static org.junit.Assert.*;

/**
 * Test Note class
 */
public class NoteTest {

    private Note n1 = new Note(0, 5, 1, 0, 70);
    private Note n2 = new Note(1, 2, 1, 17, 70);
    private Note n3 = new Note(2, 4, 6, 11, 95);
    private Note n4 = new Note(42, 46, 1, 65, 12);

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNote() {
        Note n5 = new Note(5, 3, 1, 50, 70);
        Note n6 = new Note(5, 10, 1, -10, 70);
        Note n7 = new Note(-5, 2, 1, 70, 70);
    }

    @Test
    public void testGetEndTime() throws Exception {
        assertEquals(n1.getEndTime(), 5);
        assertEquals(n2.getEndTime(), 2);
        assertEquals(n3.getEndTime(), 4);
        assertEquals(n4.getEndTime(), 46);
    }

    @Test
    public void testGetStartTime() throws Exception {
        assertEquals(n1.getStartTime(), 0);
        assertEquals(n2.getStartTime(), 1);
        assertEquals(n3.getStartTime(), 2);
        assertEquals(n4.getStartTime(), 42);
    }

    @Test
    public void testGetPitch() throws Exception {
        assertEquals(n1.getPitch(), 0);
        assertEquals(n2.getPitch(), 17);
        assertEquals(n3.getPitch(), 11);
        assertEquals(n4.getPitch(), 65);
    }

    @Test
    public void testGetVolume() throws Exception {
        assertEquals(n1.getVolume(), 70);
        assertEquals(n2.getVolume(), 70);
        assertEquals(n3.getVolume(), 95);
        assertEquals(n4.getVolume(), 12);
    }

    @Test
    public void testGetInstrument() throws Exception {
        assertEquals(n1.getInstrument(), 1);
        assertEquals(n2.getInstrument(), 1);
        assertEquals(n3.getInstrument(), 6);
        assertEquals(n4.getInstrument(), 1);
    }
}