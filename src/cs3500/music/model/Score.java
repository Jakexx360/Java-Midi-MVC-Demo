package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.util.*;

/**
 * Represents a musical score
 */
public final class Score implements Model {

    /**
     * Initial length of a song
     */
    private static final int INITIAL_CAPACITY = 32;
    /**
     * Starting number of possible notes
     */
    private static final int NUMBER_OF_NOTES = 128;

    /**
     * Represents the entire musical score, with each index of the outer ArrayList representing each
     * beat. Each array of notes represent the notes that can play on a single beat, and the List is
     * always at least as long as INITIAL_CAPACITY
     */
    private List<List<Note>> score = new ArrayList<>(INITIAL_CAPACITY);

    /**
     * The tempo (in microseconds per beat)
     */
    private int tempo;

    /**
     * Create blank Score with a specific initial capacity and tempo
     */
    private Score(int tempo) {
        setTempo(tempo);
    }

    /**
     * Initializes score array to given size. Allows songs of relatively unlimited length.
     *
     * @param size Size to increase score ArrayList to
     * @throws IllegalArgumentException if size < this.score.size()
     */
    private void initScore(int size) {
        if (size > score.size()) {
            for (int i = score.size(); i < size; i++) {
                score.add(new ArrayList<>(NUMBER_OF_NOTES));
            }
        } else {
            throw new IllegalArgumentException("Invalid size to expand to: " + size);
        }
    }

    /**
     * Creates a new Note and adds it to the Score
     *
     * @param startTime  Start time of the Note
     * @param endTime    End time of the Note
     * @param instrument Instrument that plays the Note
     * @param pitch      Pitch of the Note (includes octave)
     * @param volume     Volume of the Note
     */
    public void addNote(int startTime, int endTime, int instrument, int pitch, int volume) {
        if (score.size() < endTime) {
            initScore(endTime); // Initializes a Beat if it does not yet exist
        }
        score.get(startTime).add(new Note(startTime, endTime, instrument, pitch, volume));
    }

    /**
     * Adds given NOTES to the Score
     *
     * @param notes NOTES to add
     */
    public void addNotes(Note... notes) {
        for (Note n : notes) {
            if (score.size() < n.getEndTime()) {
                initScore(n.getEndTime()); // Initializes a Beat if it does not exist
            }
            score.get(n.getStartTime()).add(n);
        }
    }

    /**
     * Clears the entire existing musical score
     */
    public void reset() {
        score.clear();
    }

    /**
     * Sets tempo for a particular Score
     *
     * @param tempo Tempo to change to
     * @throws IllegalArgumentException If tempo is negative
     */
    public void setTempo(int tempo) {
        if (tempo >= 0) {
            this.tempo = tempo;
        } else {
            throw new IllegalArgumentException("Invalid tempo: " + tempo);
        }
    }

    /**
     * Gets the current tempo of a Score
     *
     * @return The current tempo in microseconds per beat
     */
    public int getTempo() {
        return this.tempo;
    }

    /**
     * Gets a list of notes to play at a specified beat
     *
     * @param position The number of beat to check
     * @return An array of notes within a specified beat
     * @throws ArrayIndexOutOfBoundsException if the position is out of the bounds of the array
     */
    public Note[] getNotes(int position) {
        if (position < 0 || position >= score.size())
            throw new ArrayIndexOutOfBoundsException("Unable to retrieve Note at " + position);
        return score.get(position).toArray(new Note[score.get(position).size()]);
    }

    /**
     * Gets a list of all notes playing at a specific position
     *
     * @param position Position to check
     * @return A list of all notes playing at the given position
     */
    public List<Note> getPlayingNotes(int position) {
        if (position < 0 || position >= score.size())
            throw new ArrayIndexOutOfBoundsException("Unable to retrieve Note at " + position);
        List<Note> playingNotes = new ArrayList<>();
        for (int i = 0; i <= position; i++) {
            for (Note n : getNotes(i)) {
                if (position < n.getEndTime()) {
                    playingNotes.add(n);
                }
            }
        }
        return playingNotes;
    }

    /**
     * Gets the number of beats in the Score INVARIANT: Always returns a value equal to or greater
     * than INITIAL_CAPACITY
     *
     * @return The length of the Score
     */
    public int getSongLength() {
        return this.score.size();
    }

    /**
     * Removes a given Note from the Score
     *
     * @param n Note to be removed
     */
    public void removeNote(Note n) {
        score.get(n.getStartTime()).remove(n);
    }

    /**
     * Moves a given Note to the given startTime
     *
     * @param n         Note to move
     * @param startTime Beat to move the Note to
     * @param endTime   Beat to end the Note at
     * @param pitch     Pitch to change the Note to
     * @return The moved Note
     */
    public Note moveNote(Note n, int startTime, int endTime, int pitch) {
        Note newNote = new Note(startTime, endTime, n.getInstrument(), pitch, n.getVolume());
        this.removeNote(n);
        addNotes(newNote);
        return newNote;
    }

    /**
     * Gets the lowest Note in a given List of NOTES
     *
     * @return Int representation of the lowest Note, or -1 if the song is empty
     */
    public int getMinNote() {
        int minNote = -1;
        for (int i = 0; i < this.getSongLength(); i++) {
            for (Note n : this.getNotes(i)) {
                if (minNote < 0 || minNote > n.getPitch()) {
                    minNote = n.getPitch();
                }
            }
        }
        return minNote;
    }

    /**
     * Gets the highest Note in a given List of NOTES
     *
     * @return Int representation of the highest Note, or -1 if the song is empty
     */
    public int getMaxNote() {
        int maxNote = -1;
        for (int i = 0; i < this.getSongLength(); i++) {
            for (Note n : this.getNotes(i)) {
                if (maxNote < n.getPitch()) {
                    maxNote = n.getPitch();
                }
            }
        }
        return maxNote;
    }

    /**
     * Gets a String range of all NOTES that need to be displayed
     *
     * @return An array of Strings of the range of NOTES in a piece of music
     */
    public String[] getNoteRange() {
        // Determine range of note columns to display
        String[] notes = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        int maxNote = this.getMaxNote();
        int minNote = this.getMinNote();
        String[] noteArray = new String[maxNote - minNote + 1];
        if (minNote >= 0 && maxNote >= 0) { // Make sure we have at least one note to print
            int j = 0;
            for (int i = minNote; i <= maxNote; i++) {
                String s = notes[i % 12]; // Get string representation of Note
                int oct = Math.floorDiv(i, 12) - 1; // Calculate octave
                noteArray[j] = s + oct;
                if (noteArray[j].length() < 3) {
                    noteArray[j] = " " + noteArray[j]; // Add extra space to maintain column order
                }
                j++;
            }
        }
        return noteArray;
    }

    /**
     * Converts the Score to its String representation
     *
     * @return String representation of the Score
     */
    @Override
    public String toString() {
        // Print visual representation of NOTES
        StringBuilder sb = new StringBuilder("    ");
        int noteRange = getNoteRange().length + 1;
        String[] columnRange = getNoteRange();
        // Build the column header
        for (String s : columnRange) {
            sb.append(s);
        }
        sb.append("\n");
        String[][] notesToDisplay = new String[getSongLength()][noteRange];
        for (int i = 0; i < getSongLength(); i++) {
            Note[] b = getNotes(i);
            // Maintain spacing of column of numbers
            if (i > 999) {
                sb.append(i).append(" ");
            } else if (i > 99) {
                sb.append(i).append("  ");
            } else if (i > 9) {
                sb.append(i).append("   ");
            } else {
                sb.append(i).append("    ");
            }
            // Adds a given note to notesToDisplay as Strings
            for (Note n : b) {
                notesToDisplay[i][n.getPitch() - getMinNote()] = "X  "; // Set head of Note
                for (int j = n.getEndTime() - n.getStartTime() - 1; j > 0; j--) {
                    notesToDisplay[i + j][n.getPitch() - getMinNote()] = "|  "; // Set tail of Note
                }
            }
            // Fill remaining spaces
            for (int j = 0; j < noteRange; j++) {
                if (notesToDisplay[i][j] == null) {
                    notesToDisplay[i][j] = "   ";
                }
                sb.append(notesToDisplay[i][j]); // Build a line of output
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Constructs a builder for configuring and then creating a piece of Music.
     *
     * @return the new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements CompositionBuilder<Score> {

        /**
         * Default builder arguments
         */
        private List<Note> notes = new ArrayList<>(32);
        private int tempo = 200000;

        /**
         * Constructs an actual composition, given the notes that have been added
         *
         * @return The new composition
         */
        public Score build() {
            Score s = new Score(tempo);
            Note[] noteArray = this.notes.toArray(new Note[notes.size()]);
            s.addNotes(noteArray);
            return s;
        }

        /**
         * Sets the tempo of the piece
         *
         * @param tempo The speed, in microseconds per beat
         * @return This builder
         */
        public CompositionBuilder<Score> setTempo(int tempo) {
            this.tempo = tempo;
            return this;
        }

        /**
         * Adds a new note to the piece
         *
         * @param start      The start time of the note, in beats
         * @param end        The end time of the note, in beats
         * @param instrument The instrument number (to be interpreted by MIDI)
         * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C
         * @param volume     The volume (in the range [0, 127])
         * @return This builder
         */
        public CompositionBuilder<Score> addNote(int start, int end,
                                                 int instrument, int pitch, int volume) {
            Note n = new Note(start, end, instrument, pitch, volume);
            notes.add(n);
            return this;
        }
    }
}
