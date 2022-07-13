package e3ps.common.dao.conn;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import ext.ket.shared.log.Kogger;

public class JndiDbcp implements DbcpResolvable {

    private static final String LOG_SVR_LOG4J_JNDI_ADDRESS;

    private volatile static JndiDbcp instance;

    private DataSource dataSource;

    static {

	LOG_SVR_LOG4J_JNDI_ADDRESS = ""; // LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.context.jndi");
    }

    private JndiDbcp() throws Exception {

	try {

	    Context context = new InitialContext();
	    dataSource = (javax.sql.DataSource) context.lookup(LOG_SVR_LOG4J_JNDI_ADDRESS);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	}
    }

    public static JndiDbcp getInstance() throws Exception {

	if (instance == null) {
	    synchronized (JndiDbcp.class) {
		if (instance == null) {
		    instance = new JndiDbcp();
		}
	    }
	}

	return instance;
    }

    public Connection getConnection() throws Exception {

	Connection connection = null;

	try {

	    connection = dataSource.getConnection();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new Exception(e);
	}

	return connection;
    }

}
