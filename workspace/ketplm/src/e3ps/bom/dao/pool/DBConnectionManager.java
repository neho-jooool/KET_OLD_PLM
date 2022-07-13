package e3ps.bom.dao.pool;

/*
 * Copyright (c) 1998 by Gefion software.
 *
 * Permission to use, copy, and distribute this software for
 * NON-COMMERCIAL purposes and without fee is hereby granted
 * provided that this copyright notice appears in all copies.
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import wt.util.WTProperties;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

/**
 * This class is a Singleton that provides access to one or many connection pools defined in a Property file. A client gets access to the
 * single instance through the static getInstance() method and can then check-out and check-in connections from a pool. When the client
 * shuts down it should call the release() method to close all open connections and do other clean up.
 */
public class DBConnectionManager {
    static private DBConnectionManager instance; // The single instance
    static private int clients;

    private Vector drivers = new Vector();
    private Hashtable pools = new Hashtable();

    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    /**
     * Returns the single instance, creating one if it's the first time this method is called.
     * 
     * @return DBConnectionManager The single instance.
     */
    static synchronized public DBConnectionManager getInstance() {
	if (instance == null) {
	    instance = new DBConnectionManager();
	}
	clients++;
	return instance;
    }

    /**
     * A private constructor since this is a Singleton
     */
    private DBConnectionManager() {
	init();
    }

    /**
     * Returns a connection to the named pool.
     * 
     * @param name
     *            The pool name as defined in the properties file
     * @param con
     *            The Connection
     */
    public void freeConnection(String name, Connection con) {
	DBConnectionPool pool = (DBConnectionPool) pools.get(name);
	if (pool != null) {
	    pool.freeConnection(con);
	}
    }

    /**
     * Returns an open connection. If no one is available, and the max number of connections has not been reached, a new connection is
     * created.
     * 
     * @param name
     *            The pool name as defined in the properties file
     * @return Connection The connection or null
     */
    public Connection getConnection(String name) {
	DBConnectionPool pool = (DBConnectionPool) pools.get(name);

	if (pool != null) {
	    Connection t_con = pool.getConnection();
	    return t_con;
	}
	Kogger.debug(getClass(), "### ERROR ### = DBConnection name : " + name + ", Connection is null, Debug : "
	        + printTrace("DBConnectionManager.getConnection"));
	return null;
    }

    public static String printTrace(String s) {
	String returnString = "";
	Exception exception = new Exception();
	StringWriter stringwriter = new StringWriter();
	PrintWriter printwriter = new PrintWriter(stringwriter);
	exception.printStackTrace(printwriter);
	StringBuffer stringbuffer = stringwriter.getBuffer();
	StringTokenizer st = new StringTokenizer(stringbuffer.toString(), "\n");
	while (st.hasMoreTokens()) {
	    String ts = st.nextToken();
	    if (ts.indexOf(s) != -1) {
		returnString = st.nextToken();
		break;
	    }
	}
	return returnString;
    }

    /**
     * Returns an open connection. If no one is available, and the max number of connections has not been reached, a new connection is
     * created. If the max number has been reached, waits until one is available or the specified time has elapsed.
     * 
     * @param name
     *            The pool name as defined in the properties file
     * @param time
     *            The number of milliseconds to wait
     * @return Connection The connection or null
     */
    public Connection getConnection(String name, long time) {
	DBConnectionPool pool = (DBConnectionPool) pools.get(name);
	if (pool != null) {
	    return pool.getConnection(time);
	}
	return null;
    }

    /**
     * Closes all open connections and deregisters all drivers.
     */
    public synchronized void release() {
	// Wait until called by the last client
	if (--clients != 0) {
	    return;
	}

	Enumeration allPools = pools.elements();
	while (allPools.hasMoreElements()) {
	    DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
	    pool.release();
	}
	Enumeration allDrivers = drivers.elements();
	while (allDrivers.hasMoreElements()) {
	    Driver driver = (Driver) allDrivers.nextElement();
	    try {
		DriverManager.deregisterDriver(driver);
	    } catch (SQLException e) {
	    }
	}
    }

    /**
     * Creates instances of DBConnectionPool based on the properties. A DBConnectionPool can be defined with the following properties:
     * 
     * <PRE>
     * &lt;poolname&gt;.url         The JDBC URL for the database
     * &lt;poolname&gt;.user        A database user (optional)
     * &lt;poolname&gt;.password    A database user password (if user specified)
     * &lt;poolname&gt;.maxconn     The maximal number of connections (optional)
     * </PRE>
     * 
     * @param props
     *            The connection pool properties
     */
    private void createPools(Properties props) {
	Enumeration propNames = props.propertyNames();
	while (propNames.hasMoreElements()) {
	    String name = (String) propNames.nextElement();
	    if (name.endsWith(".url")) {
		String poolName = name.substring(0, name.lastIndexOf("."));
		String url = props.getProperty(poolName + ".url");
		if (url == null) {
		    continue;
		}
		String user = props.getProperty(poolName + ".user", "scott");
		String password = props.getProperty(poolName + ".password", "tiger");
		String maxconn = props.getProperty(poolName + ".maxconn", "0");
		int max;
		try {
		    max = Integer.valueOf(maxconn).intValue();
		} catch (NumberFormatException e) {
		    max = 0;
		}
		DBConnectionPool pool = new DBConnectionPool(poolName, url, user, password, max);
		pools.put(poolName, pool);
	    }
	}
    }

    /**
     * Loads properties and initializes the instance with its values.
     */
    private void init() {
	Properties dbProps = initDbProps();
	loadDrivers(dbProps);
	createPools(dbProps);
    }

    private Properties initDbProps() {
	Properties propt = new Properties();

	// PLM Header Component
	try {
	    Kogger.debug(getClass(), "===========> Base URL : " + WTProperties.getServerCodebase().toExternalForm());
	    String baseUrl = WTProperties.getServerCodebase().toExternalForm();

	    if (baseUrl.indexOf("plmdev") > 0) {
		propt.put(registry.getString("plm") + ".url", registry.getString("plmDevUrl"));
		propt.put(registry.getString("plm") + ".user", registry.getString("plmDevUser"));
		propt.put(registry.getString("plm") + ".password", registry.getString("plmDevPassword"));
		propt.put(registry.getString("plm") + ".maxconn", "5");
	    } else if (baseUrl.indexOf("plmapdev") > 0) {
		propt.put(registry.getString("plm") + ".url", registry.getString("plmDevUrl"));
		propt.put(registry.getString("plm") + ".user", registry.getString("plmDevUser"));
		propt.put(registry.getString("plm") + ".password", registry.getString("plmDevPassword"));
		propt.put(registry.getString("plm") + ".maxconn", "5");
	    } else if (baseUrl.indexOf("plmedu") > 0) {
		propt.put(registry.getString("plm") + ".url", registry.getString("plmEduUrl"));
		propt.put(registry.getString("plm") + ".user", registry.getString("plmEduUser"));
		propt.put(registry.getString("plm") + ".password", registry.getString("plmEduPassword"));
		propt.put(registry.getString("plm") + ".maxconn", "5");
	    } else {
		propt.put(registry.getString("plm") + ".url", registry.getString("plmProdUrl"));
		propt.put(registry.getString("plm") + ".user", registry.getString("plmProdUser"));
		propt.put(registry.getString("plm") + ".password", registry.getString("plmProdPassword"));
		propt.put(registry.getString("plm") + ".maxconn", "5");
	    }

	    // ERP
	    if ((baseUrl.indexOf("plmdev") > 0) || (baseUrl.indexOf("plmedu") > 0)) {
		propt.put(registry.getString("erp") + ".url", registry.getString("erpDevUrl"));
		propt.put(registry.getString("erp") + ".user", registry.getString("erpDevUser"));
		propt.put(registry.getString("erp") + ".password", registry.getString("erpDevPassword"));
		propt.put(registry.getString("erp") + ".maxconn", "5");
	    } else {
		propt.put(registry.getString("erp") + ".url", registry.getString("erpProdUrl"));
		propt.put(registry.getString("erp") + ".user", registry.getString("erpProdUser"));
		propt.put(registry.getString("erp") + ".password", registry.getString("erpProdPassword"));
		propt.put(registry.getString("erp") + ".maxconn", "5");
	    }
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	}

	return propt;
    }

    private String[] getKeyValue(String value) {
	String[] keyValue = new String[2];
	StringTokenizer st = new StringTokenizer(value, "=");
	if (st.hasMoreTokens()) {
	    keyValue[0] = (String) (st.nextToken().trim());
	    keyValue[1] = (String) (st.nextToken().trim());
	}
	return keyValue;
    }

    /**
     * Loads and registers all JDBC drivers. This is done by the DBConnectionManager, as opposed to the DBConnectionPool, since many pools
     * may share the same driver.
     * 
     * @param props
     *            The connection pool properties
     */
    private void loadDrivers(Properties props) {
	String driverClasses = props.getProperty("drivers", "oracle.jdbc.driver.OracleDriver");
	StringTokenizer st = new StringTokenizer(driverClasses);
	while (st.hasMoreElements()) {
	    String driverClassName = st.nextToken().trim();
	    try {
		Driver driver = (Driver) Class.forName(driverClassName).newInstance();

		DriverManager.registerDriver(driver);
		drivers.addElement(driver);
	    } catch (Exception e) {
	    }
	}
    }

    /**
     * This inner class represents a connection pool. It creates new connections on demand, up to a max number if specified. It also makes
     * sure a connection is still open before it is returned to a client.
     */
    class DBConnectionPool {
	private int checkedOut;
	private Vector freeConnections = new Vector();
	private int maxConn;
	private String name;
	private String password;
	private String URL;
	private String user;

	/**
	 * Creates new connection pool.
	 * 
	 * @param name
	 *            The pool name
	 * @param URL
	 *            The JDBC URL for the database
	 * @param user
	 *            The database user, or null
	 * @param password
	 *            The database user password, or null
	 * @param maxConn
	 *            The maximal number of connections, or 0 for no limit
	 */
	public DBConnectionPool(String name, String URL, String user, String password, int maxConn) {
	    this.name = name;
	    this.URL = URL;
	    this.user = user;
	    this.password = password;
	    this.maxConn = maxConn;
	}

	/**
	 * Checks in a connection to the pool. Notify other Threads that may be waiting for a connection.
	 * 
	 * @param con
	 *            The connection to check in
	 */
	public synchronized void freeConnection(Connection con) {
	    freeConnections.addElement(con);
	    checkedOut--;
	}

	/**
	 * Checks out a connection from the pool. If no free connection is available, a new connection is created unless the max number of
	 * connections has been reached. If a free connection has been closed by the database, it's removed from the pool and this method is
	 * called again recursively.
	 */
	public synchronized Connection getConnection() {
	    Connection con = null;
	    if (freeConnections.size() > 0) {
		con = (Connection) freeConnections.firstElement();
		freeConnections.removeElementAt(0);
		try {
		    if (con.isClosed()) {
			con = getConnection();
			checkedOut--;
		    }
		} catch (SQLException e) {
		    con = getConnection();
		    checkedOut--;
		}
	    } else if (maxConn == 0 || checkedOut < maxConn) {
		con = newConnection();
	    }
	    if (con != null) {
		checkedOut++;
	    }

	    return con;
	}

	/**
	 * Checks out a connection from the pool. If no free connection is available, a new connection is created unless the max number of
	 * connections has been reached. If a free connection has been closed by the database, it's removed from the pool and this method is
	 * called again recursively.
	 * <P>
	 * If no connection is available and the max number has been reached, this method waits the specified time for one to be checked in.
	 * 
	 * @param timeout
	 *            The timeout value in milliseconds
	 */
	public synchronized Connection getConnection(long timeout) {
	    long startTime = new Date().getTime();
	    Connection con;
	    while ((con = getConnection()) == null) {
		try {
		    wait(timeout);
		} catch (InterruptedException e) {
		}
		if ((new Date().getTime() - startTime) >= timeout) {
		    return null;
		}
	    }
	    return con;
	}

	/**
	 * Closes all available connections.
	 */
	public synchronized void release() {
	    Enumeration allConnections = freeConnections.elements();
	    while (allConnections.hasMoreElements()) {
		Connection con = (Connection) allConnections.nextElement();
		try {
		    con.close();
		} catch (SQLException e) {
		}
	    }
	    freeConnections.removeAllElements();
	}

	/**
	 * Creates a new connection, using a userid and password if specified.
	 */
	private Connection newConnection() {
	    Connection con = null;
	    try {
		if (user == null) {
		    con = DriverManager.getConnection(URL);
		} else {
		    con = DriverManager.getConnection(URL, user, password);
		}
	    } catch (SQLException e) {
		Kogger.error(getClass(), e);
		return null;
	    }
	    return con;
	}
    }
}
