// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GriddedPanel.java

package e3ps.bom.common.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GriddedPanel extends JPanel
{

    public GriddedPanel()
    {
        this(new Insets(2, 2, 2, 2));
    }

    public GriddedPanel(Insets insets)
    {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.anchor = 17;
        constraints.insets = insets;
    }

    public void addComponent(JComponent component, int row, int col)
    {
        addComponent(component, row, col, 1, 1, 17, 0);
    }

    public void addComponent(JComponent component, int row, int col, int width, int height)
    {
        addComponent(component, row, col, width, height, 17, 0);
    }

    public void addAnchoredComponent(JComponent component, int row, int col, int anchor)
    {
        addComponent(component, row, col, 1, 1, anchor, 0);
    }

    public void addAnchoredComponent(JComponent component, int row, int col, int width, int height, int anchor)
    {
        addComponent(component, row, col, width, height, anchor, 0);
    }

    public void addFilledComponent(JComponent component, int row, int col)
    {
        addComponent(component, row, col, 1, 1, 17, 2);
    }

    public void addFilledComponent(JComponent component, int row, int col, int fill)
    {
        addComponent(component, row, col, 1, 1, 17, fill);
    }

    public void addFilledComponent(JComponent component, int row, int col, int width, int height, int fill)
    {
        addComponent(component, row, col, width, height, 17, fill);
    }

    public void addComponent(JComponent component, int row, int col, int width, int height, int anchor, int fill)
    {
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.anchor = anchor;
        double weightx = 0.0D;
        double weighty = 0.0D;
        if(width > 1)
            weightx = 1.0D;
        if(height > 1)
            weighty = 1.0D;
        switch(fill)
        {
        case 2: // '\002'
            constraints.weightx = weightx;
            constraints.weighty = 0.0D;
            break;

        case 3: // '\003'
            constraints.weighty = weighty;
            constraints.weightx = 0.0D;
            break;

        case 1: // '\001'
            constraints.weightx = weightx;
            constraints.weighty = weighty;
            break;

        case 0: // '\0'
            constraints.weightx = 0.0D;
            constraints.weighty = 0.0D;
            break;
        }
        constraints.fill = fill;
        add(component, constraints);
    }

    private GridBagConstraints constraints;
    private static final int C_HORZ = 2;
    private static final int C_NONE = 0;
    private static final int C_WEST = 17;
    private static final int C_WIDTH = 1;
    private static final int C_HEIGHT = 1;
}
