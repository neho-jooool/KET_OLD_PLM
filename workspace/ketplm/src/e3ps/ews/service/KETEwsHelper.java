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

package e3ps.ews.service;

import e3ps.ews.service.KETEwsService;
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

public class KETEwsHelper implements Externalizable {


   private static final String RESOURCE = "e3ps.ews.service.serviceResource";
   private static final String CLASSNAME = KETEwsHelper.class.getName();

   /**
    * <BR><BR><B>Supported API: </B>true
    **/
   public static final KETEwsService service = wt.services.ServiceFactory.getService(KETEwsService.class);
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 957977401221134810L;



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

      long readSerialVersionUID = input.readLong();                // consume UID

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID ) {  // if current version UID
      }
      else
         throw new java.io.InvalidClassException( CLASSNAME, "Local class not compatible:"
                           + " stream classdesc externalizationVersionUID=" + readSerialVersionUID 
                           + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID );
   }

}
