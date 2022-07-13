/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.arm.entity;

import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
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
 * Use the <code>newKETAnalysisMasterInfoLink</code> static factory method(s),
 * not the <code>KETAnalysisMasterInfoLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="info", type=KETAnalysisRequestInfo.class),
   roleB=@GeneratedRole(name="master", type=KETAnalysisRequestMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   tableProperties=@TableProperties(tableName="KETAnalysisMasterInfoLink")
)
public class KETAnalysisMasterInfoLink extends _KETAnalysisMasterInfoLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     info
    * @param     master
    * @return    KETAnalysisMasterInfoLink
    * @exception wt.util.WTException
    **/
   public static KETAnalysisMasterInfoLink newKETAnalysisMasterInfoLink( KETAnalysisRequestInfo info, KETAnalysisRequestMaster master )
            throws WTException {

      KETAnalysisMasterInfoLink instance = new KETAnalysisMasterInfoLink();
      instance.initialize( info, master );
      return instance;
   }

}
