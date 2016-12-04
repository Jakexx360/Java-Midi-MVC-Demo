package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.Model;
import cs3500.music.model.Note;

/**
 * Handles mouse events
 */
public class EventHandler implements MouseListener, KeyListener {

    /**
     * Model to interact with
     */
    private Model score;

    /**
     * Maps for key events
     */
    private Map<Integer, Runnable> keyType = new HashMap<>();
    private Map<Integer, Runnable> keyPress = new HashMap<>();
    private Map<Integer, Runnable> keyRelease = new HashMap<>();

    /**
     * Currently selected Note
     */
    private Note selectedNote = null;

    /**
     * Create a new mouse handler with the given score
     *
     * @param score Score that the mouse handler will interact with
     */
    public EventHandler(Model score) {
        this.score = score;
    }

    /**
     * Sets selectedNote to the Note the user clicked on, if it exists
     *
     * @param time  Beat that the Note is playing on
     * @param pitch Pitch the Note is playing
     */
    private void selectNote(int time, int pitch) {
        this.selectedNote = null;
        if (time >= 0 && pitch >= 0) {
            // If right click, remove existing note
            for (Note n : score.getPlayingNotes(time)) {
                if (n.getPitch() == pitch) {
                    this.selectedNote = n;
                }
            }
        }
    }

    /**
     * Moves the Note in the given direction
     *
     * @param direction Direction to move the Note
     */
    public void moveNote(String direction) {
        if (selectedNote != null) {
            switch (direction) {
                case "up":
                    this.selectedNote = score.moveNote(selectedNote, selectedNote.getStartTime(),
                            selectedNote.getEndTime(), selectedNote.getPitch() + 1);
                    break;
                case "down":
                    this.selectedNote = score.moveNote(selectedNote, selectedNote.getStartTime(),
                            selectedNote.getEndTime(), selectedNote.getPitch() - 1);
                    break;
                case "left":
                    if (selectedNote.getStartTime() != 0) { // Don't let the Note move to beat -1
                        this.selectedNote = score.moveNote(selectedNote, selectedNote.getStartTime() - 1,
                                selectedNote.getEndTime() - 1, selectedNote.getPitch());
                    }
                    break;
                case "right":
                    this.selectedNote = score.moveNote(selectedNote, selectedNote.getStartTime() + 1,
                            selectedNote.getEndTime() + 1, selectedNote.getPitch());
                    break;
            }
        }
    }

    /**
     * Adds a new key event to the keyboard handler
     *
     * @param charCode  Character code for key event
     * @param r         Action to perform when the key is pressed
     * @param eventType Type of the key event: type, press, or release
     */
    public void installKeyEvent(int charCode, Runnable r, String eventType) {
        switch (eventType) {
            case "type":
                keyType.put(charCode, r);
                break;
            case "press":
                keyPress.put(charCode, r);
                break;
            case "release":
                keyRelease.put(charCode, r);
                break;
            default:
                System.err.println("Invalid KeyEvent type: " + eventType + ". Unable to add event.");
                break;
        }
    }

    /**
     * Executes events when a key is typed
     *
     * @param keyEvent Key event to handle
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyType.containsKey(keyEvent.getKeyCode())) {
            keyType.get(keyEvent.getKeyCode()).run();
        }
    }

    /**
     * Executes events when a key is pressed
     *
     * @param keyEvent Key event to handle
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyPress.containsKey(keyEvent.getKeyCode())) {
            keyPress.get(keyEvent.getKeyCode()).run();
        }
    }

    /**
     * Executes events when a key is released
     *
     * @param keyEvent Key event to handle
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyRelease.containsKey(keyEvent.getKeyCode())) {
            keyRelease.get(keyEvent.getKeyCode()).run();
        }
    }

    /**
     * Handles mouse pressed events for the View
     *
     * @param mouseEvent Event to handle
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int time = (mouseEvent.getX() - 25) / 20;
        int pitch = score.getMaxNote() - ((mouseEvent.getY() - 22) / 20);
        // Figure out which Note the user clicked
        selectNote(time, pitch);
        if (selectedNote == null) {
            if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() > 1) {
                // If double left click on an empty space, create a new Note
                score.addNote(time, time + 1, 0, pitch, 70);
            }
        } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            // If right click on non-null Note, remove existing note
            score.removeNote(selectedNote);
        } else if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() > 1) {
            // If double left click on a non-null note, increase Note duration
            this.selectedNote = score.moveNote(selectedNote, selectedNote.getStartTime(),
                    selectedNote.getEndTime() + 1, selectedNote.getPitch());
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
