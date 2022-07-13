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

package e3ps.project;

import e3ps.common.impl.OwnPersistable;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.IssueType;

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
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTPrincipalReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newProjectIssue</code> static factory method(s), not the
 * <code>ProjectIssue</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={ContentHolder.class, OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="subject", type=String.class),
   @GeneratedProperty(name="answer", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="planDoneDate", type=Timestamp.class,
      javaDoc="완료예정일"),
   @GeneratedProperty(name="isDone", type=boolean.class,
      javaDoc="완료여부"),
   @GeneratedProperty(name="createDate", type=Timestamp.class),
   @GeneratedProperty(name="urgency", type=String.class,
      javaDoc="긴급도"),
   @GeneratedProperty(name="importance", type=String.class,
      javaDoc="중요도"),
   @GeneratedProperty(name="isAnswerDone", type=boolean.class, initialValue="false"),
   @GeneratedProperty(name="issueAttr1", type=String.class),
   @GeneratedProperty(name="issueAttr2", type=String.class),
   @GeneratedProperty(name="aegisTrans", type=String.class),
   @GeneratedProperty(name="aegisStatus", type=String.class),
   @GeneratedProperty(name="aegisComment", type=Object.class,
   columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="aegisCutOffComment", type=String.class,constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="aegisDate", type=Timestamp.class,javaDoc="aegis이관일"),
   @GeneratedProperty(name="issueType", type=IssueType.class,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="manager", type=WTPrincipalReference.class,
      constraints=@PropertyConstraints(required=true),
      columnProperties=@ColumnProperties(columnName="C"))
   },
   foreignKeys={
   @GeneratedForeignKey(name="ProjectIssueLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="project", type=e3ps.project.E3PSProject.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="issue")),
   @GeneratedForeignKey(name="TaskIssueLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="task", type=e3ps.project.E3PSTask.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="issue"))
   })
public class ProjectIssue extends _ProjectIssue {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ProjectIssue
    * @exception wt.util.WTException
    **/
   public static ProjectIssue newProjectIssue()
            throws WTException {

      ProjectIssue instance = new ProjectIssue();
      instance.initialize();
      return instance;
   }

   /**
    * Supports initialization, following construction of an instance.  Invoked
    * by "new" factory having the same signature.
    *
    * @exception wt.util.WTException
    **/
   protected void initialize()
            throws WTException {

   }

   /**
    * Gets the value of the attribute: IDENTITY.
    * Supplies the identity of the object for business purposes.  The identity
    * is composed of name, number or possibly other attributes.  The identity
    * does not include the type of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object)
    * to return a localizable equivalent of getIdentity().  To return a
    * localizable value which includes the object type, use IdentityFactory.getDisplayIdentity(object).
    * Other alternatives are ((WTObject)obj).getDisplayIdentifier() and
    * ((WTObject)obj).getDisplayIdentity().
    *
    * @return    String
    **/
   public String getIdentity() {

      return null;
   }

   /**
    * Gets the value of the attribute: TYPE.
    * Identifies the type of the object for business purposes.  This is
    * typically the class name of the object but may be derived from some
    * other attribute of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayType(object) to return
    * a localizable equivalent of getType().  Another alternative is ((WTObject)obj).getDisplayType().
    *
    * @return    String
    **/
   public String getType() {

      return null;
   }

   @Override
   public void checkAttributes()
            throws InvalidAttributeException {

   }

}
