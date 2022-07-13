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

package e3ps.wfm.service;

import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmService;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import wt.fc.PagingQueryResult;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class KETWfmServiceFwd implements KETWfmService, RemoteAccess {


   public Persistable createMaster( Hashtable master )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).createMaster( master );
   }

   public void createLine( Hashtable approvalLine )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).createLine( approvalLine );
   }

   public void updateMaster( String oid, String agreementType, String comment )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).updateMaster( oid, agreementType, comment );
   }

   public boolean startProcess( String pboOid, KETWfmApprovalMaster master )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).startProcess( pboOid, master );
   }

   public void completeActivity( Hashtable aHash )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).completeActivity( aHash );
   }

   public void updateProcess( String itemOid, String masterOid )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).updateProcess( itemOid, masterOid );
   }

   public void createWorkItem( Object obj )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).createWorkItem( obj );
   }

   public void changeStatePBO( Object obj )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).changeStatePBO( obj );
   }

   public String createMultiReq( ArrayList target )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).createMultiReq( target );
   }

   public String updateMultiReq( ArrayList target )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).updateMultiReq( target );
   }

   public void deleteMultiReq( String targetOid )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).deleteMultiReq( targetOid );
   }

   public PagingQueryResult getEDMQuerySpec( HashMap map )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).getEDMQuerySpec( map );
   }

   public QueryResult getRefWorklistQuery( String predate, String postdate )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETWfmService.class).getRefWorklistQuery( predate, postdate );
   }

   public void deleteHistory( Persistable pbo )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      ServiceFactory.getService(KETWfmService.class).deleteHistory( pbo );
   }
}
