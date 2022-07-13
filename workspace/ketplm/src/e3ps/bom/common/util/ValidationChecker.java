// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValidationChecker.java

package e3ps.bom.common.util;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ValidationChecker
{

    public ValidationChecker()
    {
    }

    public boolean isEmpty(JTextField txtTarget)
    {
        return txtTarget.getText().length() == 0;
    }

    public boolean isEmpty(JTextArea txtaTarget)
    {
        return txtaTarget.getText().length() == 0;
    }
}
