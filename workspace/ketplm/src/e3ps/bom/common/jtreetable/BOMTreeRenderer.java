// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package e3ps.bom.common.jtreetable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;

public class BOMTreeRenderer extends DefaultTreeCellRenderer
{
	private static final long serialVersionUID = 1L;
	private Registry appReg = null;
	private Icon modelIcon = null;
	private BOMTreeNode treeNode = null;
	private BOMAssyComponent bomcomponent = null;

	public BOMTreeRenderer(AbstractAIFUIApplication app)
    {
        appReg = Registry.getRegistry(app);
        modelIcon = appReg.getImageIcon("modelIcon");
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        treeNode = (BOMTreeNode)value;
        if( treeNode == null )
        {
        	return this;
        }

        bomcomponent = treeNode.getBOMComponent();
        if( bomcomponent == null )
        {
        	return this;
        }

        // 속도 문제로 렌더링에서 제외시킴
//        setToolTipText(bomcomponent.toString());

        // 배경색
        if( (row + 1) % 2 == 1 && !selected )
        {
        	setBackgroundNonSelectionColor( ColorList.veryLightGray  );
        }
        else
        {
        	setBackgroundNonSelectionColor( Color.white  );
        }
        
        setBackgroundSelectionColor( ColorList.veryDarkBlueColor  );

        setTextSelectionColor( Color.white  );

        if( bomcomponent.getComponentTypeStr() == "2" )
        {
            setIcon( modelIcon );		// 최상위 이미지
            setForeground(Color.blue);
        }
        else
        {
        	setIcon( null );
        	setForeground(Color.black);
        }
        
//		if( !BOMECOBasicInfoPool.getECONo().equals("") && bomcomponent.getLevelInt() > 1 )
//		{
//			setForeground( Color.lightGray );
//		}

        if( selected )
        {
            setForeground(Color.white);
        }

		if( bomcomponent.getNewFlagStr().equalsIgnoreCase("NEW") )
		{
			setBackgroundNonSelectionColor( Color.pink  );
		}

		if( bomcomponent.getIsDeleted().equalsIgnoreCase("Y") )
		{
			setForeground( Color.red );
		}
		
//        if(!bomcomponent.getCheckOutStr().equals(""))
//        {
//            setIcon(appReg.getImageIcon("checkoutIcon"));
//            if(!selected)
//                setForeground(Color.black);
//        }

//        setText(bomcomponent.toString());

        return this;
    }
}
