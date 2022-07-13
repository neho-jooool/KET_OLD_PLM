package ext.ket.shared.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import wt.pom.Transaction;
import ext.ket.shared.log.Kogger;

/**
 * Transaction을 처리하는 Aspect 클래스
 * 
 * <pre>
 * 본 Aspect의 Pointcut은 META-INF/asp.xml 파일에서 설정 합니다.
 * 신규 모듈 및 ext.ket 패키지에 포함될 프로젝트에 한하여 
 * 동작하도록 되어 있습니다.
 * </pre>
 * 
 * @클래스명 : TransactionAspect
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 15.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Aspect
public abstract class TransactionAspect {

    @Pointcut
    public abstract void scope();

    /**
     * Windchill Service 객체의 Transaction을 처리 method
     * 
     * @param joinPoint
     * @throws Throwable
     * @메소드명 : transactionAround
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 15.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Around("scope()")
    public final Object transactionAround(ProceedingJoinPoint joinPoint) throws Throwable {
	Kogger.debug(getClass(), "[" + joinPoint.toString() + "]Transaction start");
	Transaction transaction = null;
	try {
	    transaction = new Transaction();
	    transaction.start();
	    Object result = joinPoint.proceed();
	    transaction.commit();
	    Kogger.debug(getClass(), "[" + joinPoint.toString() + "]Transaction commit");
	    transaction = null;
	    return result;
	} catch (Throwable e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
		transaction = null;
		Kogger.debug(getClass(), "[" + joinPoint.toString() + "]Transaction rollback");
	    }
	}
    }
}
