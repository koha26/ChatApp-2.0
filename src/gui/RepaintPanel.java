package gui;

import javax.swing.*;

public class RepaintPanel implements Runnable {
    JPanel panel;

    public RepaintPanel(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (true) {
            panel.repaint();
            panel.revalidate();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

