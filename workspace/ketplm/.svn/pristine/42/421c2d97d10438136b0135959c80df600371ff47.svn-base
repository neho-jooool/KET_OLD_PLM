/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.issue.entity;

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
 * Use the <code>newKETIssueTask</code> static factory method(s), not the
 * <code>KETIssueTask</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="subject", type=String.class,
      javaDoc="요청항목"),
   @GeneratedProperty(name="deptCode", type=String.class),
   @GeneratedProperty(name="requestDate", type=Timestamp.class,
      javaDoc="완료요청일자"),
   @GeneratedProperty(name="completeDate", type=Timestamp.class,
      javaDoc="완료일자"),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="issueState", type=String.class,
      javaDoc="상태"),
   @GeneratedProperty(name="progressDate", type=Timestamp.class,
      javaDoc="진행요청일자"),
   @GeneratedProperty(name="version", type=Integer.class, initialValue="0"),
   @GeneratedProperty(name="sort", type=Integer.class, initialValue="0"),
   @GeneratedProperty(name="lastest", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="branchId", type=long.class, initialValue="0"),
   @GeneratedProperty(name="reStartReason", type=String.class,javaDoc="재시작사유"),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="worker", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETIssueTask", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="ketIssueTaskLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="issueMaster", type=ext.ket.issue.entity.KETIssueMaster.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="issueTask"))
   })
public class KETIssueTask extends _KETIssueTask {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETIssueTask
    * @exception wt.util.WTException
    **/
   public static KETIssueTask newKETIssueTask()
            throws WTException {

      KETIssueTask instance = new KETIssueTask();
      instance.initialize();
      return instance;
   }

}
