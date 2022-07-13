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

import e3ps.groupware.company.Department;
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
import wt.content.ContentHolder;
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
 * ȸ�Ƿ�
 * <p>
 * Use the <code>newKETMeetingMinutes</code> static factory method(s), not
 * the <code>KETMeetingMinutes</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="meetingName", type=String.class,
      javaDoc="ȸ�Ǹ�"),
   @GeneratedProperty(name="meetingDate", type=Timestamp.class,
      javaDoc="ȸ���Ͻ�"),
   @GeneratedProperty(name="attendance", type=String.class,
      javaDoc="ȸ�� ������"),
   @GeneratedProperty(name="subjectCode", type=String.class,
      javaDoc="�ְ��μ��ڵ�"),
   @GeneratedProperty(name="chargeName", type=String.class,
      javaDoc="����� ID(WTUser�� Name)"),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="meetingTime", type=String.class,
      javaDoc="ȸ�ǽð�"),
   @GeneratedProperty(name="meetingLocation", type=String.class,
      javaDoc="ȸ�����")
   },
   foreignKeys={
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="charge", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETMeetingMinutes", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="subject", type=e3ps.groupware.company.Department.class),
      myRole=@MyRole(name="theKETMeetingMinutes", cardinality=Cardinality.ONE))
   })
public class KETMeetingMinutes extends _KETMeetingMinutes {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETMeetingMinutes
    * @exception wt.util.WTException
    **/
   public static KETMeetingMinutes newKETMeetingMinutes()
            throws WTException {

      KETMeetingMinutes instance = new KETMeetingMinutes();
      instance.initialize();
      return instance;
   }

}
