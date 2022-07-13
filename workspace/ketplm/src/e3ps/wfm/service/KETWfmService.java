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

import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import wt.fc.PagingQueryResult;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.method.RemoteInterface;
import wt.util.WTException;

import e3ps.wfm.entity.KETWfmApprovalLine;  // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmApprovalMaster;  // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmApprovalMaster;  // Preserved unmodeled dependency
import e3ps.wfm.entity.KETWfmApprovalHistory;  // Preserved unmodeled dependency
import wt.fc.QueryResult;  // Preserved unmodeled dependency
import wt.query.QuerySpec;  // Preserved unmodeled dependency
import wt.fc.PagingQueryResult;  // Preserved unmodeled dependency
import wt.query.QuerySpec;  // Preserved unmodeled dependency

/**
 *
 * @version   1.0
 **/

@RemoteInterface
public interface KETWfmService {




   /**
    * @param     master
    * @return    Persistable
    * @exception wt.util.WTException
    **/
   public Persistable createMaster( Hashtable master )
            throws WTException;

   /**
    * @param     approvalLine
    * @exception wt.util.WTException
    **/
   public void createLine( Hashtable approvalLine )
            throws WTException;

   /**
    * @param     oid
    * @param     agreementType
    * @param     comment
    * @exception wt.util.WTException
    **/
   public void updateMaster( String oid, String agreementType, String comment )
            throws WTException;

   /**
    * @param     pboOid  객체의 OID
    * @param     master
    * @return    boolean
    * @exception wt.util.WTException
    **/
   public boolean startProcess( String pboOid, KETWfmApprovalMaster master )
            throws WTException;

   /**
    * @param     aHash  item = workitem OID≪≫condition = selected event
    * @exception wt.util.WTException
    **/
   public void completeActivity( Hashtable aHash )
            throws WTException;

   /**
    * @param     itemOid
    * @param     masterOid
    * @exception wt.util.WTException
    **/
   public void updateProcess( String itemOid, String masterOid )
            throws WTException;

   /**
    * @param     obj
    * @exception wt.util.WTException
    **/
   public void createWorkItem( Object obj )
            throws WTException;

   /**
    * @param     obj
    * @exception wt.util.WTException
    **/
   public void changeStatePBO( Object obj )
            throws WTException;

   /**
    * @param     target
    * @return    String
    * @exception wt.util.WTException
    **/
   public String createMultiReq( ArrayList target )
            throws WTException;

   /**
    * @param     target
    * @return    String
    * @exception wt.util.WTException
    **/
   public String updateMultiReq( ArrayList target )
            throws WTException;

   /**
    * @param     targetOid
    * @exception wt.util.WTException
    **/
   public void deleteMultiReq( String targetOid )
            throws WTException;

   /**
    * @param     map
    * @return    PagingQueryResult
    * @exception wt.util.WTException
    **/
   public PagingQueryResult getEDMQuerySpec( HashMap map )
            throws WTException;

   /**
    * @param     predate
    * @param     postdate
    * @return    QueryResult
    * @exception wt.util.WTException
    **/
   public QueryResult getRefWorklistQuery( String predate, String postdate )
            throws WTException;

   /**
    * @param     pbo
    * @exception wt.util.WTException
    **/
   public void deleteHistory( Persistable pbo )
            throws WTException;

}
