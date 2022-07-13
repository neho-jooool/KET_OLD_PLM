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

package e3ps.ews.entity;

import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningTarget;
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
import wt.fc.ForeignKeyLink;  // Preserved unmodeled dependency


/**
 *
 * <p>
 * Use the <code>newEarlyWarningTargetLink</code> static factory method(s),
 * not the <code>EarlyWarningTargetLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   roleA=@GeneratedRole(name="earlyWarning", type=KETEarlyWarning.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="earlyWarningTarget", type=KETEarlyWarningTarget.class,
      cardinality=Cardinality.ONE_TO_MANY),
   tableProperties=@TableProperties(tableName="EarlyWarningTargetLink")
)
public class EarlyWarningTargetLink extends _EarlyWarningTargetLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     earlyWarning
    * @param     earlyWarningTarget
    * @return    EarlyWarningTargetLink
    * @exception wt.util.WTException
    **/
   public static EarlyWarningTargetLink newEarlyWarningTargetLink( KETEarlyWarning earlyWarning, KETEarlyWarningTarget earlyWarningTarget )
            throws WTException {

      EarlyWarningTargetLink instance = new EarlyWarningTargetLink();
      instance.initialize( earlyWarning, earlyWarningTarget );
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
