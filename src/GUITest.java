import model.client.Client;
import model.server.Server;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Sebastian on 31-12-2016.
 */
public class GUITest {
    private JPanel panel1;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel TextPanel;
    private JPanel CirclePanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUITest");
        frame.setContentPane(new GUITest().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        if (args.length != 1) {
            JOptionPane.showMessageDialog(frame, "Start the program with command line argument server or client.");
            System.exit(1);
        } else if (args[0].equals("client")) {
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
        } else if (args[0].equals("server")) {
            String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
            while (!validPort(port)) {
                JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
                port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
            }
            new Thread(new Server(Integer.parseInt(port))).start();
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