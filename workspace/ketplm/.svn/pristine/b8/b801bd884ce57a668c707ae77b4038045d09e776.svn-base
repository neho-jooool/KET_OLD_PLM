package e3ps.bom.command.trans;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationMenuBar;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import ext.ket.shared.log.Kogger;

public class TransCmd extends AbstractAIFCommand {
    private static Registry  messageRegistry = Registry.getRegistry("e3ps.bom.message");
    JFrame                   parent;
    AbstractAIFUIApplication app;

    public TransCmd(JFrame frame, AbstractAIFUIApplication app) {
	this.app = app;
	parent = app.getDesktop();
    }

    protected void executeCommand() throws Exception {
	try {
	    String itemCode = BOMBasicInfoPool.getPublicModelName();
	    String transferFlag = BOMBasicInfoPool.getPublicTransFlag();

	    boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

	    if (itemCode.equals("Empty")) {
		MessageBox messagebox = new MessageBox(parent, messageRegistry.getString("openBOMWorkspace"), "Warning", MessageBox.WARNING);
		messagebox.setModal(true);
		messagebox.setVisible(true);
		return;
	    }

	    if (transferFlag.equals("1")) {
		MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00079")/*결재요청하지 않은 BOM작업입니다.*/, "Warning", MessageBox.WARNING);
		messagebox.setModal(true);
		messagebox.setVisible(true);
		return;
	    }
	    else if (transferFlag.equals("4")) {
		MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00071")/*결재가 완료된 BOM작업입니다.*/, "Warning", MessageBox.WARNING);
		messagebox.setModal(true);
		messagebox.setVisible(true);
		return;
	    }
	    else {
		boolean reSult = false;

		String bomOid = "";
		KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
		if (bomGubunFlag) {
		    bomOid = KETObjectUtil.getIda2a2(bean.getBOMHeader(itemCode));                                    // 신규는 BOM 헤더의 ida2a2를 넘긴다
		}
		else {
		    bomOid = KETObjectUtil.getIda2a2(EcmSearchHelper.manager.getEcoObjectOid(BOMBasicInfoPool.getBomEcoNumber()));    // 변경은 ECO 객체의 ida2a2를 넘긴다
		}

		if (transferFlag.equals("2")) {
		    MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00041")/*ERP 재전송을 시도합니다.*/, "Transfer", MessageBox.WARNING);
		    messagebox.setModal(true);
		    messagebox.setVisible(true);

		    if (bomGubunFlag) {        // 신규 BOM

			if (KETBomHelper.service.getBomInterface(bomOid)) {
			    reSult = true;
			}
		    }
		    else {                        // 변경 BOM

			if (KETBomHelper.service.getBomEcoInterface(bomOid)) {
			    reSult = true;
			}
		    }
		}
		else if (transferFlag.equals("3")) {
		    MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00048")/*PLM 재등록을 시도합니다.*/, "Transfer", MessageBox.WARNING);
		    messagebox.setModal(true);
		    messagebox.setVisible(true);

		    if (bomGubunFlag) {        // 신규 BOM

			if (KETBomHelper.service.setTransKetPartUsageLink(bomOid)) {
			    reSult = true;
			}
		    }
		    else {                        // 변경 BOM

			if (KETBomHelper.service.setTransKetPartUsageLinkEco(bomOid)) {
			    reSult = true;
			}
		    }
		}
		else {
		    MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00314")/*전송 Flag에 값이 없거나, 잘못된 값이 입력되어있습니다 \n 관리자에게 문의해주십시요.*/,
			    "Warning", MessageBox.WARNING);
		    messagebox.setModal(true);
		    messagebox.setVisible(true);
		    return;
		}

		if (reSult) {
		    MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00302")/*재전송 작업이 성공하였습니다.*/, "Warning", MessageBox.WARNING);
		    messagebox.setModal(true);
		    messagebox.setVisible(true);

		    BOMRegisterApplicationMenuBar menubar = (BOMRegisterApplicationMenuBar) app.getApplicationMenuBar();
		    menubar.setTransSuccessMenu();
		}
		else {
		    MessageBox messagebox = new MessageBox(parent, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00303")/*재전송 작업이 실패하였습니다. \n 관리자에게 문의해주십시요.*/, "Warning",
			    MessageBox.WARNING);
		    messagebox.setModal(true);
		    messagebox.setVisible(true);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }
}
