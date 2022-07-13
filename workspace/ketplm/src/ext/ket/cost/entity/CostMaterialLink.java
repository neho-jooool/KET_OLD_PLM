/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newCostMaterialLink</code> static factory method(s), not
 * the <code>CostMaterialLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="material", type=CostMaterialInfo.class),
   roleB=@GeneratedRole(name="costPart", type=CostPart.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="CostMaterialLink")
)
public class CostMaterialLink extends _CostMaterialLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     material
    * @param     costPart
    * @return    CostMaterialLink
    * @exception wt.util.WTException
    **/
   public static CostMaterialLink newCostMaterialLink( CostMaterialInfo material, CostPart costPart )
            throws WTException {

      CostMaterialLink instance = new CostMaterialLink();
      instance.initialize( material, costPart );
      return instance;
   }

}
