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

import e3ps.dms.entity.KETProjectDocument;
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
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import java.util.Vector;  // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldStdPartLine;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newKETMoldECADocLink</code> static factory method(s), not
 * the <code>KETMoldECADocLink</code> constructor, to construct instances
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
   @GeneratedProperty(name="targetPart", type=String.class),
   @GeneratedProperty(name="activityComment", type=String.class),
   @GeneratedProperty(name="preVersion", type=String.class),
   @GeneratedProperty(name="afterVersion", type=String.class),
   @GeneratedProperty(name="planDate", type=Timestamp.class)
   },
   roleA=@GeneratedRole(name="doc", type=KETProjectDocument.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="moldECA", type=KETMoldChangeActivity.class),
   tableProperties=@TableProperties(tableName="KETMoldECADocLink")
)
public class KETMoldECADocLink extends _KETMoldECADocLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     doc
    * @param     moldECA
    * @return    KETMoldECADocLink
    * @exception wt.util.WTException
    **/
   public static KETMoldECADocLink newKETMoldECADocLink( KETProjectDocument doc, KETMoldChangeActivity moldECA )
            throws WTException {

      KETMoldECADocLink instance = new KETMoldECADocLink();
      instance.initialize( doc, moldECA );
      return instance;
   }

}
