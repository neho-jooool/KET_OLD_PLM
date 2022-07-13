/**
 * @(#)	ManageBoardCategoryServlet
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.3
 * @author Cho Sung Ok, jerred@e3ps.com
 * @modify	
 *
 */

package e3ps.groupware.board.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.board.BoardCategory;
import e3ps.groupware.board.BoardCategoryData;
import ext.ket.shared.log.Kogger;

public class ManageBoardCategoryServlet extends CommonServlet {
    protected void doService(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
        String command = ParamUtil.checkStrParameter(req.getParameter("command"));		
		
		if ( command.equals("create") ) {
			create(req);			
			alertNgo(res,"카테고리(게시판)이 생성되었습니다.","/Windchill/page/groupware/board/boardcategorycreate.jsp");
		} else if ( command.equals("update") ) {
			update(req);
			alertNgo(res,"카테고리(게시판)이 수정되었습니다.","/Windchill/page/groupware/board/boardcategoryupdate.jsp");
		} else if ( command.equals("delete") ) {
			delete(req);
			alertNgo(res,"카테고리(게시판)이 삭제되었습니다.","/Windchill/page/groupware/board/boardcategorydelete.jsp");
		}			
    }
	
	
	private void create(HttpServletRequest req)  
		throws ServletException, IOException {		
		String parentoid = ParamUtil.checkStrParameter(req.getParameter("parentoid"));
 		
		String name = ParamUtil.checkStrParameter(req.getParameter("name"));	// 카테고리명 		
 		String desc = ParamUtil.checkStrParameter(req.getParameter("desc"));	// 설명
		int treetype = ParamUtil.getIntParameter(req.getParameter("treetype"),BoardCategoryData.TREETYPE_CATEGORY);	// 트리타입 1:카테고리,2:게시판		
		
		try {
			BoardCategory category = BoardCategory.newBoardCategory();
			category.setName(name);
			category.setDescription(desc);
			category.setTreeType(treetype);
			
			if ( !parentoid.equals("") && !parentoid.equals("root") ) {
				category.setParent((BoardCategory)CommonUtil.getObject(parentoid));
			}
			category = (BoardCategory)PersistenceHelper.manager.save(category);	
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}
	}
	
	private void update(HttpServletRequest req)  
		throws ServletException, IOException {
		String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
 		if ( oid.equals("") || oid.equals("root") ) return;
		else {
			String name = ParamUtil.checkStrParameter(req.getParameter("name"));					// 카테고리명 		
	 		String desc = ParamUtil.checkStrParameter(req.getParameter("desc"));					// 설명
	 		int treetype = ParamUtil.getIntParameter(req.getParameter("treetype"),BoardCategoryData.TREETYPE_CATEGORY);	// 트리타입 1:카테고리,2:게시판		
			
			try {
				BoardCategory category = (BoardCategory)CommonUtil.getObject(oid);
				category.setName(name);
				category.setDescription(desc);
				category.setTreeType(treetype);
							
				category = (BoardCategory)PersistenceHelper.manager.modify(category);	
			} catch (WTException e) {
				Kogger.error(getClass(), e);
			} catch (WTPropertyVetoException e) {
				Kogger.error(getClass(), e);
			}
		}
	}

	private void delete(HttpServletRequest req)  
		throws ServletException, IOException {

		String oid = ParamUtil.checkStrParameter(req.getParameter("oid"));
 		if ( oid.equals("") || oid.equals("root") ) return;
		else {			
			try {
				BoardCategory category = (BoardCategory)CommonUtil.getObject(oid);
				PersistenceHelper.manager.delete(category);
			} catch (WTException e) {
				Kogger.error(getClass(), e);
			}			
		}
	}
}
