// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValidatingTextField.java

package e3ps.bom.framework.util;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ValidatingTextField extends JTextField
{
    private class WholeNumberDocument extends PlainDocument
    {

        public void insertString(int i, String s, AttributeSet attributeset)
            throws BadLocationException
        {
            if(length != -1 && getLength() + s.length() > length)
                return;
            if(isNumerical)
                try
                {
                    int j = Integer.parseInt(getText(0, i) + s + getText(i, getLength() - i));
                    if(checkmax && j > max)
                        return;
                }
                catch(NumberFormatException _ex)
                {
                    return;
                }
            super.insertString(i, s, attributeset);
        }

        WholeNumberDocument()
        {
        }
    }


    public ValidatingTextField(int i, int j, boolean flag)
    {
        super(i);
        checkmax = false;
        init(j, flag);
    }

    public ValidatingTextField(int i, boolean flag)
    {
        checkmax = false;
        init(i, flag);
    }

    public ValidatingTextField(String s, int i, int j, boolean flag)
    {
        super(s, i);
        checkmax = false;
        init(j, flag);
    }

    public ValidatingTextField(String s, int i, boolean flag)
    {
        super(s);
        checkmax = false;
        init(i, flag);
    }

    protected Document createDefaultModel()
    {
        return new WholeNumberDocument();
    }

    public JLabel createLabel(String s)
    {
        label = new JLabel(s);
        return label;
    }

    public JLabel getLabel()
    {
        return label;
    }

    public int getNumber()
    {
        try
        {
            return Integer.parseInt(getText());
        }
        catch(NumberFormatException _ex)
        {
            return 0;
        }
    }

    private void init(int i, boolean flag)
    {
        length = i;
        isNumerical = flag;
    }

    public void setEnabled(boolean flag)
    {
        super.setEnabled(flag);
        if(label != null)
            label.setEnabled(flag);
    }

    public void setLabel(JLabel jlabel)
    {
        label = jlabel;
    }

    public void setMaxNum(int i)
    {
        max = i;
        checkmax = true;
    }

    public static final int INFINITE = -1;
    private JLabel label;
    private int length;
    private int max;
    private boolean isNumerical;
    private boolean checkmax;




}
