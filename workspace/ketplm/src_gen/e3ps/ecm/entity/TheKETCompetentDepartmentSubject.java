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
      name="subject",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class TheKETCompetentDepartmentSubject extends _TheKETCompetentDepartmentSubject implements Externalizable {
   static final long serialVersionUID = 1;
   public static TheKETCompetentDepartmentSubject newTheKETCompetentDepartmentSubject( e3ps.ecm.entity.KETCompetentDepartment theKETCompetentDepartment, e3ps.groupware.company.Department subject ) throws wt.util.WTException {
      TheKETCompetentDepartmentSubject TheKETCompetentDepartmentSubject_instance = new TheKETCompetentDepartmentSubject();
      TheKETCompetentDepartmentSubject_instance.initialize(theKETCompetentDepartment, subject);
      return TheKETCompetentDepartmentSubject_instance;
   }
}
