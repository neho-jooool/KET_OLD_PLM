package ext.ket.shared.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapSessionFactory {
    /*
     * 임원에게 대쉬보드 상환판 메일 발송시 Mybatis 이용하고자 사용
     */
    public static org.apache.ibatis.session.SqlSessionFactory ssf;

    static {

	String resource = "ext/ket/shared/dao/configuration.xml";
	InputStream inputStream = null;

	try {
	    inputStream = Resources.getResourceAsStream(resource);

	} catch (IOException e) {

	    e.printStackTrace();

	}

	ssf = new SqlSessionFactoryBuilder().build(inputStream);

    }

    public static org.apache.ibatis.session.SqlSessionFactory getSqlSessionFactory() {

	return ssf;

    }
}
