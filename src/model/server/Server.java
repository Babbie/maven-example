package model.server;

import model.Lane;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private boolean stopped = false;
    private static int threadCount = 0;
    private static int port;
    private static Object lock = new Object();

    public Server(int port) {
        this.port = port;
    }

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
            try {
                ServerSocket server = new ServerSocket(port + getThreadCount());
                Socket client = server.accept();
                System.out.println("checking threads");
                if (!threadsAreFull()) {
                    System.out.println("adding thread");
                    addThread();
                    System.out.println("running thread");
                    new Thread(new ServerThread(client, Lane.values()[getThreadCount()])).start();
                } else {
                    client.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error accepting connection", e);
            }
        }
    }

    public static void addThread() {
        synchronized (lock) {
            threadCount++;
        }
    }

    public static void removeThread() {
        synchronized (lock) {
            threadCount--;
        }
    }

    public static int getThreadCount() {
        synchronized (lock) {
            return threadCount;
        }
    }

    public static boolean threadsAreFull() {
        synchronized (lock) {
            return threadCount == 2;
        }
    }

    private synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized void stop() {
        stopped = true;
    }
}
