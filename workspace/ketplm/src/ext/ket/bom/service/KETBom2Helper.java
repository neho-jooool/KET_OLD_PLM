package ext.ket.bom.service;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class KETBom2Helper implements Externalizable {

    private static final String RESOURCE = "ext.ket.bom.service.serviceResource";
    private static final String CLASSNAME = KETBom2Helper.class.getName();

    /**
     * <BR>
     * <BR>
     * <B>Supported API: </B>true
     **/
    public static final KETBom2Service service = wt.services.ServiceFactory.getService(KETBom2Service.class);
    static final long serialVersionUID = 1;
    public static final long EXTERNALIZATION_VERSION_UID = 957977401221134810L;

    /**
     * Writes the non-transient fields of this class to an external source.
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @param output
     * @exception java.io.IOException
     **/
    public void writeExternal(ObjectOutput output) throws IOException {

	output.writeLong(EXTERNALIZATION_VERSION_UID);

    }

    /**
     * Reads the non-transient fields of this class from an external source.
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @param input
     * @exception java.io.IOException
     * @exception java.lang.ClassNotFoundException
     **/
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

	long readSerialVersionUID = input.readLong(); // consume UID

	if (readSerialVersionUID == EXTERNALIZATION_VERSION_UID) { // if current version UID
	} else
	    throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible:"
		    + " stream classdesc externalizationVersionUID=" + readSerialVersionUID + " local class externalizationVersionUID="
		    + EXTERNALIZATION_VERSION_UID);
    }

}
