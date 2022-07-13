package ext.ket.edm.stamping.jms;

import org.springframework.jms.core.JmsTemplate;

import e3ps.common.util.CommonUtil;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.stamping.service.StampingHelper;
import ext.ket.shared.log.Kogger;

/**
 * Queue를 쉽게 보내기 위한 Test용 클래스 실제 클래스는 StampingQueueSender
 * 
 * @클래스명 : StampReqSenderService
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StampReqSenderService implements StampReqSender {

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
	this.jmsTemplate = jmsTemplate;
    }

    @Override
    public boolean sendMessage(String reqNo, String reqOid) {

	try {

	    Kogger.debug(getClass(), ">>>>> Stamping Test Start !!");
	    Kogger.debug(getClass(), ">>>>>  reqNo :" + reqNo);
	    Kogger.debug(getClass(), ">>>>> reqOid :" + reqOid);

	    StampParam requestParam = new StampParam();
	    requestParam.setStampReqNumber(reqNo);
	    requestParam.setStampReqOid(reqOid);

	    jmsTemplate.convertAndSend(requestParam);

	    // requestParam.setStampReqId("REQID");
	    // requestParam.setStampReqOid("REQOID:22");

	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	    StampingHelper.service.createKETStampingWhenQueueInput(drawingDistReq);

	    Kogger.debug(getClass(), ">>>>> Stamping Test Complete !!");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return false;
	}

	return true;
    }
}
