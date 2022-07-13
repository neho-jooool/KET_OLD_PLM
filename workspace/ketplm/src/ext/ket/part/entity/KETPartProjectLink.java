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

package ext.ket.part.entity;

import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.ObjectToVersionLink;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.project.E3PSProject;
import e3ps.project.ProjectMaster;

/**
 *
 * <p>
 * Use the <code>newKETPartProjectLink</code> static factory method(s),
 * not the <code>KETPartProjectLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToVersionLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="project", type=ProjectMaster.class, cardinality=Cardinality.ONE_TO_MANY), // Object
   roleB=@GeneratedRole(name="part", type=WTPart.class, cardinality=Cardinality.ZERO_TO_ONE), // Version
   tableProperties=@TableProperties(tableName="KETPartProjectLink")
)
public class KETPartProjectLink extends _KETPartProjectLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     project
    * @param     part
    * @return    KETPartProjectLink
    * @exception wt.util.WTException
    **/
   public static KETPartProjectLink newKETPartProjectLink( ProjectMaster project, WTPart part )
            throws WTException {

      KETPartProjectLink instance = new KETPartProjectLink();
      instance.initialize( project, part );
      return instance;
   }

}
