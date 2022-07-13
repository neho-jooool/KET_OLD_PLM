package ext.ket.edm.entity;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.ecm.entity.KETProdChangeOrder;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistProdEOLink</code> static factory method(s), not the <code>KETDrawingDistProdEOLink</code> constructor, to construct instances of this class. Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "eoProd", type = KETProdChangeOrder.class)
, roleB = @GeneratedRole(name = "distReq", type = KETDrawingDistRequest.class, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETDrawingDistProdEOLink"))
public class KETDrawingDistProdEOLink extends _KETDrawingDistProdEOLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param eoProd
     * @param distReq
     * @return KETDrawingDistProdEOLink
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistProdEOLink newKETDrawingDistProdEOLink(KETProdChangeOrder eoProd, KETDrawingDistRequest distReq) throws WTException {

	KETDrawingDistProdEOLink instance = new KETDrawingDistProdEOLink();
	instance.initialize(eoProd, distReq);
	return instance;
    }

}
