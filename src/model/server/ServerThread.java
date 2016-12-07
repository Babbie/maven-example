package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
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
        try {
            //TODO: Talk to server
            PrintStream output = new PrintStream(client.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //TODO: Status: waiting
            String text = input.readLine();
            //TODO: Receive circle with text
            output.println(new StringBuilder(text).reverse().toString());
            //TODO: Send circle with result
            //TODO: Status: waiting
        } catch (IOException e) {
            //TODO: Status: Disconnected
        } finally {
            Server.removeThread();
        }

    }
}
