package e3ps.edm.clients.batch;

import java.io.Serializable;

import jxl.Cell;

public class EPMLoadData implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = -5360787490125588934L;

    String m_rownum;// excel row number

    String m_moldNumber;// 금형번호
    public String m_moldPartNumber;// 금형부품번호
    public String m_moldPartDesc;// 금형부품설명

    String m_oldMoldPart;// 구금형부품 *** 미사용 ***
    String m_deledTargetBOM;// BOM삭제대상(X) *** 미사용 ***
    String m_deledTargetDrw;// 도면삭제대상(X) *** 미사용 ***
    String m_lifetimeTarget;// 수명대상(X) *** 미사용 ***

    String m_qty;// 수량
    String m_designDate;// 설계일자

    String m_etc;// 기준타발수 *** 미사용 ***

    String m_material;// 재질
    String m_hardnessFrom;// 경도(From)
    String m_hardnessTo;// 경도(To)

    String m_size1;// Size(1) *** 미사용 ***
    String m_size2;// Size(2) *** 미사용 ***
    String m_size3;// Size(3) *** 미사용 ***
    String m_revNo;// Rev.NO. *** 미사용 ***
    String m_revDate;// Rev.Date *** 미사용 ***
    String m_safetyStock;// 안전재고(일반) *** 미사용 ***
    String m_initSafetyStock;// 안전재고(초경) *** 미사용 ***

    String m_cadNumber;// CAD_Number
    String m_reledPart;

    public EPMTableIconData m_symbol;

    public String manageType;
    public String bizOid;
    public String devStage;
    public String category;
    public String cadAppType;
    // /////////////////////////////////////////////
    // Added by MJOH, 2011-03-17
    // 프로젝트 링크 추가 처리
    // /////////////////////////////////////////////
    public String projectOid;
    // /////////////////////////////////////////////

    public String number;
    public String name;
    public String cadName;
    public String fileName;
    public String oid;

    public boolean iswgm = false;

    public boolean isValidate = true;
    public boolean isExist = false;
    public boolean isCADName = false;
    public boolean isFileNotFound = false;
    public boolean isAppTypeDiff = false;

    public boolean isSkipRow = false;
    public boolean isEmptyField = false;

    public boolean isAlreadyGone = false;

    public String security;

    public EPMLoadData(String rownum) {
	// m_symbol = new EDMTableIconData(getIcon(change), symbol);
	// m_change = new EDMTableColorData(new Double(change));
	// m_changePr = new EDMTableColorData(new Double(changePr));
	m_rownum = rownum;
    }

    public EPMLoadData(Cell[] cells, String rownum) {
	this(rownum);
	setCellDate(cells);
    }

    public void setCellDate(Cell[] cells) {
	int i = 0;

	m_moldNumber = getContent(cells, i++);// 금형번호*
	m_moldPartNumber = getContent(cells, i++);// 금형부품번호*
	m_moldPartDesc = getContent(cells, i++);// 금형부품설명*

	// m_oldMoldPart = getContent(cells, i++);//구금형부품
	// m_deledTargetBOM = getContent(cells, i++);//BOM삭제대상(X)
	// m_deledTargetDrw = getContent(cells, i++);//도면삭제대상(X)
	// m_lifetimeTarget = getContent(cells, i++);//수명대상(X)

	m_qty = getContent(cells, i++);// 수량
	m_designDate = getContent(cells, i++);// 설계일자

	// m_etc = getContent(cells, i++);//기준타발수

	m_material = getContent(cells, i++);// 재질
	m_hardnessFrom = getContent(cells, i++);// 경도(From)
	m_hardnessTo = getContent(cells, i++);// 경도(To)

	// m_size1 = getContent(cells, i++);//Size(1)
	// m_size2 = getContent(cells, i++);//Size(2)
	// m_size3 = getContent(cells, i++);//Size(3)
	// m_revNo = getContent(cells, i++);//Rev.NO.
	// m_revDate = getContent(cells, i++);//Rev.Date
	// m_safetyStock = getContent(cells, i++);//안전재고(일반)
	// m_initSafetyStock = getContent(cells, i++);//안전재고(초경)

	m_cadNumber = getContent(cells, i++);// CAD_Number*
	m_reledPart = getContent(cells, i++);// 관련부품

	if ((m_moldPartNumber.length() == 0) || (m_moldPartDesc.length() == 0)) {
	    isEmptyField = true;
	}

	if ("NA".equalsIgnoreCase(m_cadNumber)) {
	    isSkipRow = true;
	} // else {
	  // isSkipRow = false;
	  // }
	System.out.println("########  isSkipRow = " + isSkipRow);
    }

    public String getPartNumber() {
	return m_moldPartNumber;
    }

    private String getContent(Cell[] cell, int idx) {
	try {
	    String val = cell[idx].getContents();

	    if (val == null) {
		return "";
	    }

	    return val.trim();
	} catch (ArrayIndexOutOfBoundsException e) {
	}

	return "";
    }
}
