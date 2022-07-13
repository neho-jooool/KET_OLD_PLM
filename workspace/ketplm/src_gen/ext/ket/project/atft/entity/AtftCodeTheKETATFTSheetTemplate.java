package ext.ket.project.atft.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETATFTSheetTemplate"),
   roleA=@GeneratedRole(
      name="atftCode",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETATFTSheetTemplate",
      type=ext.ket.project.atft.entity.KETATFTSheetTemplate.class,
      cardinality=Cardinality.ONE
   )
)

public class AtftCodeTheKETATFTSheetTemplate extends _AtftCodeTheKETATFTSheetTemplate implements Externalizable {
   static final long serialVersionUID = 1;
   public static AtftCodeTheKETATFTSheetTemplate newAtftCodeTheKETATFTSheetTemplate( e3ps.common.code.NumberCode atftCode, ext.ket.project.atft.entity.KETATFTSheetTemplate theKETATFTSheetTemplate ) throws wt.util.WTException {
      AtftCodeTheKETATFTSheetTemplate AtftCodeTheKETATFTSheetTemplate_instance = new AtftCodeTheKETATFTSheetTemplate();
      AtftCodeTheKETATFTSheetTemplate_instance.initialize(atftCode, theKETATFTSheetTemplate);
      return AtftCodeTheKETATFTSheetTemplate_instance;
   }
}
