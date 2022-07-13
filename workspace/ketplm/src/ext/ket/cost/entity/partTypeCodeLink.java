/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.common.code.NumberCode;
import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newpartTypeCodeLink</code> static factory method(s), not
 * the <code>partTypeCodeLink</code> constructor, to construct instances
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
   @GeneratedProperty(name="codeType", type=String.class)
   },
   roleA=@GeneratedRole(name="codeMaster", type=CostPartTypeCodeMaster.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="manufactureCode2", type=NumberCode.class),
   tableProperties=@TableProperties(tableName="partTypeCodeLink")
)
public class partTypeCodeLink extends _partTypeCodeLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     codeMaster
    * @param     manufactureCode2
    * @return    partTypeCodeLink
    * @exception wt.util.WTException
    **/
   public static partTypeCodeLink newpartTypeCodeLink( CostPartTypeCodeMaster codeMaster, NumberCode manufactureCode2 )
            throws WTException {

      partTypeCodeLink instance = new partTypeCodeLink();
      instance.initialize( codeMaster, manufactureCode2 );
      return instance;
   }

}
