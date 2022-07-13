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

package ext.ket.project.trycondition.entity;

import e3ps.project.MoldProject;
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
import wt.fc.ObjectMappable;
import wt.fc.ObjectReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 * Try 조건표 Master 객체
 * <p>
 * Use the <code>newKETTryCondition</code> static factory method(s), not
 * the <code>KETTryCondition</code> constructor, to construct instances
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
   @GeneratedProperty(name="tryNo", type=String.class,
      javaDoc="Try NO"),
   @GeneratedProperty(name="subVer", type=int.class, initialValue="0",
      javaDoc="subVer"),
   @GeneratedProperty(name="tryDate", type=Timestamp.class,
      javaDoc="Try일자"),
   @GeneratedProperty(name="tryPlace", type=String.class,
      javaDoc="try 장소"),
   @GeneratedProperty(name="dieNo", type=String.class,
      javaDoc="dieNo"),
   @GeneratedProperty(name="partNo", type=String.class,
      javaDoc="Part No"),
   @GeneratedProperty(name="partName", type=String.class,
      javaDoc="Part Name"),
   @GeneratedProperty(name="moldRank", type=String.class,
      javaDoc="금형 난이도"),
   @GeneratedProperty(name="attendant", type=String.class,
      javaDoc="참석자"),
   @GeneratedProperty(name="debugReason", type=String.class,
      javaDoc="디버깅사유"),
   @GeneratedProperty(name="detail", type=String.class,
      javaDoc="특이사항"),
   @GeneratedProperty(name="productDesignRole", type=String.class,
      javaDoc="제품설계(제품도출도  Task 책임자)"),
   @GeneratedProperty(name="moldMake", type=String.class,
      javaDoc="금형제작(금형 프로젝트 정보의 제작구분/제작처 표시)"),
   @GeneratedProperty(name="moldDesignRole", type=String.class,
      javaDoc="금형설계(금형설계 Task 책임자)"),
   @GeneratedProperty(name="moldTryRole", type=String.class,
      javaDoc="금형Try(금형Try Task 책임자)")
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETTryConditionProjectLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldProject", type=e3ps.project.MoldProject.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETTryCondition", cardinality=Cardinality.ONE))
   })
public class KETTryCondition extends _KETTryCondition {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETTryCondition
    * @exception wt.util.WTException
    **/
   public static KETTryCondition newKETTryCondition()
            throws WTException {

      KETTryCondition instance = new KETTryCondition();
      instance.initialize();
      return instance;
   }

}
