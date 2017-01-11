package model.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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


/**
 * Class representing the connection from consumer to producer. Is threaded.
 */
public class Consumer extends LaneThread implements Observer {
    private boolean circleDone = false;
    private boolean circleArrived = false;
    private String host;
    private int port;

    public Consumer(String host, int port, String queueName, Lane lane) {
        super(lane);
        this.host = host;
        this.port = port;
    }

    /**
     * This method will be run when the thread is started and contains the main functionality.
     */
    @Override
    public void doRun() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            setMessage("Awaiting input...");

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket client = server.accept();
                doCommunication(client);
            }
        } catch (Exception e) {
            setMessage("Error.\nSocket closed.");
        }
    }

    /**
     * Handles the main communication with the producer.
     * @param client the producer.
     */
    private void doCommunication(Socket client) {
        try {
            PrintStream output = new PrintStream(client.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            setMessage("Awaiting input...");
            String text = input.readLine();
            Circle incoming = new Circle(false, false, lane, text);
            incoming.addObserver(this);
            incoming.standStill(120);
            while (!circleDone) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //busywaiting
                }
            }
            circleArrived = false;
            circleDone = false;
            setMessage("Sending output...");
            String reverseText = new StringBuilder(text).reverse().toString();
            Circle outgoing = new Circle(true, false, lane, reverseText);
            outgoing.addObserver(this);
            outgoing.standStill(120);
            while (!circleDone) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //busywaiting
                }
            }
            circleArrived = false;
            circleDone = false;
            output.println(reverseText);
            output.flush();
            setMessage("Awaiting connection...");
        } catch (IOException e) {
            setMessage("Awaiting connection...");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                //Nothing, producer is closed
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
        Circle circle = (Circle) o;
        if (circleArrived && !circle.isStandingStill()) {
            circle.delete();
            circleDone = true;
        } else if (circle.hasArrived() && !circle.isStandingStill()) {
            circle.standStill(120);
            circleArrived = true;
        }
    }
}
