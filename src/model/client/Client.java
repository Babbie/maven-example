package model.client;

import java.io.*;
import java.net.Socket;

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
            try {
                PrintStream output = new PrintStream(server.getOutputStream());
                BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                //TODO: Get text from user
                String text = "hoi sebas";
                //TODO: Send circle with text
                output.println(text);
                //TODO: Status: waiting
                String result = input.readLine();
                //TODO: Receive circle with result
            } catch (IOException e) {
                if (server.isClosed()) {
                    //TODO: Status: connection dropped
                } else {
                    throw new RuntimeException("Error with connection.", e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to host.", e);
        }
    }
}
