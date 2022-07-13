package e3ps.bom.command.comparebom;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.MainEditorPanel;
import e3ps.bom.command.comparebom.staticcompare.StaticComparePanel;
import e3ps.bom.command.comparebom.staticcompare.StaticCompareResultTableCellRenderer;
import e3ps.bom.command.comparebom.staticcompare.StaticCompareResultTableModel;
import e3ps.bom.command.saveexcel.ExcelFilter;
import e3ps.bom.command.searchitem.SearchBOMPanel;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.commands.open.OpenCommand;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;
import gui.JMouseWheelFrame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class CompareFrame extends JMouseWheelFrame
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JTabbedPane compareTabPane;
    private AbstractComparePanel staticPanel;
    private JFrame desktop;
    private JButton excelBtn, closeBtn;

    private AbstractAIFUIApplication app;
    private Registry registry;

    private int staticCurrentRow = 0;
    private int itemCurrentRow = 0;

    private boolean compareComplete = false;

    private String fileSeparator = System.getProperty("file.separator", "/");
    String panelStr = "";
    SearchBOMPanel searchPanel = null;

/*************** File Path *****************/
    String fileDirPath = "";        // File Directory Path
    String currFileName = "";        // 현재 파일명
/*****************************************/

    public CompareFrame(JFrame frame, AbstractAIFUIApplication abstractaifuiapplication)
    {
        super();

        this.desktop = frame;

        try
        {
            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            app = abstractaifuiapplication;
            registry = Registry.getRegistry(app);
            panelStr = "";

            setGUI();
            setEvent();

            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
            MessageBox.post(ex);
        }
    }

    public CompareFrame(AbstractAIFUIApplication abstractaifuiapplication, String str, SearchBOMPanel searchPanel)
    {
        super();
//        this.desktop = frame;
        try
        {

//            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            app = abstractaifuiapplication;
            registry = Registry.getRegistry(app);

            this.panelStr = str;
            this.searchPanel = searchPanel;

            setGUI();
            setEvent();

//            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
//            desktop.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
            MessageBox.post(ex);
        }
    }

    private void setGUI() throws Exception
    {
        compareTabPane = new JTabbedPane();
        JPanel buttonPanel = new JPanel();

        try
        {
            staticPanel = new StaticComparePanel(this, app, panelStr, searchPanel);
        }catch(Exception ex)
        {
            throw ex;
        }

        this.getContentPane().setLayout(new BorderLayout());

        excelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00264")/*엑셀 저장*/, registry.getImageIcon("saveexcelIcon"));
        excelBtn.setActionCommand("Excel");
        excelBtn.setMargin(new Insets(0,5,0,5));
        excelBtn.setFont(FontList.defaultFont);

        closeBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, registry.getImageIcon("closeIcon"));
        closeBtn.setActionCommand("Close");
        closeBtn.setMargin(new Insets(0,5,0,5));
        closeBtn.setFont(FontList.defaultFont);

//        this.getContentPane().add(compareTabPane, BorderLayout.CENTER);

//        compareTabPane.add(staticPanel, "Static Compare");

        this.getContentPane().add(staticPanel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(excelBtn, null);
        buttonPanel.add(closeBtn, null);

//        compareTabPane.setSelectedComponent(staticPanel);
///        compareTabPane.setVisible(false);

        setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00027")/*BOM 비교*/);
        setSize(960, 660);

        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));
        setLocation(dimCenter.width, dimCenter.height);

        setVisible(true);
    }

    private void setEvent()
    {
        ActionListener actionlistener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String actioncommand = e.getActionCommand();

                if (actioncommand.equals("Excel"))
                {
                    excelOperation();
                }
                else if (actioncommand.equals("Close"))
                {
                    closeOperation();
                    setVisible(false);
                    System.gc();
                }
            }
        };

        excelBtn.addActionListener(actionlistener);
        closeBtn.addActionListener(actionlistener);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                try
                {
                    closeOperation();
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                    MessageBox.post(ex);
                }
            }
        });
    }

    private void findPreviousDifference_Static()
    {
        JTable staticResultTable = staticPanel.getResultTable();

        if (staticCurrentRow == 0)
        {
            MessageBox.post(messageRegistry.getString("compare6"), "First Row", MessageBox.WARNING);
            return;
        }

        staticCurrentRow -= 1;

        int columnCount = staticResultTable.getColumnCount();

        for (int inx = 0; inx < columnCount; inx++)
        {
            StaticCompareResultTableCellRenderer renderer = (StaticCompareResultTableCellRenderer)staticResultTable.getCellRenderer(0, inx);
            renderer.releaseHighlightRow(staticResultTable.getSelectedRow());
            renderer.setHighlightRow(staticCurrentRow);
            staticResultTable.scrollRectToVisible(new Rectangle(0, staticCurrentRow * (staticResultTable.getRowHeight() + 2), 1, staticResultTable.getRowHeight()));
        }
        staticResultTable.updateUI();
        ((StaticComparePanel)staticPanel).setFocusWorkspacePanel(((StaticCompareResultTableModel)staticResultTable.getModel()).getComponent(staticCurrentRow));
    }

    private void findNextDifference_Static()
    {
        JTable staticResultTable = staticPanel.getResultTable();
        int rowCount = staticResultTable.getRowCount();

        if (rowCount == 0 || staticCurrentRow == (rowCount - 1))
        {
            MessageBox.post(messageRegistry.getString("compare7"), "Last Row", MessageBox.WARNING);
            return;
        }

        staticCurrentRow += 1;
        int columnCount = staticResultTable.getColumnCount();

        for (int inx = 0; inx < columnCount; inx++)
        {
            StaticCompareResultTableCellRenderer renderer = (StaticCompareResultTableCellRenderer)staticResultTable.getCellRenderer(0, inx);
            renderer.releaseHighlightRow(staticResultTable.getSelectedRow());
            renderer.setHighlightRow(staticCurrentRow);
            staticResultTable.scrollRectToVisible(new Rectangle(0, staticCurrentRow * (staticResultTable.getRowHeight() + 2), 1, staticResultTable.getRowHeight()));
        }
        staticResultTable.updateUI();
        ((StaticComparePanel)staticPanel).setFocusWorkspacePanel(((StaticCompareResultTableModel)staticResultTable.getModel()).getComponent(staticCurrentRow));
    }

    public void excelOperation()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

//        String datasetName = ((StaticComparePanel)staticPanel).getDatasetName();
        String datasetName = "";

//Kogger.debug(getClass(), ">>> datasetName : " + datasetName);

        JTable resultTable = ((StaticComparePanel)staticPanel).getResultTable();

        if (resultTable.getRowCount() == 0)
        {
            MessageBox.post(messageRegistry.getString("compare8"), "No result", MessageBox.WARNING);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return;
        }

        try
        {
            StringBuffer sb = new StringBuffer();
            sb.append("*** Compare List ***\n\n");
            sb.append("A: Item Code\t");
            sb.append(((StaticComparePanel)staticPanel).getSourceItemCode() + "\n");
            sb.append("B: Item Code\t");
            sb.append(((StaticComparePanel)staticPanel).getTargetItemCode() + "\n\n");

            datasetName = "BOM비교_" + ((StaticComparePanel)staticPanel).getSourceItemCode() + "_" + ((StaticComparePanel)staticPanel).getTargetItemCode();

            StringBuffer sbexl = new StringBuffer();

            sbexl.append(sb);

            int rowCnt = resultTable.getRowCount();
            int columnCnt = resultTable.getColumnCount();

            for (int inx = 0; inx < columnCnt; inx++)
            {
                sbexl.append(resultTable.getColumnName(inx));
                sbexl.append("\t");
            }
            sbexl.append("\n");

            for (int inx = 0; inx < rowCnt; inx++)
            {
                for (int jnx = 0; jnx < columnCnt; jnx++)
                {
                    sbexl.append(resultTable.getValueAt(inx, jnx));
                    sbexl.append("\t");
                }
                sbexl.append("\n");
            }

            datasetName=datasetName.replace('/', '~');
            String fileType="xls";
            boolean flag = false;

/************************** JFileChooser 를 이용한 Excel 다운로드... *******************************/

            JFileChooser fchooser = new JFileChooser();

            fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            fchooser.addChoosableFileFilter(new ExcelFilter( "xls", "Microsoft Excel File" ));

            fchooser.setSelectedFile(new File(datasetName + ".xls"));

            int rval = fchooser.showSaveDialog(CompareFrame.this);

            if (rval == JFileChooser.APPROVE_OPTION)
            {
                if(fchooser.getSelectedFile().getName().lastIndexOf(".") == -1)
                {
                    currFileName = fchooser.getSelectedFile().getName();
                }
                else
                {
                    currFileName = fchooser.getSelectedFile().getName().substring(0, fchooser.getSelectedFile().getName().lastIndexOf("."));
                }

                fileDirPath = "" + fchooser.getCurrentDirectory();

                try
                {
                    flag = createFileData(currFileName, sbexl);
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                }
            }

/************************************************************************************************/

            OpenCommand opencommand = new OpenCommand(fileDirPath,currFileName,fileType);
            if(flag) opencommand.executeModeless();
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
            MessageBox.post(ex);
        }
        finally
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private boolean  createFileData(String datasetName, StringBuffer sb)  throws Exception
    {
        String new_filename = datasetName + ".xls";

        try
        {
            File mk = new File(fileDirPath);
            mk.mkdirs();

            File fp = new File(fileDirPath + fileSeparator + new_filename);
            FileOutputStream fos = new FileOutputStream(fp);
            PrintWriter out = new PrintWriter(fos);

            out.println(sb.toString());
            out.flush();
            out.close();
            fos.close();
            return true;
        }
        catch(Exception e)
        {
            MessageBox.post(e);
            return false;
        }
    }

    private void closeOperation()
    {
        BOMRegisterApplicationPanel workspacePanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        JSplitPane splitPane = null;

        if (workspacePanel.getSelectedPanel().equals("BOMEditor"))
        {
            splitPane = ((MainEditorPanel)workspacePanel.getMainEditorPane()).getSplitPane();
            splitPane.setRightComponent(new JPanel());
            splitPane.updateUI();
            splitPane.setDividerLocation((new Double(app.getApplicationPanel().getSize().getWidth() - 20)).intValue());
        }
        else
        {
//            splitPane = ((SearchMainEditorPanel)workspacePanel.getSearchMainEditorPane()).getSplitPane();
//            splitPane.setRightComponent(new JPanel());
//            splitPane.updateUI();
//            splitPane.setDividerLocation((new Double(app.getApplicationPanel().getSize().getWidth() - 20)).intValue());
        }
    }

    public AbstractAIFUIApplication getApplication()
    {
        return app;
    }

    public StaticComparePanel getStaticComparePanel()
    {
        return (StaticComparePanel)staticPanel;
    }

    public void setStaticCurrentRow(int staticCurrentRow)
    {
        this.staticCurrentRow = staticCurrentRow;
    }

    public void setItemCurrentRow(int itemCurrentRow)
    {
        this.itemCurrentRow = itemCurrentRow;
    }

    public int getStaticCurrentRow()
    {
        return staticCurrentRow;
    }

    public int getItemCurrentRow()
    {
        return itemCurrentRow;
    }

    public void setCompareComplete(boolean compareComplete)
    {
        this.compareComplete = compareComplete;
    }

}
