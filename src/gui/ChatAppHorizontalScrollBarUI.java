package gui;

import javax.swing.*;
import java.awt.*;

public class ChatAppHorizontalScrollBarUI extends ChatAppScrollBarUI{
    private final int HEIGHT_SCROLLBAR = 8;

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.width, HEIGHT_SCROLLBAR);
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.width, HEIGHT_SCROLLBAR);
    }
}
