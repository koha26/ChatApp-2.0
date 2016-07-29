package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPanel extends JPanel {
    private Point currCoords;
    private Dimension sizeOfPicture;
    private int sizeOfSquare;

    public void setSizeOfSquare(int sizeOfSquare) {
        this.sizeOfSquare = sizeOfSquare;
    }

    public int getSizeOfSquare() {
        return sizeOfSquare;
    }

    public MyPanel(Dimension d) {
        this.setSize(250, 250);
        sizeOfSquare = 250;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setBorder(new LineBorder(Color.WHITE, 7));
        sizeOfPicture = d;


        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currCoords = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (((getY() + e.getY() - (int) currCoords.getY()) + sizeOfSquare <= sizeOfPicture.getHeight())
                        & (((getY() + e.getY() - (int) currCoords.getY()) >= 0))
                        & ((getX() + e.getX() - (int) currCoords.getX()) + sizeOfSquare <= sizeOfPicture.getWidth()) &
                        ((getX() + e.getX() - (int) currCoords.getX()) >= 0)) {
                    setLocation(getX() + e.getX() - (int) currCoords.getX(), getY() + e.getY() - (int) currCoords.getY());
                } else if (((getY() + e.getY() - (int) currCoords.getY()) + sizeOfSquare >= (sizeOfPicture.getHeight() - 0))) {
                    if (((getX() + e.getX() - (int) currCoords.getX()) + sizeOfSquare <= sizeOfPicture.getWidth()) &
                            ((getX() + e.getX() - (int) currCoords.getX()) >= 0)) {
                        setLocation(getX() + e.getX() - (int) currCoords.getX(), (int) sizeOfPicture.getHeight() - sizeOfSquare);
                    }
                } else if (((getY() + e.getY() - (int) currCoords.getY()) <= 0)) {
                    if (((getX() + e.getX() - (int) currCoords.getX()) + sizeOfSquare <= sizeOfPicture.getWidth()) &
                            ((getX() + e.getX() - (int) currCoords.getX()) >= 0)) {
                        setLocation(getX() + e.getX() - (int) currCoords.getX(), 0);
                    }
                } else if (((getX() + e.getX() - (int) currCoords.getX()) + sizeOfSquare >= (sizeOfPicture.getWidth() - 0))) {
                    setLocation((int) sizeOfPicture.getWidth() - sizeOfSquare, getY() + e.getY() - (int) currCoords.getY());
                } else if (((getX() + e.getX() - (int) currCoords.getX()) <= 0)) {
                    setLocation(0, getY() + e.getY() - (int) currCoords.getY());
                }
            }
        });
    }
}