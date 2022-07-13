package ext.ket.edm.entity;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.ecm.entity.KETMoldChangeOrder;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistMoldEOLink</code> static factory method(s), not the <code>KETDrawingDistMoldEOLink</code> constructor, to construct instances of this class. Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "eoMold", type = KETMoldChangeOrder.class)
, roleB = @GeneratedRole(name = "distReq", type = KETDrawingDistRequest.class, cardinality = Cardinality.ONE)
, tableProperties = @TableProperties(tableName = "KETDrawingDistMoldEOLink"))
public class KETDrawingDistMoldEOLink extends _KETDrawingDistMoldEOLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param eoMold
     * @param distReq
     * @return KETDrawingDistMoldEOLink
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistMoldEOLink newKETDrawingDistMoldEOLink(KETMoldChangeOrder eoMold, KETDrawingDistRequest distReq) throws WTException {

	KETDrawingDistMoldEOLink instance = new KETDrawingDistMoldEOLink();
	instance.initialize(eoMold, distReq);
	return instance;
    }

}
