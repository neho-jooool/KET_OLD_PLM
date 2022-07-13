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
      name="subject",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETChangeRequestExpansionSubject extends _TheKETChangeRequestExpansionSubject implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETChangeRequestExpansionSubject newTheKETChangeRequestExpansionSubject( e3ps.ecm.entity.KETChangeRequestExpansion theKETChangeRequestExpansion, e3ps.groupware.company.Department subject ) throws wt.util.WTException {
      TheKETChangeRequestExpansionSubject TheKETChangeRequestExpansionSubject_instance = new TheKETChangeRequestExpansionSubject();
      TheKETChangeRequestExpansionSubject_instance.initialize(theKETChangeRequestExpansion, subject);
      return TheKETChangeRequestExpansionSubject_instance;
   }
}
