package ext.ket.edm.stamping.oxm;

import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ext.ket.part.classify.oxm.ClazXmlGridObject;
import ext.ket.shared.log.Kogger;

public class OxmUnmarshaller {

    public DrawingDeployRequest getDrawingDeployRequest(String filePath) throws Exception {

	DrawingDeployRequest drawingDeployRequest = null;
	try {

	    File file = new File(filePath);
	    JAXBContext jaxbContext = JAXBContext.newInstance(DrawingDeployRequest.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    drawingDeployRequest = (DrawingDeployRequest) jaxbUnmarshaller.unmarshal(file);
	    Kogger.debug(getClass(), drawingDeployRequest);

	} catch (JAXBException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}

	return drawingDeployRequest;
    }

    public ClazXmlGridObject getClazXmlGridObject(String xmlData) throws Exception {

	ClazXmlGridObject clazXmlGridObject = null;
	try {

	    JAXBContext jaxbContext = JAXBContext.newInstance(ClazXmlGridObject.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    StringReader reader = new StringReader(xmlData);
	    clazXmlGridObject = (ClazXmlGridObject) jaxbUnmarshaller.unmarshal(reader);
	    Kogger.debug(getClass(), clazXmlGridObject);

	} catch (JAXBException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}

	return clazXmlGridObject;
    }

    public static void main(String[] args) {

	try {

	    String filePath = "D:\\ket\\workspace\\ketplm\\src\\ext\\ket\\edm\\stamping\\xml\\DrawingRequest.xml";
	    File file = new File(filePath);
	    JAXBContext jaxbContext = JAXBContext.newInstance(DrawingDeployRequest.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    DrawingDeployRequest drawingDeployRequest = (DrawingDeployRequest) jaxbUnmarshaller.unmarshal(file);
	    Kogger.debug(OxmUnmarshaller.class, drawingDeployRequest);

	} catch (JAXBException e) {
	    Kogger.error(OxmUnmarshaller.class, e);
	}

    }
}
