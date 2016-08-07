package gui;

import javax.swing.*;
import java.awt.*;

public class OutlineButton extends JButton {

    private Color outlineColor = Color.RED;
    private boolean isPaintingOutline = false;
    private boolean forceTransparent = false;

    public OutlineButton() {
        super();
    }

    public OutlineButton(String text) {
        super(text);
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        this.invalidate();
    }

    @Override
    public Color getForeground() {
        if ( isPaintingOutline ) {
            return outlineColor;
        } else {
            return super.getForeground();
        }
    }

    @Override
    public boolean isOpaque() {
        if ( forceTransparent ) {
            return false;
        } else {
            return super.isOpaque();
        }
    }

    @Override
    public void paint(Graphics g) {

        String text = getText();
        if ( text == null || text.length() == 0 ) {
            super.paint(g);
            return;
        }

        // 1 2 3
        // 8 9 4
        // 7 6 5

        if ( isOpaque() )
            super.paint(g);

        forceTransparent = true;
        isPaintingOutline = true;
        g.translate(-2, -2); super.paint(g); // 1
        g.translate( 2,  0); super.paint(g); // 2
        g.translate( 2,  0); super.paint(g); // 3
        g.translate( 0,  2); super.paint(g); // 4
        g.translate( 0,  2); super.paint(g); // 5
        g.translate(-2,  0); super.paint(g); // 6
        g.translate(-2,  0); super.paint(g); // 7
        g.translate( 0, -2); super.paint(g); // 8
        g.translate( 2,  0); // 9
        isPaintingOutline = false;

        super.paint(g);
        forceTransparent = false;

    }

    public static void main(String[] args) {
        JFrame w = new JFrame();
        w.setBackground(Color.BLACK);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        OutlineButton label = new OutlineButton("BOB_MORCOS");
        label.setFont(Fonts.nickFont);
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        w.setContentPane(new JPanel(new BorderLayout()));
        w.add(label, BorderLayout.CENTER);
        w.pack();
        w.setVisible(true);
    }
}
