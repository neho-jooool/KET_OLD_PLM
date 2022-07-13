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

import java.lang.String;
import java.util.ArrayList;

import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.method.RemoteInterface;
import wt.util.WTException;

import java.util.Vector;  // Preserved unmodeled dependency

/**
 *
 * @version   1.0
 **/

@RemoteInterface
public interface KETCommonService {




   /**
    * @param     oidStr
    * @return    ArrayList<RevisionControlled>
    * @exception wt.util.WTException
    **/
   public ArrayList<RevisionControlled> getVersionHistory( String oidStr )
            throws WTException;

   /**
    * @param     pbo
    * @return    WTObject
    * @exception wt.util.WTException
    **/
   public WTObject getPBO( WTObject pbo )
            throws WTException;
   
   /**
    * @param afterEpmDoc
    * @return
    */
   public Persistable getRelatedEco(EPMDocument afterEpmDoc);

}
