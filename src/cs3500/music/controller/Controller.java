package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cs3500.music.model.Model;
import cs3500.music.view.CompositeViewImpl;
import cs3500.music.view.View;

/**
 * Controller for the music editor
 */
public class Controller {

    /**
     * Creates a new music editor controller
     *
     * @param score Piece of music to represent
     */
    public Controller(Model score) {
        // Create new key handler and mouse handler
        EventHandler eventHandler = new EventHandler(score);

        // Create new combined view
        View view = new CompositeViewImpl(score, eventHandler);

        // Set up timer
        Timer timer = new Timer();
        // Increment beat each tick
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                view.display();
            }
        };
        // Schedule the tick based on the song tempo
        timer.scheduleAtFixedRate(timerTask, 0, score.getTempo() / 1000);


        // Pause Playback
        eventHandler.installKeyEvent(KeyEvent.VK_SPACE, view::pause, "press");
        // Restart playback
        eventHandler.installKeyEvent(KeyEvent.VK_HOME,
                () -> view.setCurrentBeat(0), "press");
        // End playback
        eventHandler.installKeyEvent(KeyEvent.VK_END,
                () -> view.setCurrentBeat(score.getSongLength()), "press");
        // Note movement events
        eventHandler.installKeyEvent(KeyEvent.VK_W,
                () -> eventHandler.moveNote("up"), "press");
        eventHandler.installKeyEvent(KeyEvent.VK_S,
                () -> eventHandler.moveNote("down"), "press");
        eventHandler.installKeyEvent(KeyEvent.VK_A,
                () -> eventHandler.moveNote("left"), "press");
        eventHandler.installKeyEvent(KeyEvent.VK_D,
                () -> eventHandler.moveNote("right"), "press");

        // Display the created view
        view.display();
    }
}
