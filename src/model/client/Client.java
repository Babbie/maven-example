package model.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Bab on 30-11-2016.
 */
public class Client implements Runnable {
    private String hostName;
    private int portNumber;

    public Client(String hostName, int portNumber) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
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
    public void run() {
        try (Socket server = new Socket(hostName, portNumber)) {
            //TODO: Talk to server
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to host.", e);
        }
    }
}
