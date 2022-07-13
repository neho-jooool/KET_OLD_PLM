package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="department",
      type=e3ps.groupware.company.Department.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class DepartmentTheLessonLearn extends _DepartmentTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static DepartmentTheLessonLearn newDepartmentTheLessonLearn( e3ps.groupware.company.Department department, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      DepartmentTheLessonLearn DepartmentTheLessonLearn_instance = new DepartmentTheLessonLearn();
      DepartmentTheLessonLearn_instance.initialize(department, theLessonLearn);
      return DepartmentTheLessonLearn_instance;
   }
}
