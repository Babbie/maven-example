package main;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Bab on 5-1-2017.
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

    @Override
    public void run() {
        try {
            doRun();
        } finally {
            done = true;
            notifyListeners();
        }
    }

    public abstract void doRun();
}
