package main;

import view.ConsumerGUI;
import view.ProducerGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            JFrame frame = new JFrame("");
            JOptionPane.showMessageDialog(frame, "Start the program with command line argument server or client.");
            System.exit(1);
        } else if (args[0].equals("client")) {
            ConsumerGUI.init();
        } else if (args[0].equals("server")) {
            ProducerGUI.init();
        }
    }
}