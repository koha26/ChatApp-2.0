package gui;

import javax.swing.*;

public class RepaintFrame implements Runnable {
    JFrame frame;

    public RepaintFrame(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            frame.repaint();
            frame.revalidate();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

