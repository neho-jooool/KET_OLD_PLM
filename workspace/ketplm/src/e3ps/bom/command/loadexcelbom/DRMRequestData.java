package e3ps.bom.command.loadexcelbom;

import java.io.Serializable;

public class DRMRequestData implements Serializable {
	public Object object;
	public String fileName;
	public DRMRequestData(Object object, String fileName){
		this.object = object;
		this.fileName = fileName;
	}
}
 
