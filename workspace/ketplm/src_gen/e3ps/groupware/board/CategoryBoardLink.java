package e3ps.groupware.board;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="Board"),
   roleA=@GeneratedRole(
      name="board",
      type=e3ps.groupware.board.Board.class
   ),
   roleB=@GeneratedRole(
      name="category",
      type=e3ps.groupware.board.BoardCategory.class,
      cardinality=Cardinality.ONE
   )
)

public class CategoryBoardLink extends _CategoryBoardLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static CategoryBoardLink newCategoryBoardLink( e3ps.groupware.board.Board board, e3ps.groupware.board.BoardCategory category ) throws wt.util.WTException {
      CategoryBoardLink CategoryBoardLink_instance = new CategoryBoardLink();
      CategoryBoardLink_instance.initialize(board, category);
      return CategoryBoardLink_instance;
   }
}
