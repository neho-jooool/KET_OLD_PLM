/* bcwti¡ì¡í¡ì¡íCopyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.¡ì¡í¡ì¡íThis software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.¡ì¡í¡ì¡íecwti
 */

package e3ps.groupware.board;

import e3ps.groupware.board.HelpBoard;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newManualBoard</code> static factory method(s), not the
 * <code>ManualBoard</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=HelpBoard.class,
   properties={
   @GeneratedProperty(name="classification", type=String.class,
      constraints=@PropertyConstraints(upperLimit=10)),
   @GeneratedProperty(name="url", type=String.class,
      constraints=@PropertyConstraints(upperLimit=1000)),
   @GeneratedProperty(name="classification2", type=String.class,
      javaDoc="ºÐ·ù")
   })
public class ManualBoard extends _ManualBoard {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    ManualBoard
    * @exception wt.util.WTException
    **/
   public static ManualBoard newManualBoard()
            throws WTException {

      ManualBoard instance = new ManualBoard();
      instance.initialize();
      return instance;
   }

}
