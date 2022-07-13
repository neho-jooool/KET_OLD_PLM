/* bcwti°Ï°Ì°Ï°ÌCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.°Ï°Ì°Ï°ÌThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.°Ï°Ì°Ï°Ìecwti
 */

package ext.ket.arm.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.util.Vector;
import wt.content.ContentItem;
import wt.content.DataFormatReference;
import wt.content.FormatContentHolder;
import wt.content.HttpContentOperation;
import wt.enterprise.Managed;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETAnalysisRequestInfo</code> static factory method(s),
 * not the <code>KETAnalysisRequestInfo</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={FormatContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="webEditor", type=String.class),
   @GeneratedProperty(name="webEditorText", type=String.class),
   @GeneratedProperty(name="designSpec", type=String.class),
   @GeneratedProperty(name="requestedTerm", type=String.class),
   @GeneratedProperty(name="etc", type=String.class)
   })
public class KETAnalysisRequestInfo extends _KETAnalysisRequestInfo {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETAnalysisRequestInfo
    * @exception wt.util.WTException
    **/
   public static KETAnalysisRequestInfo newKETAnalysisRequestInfo()
            throws WTException {

      KETAnalysisRequestInfo instance = new KETAnalysisRequestInfo();
      instance.initialize();
      return instance;
   }

}
