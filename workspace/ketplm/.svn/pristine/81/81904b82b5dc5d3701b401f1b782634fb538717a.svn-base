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

package ext.ket.dqm.entity;

import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmRaiseSeries;
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
 * Use the <code>newKETDqmRaiseSeriesLink</code> static factory method(s),
 * not the <code>KETDqmRaiseSeriesLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   roleA=@GeneratedRole(name="dqmRaise", type=KETDqmRaise.class),
   roleB=@GeneratedRole(name="dqmRaiseSeries", type=KETDqmRaiseSeries.class),
   tableProperties=@TableProperties(tableName="KETDqmRaiseSeriesLink")
)
public class KETDqmRaiseSeriesLink extends _KETDqmRaiseSeriesLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     dqmRaise
    * @param     dqmRaiseSeries
    * @return    KETDqmRaiseSeriesLink
    * @exception wt.util.WTException
    **/
   public static KETDqmRaiseSeriesLink newKETDqmRaiseSeriesLink( KETDqmRaise dqmRaise, KETDqmRaiseSeries dqmRaiseSeries )
            throws WTException {

      KETDqmRaiseSeriesLink instance = new KETDqmRaiseSeriesLink();
      instance.initialize( dqmRaise, dqmRaiseSeries );
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
