package ext.ket.edm.stamping;

import javax.inject.Inject;

import org.junit.Test;

import ext.ket.edm.stamping.jms.StampReqSender;
import ext.ket.shared.test.AbstractUnitTest;

public class StampReqSenderTest extends AbstractUnitTest {

    @Inject
    private StampReqSender stampReqSender;

    @Test
    public void testSendQueue() throws Exception {

	// for (int i = 0; i < 100; i++) {
		 stampReqSender.sendMessage("D1409001", "ext.ket.edm.entity.KETDrawingDistRequest:959341407");
		 stampReqSender.sendMessage("D1409002", "ext.ket.edm.entity.KETDrawingDistRequest:959341460");
		 stampReqSender.sendMessage("D1409003", "ext.ket.edm.entity.KETDrawingDistRequest:959341484");
		 stampReqSender.sendMessage("D1409004", "ext.ket.edm.entity.KETDrawingDistRequest:959341653");
		 stampReqSender.sendMessage("D1409005", "ext.ket.edm.entity.KETDrawingDistRequest:959341693");
		 stampReqSender.sendMessage("D1409006", "ext.ket.edm.entity.KETDrawingDistRequest:959341843");
		 stampReqSender.sendMessage("D1409007", "ext.ket.edm.entity.KETDrawingDistRequest:959341882");
	// }
    }
}
