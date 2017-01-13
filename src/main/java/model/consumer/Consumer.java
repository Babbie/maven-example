package model.consumer;

import com.rabbitmq.client.*;
import main.LaneThread;
import model.Circle;
import main.Lane;

import java.io.IOException;
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

    public Consumer(String host, int port) {
        super(Lane.Second);
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
            factory.setHost(host);
            factory.setPort(port);
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            channel.queueDeclare("reversequeue", false, false, true, null);
            // Accept only one at a time
            channel.basicQos(1);
            setMessage("Awaiting input...");

            com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    reverseString(new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume("reversequeue", consumer);

            //noinspection InfiniteLoopStatement
            while (true) {
                Thread.sleep(1);
            }
        } catch (Exception e) {
            setMessage("Error.\nConsumer closed.");
        }
    }

    /**
     * Handles the computation of the reverse string.
     * @param input the input to reverse.
     */
    private void reverseString(String input) {
        String text = input.substring(3);
        Circle incoming = new Circle(false, false, lane, input);
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
        String reverseText = new StringBuilder(text).reverse().toString();
        setMessage("Output: " + reverseText);
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
