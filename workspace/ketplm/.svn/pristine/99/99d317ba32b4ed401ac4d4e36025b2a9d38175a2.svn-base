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
 * Use the <code>newKETEarlyWarningStep</code> static factory method(s),
 * not the <code>KETEarlyWarningStep</code> constructor, to construct instances
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
   @GeneratedProperty(name="status", type=String.class,
      javaDoc="초기유동관리 진행단계"),
   @GeneratedProperty(name="fstCharge", type=String.class,
      javaDoc="수행담당자"),
   @GeneratedProperty(name="endDate", type=Timestamp.class,
      javaDoc="활동완료일"),
   @GeneratedProperty(name="stopdate", type=Timestamp.class,
      javaDoc="중단일자"),
   @GeneratedProperty(name="reason", type=String.class,
      javaDoc="중단이유",
      constraints=@PropertyConstraints(upperLimit=4000))
   })
public class KETEarlyWarningStep extends _KETEarlyWarningStep {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETEarlyWarningStep
    * @exception wt.util.WTException
    **/
   public static KETEarlyWarningStep newKETEarlyWarningStep()
            throws WTException {

      KETEarlyWarningStep instance = new KETEarlyWarningStep();
      instance.initialize();
      return instance;
   }

}
