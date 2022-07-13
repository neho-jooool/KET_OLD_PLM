package ext.ket.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPartMassPlan"),
   roleA=@GeneratedRole(
      name="partMaster",
      type=wt.part.WTPartMaster.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPartMassPlan",
      type=ext.ket.part.entity.KETPartMassPlan.class,
      cardinality=Cardinality.ONE
   )
)

public class PartMasterTheKETPartMassPlan extends _PartMasterTheKETPartMassPlan implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartMasterTheKETPartMassPlan newPartMasterTheKETPartMassPlan( wt.part.WTPartMaster partMaster, ext.ket.part.entity.KETPartMassPlan theKETPartMassPlan ) throws wt.util.WTException {
      PartMasterTheKETPartMassPlan PartMasterTheKETPartMassPlan_instance = new PartMasterTheKETPartMassPlan();
      PartMasterTheKETPartMassPlan_instance.initialize(partMaster, theKETPartMassPlan);
      return PartMasterTheKETPartMassPlan_instance;
   }
}
