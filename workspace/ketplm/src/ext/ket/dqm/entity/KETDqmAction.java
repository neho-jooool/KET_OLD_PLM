/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.dqm.entity;

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
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETDqmAction</code> static factory method(s), not the
 * <code>KETDqmAction</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={FormatContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="duplicateYn", type=String.class),
   @GeneratedProperty(name="duplicateReportName", type=String.class),
   @GeneratedProperty(name="duplicateReportCode", type=String.class),
   @GeneratedProperty(name="causeWebEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="causeWebEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="improveWebEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="improveWebEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="moldModifyDate", type=Timestamp.class),
   @GeneratedProperty(name="drawingOutDate", type=Timestamp.class),
   @GeneratedProperty(name="toDate", type=Timestamp.class),
   @GeneratedProperty(name="trustTestDate", type=Timestamp.class),
   @GeneratedProperty(name="causeCode", type=String.class),
   @GeneratedProperty(name="reviewDate", type=Timestamp.class),
   @GeneratedProperty(name="validationDate", type=Timestamp.class),
   @GeneratedProperty(name="execEndDate", type=Timestamp.class),//실제완료일
   @GeneratedProperty(name="problemReflectYn", type=String.class),
   @GeneratedProperty(name="DesignChangeYn", type=String.class,javaDoc="설계반영여부"),
   @GeneratedProperty(name="mainSubject", type=String.class,javaDoc="진행사항",constraints=@PropertyConstraints(upperLimit=4000)),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="user", type=wt.org.WTUser.class),
      myRole=@MyRole(name="action", cardinality=Cardinality.ZERO_TO_ONE))
   })
public class KETDqmAction extends _KETDqmAction {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETDqmAction
    * @exception wt.util.WTException
    **/
   public static KETDqmAction newKETDqmAction()
            throws WTException {

      KETDqmAction instance = new KETDqmAction();
      instance.initialize();
      return instance;
   }

}
