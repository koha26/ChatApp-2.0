package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ChatAppScrollBarUI extends BasicScrollBarUI{
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
        /*g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, thumbBounds.width, thumbBounds.height);*/

        g.translate(thumbBounds.x,thumbBounds.y);
        g.setColor(Color.WHITE);
        g.fillRect(2,2,thumbBounds.width-4,thumbBounds.height-4);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        //g.fillRect(0,0,trackBounds.width,trackBounds.height);
        //g.setColor(new Color(Color.BLACK.getTransparency()));
        //g.setColor(new Color(21, 44, 21, 179));

        g.translate(trackBounds.x, trackBounds.y);
        //g.setColor(new Color(35, 45, 25, 255));
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0,0,trackBounds.width,trackBounds.height);//квадрат

    }

}
