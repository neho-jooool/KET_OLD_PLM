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

import wt.fc.ObjectToObjectLink;
import wt.part.WTPartMaster;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 *
 * <p>
 * Use the <code>newKETHalbPartDieSetPartLink</code> static factory method(s),
 * not the <code>KETHalbPartDieSetPartLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="halb", type=WTPartMaster.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="dieSet", type=WTPartMaster.class),
   tableProperties=@TableProperties(tableName="KETHalbPartDieSetPartLink")
)
public class KETHalbPartDieSetPartLink extends _KETHalbPartDieSetPartLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     halb
    * @param     dieSet
    * @return    KETHalbPartDieSetPartLink
    * @exception wt.util.WTException
    **/
   public static KETHalbPartDieSetPartLink newKETHalbPartDieSetPartLink( WTPartMaster halb, WTPartMaster dieSet )
            throws WTException {

      KETHalbPartDieSetPartLink instance = new KETHalbPartDieSetPartLink();
      instance.initialize( halb, dieSet );
      return instance;
   }

}
