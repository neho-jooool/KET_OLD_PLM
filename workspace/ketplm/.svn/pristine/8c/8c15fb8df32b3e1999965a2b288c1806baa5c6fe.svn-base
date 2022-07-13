package e3ps.groupware.board.service;

import java.util.ArrayList;
import java.util.List;

import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.groupware.board.ImproveBoard;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import ext.ket.shared.mail.service.KETMailHelper;

/**
 * 
 * 
 * @클래스명 : StandardDqmService
 * @작성자 : 황정태
 * @작성일 : 2015. 11. 11.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardBoardService extends StandardManager implements BoardService {

    private static final long serialVersionUID = 1L;

    public static StandardBoardService newStandardBoardService() throws WTException {
	StandardBoardService instance = new StandardBoardService();
	instance.initialize();
	return instance;
    }

    @Override
    public void QnAMailSend(String oid) throws Exception {
	//TODO Auto-generated method stub
	ImproveBoard board = (ImproveBoard)CommonUtil.getObject(oid);
	
	List<WTUser> listToUser = new ArrayList<WTUser>();
	
	WTUser wtUserFrom = board.getUser();
	if(wtUserFrom.isDisabled()){//담장자가 퇴사자면 admin 계정으로 보낸다
	    wtUserFrom = KETObjectUtil.getUser("wcadmin");
	}
	
	ImproveBoard parent = (ImproveBoard)board.getParent();
	WTPrincipalReference owner = (WTPrincipalReference)parent.getOwner();
	WTUser toUser = (WTUser)owner.getPrincipal();
	
	if(!toUser.isDisabled()){
	    listToUser.add(toUser);
	}
	
	if(listToUser.size() > 0){
	    KETMailHelper.service.sendFormMail("08133", "NoticeMailLine4.html", board, wtUserFrom, listToUser);
	}
    }
}
