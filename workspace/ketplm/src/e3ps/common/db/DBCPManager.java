package e3ps.common.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

import ext.ket.shared.log.Kogger;

/**
 * DBCP를 기반으로 작성하였음. http://www.jakartaproject.com/article/jakarta/1111890409958
 */
public class DBCPManager {
    private static DBCPManager manager = null;

    public static Connection getConnection(String connName) {
	if (manager == null) {
	    initDrivers();
	}

	Connection conn = null;
	try {

	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    // conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:e3ps", "wtadmin", "wtadmin");// dsplmdev
	    // conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:wind", "wcadmin", "wcadmin");//dsgplm
	    // conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:wind10", "tklee10", "test");
	    conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + connName);

	} catch (SQLException e) {
	    Kogger.error(DBCPManager.class, e);
	} catch (ClassNotFoundException e) {
	    Kogger.error(DBCPManager.class, e);
	}
	return conn;
    }

    private synchronized static void initDrivers() {
	Kogger.debug(DBCPManager.class, ">> DBCPManager.initDrivers() ");
	manager = new DBCPManager();
	Properties dbProps = new Properties();

	// File file = new
	// File(ConfigImpl.getInstance().getString("properties.dbcp"));
	// FileInputStream is = new FileInputStream(file);
	InputStream is = manager.getClass().getResourceAsStream("/e3ps/dbcp.properties");
	try {
	    dbProps.load(is);
	    manager.loadDrivers(dbProps);
	} catch (IOException e) {
	    Kogger.error(DBCPManager.class, e);
	}
    }

    private void loadDrivers(Properties props) {
	boolean defaultAutoCommit = false;
	boolean defaultReadOnly = false;

	String name = null;
	Enumeration propNames = props.propertyNames();
	while (propNames.hasMoreElements()) {
	    name = (String) propNames.nextElement();

	    if (name.endsWith(".driver")) {
		String pool = name.substring(0, name.lastIndexOf("."));
		String driver = props.getProperty(pool + ".driver");
		String url = props.getProperty(pool + ".url");
		String user = props.getProperty(pool + ".user");
		String password = props.getProperty(pool + ".password");
		int maxActive = Integer.parseInt(props.getProperty(pool + ".maxActive"));
		int maxIdle = Integer.parseInt(props.getProperty(pool + ".maxIdle"));
		int maxWait = Integer.parseInt(props.getProperty(pool + ".maxWait"));
		defaultAutoCommit = props.getProperty(pool + ".defaultAutoCommit").equals("true");
		defaultReadOnly = props.getProperty(pool + ".defaultReadOnly").equals("true");

		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(driver);
		bds.setUrl(url);
		bds.setUsername(user);
		bds.setPassword(password);
		bds.setMaxActive(maxActive);
		bds.setMaxIdle(maxIdle);
		bds.setMaxWait(maxWait);
		bds.setDefaultAutoCommit(defaultAutoCommit);
		bds.setDefaultReadOnly(defaultReadOnly);

		if (props.containsKey(pool + ".removeAbandoned"))
		    bds.setRemoveAbandoned(props.getProperty(pool + ".removeAbandoned").equals("true"));
		else
		    bds.setRemoveAbandoned(true);

		if (props.containsKey(pool + ".removeAbandonedTimeout"))
		    bds.setRemoveAbandonedTimeout(Integer.parseInt(props.getProperty(pool + ".removeAbandonedTimeout")));
		else
		    bds.setRemoveAbandonedTimeout(60);

		if (props.containsKey(pool + ".logAbandoned"))
		    bds.setLogAbandoned(props.getProperty(pool + ".logAbandoned").equals("true"));
		else
		    bds.setLogAbandoned(true);

		createPools(pool, bds);

		Kogger.debug(getClass(), "Initialized pool : " + pool);
	    }
	}
    }

    private void createPools(String pool, BasicDataSource bds) {
	GenericObjectPool connectionPool = new GenericObjectPool(null);
	connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
	ConnectionFactory connectionFactory = new DataSourceConnectionFactory(bds);
	// ConnectionFactory connectionFactory = new
	// DriverManagerConnectionFactory(connectURI, null);
	// PoolableConnectionFactory poolableConnectionFactory = null;

	// poolableConnectionFactory =
	new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, bds.getDefaultReadOnly(), bds.getDefaultAutoCommit());

	new PoolingDriver().registerPool(pool, connectionPool);
    }
}
