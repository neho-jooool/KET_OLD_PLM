// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BOMTreeLoader.java

package e3ps.bom.common.jtreetable;

import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

// Referenced classes of package e3ps.bom.common.jtreetable:
//            BOMTreeNode, BOMTreeTableModel, JTreeTable

public class BOMTreeLoader implements Runnable {
    int preLevelNum;
    int nowLevelNum;
    int nextLevelNum;
    BOMTreeNode rootNode;
    BOMTreeNode parentNode;
    BOMTreeNode currentNode;
    BOMTreeNode nextNode;
    public Vector bomDataVec;
    public JTreeTable treeTable;
    public BOMTreeTableModel model;
    public BOMAssyComponent assyComponent;

    public BOMTreeLoader(BOMTreeNode node, Vector bomDataVec, JTreeTable treeTable, BOMTreeTableModel model) {
	preLevelNum = 0;
	nowLevelNum = 0;
	nextLevelNum = 0;
	parentNode = null;
	currentNode = null;
	nextNode = null;

	this.bomDataVec = null;
	this.treeTable = null;
	this.model = null;
	rootNode = node;
	this.bomDataVec = bomDataVec;
	this.treeTable = treeTable;
	this.model = model;
    }

    public void run() {
	Kogger.debug(getClass(), "@@@@ BOMTreeLoader  rootNode : " + rootNode);

	parentNode = rootNode;
	Vector expandNodeVec = new Vector();
	// Added by MJOH, 2011-04-07
	String partTypeStr = rootNode.getBOMComponent().getIBAPartType();

	for (int i = 0; i < bomDataVec.size(); i++) {
	    BOMAssyComponent bomComponentData = (BOMAssyComponent) bomDataVec.elementAt(i);
	    nowLevelNum = bomComponentData.getLevelInt().intValue();
	    currentNode = new BOMTreeNode(parentNode, bomComponentData);

	    // Added by MJOH, 2011-04-07
	    bomComponentData.setIBAPartType(partTypeStr);

	    if (parentNode != null) {
		parentNode.addElement(currentNode);

		// 2011-03-31 [占쏙옙占쏙옙占쏙옙 J 占썰구占쏙옙占쏙옙 占쌥울옙] 占쏙옙체 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙
		// if(bomComponentData.getLevelInt().intValue() == 1)
		expandNodeVec.addElement(new TreePath(currentNode.getPath()));

		if (i + 1 != bomDataVec.size()) {
		    BOMAssyComponent nextBomComponentData = (BOMAssyComponent) bomDataVec.elementAt(i + 1);
		    nextLevelNum = nextBomComponentData.getLevelInt().intValue();

		    if (nextLevelNum == nowLevelNum) {
			bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
		    } else if (nextLevelNum > nowLevelNum) {
			parentNode = currentNode;
			bomComponentData.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
		    } else {
			bomComponentData.setComponentTypeStr(BOMAssyComponent.PART_TYPE);
			for (int j = nextLevelNum; j < nowLevelNum; j++)
			    parentNode = (BOMTreeNode) parentNode.getParent();
		    }
		}
	    }
	}

	Enumeration seqEnum = model.getRootNode().preorderEnumeration();
	int realSeqNumber = 1;
	while (seqEnum.hasMoreElements()) {
	    BOMAssyComponent component = ((BOMTreeNode) seqEnum.nextElement()).getBOMComponent();
	    if (component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE
		    || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE) {
		assyComponent = component;
		assyComponent.setSeqInt(new Integer(realSeqNumber++));
	    }
	}

	for (int j = 0; j < expandNodeVec.size(); j++)
	    treeTable.getTree().scrollPathToVisible((TreePath) expandNodeVec.elementAt(j));

	// 占쏙옙占쏙옙 占쌓몌옙占쏙옙.
	int nodeCount = 0;
	boolean isRoot = false;
	seqEnum = model.getRootNode().preorderEnumeration();
	while (seqEnum.hasMoreElements()) {
	    BOMTreeNode treeNode = (BOMTreeNode) seqEnum.nextElement();
	    BOMAssyComponent component = treeNode.getBOMComponent();

	    if (component.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE) {
		isRoot = true;
	    }

	    nodeCount++;
	}

	if (isRoot && nodeCount == 1) {
	    model.fireTreeChanged(rootNode);
	}
	treeTable.repaint();

	TreePath treepath = new TreePath(parentNode.getPath());

	try {
	    Kogger.debug(getClass(), "##########getPreferredWidth = "
		    + treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/* 부품명 */)
		            .getPreferredWidth());
	    treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/* 부품명 */)
		    .setPreferredWidth(200);
	    Kogger.debug(getClass(), "##########getPreferredWidth = "
		    + treeTable.getColumn(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/* 부품명 */)
		            .getPreferredWidth());

	    treeTable.getTree().fireTreeWillExpand(treepath);
	    treeTable.getTree().scrollPathToVisible(treepath);
	    treeTable.getTree().fireTreeExpanded(treepath);
	    treeTable.getTree().setSelectionPath(treepath);

	    treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable
		    .getRowHeight()));

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}

	// 占쏙옙占쏙옙 占쌓몌옙占쏙옙.
	// model.fireTreeChanged(parentNode);
	// treeTable.repaint();

    }
}
