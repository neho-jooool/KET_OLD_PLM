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

package e3ps.bom.service;

import e3ps.bom.service.KETPartService;
import java.lang.Boolean;
import java.lang.String;
import java.util.Hashtable;
import java.util.Vector;
import wt.folder.Folder;
import wt.method.RemoteAccess;
import wt.part.WTPart;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class KETPartServiceFwd implements KETPartService, RemoteAccess {


   public WTPart getPart( String number, String version )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).getPart( number, version );
   }

   public Hashtable create( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).create( hash );
   }

   public Hashtable createByExcel( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).createByExcel( hash );
   }

   public Hashtable delete( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).delete( hash );
   }

   public Hashtable modify( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).modify( hash );
   }

   public WTPart getPart( String number )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).getPart( number );
   }

   public Hashtable modify( Hashtable hash, Boolean reviseFlag )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).modify( hash, reviseFlag );
   }

   public WTPart reviseUpdate( WTPart befPart )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).reviseUpdate( befPart );
   }

   public Hashtable reviseUpdate( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).reviseUpdate( hash );
   }

   public Folder getPartFolder( String folderPath )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).getPartFolder( folderPath );
   }

   public WTPart getWorkingCopy( WTPart _part )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).getWorkingCopy( _part );
   }

   public Hashtable approval( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).approval( hash );
   }

   public String getLatestMapprovalState( WTPart _part )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).getLatestMapprovalState( _part );
   }

   public Vector createSapPart( Hashtable hash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETPartService.class).createSapPart( hash );
   }
}
