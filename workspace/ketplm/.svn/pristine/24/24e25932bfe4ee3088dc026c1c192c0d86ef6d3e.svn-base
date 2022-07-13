/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.common.tag.entity;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.common.code.NumberCode;
import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETTagLink</code> static factory method(s), not the
 * <code>KETTagLink</code> constructor, to construct instances of this class.
 *  Instances must be constructed using the static factory(s), in order
 * to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="pbo", type=Persistable.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="tag", type=NumberCode.class,
      cardinality=Cardinality.ONE_TO_MANY),
   tableProperties=@TableProperties(tableName="KETTagLink")
)
public class KETTagLink extends _KETTagLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     tag
    * @param     pbo
    * @return    KETTagLink
    * @exception wt.util.WTException
    **/
   public static KETTagLink newKETTagLink( Persistable pbo, NumberCode tag )
            throws WTException {

      KETTagLink instance = new KETTagLink();
      instance.initialize( pbo, tag );
      return instance;
   }

}
