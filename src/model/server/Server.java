package model.server;

import main.LaneThread;
import model.Circle;
import main.Lane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Server extends LaneThread implements Observer {
    private boolean arrived = false;
    private int port;

    public Server(int port, Lane lane) {
        super(lane);
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
    public void doRun() {
        try {
            ServerSocket server = new ServerSocket(port);
            setMessage("Awaiting connection...");

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket client = server.accept();
                doCommunication(client);
            }
        } catch (IOException e) {
            setMessage("Error.\nSocket closed.");
        }
    }

    private void doCommunication(Socket client) {
        try {
            PrintStream output = new PrintStream(client.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            setMessage("Awaiting input...");
            String text = input.readLine();
            Circle incoming = new Circle(false, false, lane, text);
            incoming.addObserver(this);
            while (!arrived) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //busywaiting
                }
            }
            arrived = false;
            setMessage("Sending output...");
            String reverseText = new StringBuilder(text).reverse().toString();
            Circle outgoing = new Circle(true, false, lane, reverseText);
            outgoing.addObserver(this);
            while (!arrived) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //busywaiting
                }
            }
            arrived = false;
            output.println(reverseText);
            output.flush();
            setMessage("Awaiting connection...");
        } catch (IOException e) {
            setMessage("Awaiting connection...");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                //Nothing, client is closed
            }
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
