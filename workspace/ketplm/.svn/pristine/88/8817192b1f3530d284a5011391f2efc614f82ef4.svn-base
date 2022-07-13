package ext.ket.edm.entity;

import wt.epm.EPMDocument;
import wt.fc.ObjectReference;
import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistEpmLink</code> static factory method(s), not the <code>KETDrawingDistEpmLink</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "sheetType", type = String.class), @GeneratedProperty(name = "refPart", type = String.class) }, roleA = @GeneratedRole(name = "distEpm", type = EPMDocument.class, referenceType = ObjectReference.class), roleB = @GeneratedRole(name = "distReq", type = KETDrawingDistRequest.class), tableProperties = @TableProperties(tableName = "KETDrawingDistEpmLink"))
public class KETDrawingDistEpmLink extends _KETDrawingDistEpmLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param distEpm
     * @param distReq
     * @return KETDrawingDistEpmLink
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistEpmLink newKETDrawingDistEpmLink(EPMDocument distEpm, KETDrawingDistRequest distReq) throws WTException {

	KETDrawingDistEpmLink instance = new KETDrawingDistEpmLink();
	instance.initialize(distEpm, distReq);
	return instance;
    }

}
