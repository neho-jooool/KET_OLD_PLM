package ext.ket.edm.stamping.jms;

import javax.inject.Inject;

import org.springframework.jms.core.JmsTemplate;

import ext.ket.shared.log.Kogger;

/**
 * Queue를 쉽게 받기 위한 Test용 클래스
 * 
 * @클래스명 : StampReqSenderService
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StampReceiverService implements StampReceiver {

    @Inject
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
	this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void receiveMessage(StampParam param) {

	Kogger.debug(getClass(), "############################################################");
	Kogger.debug(getClass(), "#########                                        ###########");
	Kogger.debug(getClass(), "#########  Api - Receive Adaptor Start!!         ###########");
	Kogger.debug(getClass(), "#########                                        ###########");
	Kogger.debug(getClass(), "############################################################");

	Kogger.debug(getClass(), "responseParam:" + param.getStampReqNumber());
	Kogger.debug(getClass(), "responseParam:" + param.getStampReqOid());

    }
}
