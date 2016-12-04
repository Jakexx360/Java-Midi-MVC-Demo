package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.Note;
import cs3500.music.model.Score;

import static org.junit.Assert.*;

/**
 * Tests for the Score
 */
public class ScoreTest {

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

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testException() {
        initData();
        s1.addNotes(n2, n3, n4);
        s1.getNotes(-5); // Throws exception for being negative
        s1.getNotes(400); // Throws exception for being too large
    }

    @Test
    public void testAddNote() throws Exception {
        initData();
        s1.addNotes(n2, n3, n4);
        s1.addNote(33, 37, 1, 66, 70);

        assertEquals(s1.getNotes(n6.getStartTime())[0].getStartTime(), 33);
        assertEquals(s1.getNotes(n6.getStartTime())[0].getEndTime(), 37);
        assertEquals(s1.getNotes(n6.getStartTime())[0].getInstrument(), 1);
        assertEquals(s1.getNotes(n6.getStartTime())[0].getPitch(), 66);
        assertEquals(s1.getNotes(n6.getStartTime())[0].getVolume(), 70);
    }

    @Test
    public void testAddNotes() throws Exception {
        initData();
        s1.addNotes(n1, n6);
        assertEquals(s1.getNotes(n1.getStartTime())[0], n1);
        assertEquals(s1.getNotes(n6.getStartTime())[0], n6);
    }

    @Test
    public void testReset() throws Exception {
        initData();
        s1.addNotes(n1, n2, n3, n4, n5, n6);
        assertEquals(s1.getSongLength(), 37);
        s1.reset();
        assertEquals(s1.getSongLength(), 0);
    }

    @Test
    public void testGetSongLength() throws Exception {
        initData();
        assertEquals(s1.getSongLength(), 0);
        s1.addNotes(n6);
        assertEquals(s1.getSongLength(), 37);
    }

    @Test
    public void testRemoveNote() throws Exception {
        initData();
        s1.addNotes(n1, n2, n3, n4, n5, n6);
        assertEquals(s1.getNotes(n1.getStartTime()).length, 2);
        s1.removeNote(n5);
        assertEquals(s1.getNotes(n1.getStartTime()).length, 1);
        s1.removeNote(n1);
        assertEquals(s1.getNotes(n1.getStartTime()).length, 0);
    }

    @Test
    public void testMoveNote() throws Exception {
        initData();
        s1.addNotes(n1, n5);
        assertEquals(s1.getNotes(n1.getStartTime()).length, 2); // Check if added correctly
        s1.moveNote(n1, 16, n1.getEndTime() + 16, 99); // Move the Note
        assertEquals(s1.getNotes(n1.getStartTime()).length, 1); // Note isn't where it used to be
        assertEquals(s1.getNotes(16).length, 1); // Note has been moved to the new startTime
        assertEquals(s1.getNotes(16)[0].getStartTime(), 16);
        assertEquals(s1.getNotes(16)[0].getEndTime(), 21);
        assertEquals(s1.getNotes(16)[0].getPitch(), 99);
    }

    @Test
    public void testGetMinMaxNote() throws Exception {
        initData();
        s1.addNotes(n1, n2, n5);
        assertEquals(s1.getMinNote(), 0);
        assertEquals(s1.getMaxNote(), 127);
        initData();
        s1.addNote(1, 5, 1, 99, 70);
        s1.addNote(1, 5, 1, 45, 70);
        s1.addNote(1, 4, 1, 60, 70);
        assertEquals(s1.getMinNote(), 45);
        assertEquals(s1.getMaxNote(), 99);
    }

    @Test
    public void testGetNoteRange() throws Exception {
        initData();
        s1.addNotes(n3, n2);
        assertArrayEquals(s1.getNoteRange(),
                new String[]{"B-1", " C0", "C#0", " D0", "D#0", " E0", " F0"});
    }

    @Test
    public void testToString() throws Exception {
        initData();
        s1.addNotes(n4, n6);
        assertEquals(s1.toString(),
                "     D3D#3 E3 F3F#3 G3G#3 A3A#3 B3 C4C#4 D4D#4 E4 F4F#4\n" +
                        "0                                                          \n" +
                        "1                                                          \n" +
                        "2                                                          \n" +
                        "3                                                          \n" +
                        "4                                                          \n" +
                        "5                                                          \n" +
                        "6                                                          \n" +
                        "7                                                          \n" +
                        "8                                                          \n" +
                        "9    X                                                     \n" +
                        "10   |                                                     \n" +
                        "11   |                                                     \n" +
                        "12   |                                                     \n" +
                        "13   |                                                     \n" +
                        "14   |                                                     \n" +
                        "15                                                         \n" +
                        "16                                                         \n" +
                        "17                                                         \n" +
                        "18                                                         \n" +
                        "19                                                         \n" +
                        "20                                                         \n" +
                        "21                                                         \n" +
                        "22                                                         \n" +
                        "23                                                         \n" +
                        "24                                                         \n" +
                        "25                                                         \n" +
                        "26                                                         \n" +
                        "27                                                         \n" +
                        "28                                                         \n" +
                        "29                                                         \n" +
                        "30                                                         \n" +
                        "31                                                         \n" +
                        "32                                                         \n" +
                        "33                                                   X     \n" +
                        "34                                                   |     \n" +
                        "35                                                   |     \n" +
                        "36                                                   |     \n");
    }

    @Test
    public void testGetNotes() {
        initData();

        s1.addNotes(n1, n2, n3, n4, n5, n6);
        Note[] notes = new Note[1];
        notes[0] = n2;

        Note[] notes2 = new Note[2];
        notes2[0] = n1;
        notes2[1] = n5;

        assertArrayEquals(s1.getNotes(1), notes);
        assertArrayEquals(s1.getNotes(0), notes2);
    }

    @Test
    public void testSetTempo() throws Exception {
        initData();

        assertEquals(s1.getTempo(), 200000);
        s1.setTempo(53578);
        assertEquals(s1.getTempo(), 53578);
        s1.setTempo(62259);
        assertEquals(s1.getTempo(), 62259);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegalTempo() {
        initData();
        s1.addNotes(n1, n2, n3);

        //A tempo of 0 makes no sense
        s1.setTempo(0);
        //A negative tempo makes even less sense
        s1.setTempo(-1);
    }

    @Test
    public void testGetPlayingNotes() throws Exception {
        initData();

        s1.addNotes(n1, n2, n3, n4, n5, n6);

        assertEquals(s1.getPlayingNotes(0).get(0), n1);
        assertEquals(s1.getPlayingNotes(0).get(1), n5);

        assertEquals(s1.getPlayingNotes(2).size(), 3);
        assertEquals(s1.getPlayingNotes(2).get(0), n1);
        assertEquals(s1.getPlayingNotes(2).get(1), n5);
        assertEquals(s1.getPlayingNotes(2).get(2), n3);
    }
}