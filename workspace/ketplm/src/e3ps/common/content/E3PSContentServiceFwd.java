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

import e3ps.common.content.E3PSContentService;
import e3ps.common.content.uploader.WBFile;
import java.io.File;
import java.lang.String;
import java.util.Vector;
import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.WTObject;
import wt.method.RemoteAccess;
import wt.services.ServiceFactory;
import wt.util.WTException;

@Deprecated
public class E3PSContentServiceFwd implements E3PSContentService, RemoteAccess {


   public ContentHolder attach( ContentHolder holder, Vector files )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, files );
   }

   public ContentHolder attach( ContentHolder holder, Vector files, Vector descs )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, files, descs );
   }

   public ContentHolder attach( ContentHolder holder, WBFile file, String desc )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, file, desc );
   }

   public ContentHolder attach( ContentHolder holder, WBFile file, String desc, boolean isPrimary )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, file, desc, isPrimary );
   }

   public ContentHolder attach( ContentHolder holder, ApplicationData data )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, data );
   }

   public ContentHolder attach( ContentHolder holder, ApplicationData data, boolean isPrimary )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, data, isPrimary );
   }

   public ContentHolder attach( ContentHolder holder, File file, String desc, ContentRoleType contentRoleType )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, file, desc, contentRoleType );
   }

   public ContentHolder attach( ContentHolder holder, WBFile file, String desc, ContentRoleType contentRoleType )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attach( holder, file, desc, contentRoleType );
   }

   public ContentHolder attachURL( ContentHolder holder, String url, String desc, ContentRoleType contentRoleType )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).attachURL( holder, url, desc, contentRoleType );
   }

   public ContentHolder replace( ContentHolder holder, WBFile file, String desc, ContentItem replaceItem )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).replace( holder, file, desc, replaceItem );
   }

   public ContentHolder replace( ContentHolder holder, File file, String desc, ContentItem replaceItem )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).replace( holder, file, desc, replaceItem );
   }

   public ContentHolder replace( ContentHolder holder, ApplicationData data, ContentItem replaceItem )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).replace( holder, data, replaceItem );
   }

   public ContentHolder replaceURL( ContentHolder holder, String url, String desc, ContentItem contentItem )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).replaceURL( holder, url, desc, contentItem );
   }

   public ContentHolder delete( ContentHolder holder, ContentItem deleteItem )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).delete( holder, deleteItem );
   }

   public ContentHolder delete( ContentHolder holder )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).delete( holder );
   }

   public String getIconImgTag( WTObject obj )
            throws WTException {

      // TODO: remove all usages of this Fwd class, then delete it (see ServiceFactory info)
      // forward to remote service implementation
      return ServiceFactory.getService(E3PSContentService.class).getIconImgTag( obj );
   }
}
