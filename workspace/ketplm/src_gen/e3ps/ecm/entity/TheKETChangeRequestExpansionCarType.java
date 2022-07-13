package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETChangeRequestExpansion"),
   roleA=@GeneratedRole(
      name="theKETChangeRequestExpansion",
      type=e3ps.ecm.entity.KETChangeRequestExpansion.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="carType",
      type=e3ps.project.outputtype.OEMProjectType.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionCarType extends _TheKETChangeRequestExpansionCarType implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionCarType newTheKETChangeRequestExpansionCarType( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, e3ps.project.outputtype.OEMProjectType carType ) throws wt.util.WTException {
      TheKETChangeRequestExpansionCarType TheKETChangeRequestExpansionCarType_instance = new TheKETChangeRequestExpansionCarType();
      TheKETChangeRequestExpansionCarType_instance.initialize(theKETChangeRequestExpansion, carType);
      return TheKETChangeRequestExpansionCarType_instance;
   }
}
