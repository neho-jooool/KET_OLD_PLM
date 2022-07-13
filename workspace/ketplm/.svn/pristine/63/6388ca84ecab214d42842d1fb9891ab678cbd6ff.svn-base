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
      javaDoc="OOTB¿¡¼­ÀÇ Lifecycle.state ¿¡ ´õÇØ ¶Ç ´Ù¸¥ state"),
   @GeneratedProperty(name="activityType", type=String.class,
      javaDoc="1:µµ¸é, 2:BOM, 3:¹®¼­, 4:È°µ¿"),
   @GeneratedProperty(name="customCode", type=String.class,
      javaDoc="ÈÄ¼Ó¾÷¹\r, \nç¿ëÀÚ ÀÔ·Â½Ã À¯´ÏÅ© ÄÚµå"),
   @GeneratedProperty(name="customName", type=String.class,
      javaDoc="ç¿ëÀÚ ÀÔ·Â ÈÄ¼Ó¾÷¹"),
   @GeneratedProperty(name="workerId", type=String.class,
      javaDoc="´ã´çÀÚ WTUser.ida2a2"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,
      javaDoc="¿Ï·áÀÏ"),
   @GeneratedProperty(name="completeRequestDate", type=Timestamp.class,
      javaDoc="¿Ï·á¿äÃ»ÀÏ"),
   @GeneratedProperty(name="receiveConfirmedDate", type=Timestamp.class,
      javaDoc="¼ö½ÅÈ®ÀÎÀÏ"),
   @GeneratedProperty(name="activityTypeDesc", type=String.class,
      javaDoc="³»¿ë"),
   @GeneratedProperty(name="activityBackDesc", type=String.class,
      javaDoc="³»¿ë(ToDo¿¡¼­ ÀÔ·ÂµÈ Á¤º¸)")
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
