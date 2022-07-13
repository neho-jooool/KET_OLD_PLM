/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.sales.entity;

import ext.ket.part.entity.KETPartClassification;
import ext.ket.sales.entity.KETSalesProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETSalesPartClassLink</code> static factory method(s),
 * not the <code>KETSalesPartClassLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="mainYn", type=String.class),
   @GeneratedProperty(name="investCost", type=String.class,
      javaDoc="투자비"),
   @GeneratedProperty(name="planTotal", type=String.class,
      javaDoc="기획총대수"),
   @GeneratedProperty(name="planYear", type=String.class,
      javaDoc="기획년대수"),
   @GeneratedProperty(name="expectSalesTotal", type=String.class,
      javaDoc="예상총매출"),
   @GeneratedProperty(name="expectSalesYear", type=String.class,
      javaDoc="예상년매출")
   },
   roleA=@GeneratedRole(name="projectClass", type=KETSalesProject.class),
   roleB=@GeneratedRole(name="partClass", type=KETPartClassification.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="KETSalesPartClassLink")
)
public class KETSalesPartClassLink extends _KETSalesPartClassLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     projectClass
    * @param     partClass
    * @return    KETSalesPartClassLink
    * @exception wt.util.WTException
    **/
   public static KETSalesPartClassLink newKETSalesPartClassLink( KETSalesProject projectClass, KETPartClassification partClass )
            throws WTException {

      KETSalesPartClassLink instance = new KETSalesPartClassLink();
      instance.initialize( projectClass, partClass );
      return instance;
   }

}
