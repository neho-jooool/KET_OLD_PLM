/* bcwti¬­¬­Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.¬­¬­This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.¬­¬­ecwti
 */

package ext.ket.sales.entity;

import ext.ket.sales.entity.KETSalesProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETSalesMemberLink</code> static factory method(s),
 * not the <code>KETSalesMemberLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="project", type=KETSalesProject.class),
   roleB=@GeneratedRole(name="member", type=WTUser.class),
   tableProperties=@TableProperties(tableName="KETSalesMemberLink")
)
public class KETSalesMemberLink extends _KETSalesMemberLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     project
    * @param     member
    * @return    KETSalesMemberLink
    * @exception wt.util.WTException
    **/
   public static KETSalesMemberLink newKETSalesMemberLink( KETSalesProject project, WTUser member )
            throws WTException {

      KETSalesMemberLink instance = new KETSalesMemberLink();
      instance.initialize( project, member );
      return instance;
   }

}
