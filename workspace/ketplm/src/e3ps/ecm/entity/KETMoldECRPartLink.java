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

import e3ps.ecm.entity.KETMoldChangeRequest;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.part.WTPart;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETMoldECRPartLink</code> static factory method(s),
 * not the <code>KETMoldECRPartLink</code> constructor, to construct instances
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
   @GeneratedProperty(name="changeReqComment", type=String.class)
   },
   roleA=@GeneratedRole(name="part", type=WTPart.class),
   roleB=@GeneratedRole(name="moldECR", type=KETMoldChangeRequest.class),
   tableProperties=@TableProperties(tableName="KETMoldECRPartLink")
)
public class KETMoldECRPartLink extends _KETMoldECRPartLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     part
    * @param     moldECR
    * @return    KETMoldECRPartLink
    * @exception wt.util.WTException
    **/
   public static KETMoldECRPartLink newKETMoldECRPartLink( WTPart part, KETMoldChangeRequest moldECR )
            throws WTException {

      KETMoldECRPartLink instance = new KETMoldECRPartLink();
      instance.initialize( part, moldECR );
      return instance;
   }

}
