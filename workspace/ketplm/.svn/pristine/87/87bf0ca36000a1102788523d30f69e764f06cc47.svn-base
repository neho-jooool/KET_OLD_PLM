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

import ext.ket.dqm.entity.KETDqmAction;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.change2.WTChangeRequest2;
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
 * Use the <code>newKETEcrDqmLink</code> static factory method(s), not the
 * <code>KETEcrDqmLink</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   roleA=@GeneratedRole(name="ecr", type=WTChangeRequest2.class),
   roleB=@GeneratedRole(name="dqm", type=KETDqmAction.class),
   tableProperties=@TableProperties(tableName="KETEcrDqmLink")
)
public class KETEcrDqmLink extends _KETEcrDqmLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     ecr
    * @param     dqm
    * @return    KETEcrDqmLink
    * @exception wt.util.WTException
    **/
   public static KETEcrDqmLink newKETEcrDqmLink( WTChangeRequest2 ecr, KETDqmAction dqm )
            throws WTException {

      KETEcrDqmLink instance = new KETEcrDqmLink();
      instance.initialize( ecr, dqm );
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
