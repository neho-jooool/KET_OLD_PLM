// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckSameNodeOnlyPN.java

package e3ps.bom.common.util;

import java.util.Enumeration;
import java.util.Vector;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;

public class CheckSameNodeOnlyPN
{

    public CheckSameNodeOnlyPN(Enumeration data, BOMAssyComponent component)
    {
        this.data = data;
        this.component = component;
        resultList = new Vector();
        check();
    }

    private void check()
    {
        String partNumber = component.getItemCodeStr().trim();
        while(data.hasMoreElements()) 
        {
            BOMTreeNode node = (BOMTreeNode)data.nextElement();
            BOMAssyComponent dataCmp = node.getBOMComponent();
            String partNumberVal = dataCmp.getItemCodeStr();
            if(partNumber.equalsIgnoreCase(partNumberVal.trim()))
                resultList.addElement(node);
        }
    }

    public Vector getResultList()
    {
        return resultList;
    }

    private Enumeration data;
    private BOMAssyComponent component;
    private Vector resultList;
}
