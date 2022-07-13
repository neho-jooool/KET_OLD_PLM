package ext.ket.edm.entity;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETStampDistLink</code> static factory method(s), not the <code>KETStampDistLink</code> constructor, to construct instances of this class. Instances must be constructed using the
 * static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "stamp", type = KETStamping.class, cardinality = Cardinality.ZERO_TO_ONE)
, roleB = @GeneratedRole(name = "distReq", type = KETDrawingDistRequest.class, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETStampDistLink"))
public class KETStampDistLink extends _KETStampDistLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param stamp
     * @param distReq
     * @return KETStampDistLink
     * @exception wt.util.WTException
     **/
    public static KETStampDistLink newKETStampDistLink(KETStamping stamp, KETDrawingDistRequest distReq) throws WTException {

	KETStampDistLink instance = new KETStampDistLink();
	instance.initialize(stamp, distReq);
	return instance;
    }

}
