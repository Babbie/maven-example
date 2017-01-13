package main;

import view.ConsumerGUI;
import view.ProducerGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            JFrame frame = new JFrame("");
            JOptionPane.showMessageDialog(frame, "Start the program with command line argument consumer or producer.");
            System.exit(1);
        } else if (args[0].equals("consumer")) {
            ConsumerGUI.init();
        } else if (args[0].equals("producer")) {
            ProducerGUI.init();
        }
    }
}