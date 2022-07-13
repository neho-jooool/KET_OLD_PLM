package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="ProjectChangeStop"),
   roleA=@GeneratedRole(
      name="pcs",
      type=e3ps.project.ProjectChangeStop.class
   ),
   roleB=@GeneratedRole(
      name="pcsMaster",
      type=e3ps.project.ProjectMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class PcsPcsMaster extends _PcsPcsMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static PcsPcsMaster newPcsPcsMaster( e3ps.project.ProjectChangeStop pcs, e3ps.project.ProjectMaster pcsMaster ) throws wt.util.WTException {
      PcsPcsMaster PcsPcsMaster_instance = new PcsPcsMaster();
      PcsPcsMaster_instance.initialize(pcs, pcsMaster);
      return PcsPcsMaster_instance;
   }
}
