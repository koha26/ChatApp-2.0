package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPanel extends JPanel {
    private Point currCoords;

    public MyPanel() {
        this.setSize(150, 150);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setBorder(new LineBorder(Color.WHITE, 7));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currCoords = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                setLocation(getX() + e.getX() - (int) currCoords.getX(), getY() + e.getY() - (int) currCoords.getY());

            }
        });
    }
}