/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package e3ps.project;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.WTObject;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newTaskAssessResult</code> static factory method(s), not
 * the <code>TaskAssessResult</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTObject.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="rev", type=int.class, initialValue="1"),
   @GeneratedProperty(name="targetScore", type=String.class,
      javaDoc="평가관리 목표값"),
   @GeneratedProperty(name="resultScore", type=String.class,
      javaDoc="평가관리 실적값"),
   @GeneratedProperty(name="result", type=String.class,
      javaDoc="평가관리 합불여부 OK/NG/CDT)"),
   @GeneratedProperty(name="description", type=String.class,
      javaDoc="사유 및 설명")
   },
   tableProperties=@TableProperties(tableName="KETTaskAssessResult")
)
public class TaskAssessResult extends _TaskAssessResult {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    TaskAssessResult
    * @exception wt.util.WTException
    **/
   public static TaskAssessResult newTaskAssessResult()
            throws WTException {

      TaskAssessResult instance = new TaskAssessResult();
      instance.initialize();
      return instance;
   }

}
