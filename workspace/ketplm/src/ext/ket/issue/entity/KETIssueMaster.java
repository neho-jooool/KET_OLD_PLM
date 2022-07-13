/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.issue.entity;

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
 * Use the <code>newKETIssueMaster</code> static factory method(s), not
 * the <code>KETIssueMaster</code> constructor, to construct instances of
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
   @GeneratedProperty(name="reqNo", type=String.class,
      javaDoc="요청No"),
   @GeneratedProperty(name="reqName", type=String.class,
      javaDoc="요청명"),
   @GeneratedProperty(name="reqCode", type=String.class,
      javaDoc="요청구분"),
   @GeneratedProperty(name="deptCode", type=String.class),
   @GeneratedProperty(name="detailCode", type=String.class,
      javaDoc="상세구분"),
   @GeneratedProperty(name="rank", type=String.class,
      javaDoc="등급"),
   @GeneratedProperty(name="requestDate", type=Timestamp.class,
      javaDoc="완료 요청일자"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,
      javaDoc="완료일"),
   @GeneratedProperty(name="lastCustomer", type=String.class,
      javaDoc="최종고객"),
   @GeneratedProperty(name="subContractor", type=String.class,
      javaDoc="접점고객"),
   @GeneratedProperty(name="mainSubject", type=String.class,
      javaDoc="요청사항",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="issueState", type=String.class,
      javaDoc="상태"),
   @GeneratedProperty(name="divisionCode", type=String.class,
      javaDoc="사업부구분"),
   @GeneratedProperty(name="meetingTarget", type=String.class,
      javaDoc="회의대상"),
   @GeneratedProperty(name="version", type=Integer.class, initialValue="0"),
   @GeneratedProperty(name="partCount", type=Integer.class, initialValue="0"),
   @GeneratedProperty(name="partNos", type=String.class,constraints=@PropertyConstraints(upperLimit=8000)),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="manager", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETIssueMaster", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="oemType", type=e3ps.project.outputtype.OEMProjectType.class,
         constraints=@PropertyConstraints(required=false)),
      myRole=@MyRole(name="theKETIssueMaster", cardinality=Cardinality.ONE))
   })
public class KETIssueMaster extends _KETIssueMaster {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETIssueMaster
    * @exception wt.util.WTException
    **/
   public static KETIssueMaster newKETIssueMaster()
            throws WTException {

      KETIssueMaster instance = new KETIssueMaster();
      instance.initialize();
      return instance;
   }

}
