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

package ext.ket.part.entity;

import ext.ket.part.entity.KETPartClassification;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.part.WTPartMaster;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETPartClassLink</code> static factory method(s), not
 * the <code>KETPartClassLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="classific", type=KETPartClassification.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="partMaster", type=WTPartMaster.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="KETPartClassLink")
)
public class KETPartClassLink extends _KETPartClassLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     classific
    * @param     partMaster
    * @return    KETPartClassLink
    * @exception wt.util.WTException
    **/
   public static KETPartClassLink newKETPartClassLink( KETPartClassification classific, WTPartMaster partMaster )
            throws WTException {

      KETPartClassLink instance = new KETPartClassLink();
      instance.initialize( classific, partMaster );
      return instance;
   }

}
