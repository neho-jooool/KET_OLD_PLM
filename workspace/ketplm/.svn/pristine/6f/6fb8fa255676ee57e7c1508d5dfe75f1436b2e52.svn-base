package ext.ket.part.migration;

import java.sql.Timestamp;
import java.util.Date;

import wt.httpgw.GatewayAuthenticator;
import wt.method.RemoteMethodServer;
import ext.ket.edm.migration.service.KetMigPartHelper;

public class PLMUpdatePartForMig {

    public static void main(String[] args) throws Exception {
	RemoteMethodServer rms = RemoteMethodServer.getDefault();
	GatewayAuthenticator auth = new GatewayAuthenticator();
	auth.setRemoteUser("wcadmin");
	rms.setAuthenticator(auth);

	final int EXECUTE_IDX = 20;

	Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());

	System.out.println("## MIG start " + new Date());

	for (int idx = 0; idx < EXECUTE_IDX; idx++) {
	    boolean result = KetMigPartHelper.service.updateMigPart();
	    if (!result) {
		break;
	    }
	}

	// KetMigPartHelper.service.sendHpMig();

	Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());

	System.out.println("## MIG end " + new Date());
	System.out.println("## MIG elapsed " + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 / 60) + ":"
	        + ((endTimestamp.getTime() - beginTimestamp.getTime()) / 1000 % 60));

	System.exit(0);
    }
}
