package e3ps.bom.command.saveexcel;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Enumeration;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.part.WTPart;
import e3ps.bom.command.confirmbom.ConfirmLineQry;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.commands.open.OpenCommand;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class SaveExcelDialog extends AbstractAIFDialog
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

    public SaveExcelDialog(JFrame frame, AbstractAIFUIApplication app, BOMTreeNode rootNode)
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
            fchooser.setSelectedFile(new File(datasetName + ".xls"));

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
            BOMAssyComponent bom = (BOMAssyComponent)rootNode.getBOMComponent();
            String headCode = String.valueOf(bom);

            KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
            String strType = "";
            try {
                WTPart part = kh.searchItem(headCode);
                strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);  //부품유형
            } catch (Exception e) {
                Kogger.error(getClass(), e);
            }

            //shin....셀 길이 정해줌.
            sheet.setColumnView(0, 15);        // 부품번호
            sheet.setColumnView(1, 7);            // 구분
            sheet.setColumnView(2, 7);        // SEQ
            sheet.setColumnView(3, 7);        // 레벨

            if(PartUtil.isProductType(strType))
            {
                sheet.setColumnView(4, 10);    // ItemSeq
                sheet.setColumnView(5, 30);    // 부품명
                sheet.setColumnView(6, 10);    // 수량
                sheet.setColumnView(7, 10);    // 기본단위
                sheet.setColumnView(8, 7);    // 버전
                sheet.setColumnView(9, 10);    // 결재상태
                sheet.setColumnView(10, 15);    // 대체부품번호
                sheet.setColumnView(11, 15);    // Eco No
                sheet.setColumnView(12, 18);    // Check-out
            }else{
                sheet.setColumnView(4, 30);    // 부품명
                sheet.setColumnView(5, 10);    // 수량
                sheet.setColumnView(6, 10);    // 기본단위
                sheet.setColumnView(7, 10);    // 재질
                sheet.setColumnView(8, 10);    // 경도(From)
                sheet.setColumnView(9, 10);    // 경도(To)
                sheet.setColumnView(10, 10);    // 설계일자
                sheet.setColumnView(11, 7);    // 버전
                sheet.setColumnView(12, 10);    // 결재상태
                sheet.setColumnView(13, 15);    // Eco No
                sheet.setColumnView(14, 18);    // Check-out
//                sheet.setColumnView(15, 18);  // ItemSeq 지우면서 한칸씩 땡겨짐
            }

            printBOMListHeader( sheet , strType );

            Enumeration enum0 = rootNode.preorderEnumeration();
            while(enum0.hasMoreElements())
            {
                BOMTreeNode allListNode = (BOMTreeNode)enum0.nextElement();
                BOMAssyComponent bomassy = (BOMAssyComponent)allListNode.getBOMComponent();
                printBOMRowData_cell(sheet, bomassy, strType);
            }

            //printBOMListFooter( sheet );

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

    private void printBOMListHeader(WritableSheet sheet, String strType)
    {
        try
        {
            jxl.write.Label label = null;

            WritableCellFormat com_format_column = new WritableCellFormat();
            com_format_column.setBackground(Colour.YELLOW);
            com_format_column.setBorder(Border.ALL, BorderLineStyle.THIN);

            String cmp[] = new String[17];

            if(PartUtil.isProductType(strType))
            {
                cmp[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/;
                cmp[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/;
                cmp[2] = "SEQ";
                cmp[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/;
                cmp[4] = "Item Seq";
                cmp[5] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/;
                cmp[6] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/;
                cmp[7] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/;
                cmp[8] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/;
                cmp[9] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/;
                cmp[10] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00133")/*대체부품번호*/;
                cmp[11] = "ECO No";
                cmp[12] = "Check-out";

                for(int i=0; i<13; i++)
                {
                    label = new jxl.write.Label(i, 0, cmp[i], com_format_column);
                    sheet.addCell(label);
                }
            }else{
                cmp[0] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/;
                cmp[1] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00098")/*구분*/;
                cmp[2] = "SEQ";
                cmp[3] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00145")/*레벨*/;
//                cmp[4] = "Item Seq";
                cmp[4] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/;
                cmp[5] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/;
                cmp[6] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00118")/*기본단위*/;
                cmp[7] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/;
                cmp[8] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/;
                cmp[9] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/;
                cmp[10] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/;
                cmp[11] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00158")/*버전*/;
                cmp[12] = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00072")/*결재상태*/;
                cmp[13] = "ECO No";
                cmp[14] = "Check-out";
                for(int i=0; i<15; i++)
                {
                    label = new jxl.write.Label(i, 0, cmp[i], com_format_column);
                    sheet.addCell(label);
                }
            }
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    private void printBOMRowData_cell(WritableSheet sheet, BOMAssyComponent bomassy , String strType)
    {
        try
        {
            WritableCellFormat com_format_column1 = new WritableCellFormat();
            com_format_column1.setBorder(Border.ALL, BorderLineStyle.THIN);
            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();


            NumberFormat format = NumberFormat.getInstance();
            format.setMinimumFractionDigits(3);
//shin......부품 설정.---------------------------------------------------
            String cmp[] = null;
            if(PartUtil.isProductType(strType)) {        // 제품
                cmp = new String[13];
            }else{                            // 금형
                cmp = new String[15];
            }

            String itemCd = String.valueOf(bomassy);
            if(itemCd.equals("Empty") || itemCd == null ){
                itemCd = "";
            }
            KETBOMHeaderQueryBean kh = new KETBOMHeaderQueryBean();
            String bomFlag = "NEW";
            String result = "";
            try{
                WTPart part = kh.searchItem(itemCd);
                bomFlag = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpBOMFlag);
                if(bomFlag.equals("NEW")){
                    result = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00252")/*신규*/;
                }else{
                    result = KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00120")/*기존*/;
                }
            }catch(Exception e){}

            ConfirmLineQry cq = new ConfirmLineQry();
            String checkStr = Utility.checkNVL(bomassy.getCheckOutStr());
            String name = "";
            String email = "";
            String dept = "";

            try{
                 name = checkStr.substring(0,checkStr.indexOf("("));
                 email = checkStr.substring(checkStr.indexOf("(")+1, checkStr.indexOf(")"));

                 dept = cq.getDeptData(name,email);
                 checkStr =     dept +"/"+ name;
            }catch(Exception e){}

            String strVer = Utility.checkNVL(bomassy.getVersionStr());

            if(PartUtil.isProductType(strType))            // 제품
            {
                cmp[0] = Utility.checkNVL(bomassy.toString());
                cmp[1] = Utility.checkNVL(result);
                cmp[2] = String.valueOf(bomassy.getSeqInt().intValue());
                cmp[3] = String.valueOf(bomassy.getLevelInt().intValue());
                cmp[4] = String.valueOf(bomassy.getItemSeqInt().intValue());
                cmp[5] = Utility.checkNVL(bomassy.getDescStr());
                cmp[6] = format.format(bomassy.getQuantityDbl());
                cmp[7] = bean.getUnitDisplayValue(Utility.checkNVL(bomassy.getUitStr()));
                cmp[8] = strVer.substring(0, 1);
                cmp[9] = Utility.checkNVL(bomassy.getStatusKrStr());
                cmp[10] = Utility.checkNVL(bomassy.getSubItemCodeStr());
                cmp[11] = Utility.checkNVL(bomassy.getEcoNoStr());
                cmp[12] = checkStr;

                jxl.write.Label label = null;

                int rowCnt = sheet.getRows();
                for(int i=0; i<13; i++)
                {
                    label = new jxl.write.Label(i, rowCnt, cmp[i] , com_format_column1);

                    sheet.addCell(label);
                }
            }else{                                // 금형
                String strQuantity = bomassy.getQuantityDbl()+"";                    // 수량정보를 정수로 표현하기 위해 처리함
                strQuantity = strQuantity.substring(0, strQuantity.indexOf("."));

                cmp[0] = Utility.checkNVL(bomassy.toString());
                cmp[1] = Utility.checkNVL(result);
                cmp[2] = String.valueOf(bomassy.getSeqInt().intValue());
                cmp[3] = String.valueOf(bomassy.getLevelInt().intValue());
//                cmp[4] = String.valueOf(bomassy.getItemSeqInt().intValue());
                cmp[4] = Utility.checkNVL(bomassy.getDescStr());
                cmp[5] = strQuantity;
                cmp[6] = bean.getUnitDisplayValue(Utility.checkNVL(bomassy.getUitStr()));
                cmp[7] = Utility.checkNVL(bomassy.getMaterialStr());
                cmp[8] = Utility.checkNVL(bomassy.getHardnessFrom());
                cmp[9] = Utility.checkNVL(bomassy.getHardnessTo());
                cmp[10] = Utility.checkNVL(bomassy.getDesignDate());
                cmp[11] = strVer.substring(0, 1);
                cmp[12] = Utility.checkNVL(bomassy.getStatusKrStr());
                cmp[13] = Utility.checkNVL(bomassy.getEcoNoStr());
                cmp[14] = checkStr;

                jxl.write.Label label = null;

                int rowCnt = sheet.getRows();
                for(int i=0; i<16; i++)
                {
                    label = new jxl.write.Label(i, rowCnt, cmp[i] , com_format_column1);

                    sheet.addCell(label);
                }
            }

//------------------------------------------------------------------------
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
