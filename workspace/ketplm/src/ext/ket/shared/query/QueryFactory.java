package ext.ket.shared.query;

/**
 * 
 * @클래스명 : QueryFactory
 * @작성자 : tklee
 * @작성일 : 2014. 7. 21.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public final class QueryFactory {

    private static QueryFactory instance = new QueryFactory();

    private QueryFactory() {

    }

    public static QueryFactory getInstance() {
	return instance;
    }

    public SimpleQuerySpec getQuerySpec() {
	return new DefaultSimpleQuerySpec();
    }

}
