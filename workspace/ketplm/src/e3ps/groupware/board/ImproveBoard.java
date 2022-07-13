/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package e3ps.groupware.board;

import e3ps.common.impl.OwnPersistable;
import e3ps.common.impl.Tree;
import e3ps.groupware.board.HelpBoard;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.content.ContentHolder;
import wt.fc.ObjectReference;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newImproveBoard</code> static factory method(s), not the
 * <code>ImproveBoard</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=HelpBoard.class, interfaces={Tree.class, OwnPersistable.class, ContentHolder.class},
   properties={
   @GeneratedProperty(name="classification", type=String.class,
      constraints=@PropertyConstraints(upperLimit=10)),
   @GeneratedProperty(name="state", type=String.class,
      constraints=@PropertyConstraints(upperLimit=10)),
   @GeneratedProperty(name="division", type=String.class),
   @GeneratedProperty(name="handleResult", type=String.class,
      javaDoc="처리결과",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="cause", type=String.class,
      javaDoc="원인",
      constraints=@PropertyConstraints(upperLimit=4000))
   },
   foreignKeys={
   @GeneratedForeignKey(name="manager", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="user", type=wt.org.WTUser.class),
      myRole=@MyRole(name="improveboard", cardinality=Cardinality.ZERO_TO_ONE))
   })
public class ImproveBoard extends _ImproveBoard {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ImproveBoard
    * @exception wt.util.WTException
    **/
   public static ImproveBoard newImproveBoard()
            throws WTException {

      ImproveBoard instance = new ImproveBoard();
      instance.initialize();
      return instance;
   }

}
