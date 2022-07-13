package e3ps.bom.command.exceldown;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.commands.open.OpenCommand;
import e3ps.bom.framework.util.MessageBox;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class ExcelTempDownDialog extends AbstractAIFDialog
{
    private static final long serialVersionUID = 1L;
    private AbstractAIFUIApplication app;
    private Frame frame;
    private JFrame desktop;
    BOMTreeNode rootNode;

    private String fileSeparator = System.getProperty("file.separator", "/");

/*************** File Path *****************/
    String fileDirPath = "";        // File Directory Path
    String currFileName = "";        // 현재 파일명
/*****************************************/

    public ExcelTempDownDialog(JFrame frame, AbstractAIFUIApplication app, BOMTreeNode rootNode)
    {
        this.app = app;
        this.frame = frame;
        this.desktop = app.getDesktop();
        this.rootNode = rootNode;
        setEvent();
    }

    private void setEvent()
    {
        printOperation();
    }

    private void printOperation()
    {
        String datasetName = "";

        BOMAssyComponent bom = (BOMAssyComponent)rootNode.getBOMComponent();
        datasetName = "Excel_" + bom.toString();

        try
        {
            datasetName = datasetName.replace('/', '~');
            boolean flag = false;
            String fileType = "xls";

/************************** JFileChooser 를 이용한 Excel 다운로드 **********************************/
            JFileChooser fchooser = new JFileChooser();
            fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fchooser.addChoosableFileFilter(new ExcelFilter( "xls", "Microsoft Excel File" ));

            //shin...
            //fchooser.setSelectedFile(new File(datasetName + ".xls"));
            fchooser.setSelectedFile(new File("BOM_Excel_Template.xls"));

            int rval = fchooser.showSaveDialog(desktop);
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
                    flag = createFileData(currFileName);
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                }
            }
/************************************************************************************************/

            OpenCommand opencommand = new OpenCommand(fileDirPath, currFileName, fileType);
            if(flag) opencommand.executeModeless();
        }
        catch(Exception e)
        {
            loadWarningMessage(e.toString());
        }
    }

    private boolean  createFileData(String datasetName)  throws Exception
    {
        String new_filename = datasetName + ".xls";

        WritableWorkbook workbook = Workbook.createWorkbook(new File( fileDirPath + fileSeparator + new_filename ));
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);
        try
        {
            //shin....셀 길이 정해줌.
                sheet.setColumnView(0, 20);
                sheet.setColumnView(1, 20);
                sheet.setColumnView(2, 25);
                sheet.setColumnView(3, 15);
                sheet.setColumnView(4, 20);
                sheet.setColumnView(5, 15);
                sheet.setColumnView(6, 15);
                sheet.setColumnView(7, 15);
                sheet.setColumnView(8, 24);
                sheet.setColumnView(9, 15);


            printBOMListHeader( sheet );

            workbook.write();
            workbook.close();

            return true;
        }
        catch(IOException e)
        {
            workbook.write();
            workbook.close();

            MessageBox mesg = new MessageBox(desktop, e);
            mesg.setModal(true);
            mesg.setVisible(true);
            return false;
        }
    }

    private void printBOMListHeader(WritableSheet sheet)
    {
        try
        {
            jxl.write.Label label = null;

            WritableCellFormat com_format_column = new WritableCellFormat();
            com_format_column.setBackground(Colour.YELLOW);
            com_format_column.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableCellFormat com_format_column1 = new WritableCellFormat();
            com_format_column1.setBorder(Border.ALL, BorderLineStyle.THIN);


            //label = new jxl.write.Label(0, 1, "  ◎ 금형 BOM 등록 표준양식");
            //sheet.addCell(label);

            String cmp[] = new String[10];


            cmp[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00102")/*금형번호*/;
            cmp[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00106")/*금형부품번호*/;
            cmp[2] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00115")/*금형부품설명*/;
            cmp[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/;
            cmp[4] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/;
            cmp[5] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/;
            cmp[6] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/;
            cmp[7] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/;
            cmp[8] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00038")/*CAD_Number(도면유/무)*/;
            cmp[9] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00138")/*대표부품*/;


            for(int i=0; i<10; i++)
            {
                label = new jxl.write.Label(i, 0, cmp[i], com_format_column);
                sheet.addCell(label);

                /*if(i==0 || i==1 || i==2 || i==7 || i==8 || i==10 || i==11 || i==12 || i==20 || i==21)
                {
                    label = new jxl.write.Label(i, 0, cmp[i], com_format_column);
                    sheet.addCell(label);
                }else{
                    label = new jxl.write.Label(i, 0, cmp[i], com_format_column1);
                    sheet.addCell(label);
                }*/
            }

        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void loadWarningMessage(String message)
    {
        MessageBox messagebox = new MessageBox(desktop, message, "Warning", MessageBox.WARNING);
        messagebox.setModal(true);
        messagebox.setVisible(true);
    }
}
