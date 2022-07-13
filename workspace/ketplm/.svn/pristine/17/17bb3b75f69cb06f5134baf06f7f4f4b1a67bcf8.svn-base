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

package e3ps.edm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.epm.EPMDocumentMaster;
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
 * Use the <code>newModelReferenceLink</code> static factory method(s),
 * not the <code>ModelReferenceLink</code> constructor, to construct instances
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
   @GeneratedProperty(name="relatedLinkOid", type=String.class),
   @GeneratedProperty(name="required", type=int.class, initialValue="1")
   },
   roleA=@GeneratedRole(name="modelMaster", type=EPMDocumentMaster.class),
   roleB=@GeneratedRole(name="drawingMaster", type=EPMDocumentMaster.class),
   tableProperties=@TableProperties(tableName="ModelReferenceLink")
)
public class ModelReferenceLink extends _ModelReferenceLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     modelMaster
    * @param     drawingMaster
    * @return    ModelReferenceLink
    * @exception wt.util.WTException
    **/
   public static ModelReferenceLink newModelReferenceLink( EPMDocumentMaster modelMaster, EPMDocumentMaster drawingMaster )
            throws WTException {

      ModelReferenceLink instance = new ModelReferenceLink();
      instance.initialize( modelMaster, drawingMaster );
      return instance;
   }

}
