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

import e3ps.ecm.entity.KETChangeNotice;
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
 * ECR(제품, 금형)과 ECN의 링크
 * <p>
 * Use the <code>newKETEcrEcnLink</code> static factory method(s), not the
 * <code>KETEcrEcnLink</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   roleA=@GeneratedRole(name="ecr", type=WTChangeRequest2.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="ecn", type=KETChangeNotice.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   tableProperties=@TableProperties(tableName="KETEcrEcnLink")
)
public class KETEcrEcnLink extends _KETEcrEcnLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     ecr
    * @param     ecn
    * @return    KETEcrEcnLink
    * @exception wt.util.WTException
    **/
   public static KETEcrEcnLink newKETEcrEcnLink( WTChangeRequest2 ecr, KETChangeNotice ecn )
            throws WTException {

      KETEcrEcnLink instance = new KETEcrEcnLink();
      instance.initialize( ecr, ecn );
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
