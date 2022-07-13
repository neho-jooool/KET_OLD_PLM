package e3ps.access;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newAccessAuthLink</code> static factory method(s), not
 * the <code>AccessAuthLink</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="isFull", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="isWrite", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="isModify", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="isDelete", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="isRead", type=boolean.class, initialValue="false")
   },
   roleA=@GeneratedRole(name="roleAObject", type=wt.fc.Persistable.class,
      accessors=@PropertyAccessors(setExceptions={}),
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="roleBObject", type=wt.fc.Persistable.class,
      accessors=@PropertyAccessors(setExceptions={}),
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="AccessAuthLink")
)
public class AccessAuthLink extends _AccessAuthLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     roleAObject
    * @param     roleBObject
    * @return    AccessAuthLink
    * @exception wt.util.WTException
    **/
   public static AccessAuthLink newAccessAuthLink( Persistable roleAObject, Persistable roleBObject )
            throws WTException {
      AccessAuthLink instance = new AccessAuthLink();
      instance.initialize( roleAObject, roleBObject );
      
      return instance;
   }

}
