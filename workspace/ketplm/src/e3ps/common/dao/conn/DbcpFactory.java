package e3ps.common.dao.conn;

public class DbcpFactory {

    private static final String LOG_SVR_LOG4J_POOL_TYPE;
    private static final boolean IS_JNDI;
    private static final boolean IS_COMMONS;

    private static DbcpFactory instance = new DbcpFactory();

    static {

	LOG_SVR_LOG4J_POOL_TYPE = DbcpType.COMMONS.getName(); // LogResourceBundle.getInstance().getProperty("log.logging.log4j.svr.pool.context.type");
	IS_JNDI = (DbcpType.JNDI == DbcpType.toDbcpType(LOG_SVR_LOG4J_POOL_TYPE));
	IS_COMMONS = (DbcpType.COMMONS == DbcpType.toDbcpType(LOG_SVR_LOG4J_POOL_TYPE));
    }

    private DbcpFactory() {

    }

    public static DbcpFactory getInstance() {

	return instance;
    }

    public DbcpResolvable getPoolManageObject() throws Exception {

	if (IS_JNDI)
	    return JndiDbcp.getInstance();
	else if (IS_COMMONS)
	    return CommonsDbcp.getInstance();

	return JndiDbcp.getInstance();
    }

}
