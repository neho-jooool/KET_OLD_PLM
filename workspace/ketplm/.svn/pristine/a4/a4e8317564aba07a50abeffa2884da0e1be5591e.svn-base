/**
 * @(#)	BoardCateogoryData.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.3
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify	
 *
 */

package e3ps.groupware.board;

import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;

public class BoardCategoryData {
	public static final int TREETYPE_CATEGORY = 1;
	public static final int TREETYPE_BOARD = 2;
		
	public String name = "";
	public String description = "";
	public int treeType = BoardCategoryData.TREETYPE_CATEGORY;
	public int priority = 0;
	public boolean canDelete = false;
	
	public BoardCategoryData(){}
	
	public BoardCategoryData(Tree treeType){
		new BoardCategoryData((BoardCategory)treeType);		
	}
	
	public BoardCategoryData(BoardCategory category){
		this.name = category.getName();
		this.description = category.getDescription();
		this.treeType = category.getTreeType();		
		if ( TreeHelper.manager.getChildList(BoardCategory.class,category).size() == 0 ) canDelete = true; 
	}	
}
