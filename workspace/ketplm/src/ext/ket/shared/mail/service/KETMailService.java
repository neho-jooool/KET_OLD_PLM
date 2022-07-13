package ext.ket.shared.mail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.method.RemoteInterface;
import wt.org.WTUser;

/**
 * @클래스명 : KETMailService
 * @작성자 : Jason, Han
 * @작성일 : 2014. 9. 12.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@RemoteInterface
public interface KETMailService {

    /**
     * 
     * @param type
     * @param templateName
     * @param pbo
     * @param from
     * @param to
     * @throws Exception
     * @메소드명 : sendFormMail
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void sendFormMail(String type, String templateName, Object pbo, WTUser from, List<WTUser> to) throws Exception;

    /**
     * 메일 컨텐트를 작성하기 위해 특정 데이터를 넘겨줘야 하는 경우, map에 정의하여 사용한다.<br>
     * Object pbo, WTUser from, List<WTUser> to 정보 또한 맵에 정의하도록 한다.<br>
     * 08015, 08016, 08022, 08033, 08037, 08109 유형에만 사용함.
     * 
     * @param type
     * @param templateName
     * @param map
     * @throws Exception
     * @메소드명 : sendFormMail
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void sendFormMail(String type, String templateName, HashMap<String, Object> map) throws Exception;

    /**
     * 해당 pbo가 승인완료 상태일때 사용가능
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getLastApprover
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public WTUser getLastApprover(Persistable pbo) throws Exception;

    /**
     * 블록 프로세스 별 이전 결재자 정보를 가져온다. type : reviewer 합의전검토 / submitter 합의후검토 / sequential 순차합의 / parallel 병렬합의
     * 
     * @param self
     * @param type
     * @return
     * @throws Exception
     * @메소드명 : getMailFromUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public WTUser getMailFromUser(ObjectReference self, String type) throws Exception;

    /**
     * 결재를 요청한 사용자를 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getRequestUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public WTUser getRequestUser(WTObject pbo) throws Exception;

    /**
     * 결재 반려한 사용자를 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getRejectUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public WTUser getRejectUser(WTObject pbo) throws Exception;

    /**
     * 승인자를 제외한 결재선 사용자 목록을 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getApprovalLineUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ArrayList<WTUser> getApprovalLineUser(WTObject pbo) throws Exception;

    /**
     * 승인자를 포함한 결재선 사용자 목록을 가져온다. (의뢰접수 반려/검토 취소 시만 사용)
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getApprovalLineUser2
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public ArrayList<WTUser> getApprovalLineUser2(WTObject pbo) throws Exception;

    /**
     * 수신자 목록을 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getReceiverUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ArrayList<WTUser> getReceiverUser(WTObject pbo) throws Exception;

    /**
     * 참조자 목록을 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getReferenceUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ArrayList<WTUser> getReferenceUser(WTObject pbo) throws Exception;

    /**
     * 의뢰접수자 목록을 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getReqrecipientUser
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 6.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public ArrayList<WTUser> getReqrecipientUser(WTObject pbo) throws Exception;

    /**
     * 의뢰접수 한 사용자를 가져온다.
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getReqrecipientUser2
     * @작성자 : Jason, Han
     * @작성일 : 2014. 11. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public WTUser getReqrecipientUser2(WTObject pbo) throws Exception;
}
