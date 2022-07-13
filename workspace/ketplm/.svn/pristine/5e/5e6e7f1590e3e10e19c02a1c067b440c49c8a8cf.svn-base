package e3ps.project.historyprocess;

import java.lang.reflect.Method;

import wt.introspection.WTIntrospector;
import wt.util.WTException;
import wt.util.WTStringUtilities;
import e3ps.project.ProjectMaster;
import e3ps.project.TemplateProject;

public class MasterUtil {
	
	public static ProjectMaster newMasterFor(TemplateProject project)throws Exception{
		 
		 Method method = null;
		 
		  Class class1 = WTIntrospector.getClassInfo(project.getClass()).getOtherSideRole("master").getValidClass();
	      try
	      {
	          method = class1.getMethod("new" + WTStringUtilities.tail(class1.getName(), '.'), null);
	      }
	      catch(NoSuchMethodException nosuchmethodexception)
	      {
	          throw new WTException(nosuchmethodexception);
	      }
	      catch(SecurityException securityexception)
	      {
	          throw new WTException(securityexception);
	      }
	      
	      return (ProjectMaster)method.invoke(null, null);
	       	
	}
//	public static ProcessControlMaster newMasterForProcess(ProcessControl process)throws Exception{
//		 
//		 Method method = null;
//		 
//		  Class class1 = WTIntrospector.getClassInfo(process.getClass()).getOtherSideRole("master").getValidClass();
//	      try
//	      {
//	          method = class1.getMethod("new" + WTStringUtilities.tail(class1.getName(), '.'), null);
//	      }
//	      catch(NoSuchMethodException nosuchmethodexception)
//	      {
//	          throw new WTException(nosuchmethodexception);
//	      }
//	      catch(SecurityException securityexception)
//	      {
//	          throw new WTException(securityexception);
//	      }
//	      
//	      return (ProcessControlMaster)method.invoke(null, null);
//	       	
//	}
	
}
