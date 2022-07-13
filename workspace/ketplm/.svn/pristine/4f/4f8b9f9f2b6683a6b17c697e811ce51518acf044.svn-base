/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.issue.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import wt.content.ContentHolder;
import wt.enterprise.Managed;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETIssueMeetingList</code> static factory method(s),
 * not the <code>KETIssueMeetingList</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=Managed.class, interfaces={ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="sortNo", type=Integer.class),
   @GeneratedProperty(name="isFile", type=boolean.class, initialValue="false")
   },
   foreignKeys={
   @GeneratedForeignKey(name="ketIssueMeetingLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="meetingHeader", type=ext.ket.issue.entity.KETIssueMeetingHeader.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="meetingList")),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="issuemaster", type=ext.ket.issue.entity.KETIssueMaster.class),
      myRole=@MyRole(name="theKETIssueMeetingList", cardinality=Cardinality.ONE))
   })
public class KETIssueMeetingList extends _KETIssueMeetingList {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETIssueMeetingList
    * @exception wt.util.WTException
    **/
   public static KETIssueMeetingList newKETIssueMeetingList()
            throws WTException {

      KETIssueMeetingList instance = new KETIssueMeetingList();
      instance.initialize();
      return instance;
   }

}
