/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.common.dashboard.entity;

import e3ps.groupware.company.Department;
import ext.ket.common.dashboard.entity.KETDashBoardMailSetting;
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
 * Use the <code>newKETDashBoardMailLink</code> static factory method(s),
 * not the <code>KETDashBoardMailLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="mailset", type=KETDashBoardMailSetting.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="dept", type=Department.class),
   tableProperties=@TableProperties(tableName="KETDashBoardMailLink")
)
public class KETDashBoardMailLink extends _KETDashBoardMailLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     mailset
    * @param     dept
    * @return    KETDashBoardMailLink
    * @exception wt.util.WTException
    **/
   public static KETDashBoardMailLink newKETDashBoardMailLink( KETDashBoardMailSetting mailset, Department dept )
            throws WTException {

      KETDashBoardMailLink instance = new KETDashBoardMailLink();
      instance.initialize( mailset, dept );
      return instance;
   }

}
