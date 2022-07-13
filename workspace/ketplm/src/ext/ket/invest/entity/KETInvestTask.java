package ext.ket.invest.entity;

/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */
import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import wt.content.ContentHolder;
import wt.enterprise.Managed;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETInvestTask</code> static factory method(s), not the
 * <code>KETInvestTask</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="taskCode", type=String.class, javaDoc="Task구분(영업,관련부서)"),
   @GeneratedProperty(name="subject", type=String.class, javaDoc="요청항목"),
   @GeneratedProperty(name="deptCode", type=String.class),
   @GeneratedProperty(name="requestDate", type=Timestamp.class,javaDoc="완료요청일자"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,javaDoc="완료일자"),
   @GeneratedProperty(name="webEditor", type=Object.class,columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="investState", type=String.class,javaDoc="상태"),
   @GeneratedProperty(name="progressDate", type=Timestamp.class,javaDoc="진행요청일자"),
   @GeneratedProperty(name="collectCode", type=String.class, javaDoc="회수비구분" ),
   @GeneratedProperty(name="collectExpense", type=String.class, initialValue = "\"0\"", javaDoc="회수비용" ),
   @GeneratedProperty(name="collectDate", type=Timestamp.class,javaDoc="회수일자"),
   @GeneratedProperty(name="progressSubject", type=String.class,javaDoc="진행상세",constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="sort", type=Integer.class, initialValue="0"),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="worker", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETInvestTask", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETInvestTaskLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="investMaster", type=ext.ket.invest.entity.KETInvestMaster.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="investTask"))
   })
public class KETInvestTask extends _KETInvestTask {


   static final long serialVersionUID = 1;

   /**
    * Default factory for the class.
    *
    * @return    KETInvestTask
    * @exception wt.util.WTException
    **/
   public static KETInvestTask newKETInvestTask()
            throws WTException {

      KETInvestTask instance = new KETInvestTask();
      instance.initialize();
      return instance;
   }

}
