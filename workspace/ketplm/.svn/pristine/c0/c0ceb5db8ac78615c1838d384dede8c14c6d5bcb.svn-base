package ext.ket.shared.test;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import wt.method.RemoteMethodServer;

/**
 * Spring관련 Controller, 및 Service Junit Test를 지원을 위한 Abstract Class
 * <p>
 * 본 Class를 상속받아 Unit Test 코드를 작성하시기 바랍니다. 기본적으로 Transaction관련 부분은 Rollback되도록 작성되어 있으며, 필요시 @Rollback(false) annotation을 사용할 수 있습니다.
 * </p>
 * 
 * @클래스명 : AbstractUnitTest
 * @작성자 : sw775.park
 * @작성일 : 2014.05.08
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/context-*.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public abstract class AbstractUnitTest {
    protected long startTime = 0;
    protected long endTime = 0;
    protected MockHttpServletRequest request = new MockHttpServletRequest();
    protected MockHttpServletResponse response = new MockHttpServletResponse();

    @Before
    public void init() throws Exception {
	this.setSession();
	this.connectRemoteMethodServer();
    }

    private void setSession() throws Exception {
	RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	// RequestContextHolder.getRequestAttributes().setAttribute("login", login, RequestAttributes.SCOPE_SESSION);
    }

    private void connectRemoteMethodServer() {

	try {
	    if (isSystemStarted()) {
		RemoteMethodServer.getDefault().setUserName("wcadmin");
		RemoteMethodServer.getDefault().setPassword("wcadmin");
	    }
	} catch (Exception e) {
	}
    }

    /**
     * Checks to see if system is started
     */
    private static final boolean isSystemStarted() {
	try {
	    RemoteMethodServer.ping();
	    return true;
	} catch (final RemoteException ex) {
	    // Just ignore
	    return false;
	}
    }

    /**
     * 접속 시작 시간
     * 
     * @return
     */
    protected long startTime() {
	return System.currentTimeMillis();
    }

    /**
     * 접속 종료 시간
     * 
     * @return
     */
    protected long endTime() {
	return System.currentTimeMillis();
    }

    /**
     * 경과시간
     * 
     * @param start
     * @param end
     * @return
     */
    protected double currentTime(long start, long end) {
	return (end - start) / 1000.0;
    }
}