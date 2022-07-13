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

package e3ps.groupware.company;

import e3ps.groupware.company.E3PSCompanyService;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.String;



/**
 *
 * @version   1.0
 **/

public class E3PSCompanyHelper implements Externalizable {


   private static final String RESOURCE = "e3ps.groupware.company.companyResource";
   private static final String CLASSNAME = E3PSCompanyHelper.class.getName();

   /**
    **/
   public static final E3PSCompanyService service = wt.services.ServiceFactory.getService(E3PSCompanyService.class);
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 957977401221134810L;
   protected static final long OLD_FORMAT_VERSION_UID = -8648547545385205128L;



   /**
    * Writes the non-transient fields of this class to an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     output
    * @exception java.io.IOException
    **/
   public void writeExternal( ObjectOutput output )
            throws IOException {

      output.writeLong( EXTERNALIZATION_VERSION_UID );

   }

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     input
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   public void readExternal( ObjectInput input )
            throws IOException, ClassNotFoundException {

      long readSerialVersionUID = input.readLong();                  // consume UID
      readVersion( this, input, readSerialVersionUID, false, false );  // read fields
   }

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     thisObject
    * @param     input
    * @param     readSerialVersionUID
    * @param     passThrough
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   protected boolean readVersion( E3PSCompanyHelper thisObject, ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
            throws IOException, ClassNotFoundException {

      boolean success = true;

      if ( readSerialVersionUID == 957977401221134810L )
         return readVersion957977401221134810L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }

   /**
    * Reads the non-transient fields of this class from an external source,
    * which is not the current version.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     passThrough
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   private boolean readOldVersion( ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
            throws IOException, ClassNotFoundException {

      boolean success = true;

      if ( readSerialVersionUID == OLD_FORMAT_VERSION_UID ) {          // handle previous version
      }
      else
         throw new java.io.InvalidClassException( CLASSNAME, "Local class not compatible:"
                           + " stream classdesc externalizationVersionUID=" + readSerialVersionUID
                           + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID );

      return success;
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
   private boolean readVersion957977401221134810L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {


      return true;
   }

}
