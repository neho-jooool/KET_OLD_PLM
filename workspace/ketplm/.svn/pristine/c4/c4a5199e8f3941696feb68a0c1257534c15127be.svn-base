package e3ps.bom.command.comparebom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.MainEditorPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.searchitem.SearchBOMPanel;
import e3ps.bom.common.ItemTableData;
import e3ps.bom.common.ItemTableDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public abstract class AbstractComparePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    public AbstractAIFUIApplication app;
    public Registry registry;
    public CompareFrame parentFrame;

    private JTableHeader resultTableHeader = new JTableHeader();

    public BOMTreeNode sourceNode, targetNode;
    public CompareTargetPanel compareTargetPanel;
    public CompareProgressDialog progress;

    private int mouse_x, mouse_y;
    public boolean keyCheck = false;
    public boolean cancelFlag = false;

    public final String NOT_EXIST_CODE = "N";
    public final String QTY_UNLIKE_CODE = "Q";
    public final String STYPE_UNLIKE_CODE = "S";

    public AbstractComparePanel(CompareFrame parentFrame, AbstractAIFUIApplication app) throws Exception
    {
        try
        {
            this.app = app;
            this.parentFrame = parentFrame;
            registry = Registry.getRegistry(app);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public abstract JTable getResultTable();

    public abstract void setCancelFlag(boolean cancelFlag);

    public abstract void clearOperation();

    public abstract String getProgramID();

    public abstract String getDatasetName();

    public abstract JScrollPane getResultScroll();

    public abstract void addColumnOperation(Vector selectedVector, int selectedColumnIndex);

    public abstract String getSourceItemCode();

    public abstract String getTargetItemCode();

    public abstract void setTargetComponent(ItemTableData targetComponent);

    public void setCommonGUI()
    {
        ActionListener popupActionListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String actioncommand = e.getActionCommand();
                if (actioncommand.equals("Excel"))
                {
                    parentFrame.excelOperation();
                }
            }
        };

        resultTableHeader = getResultTable().getTableHeader();
        resultTableHeader.setUpdateTableInRealTime(true);
    }

    public void popupRemoveColumn()
    {
        TableColumnModel columnModel = getResultTable().getColumnModel();
        int selectedColumnIndex = columnModel.getColumnIndexAtX(mouse_x);

        TableColumn tableColumn = columnModel.getColumn(selectedColumnIndex);
        columnModel.removeColumn(tableColumn);
    }

    protected int getSelectedTableColumn()
    {
        TableColumnModel columnModel = null;
        columnModel = getResultTable().getColumnModel();

        int selectedColumnIndex = columnModel.getColumnIndexAtX(mouse_x);

        return selectedColumnIndex;
    }

    public void changeSourceItemCode(BOMAssyComponent sourceComponent, SearchBOMPanel searchBOMPanel) throws Exception
    {
        try
        {
            searchBOMPanel.clearBOMList();

            BOMTreeTableModel model = (BOMTreeTableModel)searchBOMPanel.getTreeTableModel();
            BOMTreeNode targetNode = new BOMTreeNode(model.getRootNode(), sourceComponent);
            JTreeTable treeTable = ((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTable();

            model.getRootNode().addElement(targetNode);
            int seqNumber;
            int bomDataCount;

            sourceComponent.setSeqInt(new Integer(1));
            sourceComponent.setLevelInt(new Integer(1));

            SearchPNOperation searchOp = new SearchPNOperation(targetNode, Utility.checkNVL(sourceComponent.getItemCodeStr()), model, treeTable);
            searchOp.executeModal();
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            throw ex;
        }
    }

    // 메인 에디터 패널에 비교 대상 트리 로딩
    public void loadTargetBOMTree(String itemCode) throws Exception
    {
        BOMAssyComponent rootComponent = new BOMAssyComponent(itemCode, true);
        rootComponent.setSeqInt(new Integer(0));

        BOMRegisterApplicationPanel workspacePanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        JSplitPane splitPane = null;

        if (workspacePanel.getSelectedPanel().equals("BOMEditor"))
        {
            splitPane = ((MainEditorPanel)workspacePanel.getMainEditorPane()).getSplitPane();
        }
        compareTargetPanel = new CompareTargetPanel(parentFrame);

        try
        {
            compareTargetPanel.openBOMData(rootComponent, new Integer(30));
        }
        catch (Exception ex)
        {
            throw ex;
        }

        targetNode = compareTargetPanel.getRootNode();

        splitPane.setRightComponent(compareTargetPanel);
        splitPane.updateUI();

        if (workspacePanel.getSelectedPanel().equals("BOMEditor"))
        {
            splitPane.setDividerLocation((new Double(((MainEditorPanel)workspacePanel.getMainEditorPane()).getSize().getWidth() / 2)).intValue());
        }
    }


    // BOM 조회 패널에 비교 대상 트리 로딩
    public void loadTargetBOMTree(String itemCode, SearchBOMPanel searchBOMPanel) throws Exception
    {
        Vector itemVec = new Vector();
        Hashtable inputParams = new Hashtable();
        BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

        BOMAssyComponent rootComponent = new BOMAssyComponent(itemCode, true);
        rootComponent.setSeqInt(new Integer(0));
        rootComponent.setComponentTypeStr("2");

        WTPart part = KETPartHelper.service.getPart(rootComponent.getItemCodeStr());
        rootComponent.setDescStr(part.getName());                        // 부품명
        rootComponent.setUitStr(part.getDefaultUnit().toString());        // 단위
        rootComponent.setStatusStr(part.getState().toString());        // 부품상태 (영문)
        rootComponent.setStatusKrStr(part.getLifeCycleState().getDisplay());    // 부품상태 (한글)
Kogger.debug(getClass(), "@@@@@ setStatusStr : " + part.getState().toString());
Kogger.debug(getClass(), "@@@@@ setStatusKrStr : " + part.getLifeCycleState().getDisplay());

        // 버전 정보 셋팅
        itemVec.addElement(rootComponent.getItemCodeStr());
        inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
        inputParams.put("itemCode", itemVec);

        Vector version = KETBomHelper.service.getItemVersion(inputParams);
Kogger.debug(getClass(), "@@@@@ version : " + version);
        String versionStr = "";
        for (int k=0; k<version.size(); k++) {
            if (version.size() > 0) {
                versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
                if (rootComponent.getItemCodeStr().equals(versionStr.substring(0, versionStr.indexOf("@")))) {
                    rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
                }
            }
        }

        BOMRegisterApplicationPanel workspacePanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        JSplitPane splitPane = null;

        splitPane = searchBOMPanel.getSplitPane();

        compareTargetPanel = new CompareTargetPanel(parentFrame);

        try
        {
            compareTargetPanel.openBOMData(rootComponent, new Integer(30));
        }
        catch (Exception ex)
        {
            throw ex;
        }

        targetNode = compareTargetPanel.getRootNode();

        splitPane.setRightComponent(compareTargetPanel);
        splitPane.updateUI();

        splitPane.setDividerLocation((new Double(searchBOMPanel.getSize().getWidth() / 2)).intValue());
    }

    public BOMAssyComponent itemSearchOperation(String itemCodeStr)
    {
        DBConnectionManager resource = null;
        Connection connection = null;

        BOMAssyComponent selectedComponent = null;

        Vector resultList = new Vector();

        if (itemCodeStr == null || itemCodeStr.equals(""))
        {
            MessageBox.post(messageRegistry.getString("compare"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, MessageBox.WARNING);
            return selectedComponent;
        }

        if (itemCodeStr.length() < 5)
        {
            if (JOptionPane.showConfirmDialog(parentFrame, messageRegistry.getString("compare1"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
            {
                return selectedComponent;
            }
        }

        try
        {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(registry.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.oldItemList(connection, itemCodeStr);

            resultList = dao.getResultListVec();

Kogger.debug(getClass(), "====>> oldItemList : " + resultList.size());

            if (resultList.size() == 0)
            {
                MessageBox.post(messageRegistry.getString("compare2"), "not exist", MessageBox.INFORMATION);
                return selectedComponent;
            }

            ItemTableDialog dlg = new ItemTableDialog(parentFrame, resultList, app);

            if (dlg.getOK())
            {
                selectedComponent = dlg.getSelectedComponent();
            }
        } catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            MessageBox.post(ex);
            return selectedComponent;
        } finally
        {
            if (resource != null)
            {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }

        return selectedComponent;
    }

    public boolean isValidItemCode(String sourceItemCode, String targetItemCode) throws Exception
    {
        boolean isValid = true;
        DBConnectionManager resource = null;
        Connection connection = null;

        Vector resultList = new Vector();

        // 비교 source가 되는 모델이 없으면 경고를 보여주고 return한다
        if (sourceItemCode.equals(""))
        {
            MessageBox.post(messageRegistry.getString("compare3"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00278")/*입력*/, MessageBox.WARNING);
            isValid = false;
            return isValid;
        }

        // 비교 대상이 되는 모델이 기입되지 않았으면 경고를 보여주고 return한다
        if (targetItemCode.equals(""))
        {
            MessageBox.post(messageRegistry.getString("compare4"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00278")/*입력*/, MessageBox.WARNING);
            isValid = false;
            return isValid;
        }

        // user가 입력한 Model/Assy가 ERP나 PDM의 DB에 존재하는지 확인한다
        try
        {
            resource = DBConnectionManager.getInstance();

            registry = Registry.getRegistry("e3ps.bom.bom");

            connection = resource.getConnection(registry.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.oldItemList(connection, targetItemCode);

            resultList = dao.getResultListVec();

            if (resultList.size() == 0)
            {
                MessageBox.post(messageRegistry.getString("compare5"), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, MessageBox.INFORMATION);
                isValid = false;
                return isValid;
            }
            else
            {
                ItemTableData itemData = (ItemTableData)resultList.elementAt(0);
                setTargetComponent(itemData);
            }
        }
        catch (Exception ex)
        {
            Kogger.error(getClass(), ex);
            throw ex;
        }
        finally
        {
            if (resource != null)
            {
                resource.freeConnection(registry.getString("plm"), connection);
            }
        }

        return isValid;
    }

}
