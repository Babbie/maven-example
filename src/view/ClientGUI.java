package view;

import model.client.Client;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sebastian on 2-1-2017.
 */
public class ClientGUI {
    private JPanel panel1;
    private JTextField ClientLane;
    private JPanel CirclePanel;
    private JPanel TextPanel;

    public static void init() {
        JFrame frame = new JFrame("ClientGUI");
        frame.setContentPane(new ClientGUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        String ip = JOptionPane.showInputDialog(frame, "Enter the IP to connect to.", "", JOptionPane.QUESTION_MESSAGE);
        while (!validIP(ip)) {
            JOptionPane.showMessageDialog(frame, "The IP \"" + ip + "\" is not a valid IP.");
            ip = JOptionPane.showInputDialog(frame, "Enter the IP to connect to.", "", JOptionPane.QUESTION_MESSAGE);
        }
        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!validPort(port)) {
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

    public static boolean validIP(String ip) {
        if (ip.equals("localhost")) {
            return true;
        }

        if (ip.isEmpty()) {
            return false;
        }

        if (ip.endsWith(".")) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        try {
            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean validPort(String port) {
        try {
            int i = Integer.parseInt(port);
            if ((i < 1024) || (i > 65535)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
