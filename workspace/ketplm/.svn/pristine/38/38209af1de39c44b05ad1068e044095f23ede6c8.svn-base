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

package e3ps.project;

import e3ps.project.ScheduleData;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * 실제시작일
 * <p>
 * Use the <code>newExtendScheduleData</code> static factory method(s),
 * not the <code>ExtendScheduleData</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=ScheduleData.class,
   properties={
   @GeneratedProperty(name="execStartDate", type=Timestamp.class,
      javaDoc="실제 시작일자"),
   @GeneratedProperty(name="execEndDate", type=Timestamp.class,
      javaDoc="실제 종료일자"),
   @GeneratedProperty(name="execWork", type=int.class,
      javaDoc="실 공수"),
   @GeneratedProperty(name="planWork", type=int.class,
      javaDoc="계획공수"),
   @GeneratedProperty(name="planManHour", type=float.class,
      javaDoc="계획공수(hr)")
   })
public class ExtendScheduleData extends _ExtendScheduleData {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ExtendScheduleData
    * @exception wt.util.WTException
    **/
   public static ExtendScheduleData newExtendScheduleData()
            throws WTException {

      ExtendScheduleData instance = new ExtendScheduleData();
      instance.initialize();
      return instance;
   }

}
