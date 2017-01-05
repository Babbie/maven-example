package view;

import main.LaneThread;
import main.ThreadListener;
import model.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static main.Utility.isValidIP;
import static main.Utility.isValidPort;

/**
 * Class linked to the ClientGUI form.
 * The class creates the JFrame, handles the input dialogs and starts the client.
 */
public class ClientGUI implements ThreadListener {
    private static JFrame frame;
    private JPanel panel1;
    private JTextField ClientLane;
    private JPanel CirclePanel;
    private JPanel TextPanel;
    private JButton StartClient;

    private ClientGUI(){
        ClientGUI thisClient = this;
        StartClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisClient.start();
                StartClient.setEnabled(false);
            }
        });
    }

    //Method called on initialization of the frame
    public static void init() {
        frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    //Method called on pressing the start button. It takes care of all of the input dialogs
    private void start() {
        String ip = JOptionPane.showInputDialog(frame, "Enter the IP to connect to.", "", JOptionPane.QUESTION_MESSAGE);
        while (!isValidIP(ip)) {
            JOptionPane.showMessageDialog(frame, "The IP \"" + ip + "\" is not a valid IP.");
            ip = JOptionPane.showInputDialog(frame, "Enter the IP to connect to.", "", JOptionPane.QUESTION_MESSAGE);
        }
        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!isValidPort(port)) {
            JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
            port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        }
        String text = JOptionPane.showInputDialog(frame, "Enter the text to submit.", "", JOptionPane.QUESTION_MESSAGE);

        //Start the client thread
        Client clientThread = new Client(ip, Integer.parseInt(port), text);
        clientThread.start();
        clientThread.addListener(this);
    }

    @Override
    public void threadUpdate(LaneThread laneThread) {
        ClientLane.setText(laneThread.getMessage());
        StartClient.setEnabled(laneThread.isDone());
    }
}
