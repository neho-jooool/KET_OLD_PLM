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
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.org.WTPrincipal;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import java.io.Serializable;  // Preserved unmodeled dependency
import e3ps.access.AccessAuthority;  // Preserved unmodeled dependency
import java.io.Externalizable;  // Preserved unmodeled dependency
import java.io.IOException;  // Preserved unmodeled dependency
import java.io.ObjectInput;  // Preserved unmodeled dependency
import java.io.ObjectOutput;  // Preserved unmodeled dependency
import java.lang.ClassNotFoundException;  // Preserved unmodeled dependency
import java.lang.Object;  // Preserved unmodeled dependency
import java.sql.SQLException;  // Preserved unmodeled dependency
import wt.org.WTPrincipal;  // Preserved unmodeled dependency
import wt.pds.PersistentRetrieveIfc;  // Preserved unmodeled dependency
import wt.pds.PersistentStoreIfc;  // Preserved unmodeled dependency
import wt.pom.DatastoreException;  // Preserved unmodeled dependency
import wt.util.WTException;  // Preserved unmodeled dependency
import wt.util.WTPropertyVetoException;  // Preserved unmodeled dependency
import java.io.Serializable;  // Preserved unmodeled dependency
import e3ps.access.AccessAuthority;  // Preserved unmodeled dependency
import java.lang.Object;  // Preserved unmodeled dependency
import java.sql.SQLException;  // Preserved unmodeled dependency
import wt.org.WTPrincipal;  // Preserved unmodeled dependency
import wt.pds.PersistentRetrieveIfc;  // Preserved unmodeled dependency
import wt.pds.PersistentStoreIfc;  // Preserved unmodeled dependency
import wt.pom.DatastoreException;  // Preserved unmodeled dependency
import wt.util.WTException;  // Preserved unmodeled dependency
import wt.util.WTPropertyVetoException;  // Preserved unmodeled dependency
import e3ps.access.AccessAuthority;  // Preserved unmodeled dependency
import java.lang.Object;  // Preserved unmodeled dependency
import java.sql.SQLException;  // Preserved unmodeled dependency
import wt.org.WTPrincipal;  // Preserved unmodeled dependency
import wt.pds.PersistentRetrieveIfc;  // Preserved unmodeled dependency
import wt.pds.PersistentStoreIfc;  // Preserved unmodeled dependency
import wt.pom.DatastoreException;  // Preserved unmodeled dependency
import wt.util.WTException;  // Preserved unmodeled dependency
import wt.util.WTPropertyVetoException;  // Preserved unmodeled dependency
import e3ps.access.AccessAuthority;  // Preserved unmodeled dependency
import java.lang.Object;  // Preserved unmodeled dependency
import java.sql.SQLException;  // Preserved unmodeled dependency
import wt.org.WTPrincipal;  // Preserved unmodeled dependency
import wt.pds.PersistentRetrieveIfc;  // Preserved unmodeled dependency
import wt.pds.PersistentStoreIfc;  // Preserved unmodeled dependency
import wt.pom.DatastoreException;  // Preserved unmodeled dependency
import wt.util.WTException;  // Preserved unmodeled dependency
import wt.util.WTPropertyVetoException;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newAccessUserLink</code> static factory method(s), not
 * the <code>AccessUserLink</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=AccessAuthLink.class,
   roleA=@GeneratedRole(name="access", type=AccessAuthority.class),
   roleB=@GeneratedRole(name="user", type=WTPrincipal.class),
   tableProperties=@TableProperties(tableName="AccessUserLink")
)
public class AccessUserLink extends _AccessUserLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     access
    * @param     user
    * @return    AccessUserLink
    * @exception wt.util.WTException
    **/
   public static AccessUserLink newAccessUserLink( AccessAuthority access, WTPrincipal user )
            throws WTException {

      AccessUserLink instance = new AccessUserLink();
      instance.initialize( access, user );
      return instance;
   }

}
