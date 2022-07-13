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

import java.lang.Boolean;
import java.lang.String;
import java.util.Hashtable;
import java.util.Vector;
import wt.folder.Folder;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.util.WTException;

import java.lang.Exception;  // Preserved unmodeled dependency

/**
 *
 * @version   1.0
 **/

@RemoteInterface
public interface KETPartService {




   /**
    * @param     number
    * @param     version
    * @return    WTPart
    * @exception wt.util.WTException
    **/
   public WTPart getPart( String number, String version )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable create( Hashtable hash )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable createByExcel( Hashtable hash )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable delete( Hashtable hash )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable modify( Hashtable hash )
            throws WTException;

   /**
    * @param     number
    * @return    WTPart
    * @exception wt.util.WTException
    **/
   public WTPart getPart( String number )
            throws WTException;

   /**
    * @param     hash
    * @param     reviseFlag
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable modify( Hashtable hash, Boolean reviseFlag )
            throws WTException;

   /**
    * @param     befPart
    * @return    WTPart
    * @exception wt.util.WTException
    **/
   public WTPart reviseUpdate( WTPart befPart )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable reviseUpdate( Hashtable hash )
            throws WTException;

   /**
    * @param     folderPath
    * @return    Folder
    * @exception wt.util.WTException
    **/
   public Folder getPartFolder( String folderPath )
            throws WTException;

   /**
    * @param     _part
    * @return    WTPart
    * @exception wt.util.WTException
    **/
   public WTPart getWorkingCopy( WTPart _part )
            throws WTException;

   /**
    * @param     hash
    * @return    Hashtable
    * @exception wt.util.WTException
    **/
   public Hashtable approval( Hashtable hash )
            throws WTException;

   /**
    * @param     _part
    * @return    String
    * @exception wt.util.WTException
    **/
   public String getLatestMapprovalState( WTPart _part )
            throws WTException;

   /**
    * @param     hash
    * @return    Vector
    * @exception wt.util.WTException
    **/
   public Vector createSapPart( Hashtable hash )
            throws WTException;

}
