package main;

/**
 * Simple interface forcing implementation of threadUpdate, notifying the implementer of changes
 * in the threads they are subscribed to. To subscribe, use LaneThread#addListener(ThreadListener).
 */
public interface ThreadListener {
    void threadUpdate(LaneThread laneThread);
}
