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

package e3ps.groupware.board;

import e3ps.common.impl.OwnPersistable;
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
 * Use the <code>newNotify</code> static factory method(s), not the <code>Notify</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={ContentHolder.class, OwnPersistable.class}, versions={-5040382800887107242L, -6380360563663008594L},
   properties={
   @GeneratedProperty(name="title", type=String.class,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="contents", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="deadline", type=Timestamp.class),
   @GeneratedProperty(name="readCount", type=int.class, initialValue="0"),
   @GeneratedProperty(name="docNumber", type=int.class),
   @GeneratedProperty(name="preferred", type=int.class, initialValue="0",
      javaDoc="0=false, 1=true"),
   @GeneratedProperty(name="webEditor", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="webEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB))
   })
public class Notify extends _Notify {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    Notify
    * @exception wt.util.WTException
    **/
   public static Notify newNotify()
            throws WTException {

      Notify instance = new Notify();
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

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   boolean readVersion_5040382800887107242L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      contents = (Object)input.readObject();
      deadline = (Timestamp)input.readObject();
      docNumber = input.readInt();
      owner = (WTPrincipalReference)input.readObject();
      preferred = input.readInt();
      readCount = input.readInt();
      thePersistInfo = (PersistInfo)input.readObject();
      title = (String)input.readObject();
      webEditor = (Object)input.readObject();
      webEditorText = (Object)input.readObject();
      if ( !( input instanceof wt.pds.PDSObjectInput ) ) {           // for non-persistent fields
         contentVector = (Vector)input.readObject();
         hasContents = input.readBoolean();
         httpVector = (Vector)input.readObject();
         operation = (HttpContentOperation)input.readObject();
      }

      return true;
   }

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   boolean readVersion_6380360563663008594L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      contents = (Object)input.readObject();
      deadline = (Timestamp)input.readObject();
      owner = (WTPrincipalReference)input.readObject();
      thePersistInfo = (PersistInfo)input.readObject();
      title = (String)input.readObject();
      if ( !( input instanceof wt.pds.PDSObjectInput ) ) {           // for non-persistent fields
         contentVector = (Vector)input.readObject();
         hasContents = input.readBoolean();
         httpVector = (Vector)input.readObject();
         operation = (HttpContentOperation)input.readObject();
      }

      return true;
   }

}
