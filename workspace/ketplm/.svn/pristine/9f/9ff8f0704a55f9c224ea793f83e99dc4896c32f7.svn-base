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

package ext.ket.edm.entity;

import ext.ket.edm.entity.KETEpmApprovalHis;
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
 * Use the <code>newKETEpmApprovalHisLink</code> static factory method(s),
 * not the <code>KETEpmApprovalHisLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToVersionLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="approvalHis", type=KETEpmApprovalHis.class,
   cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="epmDoc", type=EPMDocument.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="KETEpmApprovalHisLink")
)
public class KETEpmApprovalHisLink extends _KETEpmApprovalHisLink {

   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     epmDoc
    * @param     approvalHis
    * @return    KETEpmApprovalHisLink
    * @exception wt.util.WTException
    **/
   public static KETEpmApprovalHisLink newKETEpmApprovalHisLink( KETEpmApprovalHis approvalHis, EPMDocument epmDoc )
            throws WTException {

      KETEpmApprovalHisLink instance = new KETEpmApprovalHisLink();
      instance.initialize( approvalHis, epmDoc );
      return instance;
   }

}
