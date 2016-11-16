package model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private boolean stopped = false;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public synchronized void run() {
        while (!isStopped()) {
            try (
                    ServerSocket server = new ServerSocket(8080);
                    Socket client = server.accept()
            ) {
                new Thread(new ServerThread(client)).start();
            } catch (IOException e) {
                throw new RuntimeException("Error accepting connection", e);
            }
        }
    }

    private synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized void stop() {
        stopped = true;
    }
}
