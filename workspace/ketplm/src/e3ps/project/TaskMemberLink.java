/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.project;

import wt.fc.ObjectToObjectLink;
import wt.org.WTPrincipalReference;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 *
 * <p>
 * Use the <code>newTaskMemberLink</code> static factory method(s), not
 * the <code>TaskMemberLink</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
	   @GeneratedProperty(name="taskMemberType", type=int.class, javaDoc="0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member"),
	   @GeneratedProperty(name="taskRoleName", type=String.class, javaDoc="Role Name"),
	   @GeneratedProperty(name="taskHistory", type=int.class, javaDoc="TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시"),
	   @GeneratedProperty(name="actor", type=WTPrincipalReference.class, constraints=@PropertyConstraints(required=true))   
   },
   roleA=@GeneratedRole(name="task", type=TemplateTask.class),
   roleB=@GeneratedRole(name="member", type=ProjectMemberLink.class),
   tableProperties=@TableProperties(tableName="TaskMemberLink")
)
public class TaskMemberLink extends _TaskMemberLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     task
    * @param     member
    * @return    TaskMemberLink
    * @exception wt.util.WTException
    **/
   public static TaskMemberLink newTaskMemberLink( TemplateTask task, ProjectMemberLink member )
            throws WTException {

      TaskMemberLink instance = new TaskMemberLink();
      instance.initialize( task, member );
      return instance;
   }

}
