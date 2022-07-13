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

package e3ps.common.impl;

import java.lang.String;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * @version   1.0
 **/

@GenAsPersistable(
   foreignKeys={
   @GeneratedForeignKey(name="ParentChildLink",
      foreignKeyRole=@ForeignKeyRole(name="parent", type=e3ps.common.impl.Tree.class,
         constraints=@PropertyConstraints(required=true),
         columnProperties=@ColumnProperties(index=true)),
      myRole=@MyRole(name="child"))
   },
   tableProperties=@TableProperties(compositeIndex1="parentReference.key.classname+parentReference.key.id")
)
public interface Tree extends _Tree {






}
