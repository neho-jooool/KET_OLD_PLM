<%@page import="e3ps.project.beans.ProgramManagerHelper"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.drm.E3PSDRMHelper"  %>
<%@page import="e3ps.project.outputtype.ModelPlan"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "
				wt.fc.*,
				java.util.ArrayList,
				wt.query.ClassAttribute,
				wt.query.OrderBy,
				wt.query.QuerySpec,
				wt.query.SearchCondition,
				wt.query.StringSearch
				"%>
<%@page import="java.io.FileInputStream"  %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import= "jxl.Workbook,
							jxl.write.WritableWorkbook,
							jxl.write.WritableSheet,
							jxl.write.WritableCellFormat,
							jxl.write.Label,
							jxl.format.Border,
							jxl.format.BorderLineStyle"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<%
	String customer =  request.getParameter("customer");
	String cartype = request.getParameter("cartype");

	String customerHidden = request.getParameter("customerHidden");
	String cartypeHidden = request.getParameter("cartypeHidden");

	String sWtHome = "";
	String sFilePath = "",sFileName = "";

	try
	{
		sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
		sFilePath = sWtHome + "/codebase/jsp/project/report" ;

		SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
		sFileName = "ProgramList_" +  ff.format(new Date()) + ".xls";

		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
		response.setHeader("Content-Disposition", "attachment;filename="+ sFileName );

		WritableWorkbook writebook = Workbook.createWorkbook( new File(sFilePath + "/" + sFileName) );
		WritableSheet s1 = writebook.createSheet("차종차일정 목록", 1);

		QuerySpec qs = new QuerySpec();

		int idx = qs.addClassList(ModelPlan.class, true);

		if(cartype!=null){
			int idxopt = qs.addClassList(OEMProjectType.class, false);
			if(qs.getConditionCount()>0)qs.appendAnd();
			SearchCondition sc1 = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
					OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
			qs.appendWhere(sc1, new int[] { idx, idxopt });

			qs.appendAnd();

			StringSearch stringsearch = new StringSearch("name");
			stringsearch.setValue("%"+cartype.trim()+"%");
			qs.appendWhere(stringsearch.getSearchCondition(OEMProjectType.class),new int[]{idxopt});

		}

		if(customer!=null){
			int idxopt2 = qs.addClassList(OEMProjectType.class, false);
			int idxnum = qs.addClassList(NumberCode.class, false);
			if(qs.getConditionCount()>0)qs.appendAnd();
			SearchCondition sc2 = new SearchCondition(new ClassAttribute(ModelPlan.class, "carTypeReference.key.id"), "=", new ClassAttribute(
					OEMProjectType.class, "thePersistInfo.theObjectIdentifier.id"));
			qs.appendWhere(sc2, new int[] { idx, idxopt2 });

			qs.appendAnd();

			SearchCondition sc3 = new SearchCondition(new ClassAttribute(OEMProjectType.class, "makerReference.key.id"), "=", new ClassAttribute(
					NumberCode.class, "thePersistInfo.theObjectIdentifier.id"));
			qs.appendWhere(sc3, new int[] { idxopt2, idxnum });

			qs.appendAnd();

			StringSearch stringsearch = new StringSearch("name");
			stringsearch.setValue("%"+customer.trim()+"%");
			qs.appendWhere(stringsearch.getSearchCondition(NumberCode.class),new int[]{idxnum});

		}

		qs.appendOrderBy(new OrderBy(new ClassAttribute(ModelPlan.class,"thePersistInfo.createStamp"), false), new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(qs);

			  WritableCellFormat headerFormat = new WritableCellFormat();
			  headerFormat.setBorder(Border.ALL , BorderLineStyle.THIN);
			  headerFormat.setBackground(jxl.format.Colour.LIGHT_TURQUOISE);
			  headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
			  headerFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

			int i= 2;

			Label lr = new Label(0, 0, "자동차 일정" );
			s1.addCell(lr);

			lr = new Label(0, i, "번호", headerFormat );
			s1.addCell(lr);
			lr = new Label(1, i, "고객", headerFormat );
			s1.addCell(lr);
			lr = new Label(2, i, "차종", headerFormat );
			s1.addCell(lr);
			lr = new Label(3, i, "형태", headerFormat );
			s1.addCell(lr);
			lr = new Label(4, i, "차종명", headerFormat );
			s1.addCell(lr);
			lr = new Label(5, i, "생산대수", headerFormat );
			s1.addCell(lr);
			lr = new Label(6, i, "EVENT", headerFormat );
			s1.addCell(lr);
			i++;
			lr = new Label(6, i, "Model 고정", headerFormat );
			s1.addCell(lr);
			lr = new Label(7, i, "PROTO", headerFormat );
			s1.addCell(lr);
			lr = new Label(8, i, "PILOT1", headerFormat );
			s1.addCell(lr);
			lr = new Label(9, i, "PILOT2", headerFormat );
			s1.addCell(lr);
			lr = new Label(10, i, "M", headerFormat );
			s1.addCell(lr);
			lr = new Label(11, i, "SOP", headerFormat );
			s1.addCell(lr);

			int h=1;
			String state="";
			ReferenceFactory f = new ReferenceFactory();
			while(qr.hasMoreElements()){
				i++;
		  		Object[] o = (Object[])qr.nextElement();
		  		ModelPlan mp = (ModelPlan)o[0];

		  		String mpCustomer = mp.getCarType().getMaker().getName();
		  		String mpCarType = mp.getCarType().getName();
		  		String mpFormType = mp.getFormType()==null?"":mp.getFormType().getName();
		  		String mpModelName = mp.getModelName()==null?"":mp.getModelName();
		  		String mpOid = mp.getPersistInfo().getObjectIdentifier().toString();

		  		int y1 = Integer.parseInt(mp.getYield1()==null?"0":mp.getYield1());
		        int y2 = Integer.parseInt(mp.getYield2()==null?"0":mp.getYield2());
		        int y3 = Integer.parseInt(mp.getYield3()==null?"0":mp.getYield3());
		        int y4 = Integer.parseInt(mp.getYield4()==null?"0":mp.getYield4());
		        int y5 = Integer.parseInt(mp.getYield5()==null?"0":mp.getYield5());
		        int y6 = Integer.parseInt(mp.getYield6()==null?"0":mp.getYield6());
		        int y7 = Integer.parseInt(mp.getYield7()==null?"0":mp.getYield7());
		        int y8 = Integer.parseInt(mp.getYield8()==null?"0":mp.getYield8());
		        int y9 = Integer.parseInt(mp.getYield9()==null?"0":mp.getYield9());
		        int y10 = Integer.parseInt(mp.getYield10()==null?"0":mp.getYield10());
		        int sum = y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10 ;


		        String op1Date ="";
		        String op2Date = "";
		        String op3Date = "";
		        String op4Date ="";
		        String op5Date = "";
		        String op6Date = "";

		        OEMPlan op1 =  ProgramManagerHelper.getCalendar2(mpOid, 1);
		        if(op1!=null){
		        	op1Date = DateUtil.getTimeFormat(op1.getOemEndDate(),"yyyy-MM-dd");
		        }

		        OEMPlan op2 =  ProgramManagerHelper.getCalendar2(mpOid, 2);
		        if(op2!=null){
			        op2Date = DateUtil.getTimeFormat(op2.getOemEndDate(),"yyyy-MM-dd");
		        }

		        OEMPlan op3 =  ProgramManagerHelper.getCalendar2(mpOid, 3);
		        if(op3!=null){
			        op3Date = DateUtil.getTimeFormat(op3.getOemEndDate(),"yyyy-MM-dd");
		        }

		        OEMPlan op4 =  ProgramManagerHelper.getCalendar2(mpOid, 4);
		        if(op4!=null){
			        op4Date = DateUtil.getTimeFormat(op4.getOemEndDate(),"yyyy-MM-dd");
		        }

		        OEMPlan op5 =  ProgramManagerHelper.getCalendar2(mpOid, 5);
		        if(op5!=null){
			        op5Date = DateUtil.getTimeFormat(op5.getOemEndDate(),"yyyy-MM-dd");
		        }

		        OEMPlan op6 =  ProgramManagerHelper.getCalendar(mpOid, "SOP");
		        //if(op3!=null){
			        op6Date = DateUtil.getTimeFormat(op6.getOemEndDate(),"yyyy-MM-dd");
		        //}

		        lr = new Label(0, i, Integer.toString(h++));
				s1.addCell(lr);

				lr = new Label(1, i, mpCustomer);
				s1.addCell(lr);

				lr = new Label(2, i, mpCarType);
				s1.addCell(lr);

				lr = new Label(3, i, mpFormType);
				s1.addCell(lr);

				lr = new Label(4, i, mpModelName);
				s1.addCell(lr);

				lr = new Label(5, i, Integer.toString(sum));
				s1.addCell(lr);

				lr = new Label(6, i, op1Date);
				s1.addCell(lr);

				lr = new Label(7, i, op2Date);
				s1.addCell(lr);

				lr = new Label(8, i, op3Date);
				s1.addCell(lr);

				lr = new Label(9, i, op4Date);
				s1.addCell(lr);

				lr = new Label(10, i, op5Date);
				s1.addCell(lr);

				lr = new Label(11, i, op6Date);
				s1.addCell(lr);



			}

			s1.mergeCells(0,2,0,3);
			s1.mergeCells(1,2,1,3);
			s1.mergeCells(2,2,2,3);
			s1.mergeCells(3,2,3,3);
			s1.mergeCells(4,2,4,3);
			s1.mergeCells(5,2,5,3);
			s1.mergeCells(6,2,11,2);

			writebook.write();
			writebook.close();

			try
			{
				File drmfile = new File(sFilePath+ "/" + sFileName);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// Added by MJOH, 2011-04-18
				// 엑셀 다운로드 파일 DRM 암호화 적용
				//String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
				//drmfile = E3PSDRMHelper.downloadExcel( drmfile, sFileName, contentID, request.getRemoteAddr() );
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		        FileInputStream fin = new FileInputStream(drmfile);
		        int ifilesize = (int)drmfile.length();
		        byte b[] = new byte[ifilesize];

		        response.setContentLength(ifilesize);
		        response.setContentType("application/vnd.ms-excel;charset=EUC-KR");
		        response.setHeader("Content-Disposition","attachment; filename="+sFileName+";");

		        out.clear();
		        out.close();

		        ServletOutputStream oout = response.getOutputStream();
		        fin.read(b);
		        oout.write(b,0,ifilesize);

		        oout.close();
		        fin.close();
		        drmfile.delete();
			}
			catch( Exception e )
			{
			    Kogger.error(e);
			   throw e;
			}
	}
	catch(IllegalStateException e1)
	{
	    Kogger.error(e1);
	}
	catch(IOException e2)
	{
	    Kogger.error(e2);
	}
	catch(Exception e3)
	{
	    Kogger.error(e3);
	}
%>
