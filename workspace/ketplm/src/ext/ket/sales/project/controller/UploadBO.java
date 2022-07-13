package ext.ket.sales.project.controller;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class UploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile primaryFile;
    private String documentName;

    public UploadBO(MultipartFile primaryFile, String documentName) {
	this.primaryFile = primaryFile;
	this.documentName = documentName;
    }

    public UploadBO() {
	System.out.println(documentName);
    }

    public MultipartFile getPrimaryFile() {
	return primaryFile;
    }

    public void setPrimaryFile(MultipartFile primaryFile) {
	this.primaryFile = primaryFile;
    }

    public String getDocumentName() {
	return documentName;
    }

    public void setDocumentName(String documentName) {
	this.documentName = documentName;
    }

}
