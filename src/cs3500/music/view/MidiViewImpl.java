package cs3500.music.view;

import cs3500.music.model.Model;
import cs3500.music.model.Note;
import cs3500.music.tests.MockSynth;

import javax.sound.midi.*;

/**
 * Plays a piece of music using Midi
 */
public class MidiViewImpl implements View {
    private Synthesizer synth;
    private Receiver receiver;

    /**
     * Used for testing purposes
     */
    private boolean isTest;
    private StringBuilder sb = new StringBuilder();

    /**
     * Current beat being played
     */
    private int currentBeat = 0;

    /**
     * State of being paused
     */
    private boolean isPaused = true;

    /**
     * Score of music to represent
     */
    private final Model score;

    /**
     * Create a new MidiViewImpl that plays a piece of music using Midi
     *
     * @param score Music to play
     */
    public MidiViewImpl(Model score) {
        this(score, false);
    }

    /**
     * Create a new MidiViewImpl that plays a piece of music using Midi
     *
     * @param score  Music to play
     * @param isTest Are we running in debug mode?
     */
    public MidiViewImpl(Model score, boolean isTest) {
        this.score = score;
        this.isTest = isTest;
        try {
            if (isTest) {
                this.synth = new MockSynth(sb);
                this.receiver = synth.getReceiver();
            } else {
                this.synth = MidiSystem.getSynthesizer();
                this.receiver = synth.getReceiver();
            }
            this.synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays an individual Note using Midi
     *
     * @param n Note to play
     * @throws InvalidMidiDataException If the data provided to Midi is incorrect
     */
    public void playNote(Note n) throws InvalidMidiDataException {
        // Transfer Note information to Midi
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(),
                n.getPitch(), n.getVolume());
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(),
                n.getPitch(), n.getVolume());
        // Start the Note
        this.receiver.send(start, -1);
        // End the Note after its duration has elapsed
        this.receiver.send(stop,
                this.synth.getMicrosecondPosition() +
                        (score.getTempo() * (n.getEndTime() - n.getStartTime())));
    }

    /**
     * Calls the display methods and shows a representation of the music
     */
    @Override
    public void display() {
        // Play notes for the current beat
        try {
            if (currentBeat < score.getSongLength() && !isPaused) {
                for (Note n : score.getNotes(currentBeat)) {
                    playNote(n);
                }
                this.currentBeat++;
            }
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets current beat that is being played for the view
     */
    public void setCurrentBeat(int beat) {
        this.currentBeat = beat;
    }

    /**
     * Pause or unpause the music
     */
    public void pause() {
        this.isPaused = !isPaused;
    }

    /**
     * Gets the debug information when running in test mode, otherwise returns empty string
     *
     * @return The String of mock console output (if any)
     */
    public String getDebug() {
        return this.sb.toString();
    }
}
