package legacy.qmsInterface.dao;

/**
 * 
 * @클래스명 : DaoFactory
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public final class DaoFactory {

    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
	return instance;
    }

    public DaoManager getDaoManager() {
	return new DefaultDaoManager();
    }

}
