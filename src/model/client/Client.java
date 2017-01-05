package model.client;

import main.LaneThread;
import model.Circle;
import main.Lane;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Client extends LaneThread implements Observer {
    private String hostName;
    private int portNumber;
    private String text;
    private boolean arrived = false;

    public Client(String hostName, int portNumber, String text) {
        super(Lane.Second);
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.text = text;
    }

    @Override
    public void doRun() {
        setMessage("Connecting...");
        try (Socket server = new Socket(hostName, portNumber)) {
            try {
                PrintStream output = new PrintStream(server.getOutputStream());
                BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                setMessage("Sending input...");
                Circle outgoing = new Circle(true, true, lane, text);
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
                setMessage("Awaiting result...");
                String result = input.readLine();
                Circle incoming = new Circle(false, true, lane, result);
                incoming.addObserver(this);
                while (!arrived) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        //busywaiting
                    }
                }
                arrived = false;
                setMessage("Connection closed.");
            } catch (IOException e) {
                if (server.isClosed()) {
                    setMessage("Connection closed.");
                } else {
                    setMessage("Connection error.");
                }
            }
        } catch (IOException e) {
            setMessage("Could not connect.");
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                arrived = true;
                ((Circle) o).delete();
            }
        }
    }
}
