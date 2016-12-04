package cs3500.music.tests;

import java.util.Arrays;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Mock MidiReceiver
 */
public class MockReceiver implements Receiver {

    private final StringBuilder sb;

    /**
     * Create a MockReceiver
     *
     * @param sb StringBuilder to utilize
     */
    public MockReceiver(StringBuilder sb) {
        this.sb = sb;
    }

    /**
     * Take a MidiMessage and output it to console
     *
     * @param midiMessage Message to print
     * @param l           Timestamp of message
     */
    @Override
    public void send(MidiMessage midiMessage, long l) {
        sb.append(Arrays.toString(midiMessage.getMessage())).append(" : ").append(l).append("\n");
    }

    /**
     * End of the song
     */
    @Override
    public void close() {
        sb.append("End of song");
    }
}