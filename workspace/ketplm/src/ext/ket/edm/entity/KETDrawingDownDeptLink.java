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
 * Use the <code>newKETDrawingDownDeptLink</code> static factory method(s), not the <code>KETDrawingDownDeptLink</code> constructor, to construct instances of this class. Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "distDept", type = KETDrawingDistDept.class)
, roleB = @GeneratedRole(name = "distHis", type = KETDrawingDownHis.class, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETDrawingDownDeptLink"))
public class KETDrawingDownDeptLink extends _KETDrawingDownDeptLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param distDept
     * @param distHis
     * @return KETDrawingDownDeptLink
     * @exception wt.util.WTException
     **/
    public static KETDrawingDownDeptLink newKETDrawingDownDeptLink(KETDrawingDistDept distDept, KETDrawingDownHis distHis) throws WTException {

	KETDrawingDownDeptLink instance = new KETDrawingDownDeptLink();
	instance.initialize(distDept, distHis);
	return instance;
    }

}
