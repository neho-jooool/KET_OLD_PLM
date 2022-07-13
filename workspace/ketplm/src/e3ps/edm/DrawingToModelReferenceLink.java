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
import wt.epm.EPMDocument;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionToVersionLink;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newDrawingToModelReferenceLink</code> static factory method(s),
 * not the <code>DrawingToModelReferenceLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=VersionToVersionLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="relatedLinkOid", type=String.class),
   @GeneratedProperty(name="required", type=int.class, initialValue="1")
   },
   roleA=@GeneratedRole(name="model", type=EPMDocument.class),
   roleB=@GeneratedRole(name="drawing", type=EPMDocument.class),
   tableProperties=@TableProperties(tableName="DrawingToModelReferenceLink")
)
public class DrawingToModelReferenceLink extends _DrawingToModelReferenceLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     model
    * @param     drawing
    * @return    DrawingToModelReferenceLink
    * @exception wt.util.WTException
    **/
   public static DrawingToModelReferenceLink newDrawingToModelReferenceLink( EPMDocument model, EPMDocument drawing )
            throws WTException {

      DrawingToModelReferenceLink instance = new DrawingToModelReferenceLink();
      instance.initialize( model, drawing );
      return instance;
   }

}
