/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import wt.enterprise.Managed;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newCostReport</code> static factory method(s), not the
 * <code>CostReport</code> constructor, to construct instances of this class.
 *  Instances must be constructed using the static factory(s), in order
 * to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="partNo", type=String.class,
      javaDoc="품번"),
   @GeneratedProperty(name="step", type=String.class,
      javaDoc="DR단계"),
   @GeneratedProperty(name="pjtNo", type=String.class),
   @GeneratedProperty(name="logisticsFlow", type=String.class,
      javaDoc="물류흐름"),
   @GeneratedProperty(name="reviewPurpose", type=String.class,
      javaDoc="검토목적"),
   @GeneratedProperty(name="packSpec", type=String.class,
      javaDoc="포장사양"),
   @GeneratedProperty(name="version", type=String.class, initialValue="\"0\""),
   @GeneratedProperty(name="bigo", type=String.class,  javaDoc="비고"),
   @GeneratedProperty(name="releaseStep", type=String.class,  javaDoc="배포단계")
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="project", type=e3ps.project.E3PSProjectMaster.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theCostReport", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="task", type=e3ps.project.E3PSTask.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theCostReport", cardinality=Cardinality.ONE))
   })
public class CostReport extends _CostReport {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostReport
    * @exception wt.util.WTException
    **/
   public static CostReport newCostReport()
            throws WTException {

      CostReport instance = new CostReport();
      instance.initialize();
      return instance;
   }

}
