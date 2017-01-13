package model.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import main.Lane;
import main.LaneThread;
import model.Circle;

import java.util.Observable;
import java.util.Observer;

/**
 * Class representing the connection from producer to consumer. Is threaded.
 */
public class Producer extends LaneThread implements Observer {
    private String host;
    private int port;
    private String text;
    private boolean circleDone = false;
    private boolean circleArrived = false;

    public Producer(String host, int port, String text) {
        super(Lane.Second);
        this.host = host;
        this.port = port;
        this.text = text;
    }

    /**
     * This method will be run when the thread is started and contains the main functionality.
     */
    @Override
    public void doRun() {
        setMessage("Connecting...");
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare("reversequeue", false, false, true, null);

            setMessage("Sending 5 inputs...");
            Circle outgoing = new Circle(true, true, lane, text);
            outgoing.standStill(120);
            outgoing.addObserver(this);
            while (!circleDone) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //busywaiting
                }
            }
            circleArrived = false;
            circleDone = false;

            for (int i = 1; i <= 5; i++) {
                channel.basicPublish("", "reversequeue", null, (i + ": " + text).getBytes());
            }
            setMessage("Connection closed.");
        } catch (Exception e) {
            setMessage("Error\nProducer closed.");
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
