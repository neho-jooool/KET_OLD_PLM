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

package e3ps.part.service;

import e3ps.part.service.KETNewPartListService;
import java.lang.String;
import java.util.Hashtable;
import wt.method.RemoteAccess;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class KETNewPartListServiceFwd implements KETNewPartListService, RemoteAccess {


   public boolean createNewPartList( Hashtable arg )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETNewPartListService.class).createNewPartList( arg );
   }

   public boolean updateNewPartList( Hashtable arg )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETNewPartListService.class).updateNewPartList( arg );
   }

   public boolean deleteNewPartList( String oId )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETNewPartListService.class).deleteNewPartList( oId );
   }

   public Hashtable getNewPartList( Hashtable arg )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETNewPartListService.class).getNewPartList( arg );
   }

   public boolean deleteRealNewPartList( String oid )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETNewPartListService.class).deleteRealNewPartList( oid );
   }
}
