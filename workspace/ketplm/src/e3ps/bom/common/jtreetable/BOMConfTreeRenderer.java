// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package e3ps.bom.common.jtreetable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class BOMConfTreeRenderer extends DefaultTreeCellRenderer
{
	private BOMTreeNode treeNode = null;
	private BOMAssyComponent bomcomponent = null;

    public BOMConfTreeRenderer(AbstractAIFUIApplication app)
    {

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

        setBackgroundNonSelectionColor(Color.white);
        setBackgroundSelectionColor(ColorList.veryDarkBlueColor);
        setTextSelectionColor(Color.white);
        setIcon( null );

        if( !selected )
        {
            setForeground( Color.black );
        }

//        setText(bomcomponent.toString());

        return this;
    }
}
