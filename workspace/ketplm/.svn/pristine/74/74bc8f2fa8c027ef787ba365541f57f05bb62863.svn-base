// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MLabel.java

package e3ps.bom.framework.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

import javax.swing.JComponent;

public class MLabel extends JComponent
{

    public MLabel()
    {
        textAlignment = 0;
        text = new String();
        numLines = 0;
        lines = null;
        marginWidth = 5;
        marginHeight = 5;
        textAlignment = 0;
    }

    public MLabel(String s)
    {
        this(s, 10, 10, 1);
    }

    public MLabel(String s, int i)
    {
        this(s, 10, 10, i);
    }

    public MLabel(String s, int i, int j)
    {
        this(s, i, j, 1);
    }

    public MLabel(String s, int i, int j, int k)
    {
        textAlignment = 0;
        text = new String(s);
        parseString(text);
        textAlignment = k;
    }

    public void addNotify()
    {
        super.addNotify();
        measure();
    }

    public int getMarginHeight()
    {
        return marginHeight;
    }

    public int getMarginWidth()
    {
        return marginWidth;
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public Dimension getPreferredSize()
    {
        Dimension dimension = new Dimension(maxWidth + 2 * marginWidth, numLines * lineHeight + 2 * marginHeight);
        return dimension;
    }

    public String getText()
    {
        return text;
    }

    public int getTextAlignment()
    {
        return textAlignment;
    }

    private void measure()
    {
        FontMetrics fontmetrics = getFontMetrics(getFont());
        if(fontmetrics == null)
            return;
        lineHeight = fontmetrics.getHeight();
        lineAscent = fontmetrics.getAscent();
        maxWidth = 0;
        for(int i = 0; i < numLines; i++)
        {
            lineWidths[i] = fontmetrics.stringWidth(lines[i]);
            if(lineWidths[i] > maxWidth)
                maxWidth = lineWidths[i];
        }

    }

    public void paint(Graphics g)
    {
        Dimension dimension = getSize();
        g.setColor(getForeground());
        g.setFont(getFont());
        int j = lineAscent + (dimension.height - numLines * lineHeight) / 2;
        for(int k = 0; k < numLines; k++)
        {
            int i;
            switch(textAlignment)
            {
            case 0: // '\0'
                i = marginWidth;
                break;

            case 2: // '\002'
                i = dimension.width - marginWidth - lineWidths[k];
                break;

            case 1: // '\001'
            default:
                i = (dimension.width - lineWidths[k]) / 2;
                break;
            }
            g.drawString(lines[k], i, j);
            j += lineHeight;
        }

    }

    private void parseString(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\n\r");
        numLines = stringtokenizer.countTokens();
        lines = new String[numLines];
        lineWidths = new int[numLines];
        for(int i = 0; i < numLines; i++)
            lines[i] = stringtokenizer.nextToken();

    }

    public void setFont(Font font)
    {
        super.setFont(font);
        measure();
        repaint();
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
        repaint();
    }

    public void setMarginHeight(int i)
    {
        if(i > 0)
            marginHeight = i;
        else
            marginHeight = 0;
        repaint();
    }

    public void setMarginWidth(int i)
    {
        if(i > 0)
            marginWidth = i;
        else
            marginWidth = 0;
        repaint();
    }

    public void setText(String s)
    {
        char c = '\n';
        StringBuffer stringbuffer = new StringBuffer(s);
        for(int i = 0; i < stringbuffer.length(); i++)
            if(stringbuffer.charAt(i) == '\\')
            {
                if(i + 1 < stringbuffer.length() && stringbuffer.charAt(i + 1) == 'n')
                {
                    stringbuffer = stringbuffer.deleteCharAt(i + 1);
                    stringbuffer.setCharAt(i, c);
                }
            } else
            if(stringbuffer.charAt(i) == '\r')
                stringbuffer.setCharAt(i, c);

        text = new String(s);
        parseString(new String(stringbuffer));
        measure();
        repaint();
    }

    public void setTextAlignment(int i)
    {
        textAlignment = i;
        repaint();
    }

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    private String text;
    private String lines[];
    private int numLines;
    private int marginWidth;
    private int marginHeight;
    private int lineHeight;
    private int lineAscent;
    private int lineWidths[];
    private int maxWidth;
    private int textAlignment;
}
