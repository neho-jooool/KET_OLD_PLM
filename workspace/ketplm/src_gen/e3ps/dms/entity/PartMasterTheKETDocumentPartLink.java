package e3ps.dms.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDocumentPartLink"),
   roleA=@GeneratedRole(
      name="partMaster",
      type=wt.part.WTPartMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETDocumentPartLink",
      type=e3ps.dms.entity.KETDocumentPartLink.class,
      cardinality=Cardinality.ONE
   )
)

public class PartMasterTheKETDocumentPartLink extends _PartMasterTheKETDocumentPartLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartMasterTheKETDocumentPartLink newPartMasterTheKETDocumentPartLink( wt.part.WTPartMaster partMaster, e3ps.dms.entity.KETDocumentPartLink theKETDocumentPartLink ) throws wt.util.WTException {
      PartMasterTheKETDocumentPartLink PartMasterTheKETDocumentPartLink_instance = new PartMasterTheKETDocumentPartLink();
      PartMasterTheKETDocumentPartLink_instance.initialize(partMaster, theKETDocumentPartLink);
      return PartMasterTheKETDocumentPartLink_instance;
   }
}
