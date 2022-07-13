package e3ps.common.dao.conn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;

import ext.ket.shared.log.Kogger;

public class CommonsDbcp implements DbcpResolvable {

    private String driver = null;// LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.driver");
    private String username = null;// LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.username");
    private String password = null;// LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.password");
    private String jdbcConnection = null;// LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.jdbcConnection");
    private int maxActive = 1;// Integer.parseInt(LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.maxactive"));
    private int maxWait = 1;// Integer.parseInt(LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.commons.maxwait"));

    private volatile static CommonsDbcp instance;
    private SharedPoolDataSource sharedDatasource;

    private CommonsDbcp() {

	DriverAdapterCPDS connectionPoolDatasource = new DriverAdapterCPDS();

	try {

	    Properties props = new Properties();
	    InputStream in = CommonsDbcp.class.getClassLoader().getResourceAsStream("./properties/context.properties");
	    try {
		props.load(in);
		driver = props.getProperty("driver");
		username = props.getProperty("username");
		password = props.getProperty("password");
		jdbcConnection = props.getProperty("url");

	    } catch (IOException e) {
		Kogger.error(getClass(), e);
	    }
	    connectionPoolDatasource.setDriver(driver);

	} catch (Exception e) {

	    Kogger.error(getClass(), e);
	}

	connectionPoolDatasource.setUrl(jdbcConnection);
	connectionPoolDatasource.setUser(username);
	connectionPoolDatasource.setPassword(password);

	sharedDatasource = new SharedPoolDataSource();
	sharedDatasource.setConnectionPoolDataSource(connectionPoolDatasource);

	sharedDatasource.setMaxActive(maxActive);
	sharedDatasource.setMaxWait(maxWait);

    }

    public static CommonsDbcp getInstance() {

	if (instance == null) {
	    synchronized (CommonsDbcp.class) {
		if (instance == null) {
		    instance = new CommonsDbcp();
		}
	    }
	}

	return instance;
    }

    public Connection getConnection() throws SQLException {

	try {

	    return sharedDatasource.getConnection();

	} catch (SQLException e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

}
