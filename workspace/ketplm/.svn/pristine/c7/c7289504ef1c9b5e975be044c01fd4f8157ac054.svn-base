package ext.ket.invest.entity;

/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
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
 * Use the <code>newKETInvestMaster</code> static factory method(s), not
 * the <code>KETInvestMaster</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="reqNo", type=String.class, javaDoc="품의번호"),
   @GeneratedProperty(name="reqName", type=String.class, javaDoc="개발제품명"),
   @GeneratedProperty(name="deptCode", type=String.class),
   @GeneratedProperty(name="investExpense", type=String.class, initialValue = "\"0\"", javaDoc="고객투자비 합계" ),
   @GeneratedProperty(name="investExpense_1", type=String.class, initialValue = "\"0\"", javaDoc="고객투자비(금형/설비)" ),
   @GeneratedProperty(name="investExpense_2", type=String.class, initialValue = "\"0\"", javaDoc="고객투자비(QDM/기타)" ),
   @GeneratedProperty(name="collectExpense", type=String.class, initialValue = "\"0\"", javaDoc="회수비용 합계" ),
   @GeneratedProperty(name="collectExpense_1", type=String.class, initialValue = "\"0\"", javaDoc="회수비용 (금형/설비)" ),
   @GeneratedProperty(name="collectExpense_2", type=String.class, initialValue = "\"0\"", javaDoc="회수비용 (QDM/기타)" ),
   @GeneratedProperty(name="requestDate", type=Timestamp.class,javaDoc="회수 예정일자"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,javaDoc="회수 완료일"),
   @GeneratedProperty(name="investState", type=String.class,javaDoc="상태"),
   @GeneratedProperty(name="firstUserName", type=String.class, javaDoc="개발발의자(사용안함)"),
   @GeneratedProperty(name="firstDeptCode", type=String.class, javaDoc="영업부서"),
   @GeneratedProperty(name="bigo", type=String.class,javaDoc="비고사항",constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="pjtNos", type=String.class,javaDoc="연계프로젝트",constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="dateChangeCount", type = Integer.class, initialValue = "0", javaDoc="회수예정일자 변경 횟수" ),
   @GeneratedProperty(name="investTypeCode", type=String.class, javaDoc="유형"),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="manager", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETInvestMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="devRequest", type=e3ps.dms.entity.KETDevelopmentRequest.class),
      myRole=@MyRole(name="theKETInvestMaster", cardinality=Cardinality.ONE))
   })
public class KETInvestMaster extends _KETInvestMaster {


   static final long serialVersionUID = 1;

   /**
    * Default factory for the class.
    *
    * @return    KETInvestMaster
    * @exception wt.util.WTException
    **/
   public static KETInvestMaster newKETInvestMaster()
            throws WTException {

      KETInvestMaster instance = new KETInvestMaster();
      instance.initialize();
      return instance;
   }

}
