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

import e3ps.project.E3PSProject;

import java.lang.String;

import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newWorkProject</code> static factory method(s), not the
 * <code>WorkProject</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=E3PSProject.class,
//   properties={
//    @GeneratedProperty(name="workType", type=String.class)
//   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="devDept", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theWorkProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="workType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theWorkProject", cardinality=Cardinality.ONE))
   })
public class WorkProject extends _WorkProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    WorkProject
    * @exception wt.util.WTException
    **/
   public static WorkProject newWorkProject()
            throws WTException {

      WorkProject instance = new WorkProject();
      instance.initialize();
      return instance;
   }

}
