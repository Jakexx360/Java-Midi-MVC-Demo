package cs3500.music.model;

import java.util.List;

/**
 * Interface representing the MusicPlayer Model
 */
public interface Model {

    /**
     * Creates a new Note and adds it to the Score
     *
     * @param startTime  Start time of the Note
     * @param endTime    End time of the Note
     * @param instrument Instrument that plays the Note
     * @param pitch      Pitch of the Note (includes octave)
     * @param volume     Volume of the Note
     */
    void addNote(int startTime, int endTime, int instrument, int pitch, int volume);

    /**
     * Adds given NOTES to the Score
     *
     * @param notes NOTES to add
     */
    void addNotes(Note... notes);

    /**
     * Clears the entire existing musical score
     */
    void reset();

    /**
     * Sets tempo for a particular Score
     *
     * @param tempo Tempo to change to
     */
    void setTempo(int tempo);

    /**
     * Gets the current tempo of a Score
     *
     * @return The current tempo in microseconds per beat
     */
    int getTempo();

    /**
     * Gets a list of NOTES to play at a specified Beat
     *
     * @param position The number of Beat to check
     * @return An array of NOTES within a specified Beat
     * @throws ArrayIndexOutOfBoundsException if the position is out of the bounds of the array
     */
    Note[] getNotes(int position);

    /**
     * Gets the number of Beats in a Score INVARIANT: Always returns a value equal to or greater than
     * INITIAL_CAPACITY
     *
     * @return The length of the Score
     */
    int getSongLength();

    /**
     * Removes a given Note from the Scoore
     *
     * @param n Note to be removed
     */
    void removeNote(Note n);

    /**
     * Moves a given Note to the given startTime
     *
     * @param n         Note to move
     * @param startTime Beat to move the Note to
     * @param endTime   Beat to end the Note at
     * @param pitch     Pitch to change the Note to
     * @return The moved Note
     */
    Note moveNote(Note n, int startTime, int endTime, int pitch);

    /**
     * Gets the lowest Note in a given List of NOTES
     *
     * @return Int representation of the lowest Note, or -1 if the song is empty
     */
    int getMinNote();

    /**
     * Gets the highest Note in a given List of NOTES
     *
     * @return Int representation of the highest Note, or -1 if the song is empty
     */
    int getMaxNote();

    /**
     * Gets a String range of all NOTES that need to be displayed
     *
     * @return An array of Strings of the range of NOTES in a piece of music
     */
    String[] getNoteRange();

    /**
     * Gets a list of all notes playing at a specific position
     *
     * @param position Position to check
     * @return A list of all notes playing at the given position
     */
    List<Note> getPlayingNotes(int position);
}
