package e3ps.test;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldECAEpmDocLink;

public class Test {

	/**
	 * 함수명 : main
	 * 설명 :
	 * @param args
	 * 작성자 : 홍길동
	 * 작성일자 : 2011. 5. 31.
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		RemoteMethodServer.getDefault().setUserName("wcadmin");
		RemoteMethodServer.getDefault().setPassword("wcadmin");

		QuerySpec qs = new QuerySpec();

		int idx = qs.appendClassList(KETMoldChangeActivity.class, true);

		long longECO = 43397219;

		//ClassAttribute ca = new ClassAttribute(KETMoldChangeActivity.class, "moldECOReference.key.id");
		SearchCondition sc = new SearchCondition(KETMoldChangeActivity.class, "moldECOReference.key.id" , "=", longECO);
		qs.appendWhere(sc, new int[]{idx});
		QueryResult qr = PersistenceHelper.manager.find(qs);
		while(qr.hasMoreElements())
		{
			Object[] obj = (Object[])qr.nextElement();
			KETMoldChangeActivity activity = (KETMoldChangeActivity)obj[0];
			//Kogger.debug(getClass(), "KETMoldChangeActivity" + activity);
			long activityLong = activity.getPersistInfo().getObjectIdentifier().getId();

			QuerySpec _qs = new QuerySpec();
			int link = _qs.appendClassList(KETMoldECAEpmDocLink.class, true);
			SearchCondition _sc = new SearchCondition(KETMoldECAEpmDocLink.class, "roleBObjectRef.key.id", "=", activityLong);
			_qs.appendWhere(_sc, new int[]{link});
			QueryResult _qr = PersistenceHelper.manager.find(_qs);
			//Kogger.debug(getClass(), _qs);
			while(_qr.hasMoreElements())
			{
				Object[] _obj = (Object[])_qr.nextElement();
				//Kogger.debug(getClass(), _obj);
			}
		}

	}
}
