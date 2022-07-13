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

import e3ps.common.impl.OwnPersistable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.enterprise.CabinetManaged;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.org.WTPrincipalReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETWfmMultiApprovalRequest</code> static factory method(s),
 * not the <code>KETWfmMultiApprovalRequest</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=CabinetManaged.class, interfaces={OwnPersistable.class, WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="reqNumber", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=30, required=true),
      columnProperties=@ColumnProperties(index=true, unique=true)),
   @GeneratedProperty(name="reqName", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true),
      columnProperties=@ColumnProperties(index=true)),
   @GeneratedProperty(name="description", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="requestDate", type=Timestamp.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="attribute2", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute3", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute4", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute5", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute6", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute7", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute8", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute9", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute10", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000))
   })
public class KETWfmMultiApprovalRequest extends _KETWfmMultiApprovalRequest {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETWfmMultiApprovalRequest
    * @exception wt.util.WTException
    **/
   public static KETWfmMultiApprovalRequest newKETWfmMultiApprovalRequest()
            throws WTException {

      KETWfmMultiApprovalRequest instance = new KETWfmMultiApprovalRequest();
      instance.initialize();
      return instance;
   }

}
