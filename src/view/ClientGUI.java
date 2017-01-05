package view;

import model.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static main.Utility.isValidIP;
import static main.Utility.isValidPort;

/**
 * Created by Sebastian on 2-1-2017.
 */
public class ClientGUI {
    private static JFrame frame;
    private JPanel panel1;
    private JTextField ClientLane;
    private JPanel CirclePanel;
    private JPanel TextPanel;
    private JButton StartClient;

    private ClientGUI() {
        StartClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientGUI.start();
                StartClient.setVisible(false);
            }
        });
    }

    public static void init() {
        frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void start() {
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
        try {
            new Thread(new Client(ip, Integer.parseInt(port), text)).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to start client, shutting down...");
        }
    }


}
