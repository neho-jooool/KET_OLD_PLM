package ext.ket.edm.stamping.jms;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import ext.ket.shared.log.Kogger;

/**
 * Queue를 쉽게 보내고 받기 위한 Test용 클래스
 * 
 * @클래스명 : CommonParamMessageConverter
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class CommonParamMessageConverter implements MessageConverter {

    /**
     * JMS Message 받을 경우 변환
     */
    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {

	if (!(message instanceof ObjectMessage)) {
	    Kogger.debug(getClass(), "Messaeg isn't a ObjectMessage");
	    throw new MessageConversionException("Messaeg isn't a ObjectMessage");
	} else {
	    Kogger.debug(getClass(), "Messaeg is a ObjectMessage");
	}

	ObjectMessage receiveMsg = (ObjectMessage) message;
	// byte[] requestString = (byte[]) receiveMsg.getObject();
	// Codec codec = CodecFactory.getInstance().getCodec(CODEC_TYPE_LTLV);
	// StampParam responseParam = (StampParam) codec.decode(requestString, new StampParam());

	return receiveMsg;
    }

    /**
     * JMS Message 보낼 경우 변환
     */
    @Override
    public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {

	if (!(obj instanceof StampParam)) {
	    Kogger.debug(getClass(), "Messaeg isn't a StampParam");
	    throw new MessageConversionException("Messaeg isn't a StampParam");
	} else {
	    Kogger.debug(getClass(), "Messaeg is a StampParam");
	}

	StampParam requestParam = (StampParam) obj;
	// Codec codec = CodecFactory.getInstance().getCodec(CODEC_TYPE_LTLV);
	// byte[] requestString = codec.encode(requestParam);
	// Object retObject = requestString;

	// ObjectMessage sendMsg = session.createObjectMessage();
	// sendMsg.setObject((Serializable) retObject);

	ObjectMessage sendMsg = session.createObjectMessage();
	sendMsg.setObject((Serializable) requestParam);

	return sendMsg;
    }
}
