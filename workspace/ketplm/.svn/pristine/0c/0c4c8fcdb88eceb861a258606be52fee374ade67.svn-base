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
 * Use the <code>newKETStampEpmLink</code> static factory method(s), not the <code>KETStampEpmLink</code> constructor, to construct instances of this class. Instances must be constructed using the
 * static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "stampItem", type = KETStampingItem.class, cardinality = Cardinality.ZERO_TO_ONE)
, roleB = @GeneratedRole(name = "distEpmLink", type = KETDrawingDistEpmLink.class, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETStampEpmLink"))
public class KETStampEpmLink extends _KETStampEpmLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param stampItem
     * @param distEpmLink
     * @return KETStampEpmLink
     * @exception wt.util.WTException
     **/
    public static KETStampEpmLink newKETStampEpmLink(KETStampingItem stampItem, KETDrawingDistEpmLink distEpmLink) throws WTException {

	KETStampEpmLink instance = new KETStampEpmLink();
	instance.initialize(stampItem, distEpmLink);
	return instance;
    }

}
