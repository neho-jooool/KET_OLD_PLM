package e3ps.groupware.company;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="People"),
   roleA=@GeneratedRole(
      name="people",
      type=e3ps.groupware.company.People.class
   ),
   roleB=@GeneratedRole(
      name="department",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class DepartmentPeopleLink extends _DepartmentPeopleLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static DepartmentPeopleLink newDepartmentPeopleLink( e3ps.groupware.company.People people, e3ps.groupware.company.Department department ) throws wt.util.WTException {
      DepartmentPeopleLink DepartmentPeopleLink_instance = new DepartmentPeopleLink();
      DepartmentPeopleLink_instance.initialize(people, department);
      return DepartmentPeopleLink_instance;
   }
}
