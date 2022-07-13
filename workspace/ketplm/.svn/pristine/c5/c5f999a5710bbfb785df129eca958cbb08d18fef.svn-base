package e3ps.bom.common.jtreetable;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class KetMainJTreeTable {

    public KetMainJTreeTable() {
    }

    // 제품인 경우 헤더정보 Display 설정
    public void setGenMain(AbstractAIFUIApplication app) {
	BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	JTreeTable treeTable = bomPanel.getTreeTable();

	// 기본 숨김 컬럼 닫음.
	TableColumnModel columnModel = treeTable.getColumnModel();
	TableColumn column = columnModel.getColumn(8);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(19);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 제품관련 칼럼 열어줌.
	column = columnModel.getColumn(15);
	column.setMinWidth(0);
	// column.setMaxWidth(350);
	column.setResizable(true);
	column.setPreferredWidth(120);

	// 금형관련 칼럼 닫음.
	column = columnModel.getColumn(9);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(10);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(11);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(12);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);
    }

    // 금형인 경우 헤더정보 Display 설정
    public void setMoldMain(AbstractAIFUIApplication app) {
	BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	JTreeTable treeTable = bomPanel.getTreeTable();

	// 기본 숨김 컬럼 닫음.
	TableColumnModel columnModel = treeTable.getColumnModel();
	TableColumn column = columnModel.getColumn(8);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(19);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 제품관련 칼럼 닫음.
	column = columnModel.getColumn(15);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 금형관련 칼럼 열어줌.
	column = columnModel.getColumn(9);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(45);

	column = columnModel.getColumn(10);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(65);

	column = columnModel.getColumn(11);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(55);

	column = columnModel.getColumn(12);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(60);

	// [2011-03-04] 임승영D 요구사항 반영 :: 금형인 경우 ItemSeq 컬럼 숨기기
	column = columnModel.getColumn(4);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);
    }

    // BOM 비교시 Open 되는 조회 화면에 사용 :: 제품인 경우
    public void setGenCompairMain(JTreeTable treeTable) {
	// BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
	// JTreeTable treeTable = bomPanel.getTreeTable();

	// 기본 숨김 컬럼 닫음.
	TableColumnModel columnModel = treeTable.getColumnModel();
	TableColumn column = columnModel.getColumn(8);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(19);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 제품관련 칼럼 열어줌.
	column = columnModel.getColumn(15);
	column.setMinWidth(0);
	// column.setMaxWidth(350);
	column.setResizable(true);
	column.setPreferredWidth(120);

	// 금형관련 칼럼 닫음.
	column = columnModel.getColumn(9);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(10);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(11);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(12);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);
    }

    // BOM 비교시 Open 되는 조회 화면에 사용 :: 금형인 경우
    public void setMoldCompairMain(JTreeTable treeTable) {
	// BOMRegisterApplicationPanel bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
	// JTreeTable treeTable = bomPanel.getTreeTable();

	// 기본 숨김 컬럼 닫음.
	TableColumnModel columnModel = treeTable.getColumnModel();
	TableColumn column = columnModel.getColumn(8);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	column = columnModel.getColumn(19);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 제품관련 칼럼 닫음.
	column = columnModel.getColumn(15);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);

	// 금형관련 칼럼 열어줌.
	column = columnModel.getColumn(9);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(45);

	column = columnModel.getColumn(10);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(65);

	column = columnModel.getColumn(11);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(55);

	column = columnModel.getColumn(12);
	column.setMinWidth(0);
	column.setMaxWidth(150);
	column.setResizable(true);
	column.setPreferredWidth(60);

	// [2011-03-04] 임승영D 요구사항 반영 :: 금형인 경우 ItemSeq 컬럼 숨기기
	column = columnModel.getColumn(4);
	column.setWidth(0);
	column.setMinWidth(0);
	column.setMaxWidth(0);
	column.setResizable(false);
    }
}
