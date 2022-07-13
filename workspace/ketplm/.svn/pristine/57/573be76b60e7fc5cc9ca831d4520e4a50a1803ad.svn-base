/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.project.atft.entity;

import e3ps.project.E3PSProject;
import ext.ket.project.atft.entity.KETATFTMainSheet;
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
 * Use the <code>newKETProjectATFTSheetLink</code> static factory method(s),
 * not the <code>KETProjectATFTSheetLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   roleA=@GeneratedRole(name="atft", type=KETATFTMainSheet.class,
      cardinality=Cardinality.ZERO_TO_ONE),
   roleB=@GeneratedRole(name="project", type=E3PSProject.class,
      cardinality=Cardinality.ONE),
   tableProperties=@TableProperties(tableName="KETProjectATFTSheetLink")
)
public class KETProjectATFTSheetLink extends _KETProjectATFTSheetLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     atft
    * @param     project
    * @return    KETProjectATFTSheetLink
    * @exception wt.util.WTException
    **/
   public static KETProjectATFTSheetLink newKETProjectATFTSheetLink( KETATFTMainSheet atft, E3PSProject project )
            throws WTException {

      KETProjectATFTSheetLink instance = new KETProjectATFTSheetLink();
      instance.initialize( atft, project );
      return instance;
   }

}
