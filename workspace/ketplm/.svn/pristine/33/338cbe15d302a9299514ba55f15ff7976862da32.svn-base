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

package e3ps.bom.entity;

import e3ps.bom.entity.KETBomEcoHeader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETBomHeader</code> static factory method(s), not the
 * <code>KETBomHeader</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=KETBomEcoHeader.class,
   properties={
   @GeneratedProperty(name="attribute1", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
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
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="newBOMCode", type=String.class),
   @GeneratedProperty(name="BOMVersion", type=String.class),
   @GeneratedProperty(name="creatorDept", type=String.class),
   @GeneratedProperty(name="approverId", type=String.class),
   @GeneratedProperty(name="approverName", type=String.class),
   @GeneratedProperty(name="approverDept", type=String.class),
   @GeneratedProperty(name="approveDate", type=Timestamp.class),
   @GeneratedProperty(name="orgCode", type=String.class),
   @GeneratedProperty(name="orgName", type=String.class),
   @GeneratedProperty(name="checkoutStatus", type=String.class),
   @GeneratedProperty(name="substitudeBOM", type=String.class),
   @GeneratedProperty(name="BOMUse", type=String.class),
   @GeneratedProperty(name="BOMText", type=String.class),
   @GeneratedProperty(name="substitudeText", type=String.class),
   @GeneratedProperty(name="quantity", type=String.class),
   @GeneratedProperty(name="unitOfQuantity", type=String.class),
   @GeneratedProperty(name="BOMStatus", type=String.class),
   @GeneratedProperty(name="transferFlag", type=String.class),
   @GeneratedProperty(name="boxQuantity", type=String.class),
   @GeneratedProperty(name="description", type=String.class)
   })
public class KETBomHeader extends _KETBomHeader {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETBomHeader
    * @exception wt.util.WTException
    **/
   public static KETBomHeader newKETBomHeader()
            throws WTException {

      KETBomHeader instance = new KETBomHeader();
      instance.initialize();
      return instance;
   }

}
