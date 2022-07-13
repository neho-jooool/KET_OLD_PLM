package ext.ket.shared.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * session Util - Spring에서 제공하는 RequestContextHolder 를 이용하여 request 객체를 service까지 전달하지 않고 사용할 수 있게 해줌
 * 
 * @클래스명 : SessionUtil
 * @작성자 : sw775.park
 * @작성일 : 2014. 6. 4.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SessionUtil {

    /**
     * attribute 값을 가져 오기 위한 method
     * 
     * @param name
     * @return
     * @throws Exception
     * @메소드명 : getAttribute
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static Object getAttribute(String name) throws Exception {
	return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * attribute 설정 method
     * 
     * @param name
     * @param object
     * @throws Exception
     * @메소드명 : setAttribute
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void setAttribute(String name, Object object) throws Exception {
	RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 설정한 attribute 삭제
     * 
     * @param name
     * @throws Exception
     * @메소드명 : removeAttribute
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static void removeAttribute(String name) throws Exception {
	RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * session id
     * 
     * @return
     * @throws Exception
     * @메소드명 : getSessionId
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 4.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public static String getSessionId() throws Exception {
	return RequestContextHolder.getRequestAttributes().getSessionId();
    }
}
