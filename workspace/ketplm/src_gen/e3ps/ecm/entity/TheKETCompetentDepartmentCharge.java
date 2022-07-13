package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETCompetentDepartment"),
   roleA=@GeneratedRole(
      name="theKETCompetentDepartment",
      type=e3ps.ecm.entity.KETCompetentDepartment.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="charge",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETCompetentDepartmentCharge extends _TheKETCompetentDepartmentCharge implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETCompetentDepartmentCharge newTheKETCompetentDepartmentCharge( e3ps.ecm.entity.KETCompetentDepartment theKETCompetentDepartment, wt.org.WTUser charge ) throws wt.util.WTException {
      TheKETCompetentDepartmentCharge TheKETCompetentDepartmentCharge_instance = new TheKETCompetentDepartmentCharge();
      TheKETCompetentDepartmentCharge_instance.initialize(theKETCompetentDepartment, charge);
      return TheKETCompetentDepartmentCharge_instance;
   }
}
