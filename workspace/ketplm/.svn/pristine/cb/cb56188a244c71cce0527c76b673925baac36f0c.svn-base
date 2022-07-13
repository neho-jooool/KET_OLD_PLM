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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.doc.WTDocument;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETEarlyWarningEnd</code> static factory method(s),
 * not the <code>KETEarlyWarningEnd</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTDocument.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="endresult", type=String.class,
      javaDoc="판정결과"),
   @GeneratedProperty(name="reason", type=String.class,
      javaDoc="연장사유",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="extensiondate", type=Timestamp.class,
      javaDoc="연장일"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="기타1",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="기타2",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="기타3",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="기타4",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="기타5",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="기타6",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="기타7",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="기타8",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="기타9",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="기타10",
      constraints=@PropertyConstraints(upperLimit=4000))
   })
public class KETEarlyWarningEnd extends _KETEarlyWarningEnd {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETEarlyWarningEnd
    * @exception wt.util.WTException
    **/
   public static KETEarlyWarningEnd newKETEarlyWarningEnd()
            throws WTException {

      KETEarlyWarningEnd instance = new KETEarlyWarningEnd();
      instance.initialize();
      return instance;
   }

}
