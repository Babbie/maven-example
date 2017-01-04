package model.client;

import model.Circle;
import model.Lane;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Bab on 30-11-2016.
 */
public class Client implements Runnable, Observer {
    private String hostName;
    private int portNumber;
    private String text;
    private boolean arrived = false;

    public Client(String hostName, int portNumber, String text) throws IOException {
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.text = text;
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
                Circle outgoing = new Circle(true, true, Lane.Second, text);
                outgoing.addObserver(this);
                while (!arrived) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        //busywaiting
                    }
                }
                output.println(text);
                arrived = false;
                output.flush();
                System.out.println("awaiting result");
                String result = input.readLine();
                Circle incoming = new Circle(false, true, Lane.Second, result);
                incoming.addObserver(this);
                while (!arrived) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        //busywaiting
                    }
                }
                arrived = false;
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

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (((Circle)o).hasArrived()) {
            synchronized (o) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    arrived = true;
                    ((Circle) o).delete();
                }
            }
        }
    }
}
