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

package e3ps.common.service;

import e3ps.common.service.KETCommonService;

import java.lang.String;
import java.util.ArrayList;

import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.method.RemoteAccess;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class KETCommonServiceFwd implements KETCommonService, RemoteAccess {


   public ArrayList<RevisionControlled> getVersionHistory( String oidStr )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETCommonService.class).getVersionHistory( oidStr );
   }

   public WTObject getPBO( WTObject pbo )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(KETCommonService.class).getPBO( pbo );
   }
   
   public Persistable getRelatedEco(EPMDocument afterEpmDoc) {
       return ServiceFactory.getService(KETCommonService.class).getRelatedEco( afterEpmDoc );
   }
}
