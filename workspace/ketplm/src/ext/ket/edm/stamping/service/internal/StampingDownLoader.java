package ext.ket.edm.stamping.service.internal;

import java.util.List;

import wt.epm.EPMDocument;
import e3ps.common.util.CommonUtil;
import ext.ket.edm.stamping.util.CadFileDownUtil;
import ext.ket.edm.stamping.util.ModelStrucUtil;

public class StampingDownLoader {

    // Drawing 2D Download
    public void downloadDrawing(EPMDocument epm2d, String filePath) throws Exception {
	CadFileDownUtil cadFileDownUtil = new CadFileDownUtil();
	cadFileDownUtil.execute2DFileInfo(epm2d, filePath);
    }

    public void downloadModel(String cad3Oid, String filePath) throws Exception {
	downloadModel((EPMDocument) CommonUtil.getObject(cad3Oid), filePath);
    }

    // Model 3D Download
    public void downloadModel(EPMDocument epm3d, String filePath) throws Exception {

	ModelStrucUtil modelStrucUtil = new ModelStrucUtil();
	List<EPMDocument> epmDocList = modelStrucUtil.getModel(epm3d);
	CadFileDownUtil cadFileDownUtil = new CadFileDownUtil();

	for (EPMDocument epmDoc : epmDocList) {
	    cadFileDownUtil.execute3DFileInfo(epmDoc, filePath);
	}
    }
}
