// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CheckSameNode.java

package e3ps.bom.common.util;

import java.util.Enumeration;
import java.util.Vector;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;

public class CheckSameNode
{

    public CheckSameNode(Enumeration data, BOMAssyComponent component)
    {
        this.data = data;
        this.component = component;
        resultList = new Vector();
        check();
    }

    private void check()
    {
        String partNumber = Utility.checkNVL(component.getItemCodeStr());
        String parentPN = Utility.checkNVL(component.getParentItemCodeStr());
		String sortOrder = Utility.checkNVL(component.getSortOrderStr());
        while(data.hasMoreElements()) 
        {
            BOMTreeNode node = (BOMTreeNode)data.nextElement();
            BOMAssyComponent dataCmp = node.getBOMComponent();
            String partNumberVal = Utility.checkNVL(dataCmp.getItemCodeStr());
            String parentPNVal = Utility.checkNVL(dataCmp.getParentItemCodeStr());
			String sortOrderVal = Utility.checkNVL(dataCmp.getSortOrderStr());

            if(partNumber.equalsIgnoreCase(partNumberVal.trim()) && parentPN.equalsIgnoreCase(parentPNVal.trim()))
			{
                // 미도래 시방인경우에는 제외
                if ( sortOrder.length() != sortOrderVal.length() ||
                     !sortOrder.substring(0,sortOrder.length()-4).equalsIgnoreCase(sortOrderVal.substring(0,sortOrderVal.length()-4)) )
                    resultList.addElement(node);
                else if ( sortOrder.equalsIgnoreCase(sortOrderVal))
                    resultList.addElement(node);    // 자기 자신은 포함.
			}
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
