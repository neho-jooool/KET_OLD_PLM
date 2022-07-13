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

package e3ps.wfm.entity;

import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.doc.WTDocument;
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
 * Use the <code>newKETWfmMultiReqDocLink</code> static factory method(s),
 * not the <code>KETWfmMultiReqDocLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="taskoid", type=String.class)
   },
   roleA=@GeneratedRole(name="request", type=KETWfmMultiApprovalRequest.class, supportedAPI=SupportedAPI.PUBLIC,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="doc", type=WTDocument.class, supportedAPI=SupportedAPI.PUBLIC),
   tableProperties=@TableProperties(tableName="KETWfmMultiReqDocLink")
)
public class KETWfmMultiReqDocLink extends _KETWfmMultiReqDocLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     request
    * @param     doc
    * @return    KETWfmMultiReqDocLink
    * @exception wt.util.WTException
    **/
   public static KETWfmMultiReqDocLink newKETWfmMultiReqDocLink( KETWfmMultiApprovalRequest request, WTDocument doc )
            throws WTException {

      KETWfmMultiReqDocLink instance = new KETWfmMultiReqDocLink();
      instance.initialize( request, doc );
      return instance;
   }

}
