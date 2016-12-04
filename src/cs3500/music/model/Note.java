package cs3500.music.model;

/**
 * Represents a musical note
 */
public final class Note {

    /**
     * Start time of the Note INVARIANT: StartTime >= 0
     */
    private final int startTime;
    /**
     * End time of the Note INVARIANT: EndTime > 0
     **/
    private final int endTime;
    /**
     * Represents the pitch of the Note within an octave
     */
    private final int pitch;
    /**
     * The instrument that plays the Note
     */
    private final int instrument;
    /**
     * The volume of the Note
     */
    private final int volume;

    /**
     * Constructs a representation of a Note INVARIANTS: 0<=pitch<=127, 0<=startTime<endTime,
     * 0<=volume<=127
     *
     * @param startTime  Start time of the Note
     * @param endTime    End time of the Note
     * @param instrument Instrument that plays the Note
     * @param pitch      Pitch of the Note (includes octave)
     * @param volume     Volume of the Note
     */
    public Note(int startTime, int endTime, int instrument, int pitch, int volume) {
        if (pitch < 0 || pitch > 127) {
            throw new IllegalArgumentException("Invalid note value: " + pitch);
        }
        if (startTime < 0 || startTime >= endTime) {
            throw new IllegalArgumentException("Invalid start time: " + startTime);
        }
        if (volume < 0 || volume > 127) {
            throw new IllegalArgumentException("Invalid volume: " + volume);
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.instrument = instrument;
        this.pitch = pitch;
        this.volume = volume;
    }

    /**
     * Gets note end time
     *
     * @return End time of the note
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Gets Note startTime
     *
     * @return Start time of the Note
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Gets Note pitch
     *
     * @return Pitch of the Note
     */
    public int getPitch() {
        return pitch;
    }

    /**
     * Gets Note volume
     *
     * @return Volume of the Note
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Gets Note instrument
     *
     * @return Instrument that plays the Note
     */
    public int getInstrument() {
        return instrument;
    }
}
