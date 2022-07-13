package e3ps.cost.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import xlib.cmc.GridData;
import xlib.cmc.OperateGridData;

import e3ps.cost.control.CostAccCtl;

public class CostAccTestServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        GridData gdReq = null;
        GridData gdReq3 = null;
        GridData gdReq4 = null;
        GridData gdRes = null;
        GridData gdRes4 = null;
        String page_name = "";
        //  Encode Type을 UTF-8로 변환한다.
        req.setCharacterEncoding("utf-8");
        res.setContentType("text / html; charset=utf-8");
        PrintWriter out = res.getWriter();
        try {
            //  WISEGRID_DATA 라는 Param으로 WiseGrid의 전문이 올라온다.
            String rawData = req.getParameter("WISEGRID_DATA");
            String rawData3 = req.getParameter("WISEGRID_DATA3");
            String rawData4 = req.getParameter("WISEGRID_DATA4");

            //  올라온 전문을 Parsing하여 자료구조 형태로 반환해준다.
            gdReq = OperateGridData.parse(rawData);
            //  전달받은 파라미터값을 가져온다.
            page_name = gdReq.getParam("page_name");
            String mode = gdReq.getParam("mode");

            String pk_cr_group = gdReq.getParam("pk_cr_group");
            String rev_no = gdReq.getParam("rev_no");
            String table_row = gdReq.getParam("table_row");

            if ("cost_acc".equals(mode)){
                gdReq3 = OperateGridData.parse(rawData3);
                if("work".equals(page_name)){
                    gdReq4 = OperateGridData.parse(rawData4);
                }
                CostAccCtl accCtl = new CostAccCtl();
                ArrayList accList = accCtl.doQry(page_name, gdReq, gdReq3, gdReq4);
                Hashtable accMap = null;

                for(int i = 0; i < accList.size() ; i++){
                    accMap = (Hashtable)accList.get(i);
                    gdRes = (GridData)accMap.get("gdRes");
                    gdRes4 = (GridData)accMap.get("gdRes4");
                }

            }
        }catch (Exception e) {
            gdRes = new GridData();
            gdRes.setMessage("Error: "  +  e.getMessage());
            gdRes.setStatus("false");
            e.printStackTrace();
        }finally{
            try
            {
                 //  WiseGrid에 전달할 데이터가 담긴 GridData 객체와 미리 생성해 놓은 PrintWriter 객체를 넘겨
                 //  데이터를 Parsing 한 후 전송합니다.

                if("work".equals(page_name)){
                    //OperateGridData.addGridRawData("WISEGRID4",gdRes4,out);
                    String[] strKey = new String[2];
                    strKey[0] = "WISEGRIDDATA_MASTER";
                    strKey[1] = "WISEGRID4";
                    GridData[] gdobj = new GridData[2];
                    gdobj[0] = gdRes;
                    gdobj[1] = gdRes4;
                    OperateGridData.write(strKey, gdobj, res.getWriter());
                    //System.out.println(OperateGridData.getSendData(gdRes4));
                }else{
                    //String sendData = OperateGridData.getSendData(gdRes4);
                    //System.out.println("senddata==="+sendData);
                    OperateGridData.write(gdRes, out);
                }



            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


}















