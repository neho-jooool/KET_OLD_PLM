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

package e3ps.access;

import e3ps.access.AccessAuthLink;
import e3ps.access.AccessAuthority;
import e3ps.groupware.company.Department;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import java.io.Serializable;  // Preserved unmodeled dependency
import java.io.Externalizable;  // Preserved unmodeled dependency
import java.io.Serializable;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newAccessDeptLink</code> static factory method(s), not
 * the <code>AccessDeptLink</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=AccessAuthLink.class,
   roleA=@GeneratedRole(name="access", type=AccessAuthority.class),
   roleB=@GeneratedRole(name="dept", type=Department.class),
   tableProperties=@TableProperties(tableName="AccessDeptLink")
)
public class AccessDeptLink extends _AccessDeptLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     access
    * @param     dept
    * @return    AccessDeptLink
    * @exception wt.util.WTException
    **/
   public static AccessDeptLink newAccessDeptLink( AccessAuthority access, Department dept )
            throws WTException {

      AccessDeptLink instance = new AccessDeptLink();
      instance.initialize( access, dept );
      return instance;
   }

}
