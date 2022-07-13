/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.part.entity;

import e3ps.common.code.NumberCode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.Boolean;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.Date;
import java.sql.SQLException;
import wt.enterprise.Simple;
import wt.fc.ObjectReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETNewPartList</code> static factory method(s), not
 * the <code>KETNewPartList</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Simple.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="partNumber", type=String.class,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="partName", type=String.class),
   @GeneratedProperty(name="rawMaterial", type=String.class),
   @GeneratedProperty(name="customer", type=String.class),
   @GeneratedProperty(name="register", type=String.class),
   @GeneratedProperty(name="regDate", type=Date.class),
   @GeneratedProperty(name="modifier", type=String.class),
   @GeneratedProperty(name="modDate", type=Date.class),
   @GeneratedProperty(name="description", type=String.class),
   @GeneratedProperty(name="del", type=Boolean.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETNewPartListTypeLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="newparttype", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="newpartlist", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETNewPartProductLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="newproduct", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="newpartlist", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETNewPartDieLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="newdie", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="newpartlist", cardinality=Cardinality.ONE))
   },
   tableProperties=@TableProperties(compositeUnique1="+ partNumber")
)
public class KETNewPartList extends _KETNewPartList {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETNewPartList
    * @exception wt.util.WTException
    **/
   public static KETNewPartList newKETNewPartList()
            throws WTException {

      KETNewPartList instance = new KETNewPartList();
      instance.initialize();
      return instance;
   }

}
