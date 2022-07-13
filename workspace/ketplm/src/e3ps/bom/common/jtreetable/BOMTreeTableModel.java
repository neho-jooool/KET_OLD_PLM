package e3ps.bom.common.jtreetable;

import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.tree.TreePath;

import e3ps.bom.command.confirmbom.ConfirmLineQry;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class BOMTreeTableModel extends AbstractTreeTableModel {
    int reloadCount;
    String publicModelName;
    protected BOMTreeNode reloadNode;

    protected static String cNames[] = new String[20];
    protected static Class cTypes[] = new Class[20];

    protected static int COL_ITEMCODE;
    protected static int COL_GUBUN;
    protected static int COL_REVISE;
    protected static int COL_CHECKOUT;
    protected static int COL_R;
    protected static int COL_SEQ;
    protected static int COL_LEVEL;
    protected static int COL_ITEMSEQ;
    protected static int COL_OPSEQ;
    protected static int COL_DESCRIPTION;
    protected static int COL_VERSION;
    protected static int COL_STATUS;
    protected static int COL_STATUSKR;
    protected static int COL_UIT;
    protected static int COL_UOM;
    protected static int COL_QUANTITY;
    protected static int COL_COSTROLLUP;
    protected static int COL_SUPPLYTYPE;
    protected static int COL_DESIGNATORNO;
    protected static int COL_SUBSTITUTEITEM;
    protected static int COL_ECONO;
    protected static int COL_STARTDATE;
    protected static int COL_ENDDATE;
    protected static int COL_NEWFLAG;
    protected static int COL_DELETE;

    protected static int COL_MATERIAL;
    protected static int COL_HARDNESSFROM;
    protected static int COL_HARDNESSTO;
    protected static int COL_DESIGNDATE;

    public void tableModel() {
	cNames[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/* 부품번호 */; // Item Code
	cNames[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/* 구분 */;
	cNames[2] = "SEQ"; // SEQ
	cNames[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/* 레벨 */; // Level
	cNames[4] = "Item Seq"; // Item Seq
	cNames[5] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/* 부품명 */; // Description
	cNames[6] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/* 수량 */; // Quantity
	cNames[7] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/* 기본단위 */; // Unit
	cNames[8] = "Status"; // Status
	cNames[9] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/* 재질 */; // MATERIAL
	cNames[10] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/* 경도(From) */; // HARDNESSFROM
	cNames[11] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/* 경도(To) */; // HARDNESSTO
	cNames[12] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/* 설계일자 */; // DESIGNDATE
	cNames[13] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/* 버전 */; // Version
	cNames[14] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/* 결재상태 */; // Status
	cNames[15] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/* 대체부품번호 */; // Substitute Item
	cNames[16] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00216")/* 삭제여부 */; // 삭제여부
	cNames[17] = "ECO No"; // ECO No
	cNames[18] = "Check-Out"; // Check-Out
	cNames[19] = "New Flag"; // New Flag

	cTypes[0] = e3ps.bom.common.jtreetable.TreeTableModel.class;
	cTypes[1] = java.lang.String.class;
	cTypes[2] = java.lang.String.class;
	cTypes[3] = java.lang.String.class;
	cTypes[4] = java.lang.Integer.class;
	cTypes[5] = java.lang.Integer.class;
	cTypes[6] = java.lang.Integer.class;
	cTypes[7] = java.lang.String.class;
	cTypes[8] = java.lang.String.class;
	cTypes[9] = java.lang.String.class;
	cTypes[10] = java.lang.String.class;
	cTypes[11] = java.lang.String.class;
	cTypes[12] = java.lang.String.class;
	cTypes[13] = java.lang.String.class;
	cTypes[14] = java.lang.String.class;
	cTypes[15] = java.lang.String.class;
	cTypes[16] = java.lang.String.class;
	cTypes[17] = java.lang.String.class;
	cTypes[18] = java.lang.String.class;
	cTypes[19] = java.lang.String.class;

	COL_ITEMCODE = 0;
	COL_GUBUN = 1;
	COL_SEQ = 2;
	COL_LEVEL = 3;
	COL_ITEMSEQ = 4;
	COL_DESCRIPTION = 5;
	COL_QUANTITY = 6;
	COL_UIT = 7;
	COL_STATUS = 8;
	COL_MATERIAL = 9;
	COL_HARDNESSFROM = 10;
	COL_HARDNESSTO = 11;
	COL_DESIGNDATE = 12;
	COL_VERSION = 13;
	COL_STATUSKR = 14;
	COL_SUBSTITUTEITEM = 15;
	COL_DELETE = 16;
	COL_ECONO = 17;
	COL_CHECKOUT = 18;
	COL_NEWFLAG = 19;
	// ...............................................................................

    }

    public BOMTreeTableModel() {
	super(null);
	reloadCount = 0;
	publicModelName = "";
	tableModel();
	root = new BOMTreeNode(new BOMAssyComponent());
    }

    public BOMTreeTableModel(BOMAssyComponent rootBom) {
	super(null);
	reloadCount = 0;
	publicModelName = "";
	tableModel();
	root = new BOMTreeNode(rootBom);
	reloadCount = 1;
    }

    public void setModel(BOMTreeNode rootNode) {
	this.root = rootNode;
	fireTreeChanged(rootNode);
    }

    public void reloadChildren() {
    }

    public BOMTreeNode getRootNode() {
	return (BOMTreeNode) root;
    }

    public Object getChild(Object node, int i) {
	return getChildren(node)[i];
    }

    protected Object[] getChildren(Object node) {
	BOMTreeNode bomNode = (BOMTreeNode) node;
	return bomNode.getChildren();
    }

    public int getChildCount(Object node) {
	Object children[] = getChildren(node);
	return children != null ? children.length : 0;
    }

    public int getColumnCount() {
	return cNames.length;
    }

    public String getColumnName(int column) {
	return cNames[column];
    }

    public Class getColumnClass(int column) {
	return cTypes[column];
    }

    public void fireTreeChanged(BOMTreeNode node) {
	fireTreeStructureChanged(node, node.getPath(), null, node.getChildren());
    }

    public Object getValueAt(Object node, int column) {
	if (node == null) {
	    return "";
	}

	BOMTreeNode bomNode = (BOMTreeNode) node;
	BOMAssyComponent nodeComponent = bomNode.getBOMComponent();

	if (nodeComponent == null) {
	    return "";
	}

	if (nodeComponent instanceof BOMAssyComponent) {
	    KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();

	    try {
		Vector subAssys;
		BOMAssyComponent assyComponent = nodeComponent;

		switch (column) {
		case 0:
		    return assyComponent;

		case 1:
		    return assyComponent.getNewFlagStr().equalsIgnoreCase("NEW") ? KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00252")/* 신규 */: KETMessageService.service
			    .getString("e3ps.bom.bom_editor_message", "00120")/* 기존 */;

		case 2:
		    return assyComponent.getSeqInt();

		case 3:
		    int i = assyComponent.getLevelInt().intValue();
		    String s = "";

		    for (int j = 1; j < i; j++)
			s = s + "*";

		    s = s + i;
		    return s;

		case 4:
		    return assyComponent.getItemSeqInt();

		case 5:
		    return assyComponent.getDescStr();

		case 6:
		    // [2011-03-04] 임승영D 요구사항 반영 :: 금형인 경우 소숫점을 지운다
		    NumberFormat format = NumberFormat.getInstance();
		    format.setMinimumFractionDigits(3);

		    String quantity = assyComponent.getQuantityDbl() + "";
		    if (!quantity.equals("") && quantity.indexOf(".") >= 0) {
			quantity = quantity.substring(0, quantity.indexOf("."));
		    }

		    if (PartUtil.isProductType(assyComponent.getIBAPartType())) {
			return format.format(assyComponent.getQuantityDbl());
		    } else {
			return quantity;
		    }

		case 7:
		    return kh.getUnitDisplayValue(assyComponent.getUitStr());

		case 8:
		    return assyComponent.getStatusStr();

		case 9:
		    return assyComponent.getMaterialStr();

		case 10:
		    return assyComponent.getHardnessFrom();

		case 11:
		    return assyComponent.getHardnessTo();

		case 12:
		    return assyComponent.getDesignDate();

		case 13:
		    String ver = assyComponent.getVersionStr();

		    if (ver.indexOf(".") > 0) {
			ver = ver.substring(0, ver.indexOf("."));
		    }

		    return ver;

		case 14:
		    return assyComponent.getStatusKrStr();

		case 15:
		    subAssys = assyComponent.getSubAssyComponent();
		    if (subAssys != null && subAssys.size() > 0) {
			StringBuffer subAssyNos = new StringBuffer();

			for (int c = 0; c < subAssys.size(); c++) {
			    BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent) subAssys.elementAt(c);
			    if (subAssyComponent.getSubstituteItemCodeStr() != null && !subAssyComponent.getSubstituteItemCodeStr().trim().equals(""))
				subAssyNos.append(subAssyComponent.getSubstituteItemCodeStr() + ", ");
			}

			String subAssyStr = subAssyNos.toString();

			if (subAssyStr.indexOf(",") > 0) {
			    subAssyStr = subAssyStr.substring(0, (subAssyStr.length() - 2));
			}

			return subAssyStr;
		    } else {
			return "";
		    }

		case 16:
		    String strDeleted = "";
		    if (assyComponent.getIsDeleted().equals("Y")) {
			strDeleted = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00215")/* 삭제됨 */;
		    }

		    return strDeleted;

		case 17:
		    return assyComponent.getEcoHeaderNumberStr();

		case 18:
		    ConfirmLineQry cq = new ConfirmLineQry();

		    String checkStr = String.valueOf(assyComponent.getCheckOutStr());
		    String name = "";
		    String email = "";
		    String dept = "";

		    if (checkStr.indexOf("(") > -1 && checkStr.indexOf(")") > -1) {
			name = checkStr.substring(0, checkStr.indexOf("("));
			email = checkStr.substring(checkStr.indexOf("(") + 1, checkStr.indexOf(")"));
			dept = cq.getDeptData(name, email);

			checkStr = dept + "/" + name;
		    } else {
			checkStr = "";
		    }

		    return checkStr;

		case 19:
		    return assyComponent.getNewFlagStr();
		}
	    } catch (SecurityException se) {
		Kogger.error(getClass(), se);
	    }
	}

	return null;
    }

    public boolean isReloading() {
	return reloadCount > 0;
    }

    public TreePath getPathLoading() {
	BOMTreeNode rn = reloadNode;

	if (rn != null) {
	    return new TreePath(rn.getPath());
	}

	return null;
    }
}
