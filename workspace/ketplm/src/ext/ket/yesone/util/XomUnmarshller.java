package ext.ket.yesone.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ext.ket.yesone.xom.YesoneOxm;

public class XomUnmarshller {

    public YesoneOxm getYesoneOxmRoot(String filePath) throws JAXBException {

	YesoneOxm yesoneOxm = null;
	try {

	    File file = new File(filePath);
	    JAXBContext jaxbContext = JAXBContext.newInstance(YesoneOxm.class);

	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    yesoneOxm = (YesoneOxm) jaxbUnmarshaller.unmarshal(file);
	    

	} catch (JAXBException e) {
	    e.printStackTrace();
	    throw e;
	}

	return yesoneOxm;
    }
}
