// Generated OwnPersistable%499A5A6A00EA: ? 02/18/09 14:24:56
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

package e3ps.common.util;

import java.lang.String;
import wt.fc.Persistable;
import wt.org.WTPrincipalReference;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
//##end user.imports

//##begin OwnPersistable%499A5A6A00EA.doc preserve=no
/**
 *
 * @version   1.0
 **/
//##end OwnPersistable%499A5A6A00EA.doc

public interface OwnPersistable extends Persistable {


   // --- Attribute Section ---



   //##begin OWNER%OWNER.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OWNER%OWNER.doc
   public static final String OWNER = "owner";


   //##begin user.attributes preserve=yes
   //##end user.attributes

   //##begin static.initialization preserve=yes
   //##end static.initialization


   // --- Operation Section ---

   //##begin getOwner%499A5ADB02DEg.doc preserve=no
   /**
    * Gets the object for the association that plays role: OWNER.
    *
    * @return    WTPrincipalReference
    **/
   //##end getOwner%499A5ADB02DEg.doc

   public WTPrincipalReference getOwner();

   //##begin setOwner%499A5ADB02DEs.doc preserve=no
   /**
    * Sets the object for the association that plays role: OWNER.
    *
    * @param     a_Owner
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setOwner%499A5ADB02DEs.doc

   public void setOwner( WTPrincipalReference a_Owner )
            throws WTPropertyVetoException;

   //##begin user.operations preserve=yes
   //##end user.operations
}
