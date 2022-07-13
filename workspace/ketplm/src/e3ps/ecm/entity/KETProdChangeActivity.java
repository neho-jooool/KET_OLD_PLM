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

package e3ps.ecm.entity;

import e3ps.ecm.entity.KETProdChangeOrder;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETProdChangeActivity</code> static factory method(s),
 * not the <code>KETProdChangeActivity</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="activityStatus", type=String.class,
      javaDoc="OOTB에서의 Lifecycle.state 에 더해 또 다른 state"),
   @GeneratedProperty(name="activityType", type=String.class,
      javaDoc="1:도면, 2:BOM, 3:문서, 4:활동"),
   @GeneratedProperty(name="customCode", type=String.class,
      javaDoc="후속업�\r, \n玲育� 입력시 유니크 코드"),
   @GeneratedProperty(name="customName", type=String.class,
      javaDoc="玲育� 입력 후속업�"),
   @GeneratedProperty(name="workerId", type=String.class,
      javaDoc="담당자 WTUser.ida2a2"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,
      javaDoc="완료일"),
   @GeneratedProperty(name="completeRequestDate", type=Timestamp.class,
      javaDoc="완료요청일"),
   @GeneratedProperty(name="receiveConfirmedDate", type=Timestamp.class,
      javaDoc="수신확인일"),
   @GeneratedProperty(name="activityTypeDesc", type=String.class,
      javaDoc="내용"),
   @GeneratedProperty(name="activityBackDesc", type=String.class,
      javaDoc="내용(ToDo에서 입력된 정보)")
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETProdChangeActivityLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="prodECO", type=e3ps.ecm.entity.KETProdChangeOrder.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="prodECA"))
   })
public class KETProdChangeActivity extends _KETProdChangeActivity {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETProdChangeActivity
    * @exception wt.util.WTException
    **/
   public static KETProdChangeActivity newKETProdChangeActivity()
            throws WTException {

      KETProdChangeActivity instance = new KETProdChangeActivity();
      instance.initialize();
      return instance;
   }

}
