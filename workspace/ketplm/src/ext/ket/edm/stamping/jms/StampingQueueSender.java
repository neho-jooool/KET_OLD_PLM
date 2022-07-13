package ext.ket.edm.stamping.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import e3ps.common.util.CommonUtil;
import e3ps.edm.util.EDMProperties;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.stamping.service.StampingHelper;
import ext.ket.edm.stamping.util.StampingConstants;
import ext.ket.shared.log.Kogger;

public class StampingQueueSender {

    // Create a ConnectionFactory
    private ActiveMQConnectionFactory connectionFactory = null;

    // Create a Connection
    private Connection connection = null;

    // Create a Session
    private Session session = null;

    // Create the destination (Topic or Queue)
    private Destination destination = null;

    // Create a MessageProducer from the Session to the Topic or Queue
    private MessageProducer producer = null;

    // Wait for a message
    private Message message = null;

    private static volatile StampingQueueSender instance;

    private StampingQueueSender() {
    }

    public static StampingQueueSender getInstance() {
	if (instance == null) {
	    synchronized (StampingQueueSender.class) {
		if (instance == null) {
		    instance = new StampingQueueSender();
		}
	    }
	}

	return instance;
    }

    public void sendMessage(String reqNumber, String reqOid) throws Exception {

	StampParam param = null;

	try {

	    String brokerUrl = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_JMS_BROKERURL); // "epm.stamping.jms.brokerUrl" > "tcp://ketplmcad:61616"
	    String queue = EDMProperties.getInstance().getString(StampingConstants.EPM_STAMPING_JMS_QUEUE_NAME); // "epm.stamping.jms.queue" > "Stamping"

	    connectionFactory = new ActiveMQConnectionFactory(brokerUrl);// "tcp://ketplmcad:61616");

	    // Create a Connection
	    connection = connectionFactory.createConnection();
	    connection.start();

	    // connection.setExceptionListener(this);
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	    destination = session.createQueue(queue); // "Stamping"

	    producer = session.createProducer(destination);

	    Kogger.debug(getClass(), ">>>>> Stamping Test Start !!");
	    Kogger.debug(getClass(), ">>>>>  reqNo :" + reqNumber);
	    Kogger.debug(getClass(), ">>>>> reqOid :" + reqOid);

	    StampParam requestParam = new StampParam();
	    requestParam.setStampReqNumber(reqNumber);
	    requestParam.setStampReqOid(reqOid);

	    ObjectMessage sendMsg = session.createObjectMessage();
	    sendMsg.setObject((Serializable) requestParam);
	    // for(int k=0; k<300; k++){
	    producer.send(sendMsg);
	    // }

	    KETDrawingDistRequest drawingDistReq = (KETDrawingDistRequest) CommonUtil.getObject(reqOid);
	    StampingHelper.service.createKETStampingWhenQueueInput(drawingDistReq);

	    Kogger.debug(getClass(), ">>>>> Stamping Test Complete !!");

	} catch (JMSException e) {
	    
	    Kogger.error(getClass(), e);
	    Kogger.debug(getClass(), "JMSException occurred: " + e.toString());
	    param = null;
	    // closeJms();

	} catch (Exception e) {

	    Kogger.error(getClass(), e);
	    Kogger.debug(getClass(), "Exception occurred: " + e.toString());
	    param = null;
	    // closeJms();

	} finally {
	    closeJms();
	}
    }

    private void closeJms() {
	if (connection != null) {
	    try {
		connection.close();
	    } catch (JMSException e) {
	    }
	}
	if (producer != null) {
	    try {
		producer.close();
	    } catch (JMSException e) {
	    }
	}
	if (session != null) {
	    try {
		session.close();
	    } catch (JMSException e) {
	    }
	}

	connectionFactory = null;
	connection = null;
	session = null;
	destination = null;
	producer = null;
	connection = null;
    }

}
