package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETTryPress"),
   roleA=@GeneratedRole(
      name="punchingOil",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETTryPress",
      type=ext.ket.project.trycondition.entity.KETTryPress.class,
      cardinality=Cardinality.ONE
   )
)

public class PunchingOilLink extends _PunchingOilLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static PunchingOilLink newPunchingOilLink( e3ps.common.code.NumberCode punchingOil, ext.ket.project.trycondition.entity.KETTryPress theKETTryPress ) throws wt.util.WTException {
      PunchingOilLink PunchingOilLink_instance = new PunchingOilLink();
      PunchingOilLink_instance.initialize(punchingOil, theKETTryPress);
      return PunchingOilLink_instance;
   }
}
