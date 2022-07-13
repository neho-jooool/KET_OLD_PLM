package ext.ket.issue.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETIssueMaster"),
   roleA=@GeneratedRole(
      name="oemType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theKETIssueMaster",
      type=ext.ket.issue.entity.KETIssueMaster.class,
      cardinality=Cardinality.ONE
   )
)

public class OemTypeTheKETIssueMaster extends _OemTypeTheKETIssueMaster implements Externalizable {
   static final long serialVersionUID = 1;
   public static OemTypeTheKETIssueMaster newOemTypeTheKETIssueMaster( e3ps.project.outputtype.OEMProjectType oemType, ext.ket.issue.entity.KETIssueMaster theKETIssueMaster ) throws wt.util.WTException {
      OemTypeTheKETIssueMaster OemTypeTheKETIssueMaster_instance = new OemTypeTheKETIssueMaster();
      OemTypeTheKETIssueMaster_instance.initialize(oemType, theKETIssueMaster);
      return OemTypeTheKETIssueMaster_instance;
   }
}
