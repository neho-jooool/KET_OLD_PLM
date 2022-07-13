/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.issue.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETGeneralissueLink</code> static factory method(s),
 * not the <code>KETGeneralissueLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="pboNo", type=String.class),
   @GeneratedProperty(name="pboName", type=String.class)
   },
   roleA=@GeneratedRole(name="theKETIssueMaster", type=KETIssueMaster.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="pbo", type=Persistable.class),
   tableProperties=@TableProperties(tableName="KETGeneralissueLink")
)
public class KETGeneralissueLink extends _KETGeneralissueLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     theKETIssueMaster
    * @param     pbo
    * @return    KETGeneralissueLink
    * @exception wt.util.WTException
    **/
   public static KETGeneralissueLink newKETGeneralissueLink( KETIssueMaster theKETIssueMaster, Persistable pbo )
            throws WTException {

      KETGeneralissueLink instance = new KETGeneralissueLink();
      instance.initialize( theKETIssueMaster, pbo );
      return instance;
   }

}
