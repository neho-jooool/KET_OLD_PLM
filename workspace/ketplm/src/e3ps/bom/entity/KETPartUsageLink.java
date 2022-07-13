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

package e3ps.bom.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.configuration.SerialNumbered;
import wt.configuration.TraceCode;
import wt.configuration.TraceableLink;
import wt.fc.Persistable;
import wt.fc.archive.Archiveable;
import wt.iba.value.AttributeContainer;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.type.TypeDefinitionInfo;
import wt.type.TypeDefinitionReference;
import wt.type.Typed;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;
import wt.vc.Mastered;
import wt.vc.struct.IteratedUsageLink;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETPartUsageLink</code> static factory method(s), not
 * the <code>KETPartUsageLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * <BR><BR><B>Supported API: </B>true
 * <BR><BR><B>Extendable: </B>false
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=IteratedUsageLink.class, interfaces={Typed.class, TraceableLink.class, Archiveable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="parentItemCode", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="childItemCode", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="childItemVersion", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="bomLevel", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="sequenceNumber", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="itemSeq", type=int.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="quantity", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="boxQuantity", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="unit", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="material", type=String.class),
   @GeneratedProperty(name="hardnessFrom", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="hardnessTo", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="orgCode", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(required=true)),
   @GeneratedProperty(name="mfgFlag", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="startDate", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="endDate", type=String.class, supportedAPI=SupportedAPI.PUBLIC),
   @GeneratedProperty(name="attribute1", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute2", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute3", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute4", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute5", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute6", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute7", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute8", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute9", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="attribute10", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="bomVersion", type=String.class),
   @GeneratedProperty(name="versionItemCode", type=String.class),
   @GeneratedProperty(name="designDate", type=String.class),
   @GeneratedProperty(name="bomType", type=String.class),
   @GeneratedProperty(name="ict", type=String.class,
      javaDoc="Á¦Ç° ICT",
      constraints=@PropertyConstraints(upperLimit=4)),
   @GeneratedProperty(name="refTop", type=String.class),
   @GeneratedProperty(name="refBottom", type=String.class)
   },
   roleA=@GeneratedRole(name="usedBy", type=wt.part.WTPart.class, supportedAPI=SupportedAPI.PUBLIC,
      cardinality=Cardinality.ONE_TO_MANY),
   roleB=@GeneratedRole(name="uses", type=wt.part.WTPartMaster.class, supportedAPI=SupportedAPI.PUBLIC,
      cardinality=Cardinality.ONE_TO_MANY, owner=false),
   tableProperties=@TableProperties(compositeUnique1="+ roleAObjectRef.key.id+roleBObjectRef.key.id", tableName="KETPartUsageLink", oracleTableSize=OracleTableSize.HUGE)
)
public class KETPartUsageLink extends _KETPartUsageLink {


   static final long serialVersionUID = 1;




   /**
    * Default initializer for Link objects.
    *
    * @param     aObj
    * @param     bObj
    * @exception wt.util.WTException
    **/
   @Override
   protected void initialize( Persistable aObj, Persistable bObj )
            throws WTException {

      super.initialize( aObj, bObj );

   }

   /**
    * Default factory for the class.
    *
    * <BR><BR><B>Supported API: </B>true
    *
    * @param     usedBy
    * @param     uses
    * @return    KETPartUsageLink
    * @exception wt.util.WTException
    **/
   public static KETPartUsageLink newKETPartUsageLink( WTPart usedBy, WTPartMaster uses )
            throws WTException {

      KETPartUsageLink instance = new KETPartUsageLink();
      instance.initialize( usedBy, uses );
      return instance;
   }

   @Override
   public TypeDefinitionInfo getTypeDefinitionInfo() {

      return null;
   }

   @Override
   public Object getValue() {

      return null;
   }

   @Override
   public void setValue( String key, String value ) {

   }

   @Override
   public String getFlexTypeIdPath() {

      return null;
   }

   @Override
   public SerialNumbered getChildRole() {

      return null;
   }

}
