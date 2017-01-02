package view;

import model.server.Server;

import javax.swing.*;

/**
 * Created by Sebastian on 31-12-2016.
 */
public class GUI {
    private JPanel panel1;
    private JTextField Lane1;
    private JTextField Lane2;
    private JTextField Lane3;
    private JPanel TextPanel;
    private JPanel CirclePanel;

    public static void init() {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        String port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        while (!validPort(port)) {
            JOptionPane.showMessageDialog(frame, "The port \"" + port + "\" is not a valid port.");
            port = JOptionPane.showInputDialog(frame, "Enter the port to connect to (1025-65535).", "", JOptionPane.QUESTION_MESSAGE);
        }
        new Thread(new Server(Integer.parseInt(port))).start();
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