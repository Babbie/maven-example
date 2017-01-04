package model.server;

import model.Circle;
import model.Lane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ServerThread implements Runnable, Observer {

    private Socket client;
    private Lane lane;
    private boolean arrived = false;

    public ServerThread(Socket client, Lane lane) {
        this.client = client;
        this.lane = lane;
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
            System.out.println("thread running!");
            PrintStream output = new PrintStream(client.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //TODO: Status: waiting
            System.out.println("awaiting input");
            String text = input.readLine();
            System.out.println(text);
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
            //TODO: Status: waiting
        } catch (IOException e) {
            //TODO: Status: disconnected
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                //Nothing, client is closed
            }
            Server.removeThread();
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
