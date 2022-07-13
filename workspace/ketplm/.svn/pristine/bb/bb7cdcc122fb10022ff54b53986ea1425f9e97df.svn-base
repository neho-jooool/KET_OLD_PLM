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

package e3ps.common.content;

import e3ps.common.content.uploader.WBFile;
import java.io.File;
import java.lang.String;
import java.util.Vector;
import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.WTObject;
import wt.method.RemoteInterface;
import wt.util.WTException;

//import e3ps.load.remote.CachFile;  // Preserved unmodeled dependency
//import e3ps.load.remote.CachFile;  // Preserved unmodeled dependency

/**
 *
 * @version   1.0
 **/

@RemoteInterface
public interface E3PSContentService {




   /**
    * @param     holder
    * @param     files
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, Vector files )
            throws WTException;

   /**
    * @param     holder
    * @param     files
    * @param     descs
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, Vector files, Vector descs )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, WBFile file, String desc )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @param     isPrimary
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, WBFile file, String desc, boolean isPrimary )
            throws WTException;

   /**
    * @param     holder
    * @param     data
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, ApplicationData data )
            throws WTException;

   /**
    * @param     holder
    * @param     data
    * @param     isPrimary
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, ApplicationData data, boolean isPrimary )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @param     contentRoleType
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, File file, String desc, ContentRoleType contentRoleType )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @param     contentRoleType
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attach( ContentHolder holder, WBFile file, String desc, ContentRoleType contentRoleType )
            throws WTException;

   /**
    * @param     holder
    * @param     url
    * @param     desc
    * @param     contentRoleType
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder attachURL( ContentHolder holder, String url, String desc, ContentRoleType contentRoleType )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @param     replaceItem
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder replace( ContentHolder holder, WBFile file, String desc, ContentItem replaceItem )
            throws WTException;

   /**
    * @param     holder
    * @param     file
    * @param     desc
    * @param     replaceItem
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder replace( ContentHolder holder, File file, String desc, ContentItem replaceItem )
            throws WTException;

   /**
    * @param     holder
    * @param     data
    * @param     replaceItem
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder replace( ContentHolder holder, ApplicationData data, ContentItem replaceItem )
            throws WTException;

   /**
    * @param     holder
    * @param     url
    * @param     desc
    * @param     contentItem
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder replaceURL( ContentHolder holder, String url, String desc, ContentItem contentItem )
            throws WTException;

   /**
    * @param     holder
    * @param     deleteItem
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder delete( ContentHolder holder, ContentItem deleteItem )
            throws WTException;

   /**
    * @param     holder
    * @return    ContentHolder
    * @exception wt.util.WTException
    **/
   public ContentHolder delete( ContentHolder holder )
            throws WTException;

   /**
    * @param     obj
    * @return    String
    * @exception wt.util.WTException
    **/
   public String getIconImgTag( WTObject obj )
            throws WTException;

}
