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
 * Use the <code>newKETStampingItemLink</code> static factory method(s), not the <code>KETStampingItemLink</code> constructor, to construct instances of this class. Instances must be constructed using
 * the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "stampItem", type = KETStampingItem.class)
, roleB = @GeneratedRole(name = "stamp", type = KETStamping.class
, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETStampingItemLink"))
public class KETStampingItemLink extends _KETStampingItemLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param stampItem
     * @param stamp
     * @return KETStampingItemLink
     * @exception wt.util.WTException
     **/
    public static KETStampingItemLink newKETStampingItemLink(KETStampingItem stampItem, KETStamping stamp) throws WTException {

	KETStampingItemLink instance = new KETStampingItemLink();
	instance.initialize(stampItem, stamp);
	return instance;
    }

}
