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

package e3ps.dms.entity;

import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETRequestPartList;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
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
 * Use the <code>newKETRequestPartLink</code> static factory method(s),
 * not the <code>KETRequestPartLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   roleA=@GeneratedRole(name="developmentRequest", type=KETDevelopmentRequest.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="RequestPart", type=KETRequestPartList.class),
   tableProperties=@TableProperties(tableName="KETRequestPartLink")
)
public class KETRequestPartLink extends _KETRequestPartLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     developmentRequest
    * @param     RequestPart
    * @return    KETRequestPartLink
    * @exception wt.util.WTException
    **/
   public static KETRequestPartLink newKETRequestPartLink( KETDevelopmentRequest developmentRequest, KETRequestPartList RequestPart )
            throws WTException {

      KETRequestPartLink instance = new KETRequestPartLink();
      instance.initialize( developmentRequest, RequestPart );
      return instance;
   }

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   boolean readVersion2538346186404157511L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      if ( !superDone )                                             // if not doing backward compatibility
         super.readExternal( input );                               // handle super class


      return true;
   }

}
