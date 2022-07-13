package ext.ket.edm.entity;

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistFileType</code> static factory method(s), not the <code>KETDrawingDistFileType</code> constructor, to construct instances of this class. Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(serializable = Serialization.EXTERNALIZABLE_BASIC, 
properties = { 
	@GeneratedProperty(name = "distFileType", type = String.class) }
	, foreignKeys = { @GeneratedForeignKey(name = "KETDrawingDistFileTypeLink"
		, foreignKeyRole = @ForeignKeyRole(name = "distReq"
		, type = ext.ket.edm.entity.KETDrawingDistRequest.class
		, constraints = @PropertyConstraints(required = true))
	, myRole = @MyRole(name = "distFileType"
		, cardinality = Cardinality.ONE_TO_MANY)) })
public class KETDrawingDistFileType extends _KETDrawingDistFileType {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETDrawingDistFileType
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistFileType newKETDrawingDistFileType() throws WTException {

	KETDrawingDistFileType instance = new KETDrawingDistFileType();
	instance.initialize();
	return instance;
    }

    /**
     * Supports initialization, following construction of an instance. Invoked by "new" factory having the same signature.
     * 
     * @exception wt.util.WTException
     **/
    protected void initialize() throws WTException {

    }

    /**
     * Gets the value of the attribute: IDENTITY. Supplies the identity of the object for business purposes. The identity is composed of name, number or possibly other attributes. The identity does
     * not include the type of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object) to return a localizable equivalent of getIdentity(). To return a localizable value which includes the object type, use
     *             IdentityFactory.getDisplayIdentity(object). Other alternatives are ((WTObject)obj).getDisplayIdentifier() and ((WTObject)obj).getDisplayIdentity().
     * 
     * @return String
     **/
    public String getIdentity() {

	return null;
    }

    /**
     * Gets the value of the attribute: TYPE. Identifies the type of the object for business purposes. This is typically the class name of the object but may be derived from some other attribute of
     * the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayType(object) to return a localizable equivalent of getType(). Another alternative is ((WTObject)obj).getDisplayType().
     * 
     * @return String
     **/
    public String getType() {

	return null;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

}
