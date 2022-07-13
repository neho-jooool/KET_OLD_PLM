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

import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningStep;
import java.lang.Boolean;
import java.util.Hashtable;
import java.util.Vector;
import wt.method.RemoteInterface;
import wt.util.WTException;

import java.lang.String;  // Preserved unmodeled dependency
import java.lang.Void;  // Preserved unmodeled dependency
import java.lang.String;  // Preserved unmodeled dependency

/**
 *
 * @version   1.0
 **/

@RemoteInterface
public interface KETEwsService {




   /**
    * 초기유동관리 지정 등록
    *
    * @param     hash
    * @param     files
    * @return    KETEarlyWarning
    * @exception wt.util.WTException
    **/
   public KETEarlyWarning createEarlyWarning( Hashtable hash, Vector files )
            throws WTException;

   /**
    * 초기유동관리 지정 목표 등록
    *
    * @param     hash
    * @param     ketEarlyWarning
    * @exception wt.util.WTException
    **/
   public void createEarlyWarningTarget( Hashtable hash, KETEarlyWarning ketEarlyWarning )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarning
    * @exception wt.util.WTException
    **/
   public KETEarlyWarning updateEarlyWarning( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     ketEarlyWarning
    * @exception wt.util.WTException
    **/
   public void deleteEarlyWarningTarget( Hashtable hash, KETEarlyWarning ketEarlyWarning )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarning
    * @exception wt.util.WTException
    **/
   public KETEarlyWarning reviseEarlyWarning( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean deleteEarlyWarning( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningPlan
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningPlan createEarlyWarningPlan( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningPlan
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningPlan updateEarlyWarningPlan( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningPlan
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningPlan reviseEarlyWarningPlan( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean deleteEarlyWarningPlan( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningResult
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningResult createEarlyWarningResult( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningResult
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningResult updateEarlyWarningResult( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean deleteEarlyWarningResult( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     ketEarlyWarningResult
    * @exception wt.util.WTException
    **/
   public void createEarlyWarningProblem( Hashtable hash, KETEarlyWarningResult ketEarlyWarningResult )
            throws WTException;

   /**
    * @param     hash
    * @param     ketEarlyWarningResult
    * @exception wt.util.WTException
    **/
   public void deleteEarlyWarningProblem( Hashtable hash, KETEarlyWarningResult ketEarlyWarningResult )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningEndReq
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningEndReq createEarlyWarningEndReq( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningEndReq
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningEndReq updateEarlyWarningEndReq( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean deleteEarlyWarningEndReq( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningEnd
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningEnd createEarlyWarningEnd( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    KETEarlyWarningEnd
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningEnd updateEarlyWarningEnd( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @param     files
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean deleteEarlyWarningEnd( Hashtable hash, Vector files )
            throws WTException;

   /**
    * @param     hash
    * @return    KETEarlyWarningStep
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningStep stopEarlyWarning( Hashtable hash )
            throws WTException;

   /**
    * @param     hash
    * @return    KETEarlyWarningStep
    * @exception wt.util.WTException
    **/
   public KETEarlyWarningStep reopenEarlyWarning( Hashtable hash )
            throws WTException;

   /**
    * @return    Boolean
    * @exception wt.util.WTException
    **/
   public Boolean updatePartner()
            throws WTException;

}
