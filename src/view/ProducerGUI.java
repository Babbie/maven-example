package view;

import main.Lane;
import main.LaneThread;
import main.ThreadListener;
import model.consumer.Consumer;
import model.producer.Producer;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Utility.isValidPort;

/**
 * Class linked to the ProducerGUI form.
 * The class creates the JFrame, handles the input and starts the consumer thread
 */
public class ProducerGUI implements ThreadListener {
    private static JFrame frame;
    private JPanel panel1;
    private JTextField textField;
    private JPanel TextPanel;
    private JButton StartProducer;
    private JPanel ProducerPanel;

    private ProducerGUI(){
        ProducerGUI thisProducer = this;
        StartProducer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisProducer.start(Lane.Second);
            }
        });
    }

    public static void init() {
        frame = new JFrame("ProducerGUI");
        frame.setContentPane(new ProducerGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void start(Lane lane) {
        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!isValidPort(port)) {
            JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
            port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        }
        Producer producerThread = new Producer(Integer.parseInt(port), lane);
        producerThread.start();
        producerThread.addListener(this);
    }

    @Override
    public void threadUpdate(LaneThread laneThread) {
        textField.setText(laneThread.getMessage());
        StartProducer.setEnabled(laneThread.isDone());
    }
}