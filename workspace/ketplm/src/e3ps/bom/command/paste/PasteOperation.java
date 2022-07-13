package e3ps.bom.command.paste;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreePath;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.ClipBoardPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.CheckSameNodeOnlyPN;
import e3ps.bom.common.util.ColorList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFOperation;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.shared.log.Kogger;

public class PasteOperation extends AbstractAIFOperation
{
    private BOMTreeNode[] nodes;
    private BOMTreeTableModel model;
    private JTreeTable treeTable;
    private JFrame desktop;
    private AbstractAIFUIApplication app;
    private Registry appReg;

    private Object[] targets = null;
    private ResultDialog dlg;
    private int totalNodeCount = 0;
    boolean bomGubunFlag = false;
    private String type = "";

    BOMAssyComponent rc = null;

    DBConnectionManager resource = null;
    Connection connection = null;
    BOMSaveDao saveDao = new BOMSaveDao();

    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public PasteOperation(JFrame desktop, BOMTreeNode[] nodes, BOMTreeTableModel model, JTreeTable treeTable, AbstractAIFUIApplication app )
    {
        this.nodes = nodes;
        this.model = model;
        this.treeTable = treeTable;
        this.desktop = desktop;
        this.app = app;
        appReg = Registry.getRegistry(app);

        targets = ClipBoardPool.getSavedTreeNode();
        totalNodeCount = ClipBoardPool.getTotalNodeCount();
        type = ClipBoardPool.getType();
    }

    public void executeOperation() throws Exception
    {
        // 선택된 Node가 Null 아닌 경우에만 Clipboard에 저장한다.
        if (nodes == null)
            return;

        if (nodes.length > 1)
        {
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("paste1"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }
        if (targets == null)
            return;

//Kogger.debug(getClass(), "@@@@@@@@@@@ targets.length : " + targets.length);
//Kogger.debug(getClass(), "@@@@@@@@@@@ targets : " + ((BOMTreeNode)targets[0]).getBOMComponent().getItemCodeStr());

//Kogger.debug(getClass(), "@@@@@@@@@@@ nodes.length : " + nodes.length);
//Kogger.debug(getClass(), "@@@@@@@@@@@ nodes : " + ((BOMTreeNode)nodes[0]).getBOMComponent().getItemCodeStr());

        dlg = new ResultDialog(desktop);
    }

    // Paste Operation을 수행하면서, 그 상황을 Dialog로 보여준다.
    class ResultDialog extends JDialog
    {
        JLabel examTitleLbl;
        JTextArea detailTxa;
        JProgressBar progress;
        JButton okBtn, cancelBtn;
        private BtnListener btnListener;
        public int m_counter = 0;
        public Thread runner = null;
        private int totalProgress = 0;
        private Timer timer;
        private final static int TIME_UNIT = 100;

        private String errorCode = "";
        private int errorVal = 0;     // 0 : 정상수행, -1 : 에러처리.
        private Frame frame;

        class BtnListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("OK"))
                {
                    dispose();
                }
                else if (e.getActionCommand().equals("Cancel"))
                {
                    errorVal = -1;
                    errorCode = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00342")/*취소하셨습니다.*/;
                    cancelBtn.setEnabled(false);
                    ResultDialog.this.setCursor(Cursor.getPredefinedCursor(3));
                }
            }
        }

        class TimerListener implements ActionListener
        {
            public void actionPerformed(ActionEvent evt)
            {
                progress.setValue(m_counter);

                if (m_counter == 100)
                {
                    setSuccess();
                }
                else if (m_counter > 100 || m_counter < 0)
                {
                    setError("Progress Bar Error");
                }
            }
        }

        public ResultDialog(JFrame frame)
        {
            super(frame, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00199")/*붙여넣기*/, true);
            this.frame = frame;

            this.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    dispose();
                }
            });
            try
            {
                jInit();

                timer = new Timer(TIME_UNIT, new TimerListener());
                timer.start();

                runner = new Thread()
                {
                    public void run()
                    {
                        excuteOperation();
                    }
                };
                runner.start();

                setSize(350,230);
                setResizable(false);

                ScreenCenterer scent = new ScreenCenterer();
                Dimension dimCenter = new Dimension(scent.getCenterDim(this));
                setLocation(dimCenter.width, dimCenter.height);
                setVisible(true);

            }
            catch(Exception e)
            {
                Kogger.error(getClass(), e);
            }
        }

        public void excuteOperation()
        {
            resource = DBConnectionManager.getInstance();
            connection = resource.getConnection(appReg.getString("plm"));

            BOMAssyComponent selectedComponent = null;
            String strSelectedNode = "";

            String strRootModelName = BOMBasicInfoPool.getPublicModelName();
            WTPart part = null;
            String strType = "";
            try {
                part = KETPartHelper.service.getPart(strRootModelName);
                strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }

            selectedComponent = nodes[0].getBOMComponent();

            // 현재 에디터상의 BOM 이 금형인 경우
            if (strType.equals("D")) {
                strSelectedNode = selectedComponent.getItemCodeStr();
                if (!strRootModelName.equals(strSelectedNode)) {        // 최상위 노드가 아닌경우 최상위 노드로 임의로 변경해준다
                    selectedComponent = ((BOMTreeNode)nodes[0].getRoot()).getBOMComponent();
                }
            }

            // 현재 BOM Model 내에.. 같은 Item Code 를 가진 node가 있는지 조사하여 모든 변화를 같이 적용시킨다...
            CheckSameNodeOnlyPN check = new CheckSameNodeOnlyPN(model.getRootNode().preorderEnumeration(), selectedComponent);
            Vector sameNodeResult = check.getResultList();
//Kogger.debug(getClass(), "@@@@@@@@@@@@ sameNodeResult : " + sameNodeResult);

            bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

            for (int i=0; i<sameNodeResult.size(); i++)
            {
                BOMTreeNode sameNode = (BOMTreeNode)sameNodeResult.elementAt(i);
                BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

                setTitle(messageRegistry.getString("paste4"));

                if(bomGubunFlag)
                {
                    // 1-1 : Selected Node 의 Item Code 가 New Item 인지 검사.
                    setDetail(messageRegistry.getString("paste5"));
                    if (!checkUpperNodeNewItem(nodes[0]))
                    {
                        setError(errorCode);
                        return;
                    }

                    // 사용자가 중간에 Cancel 을 하였는지에 대한 조사.
                    if (errorVal == -1)
                    {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 10;
                    waitSleep();

                    // 1-2 : Selected Node의 Parent Item Code 가 Target Node의 하위 Child Item Code 로 사용되었는지 검사
                    setDetail(messageRegistry.getString("paste6"));
                    if (!checkParentPNUsingAtChild(sameNode))
                    {
                        setError(errorCode);
                        return;
                    }

                    // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                    if (errorVal == -1)
                    {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 20;
                    waitSleep();
                }
                else
                {
                    // 1-1 : Selected Node의 Parent Item Code 가 Target Node의 하위 Child Item Code 로 사용되었는지 검사
                    setDetail(messageRegistry.getString("paste7"));
                    if (!checkParentPNUsingAtChild(sameNode))
                    {
                        setError(errorCode);
                        return;
                    }

                    // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                    if (errorVal == -1)
                    {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 20;
                    waitSleep();
                }

                // 2-1 : Target Node의 Item Code 가 선택된 Assy의 Child Item Code 중에 같은 것이 있는지 검사.
                setTitle(messageRegistry.getString("paste8"));
                setDetail(messageRegistry.getString("paste9"));
                if (!checkMultipleNodeAtSameLevel(sameNode))
                {
                    setError(errorCode);
                    return;
                }

                // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                if (errorVal == -1)
                {
                    setError(errorCode);
                    return;
                }

                totalProgress = 50;
                waitSleep();
            }

            try
            {
                // 검사가 모두 끝나고 난 후, 실제 Data를 Component 에 담는다.
                for (int i=0; i<sameNodeResult.size(); i++)
                {
                    BOMTreeNode sameNode = (BOMTreeNode)sameNodeResult.elementAt(i);
                    BOMAssyComponent sameAssyComponent = sameNode.getBOMComponent();

                    // 4-1 : Assy Level 검증
                    setTitle(messageRegistry.getString("paste10"));
                    setDetail(messageRegistry.getString("paste11"));

                    if (!adjustLevelInformation(sameNode))
                    {
                        setError(errorCode);
                        return;
                    }

                    // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                    if (errorVal == -1)
                    {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 70;
                    waitSleep();

                    // 4-2 : TreeNode 붙이기
                    setTitle(messageRegistry.getString("paste12"));
                    setDetail(messageRegistry.getString("paste13"));

                    if (!pasteTreeNode(sameNode))
                    {
                        setError(errorCode);
                        return;
                    }

                    // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                    if (errorVal == -1)
                    {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 80;
                    waitSleep();

                    // 4-3 : Sequence Number 보정, 최대값에서 1 증가.
                    setTitle(messageRegistry.getString("paste14"));
                    setDetail(messageRegistry.getString("paste15"));

                    if (!adjustSeqInformation())
                    {
                        setError(errorCode);
                        return;
                    }

                    if(bomGubunFlag)
                    {
                        if (!insertPasteData(connection, sameNode))
                        {
                            setError(errorCode);
                            return;
                        }
                    }

                    // 현재 선택된 Node의 P/N의 First Mark는 NEW가 되어야 한다.
                    if (sameAssyComponent.getFirstMarkStr() == null || !sameAssyComponent.getFirstMarkStr().trim().equalsIgnoreCase("NEW") )
                    {
                        sameAssyComponent.setFirstMarkStr("NEW");

                        if(bomGubunFlag)
                        {
                            saveDao.addItemUpdate(connection, sameAssyComponent);
                        }
                    }

                    // 사용자가 중간에 Cancel을 하였는지에 대한 조사.
                    if (errorVal == -1) {
                        setError(errorCode);
                        return;
                    }

                    totalProgress = 90;
                    setProgress(100);
                }
                connection.commit();

                // 전체레벨 펼치기
                treeTable.getTree().setSelectionRow(0);
                ((BOMRegisterApplicationPanel)app.getApplicationPanel()).mainEditorPane.expandTreeTable(20);
            }
            catch (Exception ex)
            {
                Kogger.error(getClass(), ex);

                try
                {
                    connection.rollback();
                }
                catch (Exception dbex)
                {}

                MessageBox m = new MessageBox(desktop, "DB Error : \n" + ex.toString(), "Error", MessageBox.ERROR);
                m.setVisible(true);
                m.setModal(true);
            }
            finally
            {
                if(resource != null)
                {
                    resource.freeConnection(appReg.getString("plm"), connection);
                }
            }
        }

        // Argument의 상위 Node가 모두 New Item Code 인지 확인.
        public boolean checkUpperNodeNewItem(BOMTreeNode samenode)
        {
            try
            {
                int progress = 0;
                int requiredProgress = 10;

                if (!samenode.getBOMComponent().getNewFlagStr().equalsIgnoreCase("NEW"))
                {
                    errorCode = messageRegistry.getString("paste16");
                    return false;
                }
                setProgress(totalProgress + requiredProgress);
            }
            catch (Exception e)
            {
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        // 선택된 Node의 Parent Item Code 가 하위 Child(targets) Item Code 로 사용되었는지 검사.
        public boolean checkParentPNUsingAtChild(BOMTreeNode checkNode)
        {
            try
            {
                BOMTreeNode[] parents = checkNode.getPathNode();
                Hashtable parentHash = new Hashtable();

                for(int i=0; i<parents.length; i++)
                {
                    parentHash.put(parents[i].toString(), parents[i]);
                }

                int progress = 0;
                int requiredProgress = 10;

                int count = 0;
                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode bomNode = (BOMTreeNode)targets[i];
                    Enumeration enum0 = bomNode.postorderEnumeration();
                    int j=0;

                    while(enum0.hasMoreElements())
                    {
                        BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
                        if ( parentHash.containsKey(allListNode.toString()))
                        {
                            // 부모 노드 중에 paste 하고자 하는 노드와 같은 이름의 노드가 존재
                            errorCode = messageRegistry.getString("paste17");
                            errorCode = errorCode + "\n" + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : " + allListNode.toString();
                            return false;
                        }

                        j++;
                        double m1 = (double)((double)(count + j) / (double)totalNodeCount);
                        progress =  (int)(m1 * requiredProgress);
                        setProgress(totalProgress + progress);
                    }
                    count = count + j;
                }
            }
            catch (Exception e)
            {
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        // 같은 레벨에 동일 Item Code 가 있는지 조사..
        public boolean checkMultipleNodeAtSameLevel(BOMTreeNode checkNode)
        {
            try
            {
                int progress = 0;
                int requiredProgress = 20;

                Object[] childNodes = checkNode.getChildren();
                int count = 0;
                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode bomNode = (BOMTreeNode)targets[i];

                    for(int j=1; j<=childNodes.length; j++)
                    {
                        BOMTreeNode child = (BOMTreeNode)childNodes[j-1];
                        if ( child.toString().equalsIgnoreCase(bomNode.toString()))
                        {
                            // 같은 레벨에 같은 Item Code 의 Assy를 추가할려고 함.
                            errorCode = messageRegistry.getString("paste18");
                            errorCode = errorCode + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/ + " : " + child.toString();
                            return false;
                        }

                        double m1 = (double)((double)(count + j) / (double)(childNodes.length * targets.length));
                        progress =  (int)(m1 * requiredProgress);

                        setProgress(totalProgress + progress);
                    }
                    count = count + childNodes.length;
                }
            }
            catch (Exception e)
            {
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        // 현재 Target Node의 Level 정보를 기준으로 level을 1씩 증가시켜준다.
        public boolean adjustLevelInformation(BOMTreeNode checkNode)
        {
            try
            {
                int progress = 0;
                int requiredProgress = 10;

                int count = 0;

                BOMAssyComponent bomcomponent = checkNode.getBOMComponent();
                int selectedNodeLevel = 0;
                if (bomcomponent.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE && (type.equals("BOMEdit")))
                {
                    selectedNodeLevel = 0;
                }
                else
                {
                    BOMAssyComponent assyComponent = (BOMAssyComponent)bomcomponent;
                    selectedNodeLevel = assyComponent.getLevelInt().intValue();
                }

                BOMTreeNode selectedNode = checkNode;
                BOMAssyComponent bomcomponent1 = ((BOMTreeNode)targets[0]).getBOMComponent();
                int targetNodeLevel = 0;
                if (bomcomponent1.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE && (type.equals("BOMEdit")))
                {
                    selectedNodeLevel = 0;
                }
                else
                {
                    BOMAssyComponent assyComponent = (BOMAssyComponent)bomcomponent1;
                    targetNodeLevel = assyComponent.getLevelInt().intValue();
                }

                int maxSortOrder = 0;
                if (selectedNode.getChildCount() == 0)
                {
                    maxSortOrder = 1;
                }
                else
                {
                    Object[] childNodeArray = selectedNode.getChildren();

                    for (int x=0; x<childNodeArray.length; x++)
                    {
                        BOMTreeNode childNode = (BOMTreeNode)childNodeArray[x];
                        String s = ((BOMAssyComponent)childNode.getBOMComponent()).getSortOrderStr();
                        String ss = s.substring(s.length()-4, s.length());
                        int sortNum = (new Integer(ss)).intValue();
                        if( maxSortOrder < sortNum)
                            maxSortOrder = sortNum;
                    }
                    maxSortOrder++;
                }

                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode bomNode = (BOMTreeNode)targets[i];
                    Enumeration enum0 = bomNode.preorderEnumeration();
                    int j=0;
                    String sortOrder = "";
                    if (maxSortOrder < 10)
                        sortOrder = "000" + maxSortOrder;
                    else if (maxSortOrder < 100)
                        sortOrder = "00" + maxSortOrder;
                    else if (maxSortOrder < 1000)
                        sortOrder = "0" + maxSortOrder;
                    else if (maxSortOrder < 10000)
                        sortOrder = "" + maxSortOrder;
                    maxSortOrder++;

                    int preLength = 0;
                    String preSortOrder = "";
                    while(enum0.hasMoreElements())
                    {
                        BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
                        BOMAssyComponent bomcomponent2 = allListNode.getBOMComponent();
                        j++;
                        if (bomcomponent2.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE && (type.equals("BOMSearch")))
                        {
                            BOMAssyComponent assyComponent2 = (BOMAssyComponent)bomcomponent2;
                            int newLevel = assyComponent2.getLevelInt().intValue() + selectedNodeLevel - targetNodeLevel + 1;
                            assyComponent2.setLevelInt(new Integer(newLevel));
                            assyComponent2.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                        }
                        else if (!(bomcomponent2.getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE))
                        {
                            BOMAssyComponent assyComponent2 = (BOMAssyComponent)bomcomponent2;
                            int newLevel = assyComponent2.getLevelInt().intValue() + selectedNodeLevel - targetNodeLevel + 1;
                            assyComponent2.setLevelInt(new Integer(newLevel));
                        }
                        else
                        {
                            errorCode = messageRegistry.getString("paste19");
                            errorCode = errorCode + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00336")/*최상위 부품번호*/ + " : " + allListNode.toString();
                            return false;
                        }

Kogger.debug(getClass(), "j = [" + j + "]         sortOrder = [" + bomcomponent2.getSortOrderStr() + "]");

                        if (j==1)
                        {
                            preLength = bomcomponent2.getSortOrderStr().trim().length();
                            preSortOrder = bomcomponent.getSortOrderStr() + sortOrder;

Kogger.debug(getClass(), "==>> preSortOrder : " + preSortOrder);
                            bomcomponent2.setSortOrderStr(preSortOrder);
                            bomcomponent2.setParentItemCodeStr(bomcomponent.getItemCodeStr());
                            bomcomponent2.setSecondMarkStr("NEW");
                            if (bomcomponent2.getStartDate().compareTo(Utility.currentDate()) < 0)
                            {
                                bomcomponent2.setStartDate(Utility.currentDate());
                            }

                            String sortOrderStr = "";
                            sortOrderStr = bomcomponent2.getSortOrderStr().trim();

                            if (!sortOrderStr.substring(sortOrderStr.length()-4).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-4) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-3).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-3) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-2).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-2) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-1).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-1) + 0));
                        }
                        else
                        {
                            bomcomponent2.setSortOrderStr(preSortOrder + bomcomponent2.getSortOrderStr().substring(preLength));

                            String sortOrderStr = "";
                            sortOrderStr = bomcomponent2.getSortOrderStr().trim();

                            if (!sortOrderStr.substring(sortOrderStr.length()-4).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-4) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-3).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-3) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-2).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-2) + 0));
                            else if (!sortOrderStr.substring(sortOrderStr.length()-1).toString().equals("0"))
                                bomcomponent2.setItemSeqInt(new Integer(sortOrderStr.substring(sortOrderStr.length()-1) + 0));
                        }

                        double m1 = (double)((double)(count + j) / (double)totalNodeCount);
                        progress =  (int)(m1 * requiredProgress);
                        setProgress(totalProgress + progress);
                    }
                    count = count + j;
                }
            }
            catch (Exception e)
            {
                Kogger.error(getClass(), e);
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        // 실제 Data를 모두 PLM DB에 Insert한다.
        public boolean insertPasteData(Connection conn, BOMTreeNode selectedNode) throws Exception
        {
            boolean insertResult;

            try
            {
                 Vector nodeVec = new Vector();
                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode targetNode = (BOMTreeNode)targets[i];
                    Enumeration tNode = targetNode.preorderEnumeration();
                    while(tNode.hasMoreElements())
                    {
                        BOMTreeNode node = (BOMTreeNode)tNode.nextElement();
                        BOMAssyComponent rc = (BOMAssyComponent)node.getBOMComponent();
                        nodeVec.add(rc);
                    }
                }

                saveDao.saveBomList(connection, BOMBasicInfoPool.getPublicModelName().trim(), nodeVec);

                insertResult = true;
            }
            catch(Exception ex)
            {
                // ----------------------- rollback 추가부분-----------------------------------------
                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode targetNode = (BOMTreeNode)targets[i];
                    selectedNode.removeElement(targetNode);
                }
                if (selectedNode.getChildCount()==0)
                {
                    if(selectedNode.getBOMComponent().getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE)
                    {
                        selectedNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.MODEL_TYPE);
                    }
                    else
                    {
                        selectedNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.PART_TYPE);
                    }
                }
                else
                {
                    if(selectedNode.getBOMComponent().getComponentTypeStr() == BOMAssyComponent.MODEL_TYPE)
                    {
                        selectedNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.MODEL_TYPE);
                    }
                    else
                    {
                        selectedNode.getBOMComponent().setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                    }
                }

                insertResult = false;
                errorCode = "DB Error : " + ex.toString();
                throw ex;
            }
            return insertResult;
        }

        // 현재 Target Node의 Level정보를 기준으로 Sequence를 보정한다. ( MAX 값으로 일률적 )
        public boolean adjustSeqInformation()
        {
            try
            {
                // Sequence Number 보정
                Enumeration enum0 = model.getRootNode().preorderEnumeration();
                int realSeqNumber = 1;
                while(enum0.hasMoreElements())
                {
                    BOMAssyComponent component = ((BOMTreeNode)enum0.nextElement()).getBOMComponent();
                    if ((component.getComponentTypeStr() == BOMAssyComponent.ASSY_TYPE || component.getComponentTypeStr() == BOMAssyComponent.PART_TYPE))
                    {
                        BOMAssyComponent assyComponent = (BOMAssyComponent)component;
                        assyComponent.setSeqInt(new Integer(realSeqNumber++));
                    }
                }

                BOMBasicInfoPool.setPublicTotalDataCount(realSeqNumber);

                int progress = 10;
                setProgress(totalProgress + progress);
            }
            catch (Exception e)
            {
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        // Paste 하기전, 모든 Node를 새로 생성해서 Tree를 구성해야만 한다.
        private void makeNewNode(BOMTreeNode newParent, BOMTreeNode targetedNode)
        {
            // 더이상 자식 노드가 없을 경우.
            if(targetedNode.getChildCount() == 0)
                return;

            Object []childrenNode = targetedNode.getChildren();
            for(int i=0; i<childrenNode.length; i++)
            {
                BOMTreeNode childNode = (BOMTreeNode)childrenNode[i];
                BOMTreeNode newNode = new BOMTreeNode(newParent, childNode.getBOMComponent().createNewComponent());
                newParent.addElement(newNode);
                makeNewNode(newNode, childNode);
            }
        }

        public boolean pasteTreeNode(BOMTreeNode checkNode)
        {
            try
            {
                // 실제 만들어진 Node를 화면에 붙이는 동작..
                int progress = 0;
                int requiredProgress = 10;

                Vector expandVec = new Vector();

                for(int i=0; i<targets.length; i++)
                {
                    BOMTreeNode addedNode = (BOMTreeNode)targets[i];
                    BOMTreeNode newNode = new BOMTreeNode(checkNode, addedNode.getBOMComponent().createNewComponent());
                    expandVec.addElement(newNode);
                    checkNode.addElement(newNode);

                    makeNewNode(newNode, addedNode);

                    double m1 = (double)((double)i / (double)targets.length);
                    progress =  (int)(m1 * requiredProgress);
                    setProgress(totalProgress + progress);
                }
                int total = totalProgress + requiredProgress;

                model.fireTreeChanged(checkNode);

                requiredProgress = 5;
                setProgress(totalProgress + requiredProgress);
                total = total + requiredProgress;

                // 현재 선택된 Node의 Type이 단품인 경우. assy로 수정해준다.
                requiredProgress = 5;
                BOMAssyComponent bomcomponent = checkNode.getBOMComponent();

                if (bomcomponent.getComponentTypeStr() == BOMAssyComponent.PART_TYPE)
                {
                    bomcomponent.setComponentTypeStr(BOMAssyComponent.ASSY_TYPE);
                }
                setProgress(total + requiredProgress);

//                if (checkNode == nodes[0])     // 금형인 경우 선택한 노드를 임의로 최상위 노드로 바꿔주기때문에 주석처리해야 화면이 Refresh 된다
//                {
                    for(int i=0; i<expandVec.size(); i++)
                    {
                        BOMTreeNode selectedNode = (BOMTreeNode)expandVec.elementAt(i);
                        TreePath treepath = new TreePath(selectedNode.getPath());
                        try
                        {
                            treeTable.getTree().fireTreeWillExpand(treepath);
                            treeTable.getTree().scrollPathToVisible(treepath);
                            treeTable.getTree().fireTreeExpanded(treepath);
                            treeTable.getTree().setSelectionPath(treepath);
                            treeTable.scrollRectToVisible(new Rectangle(0, treeTable.getSelectedRow() * treeTable.getRowHeight(), 1, treeTable.getRowHeight()));
                        }
                        catch (Exception ex)
                        {}
                        break;
                    }
//                }
                treeTable.repaint();
            }
            catch (Exception e)
            {
                errorCode = e.toString();
                return false;
            }
            return true;
        }

        public void stopTimer()
        {
            if(timer.isRunning())
                timer.stop();
        }

        public void waitSleep()
        {
            try
            {
                Thread.sleep(50);
            }
            catch (Exception ex) {}
        }

        public void dispose()
        {
            int count = getComponentCount();
            for(int i=0; i<count; i++)
            {
                Component c = getComponent(i);
                this.remove(c);
                c = null;
            }
            super.dispose();
            System.gc();
        }

        public void setProgress(int m)
        {
            m_counter = m;
        }

        private void jInit()
        {
            try
            {
                btnListener = new BtnListener();

                JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                examTitleLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00201")/*붙여넣기 준비중입니다.*/ + "...");
                examTitleLbl.setPreferredSize(new Dimension(200, 20));
                examTitleLbl.setForeground(ColorList.darkBlue);
                examTitleLbl.setFont(FontList.defaultFont);

                topPanel.add(examTitleLbl);

                // 설명, Progress Bar Panel
                JPanel centerPanel = new JPanel();
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

                centerPanel.add(Box.createRigidArea(new Dimension(0,10)));

                detailTxa = new JTextArea("Paste BOM from clipboard.", 3, 18);
                detailTxa.setForeground(ColorList.darkBlue);
                detailTxa.setBackground(ColorList.gray2Color);
                detailTxa.setEditable(false);
                detailTxa.setLineWrap(true);
                detailTxa.setWrapStyleWord(true);
                detailTxa.setFont(FontList.defaultFont);
                centerPanel.add(new JScrollPane(detailTxa));

                centerPanel.add(Box.createRigidArea(new Dimension(0,15)));

                UIManager.put("ProgressBar.selectionBackground", Color.black);
                UIManager.put("ProgressBar.selectionForeground", Color.white);
                UIManager.put("ProgressBar.foreground", new Color(8,25,128));

                progress = new JProgressBar();
                progress.setMinimum(0);
                progress.setMaximum(100);
                progress.setStringPainted(true);
                centerPanel.add(progress);
                centerPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00260")/*실행결과*/, 0, 0, FontList.defaultFont));

                JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00367")/*확인*/, appReg.getImageIcon("okIcon"));
                okBtn.setMargin(new Insets(0,5,0,5));
                okBtn.setActionCommand("OK");
                okBtn.setDefaultCapable(true);
                okBtn.addActionListener(btnListener);
                okBtn.setEnabled(false);
                okBtn.setFont(FontList.defaultFont);
                btnFlowPanel.add(okBtn);

                cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/, appReg.getImageIcon("closeIcon"));
                cancelBtn.setActionCommand("Cancel");
                cancelBtn.addActionListener(btnListener);
                cancelBtn.setMargin(new Insets(0,5,0,5));
                cancelBtn.setFont(FontList.defaultFont);
                btnFlowPanel.add(cancelBtn);

                this.getContentPane().add(topPanel, BorderLayout.NORTH);
                this.getContentPane().add(new JLabel("   "), BorderLayout.EAST);
                this.getContentPane().add(new JLabel("   "), BorderLayout.WEST);
                this.getContentPane().add(centerPanel, BorderLayout.CENTER);
                this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
            }
            catch(Exception ex)
            {
                Kogger.error(getClass(), ex);
            }
        }

        public void setTitle(String title)
        {
            examTitleLbl.setText(title);
        }

        public void setDetail(String detail)
        {
            detailTxa.setText(detail);
        }

        public void setError(String error)
        {
            stopTimer();

            setCursor(Cursor.getPredefinedCursor(0));

            examTitleLbl.setText( " Error : " + examTitleLbl.getText());
            examTitleLbl.setForeground(Color.red);
            detailTxa.setText(detailTxa.getText() + "\n\n ERROR : " + error);
            okBtn.setEnabled(true);
            cancelBtn.setEnabled(false);
        }

        public void setSuccess() {
            stopTimer();
            examTitleLbl.setText("  " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00200")/*붙여넣기 성공!!*/);
            detailTxa.setText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00202")/*붙여넣기가 완료되었습니다.*/);
            okBtn.setEnabled(true);
            cancelBtn.setEnabled(false);
        }
    }
}
