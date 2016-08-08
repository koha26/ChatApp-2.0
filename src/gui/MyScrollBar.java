package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MyScrollBar extends BasicScrollBarUI {
    private JButton b = new JButton() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(0, 0);
        }
    };

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return b;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return b;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, thumbBounds.width, thumbBounds.height);
    }


    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.translate(trackBounds.x, trackBounds.y);
        g.setColor(new Color(255, 255, 255, 0));
        g.drawRect(0, 0, trackBounds.width, trackBounds.height);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(8, dim.height);
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(8, dim.height);
    }
}
