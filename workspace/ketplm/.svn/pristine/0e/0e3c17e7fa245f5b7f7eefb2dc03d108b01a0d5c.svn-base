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
 * Use the <code>newEPMLink</code> static factory method(s), not the <code>EPMLink</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="required", type=int.class, initialValue="1"),
   @GeneratedProperty(name="referenceType", type=String.class),
   @GeneratedProperty(name="ecad", type=boolean.class, initialValue="false")
   },
   roleA=@GeneratedRole(name="epmMaster", type=EPMDocumentMaster.class),
   roleB=@GeneratedRole(name="partMaster", type=WTPartMaster.class),
   tableProperties=@TableProperties(tableName="EPMLink")
)
public class EPMLink extends _EPMLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     epmMaster
    * @param     partMaster
    * @return    EPMLink
    * @exception wt.util.WTException
    **/
   public static EPMLink newEPMLink( EPMDocumentMaster epmMaster, WTPartMaster partMaster )
            throws WTException {

      EPMLink instance = new EPMLink();
      instance.initialize( epmMaster, partMaster );
      return instance;
   }

}
