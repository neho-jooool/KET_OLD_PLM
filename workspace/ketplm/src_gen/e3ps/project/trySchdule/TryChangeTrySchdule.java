package e3ps.project.trySchdule;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="TryChange"),
   roleA=@GeneratedRole(
      name="tryChange",
      type=e3ps.project.trySchdule.TryChange.class
   ),
   roleB=@GeneratedRole(
      name="trySchdule",
      type=e3ps.project.trySchdule.TrySchdule.class,
      cardinality=Cardinality.ONE
   )
)

public class TryChangeTrySchdule extends _TryChangeTrySchdule implements Externalizable {
   static final long serialVersionUID = 1;
   public static TryChangeTrySchdule newTryChangeTrySchdule( e3ps.project.trySchdule.TryChange tryChange, e3ps.project.trySchdule.TrySchdule trySchdule ) throws wt.util.WTException {
      TryChangeTrySchdule TryChangeTrySchdule_instance = new TryChangeTrySchdule();
      TryChangeTrySchdule_instance.initialize(tryChange, trySchdule);
      return TryChangeTrySchdule_instance;
   }
}
