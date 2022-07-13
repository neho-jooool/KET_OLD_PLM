package e3ps.common.dao.conn;

public class DbcpType {

    private String name;
    private int value;

    public static final DbcpType JNDI = new DbcpType("JNDI", 0);

    public static final DbcpType COMMONS = new DbcpType("COMMONS", 1);

    private DbcpType(String name, int value) {
	this.name = name;
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public int getValue() {
	return value;
    }

    public static DbcpType toDbcpType(String name) {
	if (JNDI.getName().equals(name))
	    return JNDI;
	else if (COMMONS.getName().equals(name))
	    return COMMONS;

	return JNDI;
    }

}
