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

package e3ps.ecm.entity;

import e3ps.ecm.entity.KETMoldChangeActivity;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.epm.EPMDocument;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETMoldECAEpmDocLink</code> static factory method(s),
 * not the <code>KETMoldECAEpmDocLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="changeType", type=String.class),
   @GeneratedProperty(name="activityComment", type=String.class),
   @GeneratedProperty(name="preVersion", type=String.class),
   @GeneratedProperty(name="afterVersion", type=String.class),
   @GeneratedProperty(name="planDate", type=Timestamp.class),
   @GeneratedProperty(name="dieNo", type=String.class),
   @GeneratedProperty(name="scheduleId", type=String.class),
   @GeneratedProperty(name="changeYn", type=String.class),
   @GeneratedProperty(name="epmDocType", type=String.class)
   },
   roleA=@GeneratedRole(name="epmDoc", type=EPMDocument.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="moldECA", type=KETMoldChangeActivity.class),
   tableProperties=@TableProperties(tableName="KETMoldECAEpmDocLink")
)
public class KETMoldECAEpmDocLink extends _KETMoldECAEpmDocLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     epmDoc
    * @param     moldECA
    * @return    KETMoldECAEpmDocLink
    * @exception wt.util.WTException
    **/
   public static KETMoldECAEpmDocLink newKETMoldECAEpmDocLink( EPMDocument epmDoc, KETMoldChangeActivity moldECA )
            throws WTException {

      KETMoldECAEpmDocLink instance = new KETMoldECAEpmDocLink();
      instance.initialize( epmDoc, moldECA );
      return instance;
   }

}
