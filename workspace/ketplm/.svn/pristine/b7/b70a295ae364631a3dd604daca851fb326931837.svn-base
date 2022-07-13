/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.project.atft.entity;

import ext.ket.project.atft.entity.KETATFTMainSheet;
import ext.ket.project.atft.entity.KETATFTSheetTemplate;
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
 * Use the <code>newKETATFTSheetLink</code> static factory method(s), not
 * the <code>KETATFTSheetLink</code> constructor, to construct instances
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
   @GeneratedProperty(name="basis", type=String.class,
      javaDoc="판정근거",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="desision", type=String.class,
      javaDoc="상태(OK or NG)")
   },
   roleA=@GeneratedRole(name="mainSheet", type=KETATFTMainSheet.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="sheetTemplate", type=KETATFTSheetTemplate.class),
   tableProperties=@TableProperties(tableName="KETATFTSheetLink")
)
public class KETATFTSheetLink extends _KETATFTSheetLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     mainSheet
    * @param     sheetTemplate
    * @return    KETATFTSheetLink
    * @exception wt.util.WTException
    **/
   public static KETATFTSheetLink newKETATFTSheetLink( KETATFTMainSheet mainSheet, KETATFTSheetTemplate sheetTemplate )
            throws WTException {

      KETATFTSheetLink instance = new KETATFTSheetLink();
      instance.initialize( mainSheet, sheetTemplate );
      return instance;
   }

}
