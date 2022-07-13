package e3ps.common.dao.conn;

import java.sql.Connection;

public class DbcpManager {

    private volatile static DbcpManager instance;

    private static DbcpResolvable dbcpResolvable;

    private DbcpManager() throws Exception {
	dbcpResolvable = DbcpFactory.getInstance().getPoolManageObject();
    }

    public static DbcpManager getInstance() throws Exception {

	if (instance == null) {
	    synchronized (DbcpManager.class) {
		if (instance == null) {
		    instance = new DbcpManager();
		}
	    }
	}

	return instance;
    }

    public Connection getConnection() throws Exception {
	return dbcpResolvable.getConnection();

    }

}
