package ext.ket.bom.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import wt.method.RemoteInterface;
import wt.util.WTException;
import e3ps.common.content.uploader.FileUploader;

@RemoteInterface
public interface KETBom2Service {

    public Vector searchItem(Hashtable searchAttrHash) throws WTException;

    public String savePart(ArrayList arr) throws WTException;

    public Map<String, Object> excelUploadPart(FileUploader uploader, Hashtable params) throws WTException;

}