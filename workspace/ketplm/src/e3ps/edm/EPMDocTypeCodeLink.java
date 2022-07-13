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

import e3ps.common.code.NumberCode;
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
import wt.vc.ObjectToVersionLink;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newEPMDocTypeCodeLink</code> static factory method(s),
 * not the <code>EPMDocTypeCodeLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToVersionLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="codeType", type=String.class)
   },
   roleA=@GeneratedRole(name="typeCode", type=NumberCode.class),
   roleB=@GeneratedRole(name="typeDoc", type=EPMDocument.class),
   tableProperties=@TableProperties(tableName="EPMDocTypeCodeLink")
)
public class EPMDocTypeCodeLink extends _EPMDocTypeCodeLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     typeCode
    * @param     typeDoc
    * @return    EPMDocTypeCodeLink
    * @exception wt.util.WTException
    **/
   public static EPMDocTypeCodeLink newEPMDocTypeCodeLink( NumberCode typeCode, EPMDocument typeDoc )
            throws WTException {

      EPMDocTypeCodeLink instance = new EPMDocTypeCodeLink();
      instance.initialize( typeCode, typeDoc );
      return instance;
   }

}
