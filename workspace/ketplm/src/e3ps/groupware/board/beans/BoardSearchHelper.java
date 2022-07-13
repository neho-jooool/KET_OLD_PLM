package e3ps.groupware.board.beans;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.groupware.board.BoardComment;
import ext.ket.shared.log.Kogger;

public class BoardSearchHelper {
	public static BoardSearchHelper manager = new BoardSearchHelper();
	
	public QueryResult commentSeach(String oid){
		QueryResult qr = null;
		try{
			oid = oid.substring ( oid.lastIndexOf ( ":" ) + 1 );
			QuerySpec spec = new QuerySpec();
		    int idx = spec.addClassList(BoardComment.class, true);
		    spec.appendWhere(new SearchCondition(BoardComment.class , "boardReference.key.id" , "=" ,Long.parseLong ( oid ) ), new int[idx]);	
		    spec.appendOrderBy(new OrderBy(new ClassAttribute(BoardComment.class,"thePersistInfo.createStamp"), true), new int[] { idx });    
		    qr = PersistenceHelper.manager.find(spec);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return qr;
	}
}
