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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.change2.WTChangeOrder2;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETMoldChangeOrder</code> static factory method(s),
 * not the <code>KETMoldChangeOrder</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=WTChangeOrder2.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="ecoId", type=String.class),
   @GeneratedProperty(name="ecoName", type=String.class),
   @GeneratedProperty(name="devYn", type=String.class),
   @GeneratedProperty(name="divisionFlag", type=String.class),
   @GeneratedProperty(name="projectOid", type=String.class),
   @GeneratedProperty(name="vendorFlag", type=String.class),
   @GeneratedProperty(name="prodVendor", type=String.class),
   @GeneratedProperty(name="changeReason", type=String.class),
   @GeneratedProperty(name="otherReasonDesc", type=String.class),
   @GeneratedProperty(name="increaseProdType", type=String.class),
   @GeneratedProperty(name="otherIncreaseProdType", type=String.class),
   @GeneratedProperty(name="ecoWorkerId", type=String.class),
   @GeneratedProperty(name="deptName", type=String.class),
   @GeneratedProperty(name="attribute1", type=String.class),
   @GeneratedProperty(name="attribute2", type=String.class),
   @GeneratedProperty(name="attribute3", type=String.class),
   @GeneratedProperty(name="attribute4", type=String.class),
   @GeneratedProperty(name="attribute5", type=String.class),
   @GeneratedProperty(name="attribute6", type=String.class),
   @GeneratedProperty(name="attribute7", type=String.class),
   @GeneratedProperty(name="attribute8", type=String.class),
   @GeneratedProperty(name="attribute9", type=String.class),
   @GeneratedProperty(name="attribute10", type=String.class),
   @GeneratedProperty(name="webEditor", type=Object.class,
      javaDoc="현상",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      javaDoc="현상",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditor1", type=Object.class,
      javaDoc="문제점 및 요구사항",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText1", type=Object.class,
      javaDoc="문제점 및 요구사항",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB))
   })
public class KETMoldChangeOrder extends _KETMoldChangeOrder {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETMoldChangeOrder
    * @exception wt.util.WTException
    **/
   public static KETMoldChangeOrder newKETMoldChangeOrder()
            throws WTException {

      KETMoldChangeOrder instance = new KETMoldChangeOrder();
      instance.initialize();
      return instance;
   }

}
