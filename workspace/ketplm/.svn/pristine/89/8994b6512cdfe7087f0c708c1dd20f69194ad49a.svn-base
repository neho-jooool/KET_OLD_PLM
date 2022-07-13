package ext.ket.edm.stamping.oxm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ext.ket.part.classify.oxm.ClazXmlGridObject;
import ext.ket.shared.log.Kogger;

public class OxmMarshaller {

    public void convertDrawingDeployRequest(String filePath, DrawingDeployRequest drawingDeployRequest) throws Exception {

	try {

	    File file = new File(filePath);
//	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
	    JAXBContext jaxbContext = JAXBContext.newInstance(DrawingDeployRequest.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    // output pretty printed
	    jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.setProperty(com.sun.xml.bind.marshaller.CharacterEscapeHandler.class.getName(), new com.sun.xml.bind.marshaller.CharacterEscapeHandler() {
		public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
		    writer.write(ac, i, j);
		}
	    });

	    jaxbMarshaller.marshal(drawingDeployRequest, writer);
	    jaxbMarshaller.marshal(drawingDeployRequest, System.out);
	    writer.close();
	    

	} catch (JAXBException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}
    }

    public String convertClassification(ClazXmlGridObject clazXmlGridObject) throws Exception {

	try {

	    java.io.StringWriter writer = new StringWriter();
	    JAXBContext jaxbContext = JAXBContext.newInstance(ClazXmlGridObject.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	    // output pretty printed
	    jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    
	    // jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    // jaxbMarshaller.setProperty(com.sun.xml.bind.marshaller.CharacterEscapeHandler.class.getName(), new com.sun.xml.bind.marshaller.CharacterEscapeHandler() {
	    // public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
	    // writer.write(ac, i, j);
	    // }
	    // });

	    jaxbMarshaller.marshal(clazXmlGridObject, writer);
	    jaxbMarshaller.marshal(clazXmlGridObject, System.out);
	    String result = writer.toString();

//	    // TKLEE TESTING
//	    String filePath = "D:\\ket\\workspace\\ketplm\\codebase\\extcore\\jsp\\part\\classify\\listClazDataXml.xml";
//	    File file = new File(filePath);
//	    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
//	    jaxbMarshaller = jaxbContext.createMarshaller();
//	    jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//	    jaxbMarshaller.setProperty(com.sun.xml.bind.marshaller.CharacterEscapeHandler.class.getName(), new com.sun.xml.bind.marshaller.CharacterEscapeHandler() {
//		public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
//		    writer.write(ac, i, j);
//		}
//	    });
//
//	    jaxbMarshaller.marshal(clazXmlGridObject, fileWriter);
//	    jaxbMarshaller.marshal(clazXmlGridObject, System.out);

	    return result;

	} catch (JAXBException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}

    }

    public static void main(String[] args) throws Exception {

	try {

	    String orgfilePath = "\\\\192.168.1.112\\e$\\stamping\\download\\2014\\12\\04\\TAG100\\1\\TAG100.xml";
	    OxmUnmarshaller oxmUnmarshaller = new OxmUnmarshaller();
	    DrawingDeployRequest drawingDeployRequest = oxmUnmarshaller.getDrawingDeployRequest(orgfilePath);
	    RequestDrawingList list = drawingDeployRequest.getRequestDrawingList();
	    List<DeployDrawing> getDrawingList = list.getDrawingList();
	    DeployDrawing dep = getDrawingList.get(0);
	    dep.setStampType("4M출도(승인)");
	    
	    // String filePath = "D:\\ket\\workspace\\ketplm\\src\\ext\\ket\\edm\\stamping\\xml\\DrawingRequest3.xml";
	    String filePath = "\\\\192.168.1.112\\e$\\stamping\\download\\2014\\12\\04\\TAG100\\1\\TAG100__.xml";
	    OxmMarshaller oxmMarshaller = new OxmMarshaller();
	    oxmMarshaller.convertDrawingDeployRequest(filePath, drawingDeployRequest);

	    // File file = new File(filePath);
	    // BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    // JAXBContext jaxbContext = JAXBContext.newInstance(DrawingDeployRequest.class);
	    // Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    //
	    // // output pretty printed
	    // jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    // jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    // jaxbMarshaller.setProperty(com.sun.xml.bind.marshaller.CharacterEscapeHandler.class.getName(), new com.sun.xml.bind.marshaller.CharacterEscapeHandler() {
	    // public void escape(char[] ac, int i, int j, boolean flag, Writer writer) throws IOException {
	    // writer.write(ac, i, j);
	    // }
	    // });
	    //
	    // jaxbMarshaller.marshal(drawingDeployRequest, writer);
	    // jaxbMarshaller.marshal(drawingDeployRequest, System.out);

	} catch (JAXBException e) {
	    Kogger.error(OxmUnmarshaller.class, e);
	    throw e;
	}

    }
}
