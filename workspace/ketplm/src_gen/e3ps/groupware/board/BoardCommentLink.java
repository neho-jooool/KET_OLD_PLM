package e3ps.groupware.board;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="BoardComment"),
   roleA=@GeneratedRole(
      name="comment",
      type=e3ps.groupware.board.BoardComment.class
   ),
   roleB=@GeneratedRole(
      name="board",
      type=e3ps.groupware.board.Board.class,
      cardinality=Cardinality.ONE
   )
)

public class BoardCommentLink extends _BoardCommentLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static BoardCommentLink newBoardCommentLink( e3ps.groupware.board.BoardComment comment, e3ps.groupware.board.Board board ) throws wt.util.WTException {
      BoardCommentLink BoardCommentLink_instance = new BoardCommentLink();
      BoardCommentLink_instance.initialize(comment, board);
      return BoardCommentLink_instance;
   }
}
