package e3ps.project.trySchdule;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="TrySchdule"),
   roleA=@GeneratedRole(
      name="property",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theTrySchdule",
      type=e3ps.project.trySchdule.TrySchdule.class,
      cardinality=Cardinality.ONE
   )
)

public class PropertyTheTrySchdule extends _PropertyTheTrySchdule implements Externalizable {
   static final long serialVersionUID = 1;
   public static PropertyTheTrySchdule newPropertyTheTrySchdule( e3ps.common.code.NumberCode property, e3ps.project.trySchdule.TrySchdule theTrySchdule ) throws wt.util.WTException {
      PropertyTheTrySchdule PropertyTheTrySchdule_instance = new PropertyTheTrySchdule();
      PropertyTheTrySchdule_instance.initialize(property, theTrySchdule);
      return PropertyTheTrySchdule_instance;
   }
}
