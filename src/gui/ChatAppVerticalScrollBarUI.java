package gui;

import javax.swing.*;
import java.awt.*;

public class ChatAppVerticalScrollBarUI extends ChatAppScrollBarUI{
    private final int WIDTH_SCROLLBAR = 8;

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(WIDTH_SCROLLBAR, dim.height);
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(WIDTH_SCROLLBAR, dim.height);
    }
}
