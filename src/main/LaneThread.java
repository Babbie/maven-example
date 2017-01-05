package main;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * An abstract class providing functionality for the server and client, making it so
 * that the GUI can obtain information by registering as a listener.
 */
public abstract class LaneThread extends Thread {
    private Set<ThreadListener> listeners = new CopyOnWriteArraySet<>();
    private boolean done = false;
    protected Lane lane;
    private String messageContent;

    public void addListener(ThreadListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (ThreadListener listener : listeners) {
            listener.threadUpdate(this);
        }
    }

    public LaneThread(Lane lane) {
        this.lane = lane;
    }

    public void setMessage(String message) {
        messageContent = message;
        notifyListeners();
    }

    public String getMessage() {
        return messageContent;
    }

    public boolean isDone() {
        return done;
    }

    public Lane getLane() {
        return lane;
    }

    /**
     * Wrapper for doRun
     */
    @Override
    public void run() {
        try {
            doRun();
        } finally {
            done = true;
            notifyListeners();
        }
    }
    /**
     * This method will be run when the thread is started and contains the main functionality.
     */
    public abstract void doRun();
}
