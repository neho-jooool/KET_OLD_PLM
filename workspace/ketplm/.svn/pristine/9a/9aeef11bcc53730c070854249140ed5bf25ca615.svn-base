package ext.ket.edm.entity;

import wt.doc.WTDocument;
import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistDocLink</code> static factory method(s), not the <code>KETDrawingDistDocLink</code> constructor, to construct instances of this class. Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC
, roleA = @GeneratedRole(name = "distDoc", type = WTDocument.class)
, roleB = @GeneratedRole(name = "distReq", type = KETDrawingDistRequest.class)
, tableProperties = @TableProperties(
	tableName = "KETDrawingDistDocLink"))
public class KETDrawingDistDocLink extends _KETDrawingDistDocLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param distDoc
     * @param distReq
     * @return KETDrawingDistDocLink
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistDocLink newKETDrawingDistDocLink(WTDocument distDoc, KETDrawingDistRequest distReq) throws WTException {

	KETDrawingDistDocLink instance = new KETDrawingDistDocLink();
	instance.initialize(distDoc, distReq);
	return instance;
    }

}
