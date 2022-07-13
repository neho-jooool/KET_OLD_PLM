package ext.ket.main.controller.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ext.ket.shared.dao.CommonDao;

/**
 * @클래스명 : MainService
 * @작성자 : psb386
 * @작성일 : 2014. 9. 11.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Service
public class MainService {

    @Inject
    private CommonDao dao;

    @SuppressWarnings("unchecked")
    public Map<String, String> getMainContentsMyTask(long userOid) throws Exception {

	Map<String, Long> pkMap = new HashMap<String, Long>();
	pkMap.put("userOid", userOid);
	return (Map<String, String>) dao.get("main.mainContentsMyTask", pkMap);

    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMainContentsMyProject(long userOid) throws Exception {

	Map<String, Long> pkMap = new HashMap<String, Long>();
	pkMap.put("userOid", userOid);
	Map<String, String> rtnMap = (Map<String, String>) dao.get("main.mainContentsMyProject", pkMap);
	return rtnMap;

    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMainContentsMyTodo(long userOid) throws Exception {

	Map<String, Long> pkMap = new HashMap<String, Long>();
	pkMap.put("userOid", userOid);
	Map<String, String> rtnMap = (Map<String, String>) dao.get("main.mainContentsMyTodo", pkMap);
	return rtnMap;

    }
}
