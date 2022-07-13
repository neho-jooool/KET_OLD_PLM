/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.dqm.entity;

import e3ps.project.ProductProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import wt.content.ContentItem;
import wt.content.DataFormatReference;
import wt.content.FormatContentHolder;
import wt.content.HttpContentOperation;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETDqmRaise</code> static factory method(s), not the
 * <code>KETDqmRaise</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={FormatContentHolder.class, WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="pjtno", type=String.class),
   @GeneratedProperty(name="pjtname", type=String.class),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="customerName", type=String.class,
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="customerCode", type=String.class,
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="cartypeName", type=String.class),
   @GeneratedProperty(name="cartypeCode", type=String.class),
   @GeneratedProperty(name="problemTypeName", type=String.class),
   @GeneratedProperty(name="problemTypeCode", type=String.class),
   @GeneratedProperty(name="urgencyName", type=String.class),
   @GeneratedProperty(name="urgencyCode", type=String.class),
   @GeneratedProperty(name="partCategoryName", type=String.class),
   @GeneratedProperty(name="partCategoryCode", type=String.class),
   @GeneratedProperty(name="occurDivName", type=String.class),
   @GeneratedProperty(name="occurDivCode", type=String.class),
   @GeneratedProperty(name="occurStepName", type=String.class),
   @GeneratedProperty(name="occurStepCode", type=String.class),
   @GeneratedProperty(name="occurName", type=String.class),
   @GeneratedProperty(name="occurCode", type=String.class),
   @GeneratedProperty(name="occurDate", type=Timestamp.class),
   @GeneratedProperty(name="actionDeptName", type=String.class),//제기자
   @GeneratedProperty(name="actionUserDeptName", type=String.class),//검토자 부서
   @GeneratedProperty(name="actionDeptCode", type=String.class),//검토자 부서코드
   @GeneratedProperty(name="actionUserName", type=String.class), //검토자 이름
   @GeneratedProperty(name="creatorDeptName", type=String.class), //작성자 부서
   @GeneratedProperty(name="creatorDeptCode", type=String.class), //작성자 부서코드
   @GeneratedProperty(name="creatorUserName", type=String.class), //작성자 이름
   @GeneratedProperty(name="requestDate", type=Timestamp.class),
   @GeneratedProperty(name="defectDivCode", type=String.class),
   @GeneratedProperty(name="defectDivName", type=String.class),
   @GeneratedProperty(name="defectTypeCode", type=String.class),
   @GeneratedProperty(name="defectTypeName", type=String.class),
   @GeneratedProperty(name="applyArea1", type=String.class),
   @GeneratedProperty(name="applyArea2", type=String.class),
   @GeneratedProperty(name="applyArea3", type=String.class),
   @GeneratedProperty(name="occurPlaceName", type=String.class),
   @GeneratedProperty(name="occurPlaceCode", type=String.class),
   @GeneratedProperty(name="series", type=String.class),
   @GeneratedProperty(name="issueCode", type=String.class), //문제점구분 코드
   @GeneratedProperty(name="issueName", type=String.class), //문제점구분 명칭
   @GeneratedProperty(name="occurPointCode", type=String.class), //발생시점 코드
   @GeneratedProperty(name="occurPointName", type=String.class), //발생시점 명칭
   @GeneratedProperty(name="importanceCode", type=String.class), //중요도 코드
   @GeneratedProperty(name="importanceName", type=String.class) //중요도 명칭
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="user", type=wt.org.WTUser.class),
      myRole=@MyRole(name="raise", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="part", type=wt.part.WTPart.class),
      myRole=@MyRole(name="raise", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="pmUser", type=wt.org.WTUser.class),
      myRole=@MyRole(name="raise", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="productProject", type=e3ps.project.ProductProject.class),
      myRole=@MyRole(name="raise", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="closeUser", type=wt.org.WTUser.class),
      myRole=@MyRole(name="raise", cardinality=Cardinality.ZERO_TO_ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="actionUser", type=wt.org.WTUser.class),
      myRole=@MyRole(name="action", cardinality=Cardinality.ZERO_TO_ONE))
   })
public class KETDqmRaise extends _KETDqmRaise {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETDqmRaise
    * @exception wt.util.WTException
    **/
   public static KETDqmRaise newKETDqmRaise()
            throws WTException {

      KETDqmRaise instance = new KETDqmRaise();
      instance.initialize();
      return instance;
   }

}
