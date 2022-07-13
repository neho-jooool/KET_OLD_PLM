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
 * Use the <code>newCostReduceLink</code> static factory method(s), not
 * the <code>CostReduceLink</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="reduce", type=CostInvestInfo.class),
   roleB=@GeneratedRole(name="costPart", type=CostPart.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="CostReduceLink")
)
public class CostReduceLink extends _CostReduceLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     reduce
    * @param     costPart
    * @return    CostReduceLink
    * @exception wt.util.WTException
    **/
   public static CostReduceLink newCostReduceLink( CostInvestInfo reduce, CostPart costPart )
            throws WTException {

      CostReduceLink instance = new CostReduceLink();
      instance.initialize( reduce, costPart );
      return instance;
   }

}
