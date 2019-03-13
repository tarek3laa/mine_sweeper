package com.company;

import javax.swing.*;

public class Main {
    public Main() {
        JFrame frame = new JFrame(); // create new instance from Jframe to draw frame
        frame.add(new GamePanel(650, 650));  /** add panel to frame ,{@link GamePanel} */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close frame when EXIT button clicked
        frame.setTitle("Mine Sweeper");
        frame.setResizable(false); // make frame not resizable
        frame.pack();
        frame.setLocationRelativeTo(null); // set frame location
        frame.setVisible(true); // make frame  visible

    }

    public static void main(String[] args) {
        // write your code here
        new Main();
    }
}
