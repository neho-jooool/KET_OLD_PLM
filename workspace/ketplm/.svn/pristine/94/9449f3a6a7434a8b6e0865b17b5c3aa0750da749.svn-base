package ext.ket.yesone.service.internal.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

import e3ps.common.util.PlmDBUtil;
import e3ps.cost.util.DBUtil;
import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.util.FormTypeEnum;
import ext.ket.yesone.xom.AmtOxm;

public class YesoneDao {
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;
    QueryUtil Queryutil = null;

    public YesoneDao(Connection conn, PreparedStatement pstmt, ResultSet rs, KETYesoneBaseDTO baseDto) throws Exception {
	this.conn = conn;
	this.pstmt = pstmt;
	this.rs = rs;

	Queryutil = new QueryUtil(conn, pstmt, rs, baseDto);
    }

    public ArrayList<TreeMap<String, String>> getLoginInfo(KETYesoneBaseDTO baseDto) {

	ArrayList<TreeMap<String, String>> CommonCheck = Queryutil.CommonCheck;
	return CommonCheck;

    }

    public String getBaseChasu(KETYesoneBaseDTO baseDto) throws Exception {

	String emp_no = baseDto.getEmp_no();
	String year = baseDto.getYear();
	String site = baseDto.getSite();

	this.ParamNullCheck(baseDto);

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT COUNT(*)+1 AS CHASU FROM KETYesoneBaseDTO@LEGACY WHERE EMP_NO='" + emp_no + "' AND YEAR='" + year
	        + "' AND SITE='" + site + "'");

	ArrayList<TreeMap<String, String>> DataSet = Queryutil.ExceuteSelect(sb.toString());

	String chasu = null;

	for (TreeMap<String, String> ds : DataSet) {
	    chasu = ds.get("CHASU");
	}

	return chasu;
    }

    public void deleteLegacy(KETYesoneBaseDTO baseDto) throws Exception {

	this.ParamNullCheck(baseDto);

	String Query = "DELETE FROM KET.HCLRN12@LEGACY WHERE CLR_YEAR = '" + baseDto.getYear() + "' AND EMP_NO = '" + baseDto.getEmp_no()
	        + "'";

	pstmt = conn.prepareStatement(Query);
	pstmt.executeQuery();

	Query = "DELETE FROM KET.HCLRN20@LEGACY WHERE YEAR = '" + baseDto.getYear() + "' AND EMP_NO = '" + baseDto.getEmp_no() + "'";

	pstmt = conn.prepareStatement(Query);
	pstmt.executeQuery();

	Query = "DELETE FROM KET.HCLRN16@LEGACY WHERE YEAR = '" + baseDto.getYear() + "' AND EMP_NO = '" + baseDto.getEmp_no() + "'";

	pstmt = conn.prepareStatement(Query);
	pstmt.executeQuery();

	Query = "DELETE FROM KET.HCLRN14@LEGACY WHERE YEAR = '" + baseDto.getYear() + "' AND EMP_NO = '" + baseDto.getEmp_no() + "'";

	pstmt = conn.prepareStatement(Query);
	pstmt.executeUpdate();

	DBUtil.close(pstmt);

    }

    public void import2KETYesoneBaseDTO(KETYesoneBaseDTO baseDto) throws Exception {

	this.ParamNullCheck(baseDto);

	StringBuffer sb = new StringBuffer();

	sb.append("INSERT INTO KETYesoneBaseDTO@LEGACY (");
	sb.append("RESID,NAME,CHASU,EMP_NO,YEAR,SITE");
	sb.append(")VALUES(");
	sb.append("'" + StringUtils.defaultString(baseDto.getResid()) + "',");
	sb.append("'" + StringUtils.defaultString(baseDto.getName()) + "',");
	sb.append("'" + StringUtils.defaultString(baseDto.getChasu()) + "',");
	sb.append("'" + StringUtils.defaultString(baseDto.getEmp_no()) + "',");
	sb.append("'" + StringUtils.defaultString(baseDto.getYear()) + "',");
	sb.append("'" + StringUtils.defaultString(baseDto.getSite()) + "'");
	sb.append(")");

	pstmt = conn.prepareStatement(sb.toString());
	pstmt.executeQuery();

	PlmDBUtil.close(pstmt);
    }

    public String getMaxChasu(KETYesoneBaseDTO baseDto) throws Exception {

	this.ParamNullCheck(baseDto);

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT MAX(to_number(chasu)) AS chasu FROM KETYesoneBaseDTO@LEGACY WHERE EMP_NO='" + baseDto.getEmp_no() + "' AND YEAR='"+ baseDto.getYear()+"'");

	ArrayList<TreeMap<String, String>> DataSet = Queryutil.ExceuteSelect(sb.toString());

	String chasu = null;

	for (TreeMap<String, String> ds : DataSet) {
	    chasu = ds.get("CHASU");
	}

	return chasu;
    }

    public KETYesoneBaseDTO import2DtoMappingData(Object yesoneDto, KETYesoneBaseDTO baseDto, int pk_helper) throws Exception {
	/*
	 * FormTypeEnum에 서식코드별로 Class정보가 정의되어있다. Class로 대상 테이블을 찾아 insert 작업을 수행한다
	 * 
	 * 해당 메서드를 수행하다 에러가 발생했을 경우 KETYesoneBaseDTO에 멤버변수가 추가됐을 가능성이 가장 크므로 확인할것
	 */

	this.ParamNullCheck(baseDto);

	PreparedStatement pstmt = null;
	StringBuffer sb = new StringBuffer();

	String ClassName = FormTypeEnum.getClassName(yesoneDto);

	Class<?> TargetClass = Class.forName(ClassName);
	Object targetObject = TargetClass.newInstance();

	targetObject = yesoneDto;// 서식코드로 정의된 클래스를 찾아서 Object로 다운캐스팅

	// targetObject에 상속받은 KETYesoneBaseDTO의 멤버변수를 가져올 수 없으므로 BeanUtils를 사용한다
	String form_cd = StringUtils.defaultString(BeanUtils.getProperty(targetObject, "form_cd"));
	String resid = StringUtils.defaultString(BeanUtils.getProperty(targetObject, "resid"));
	String name = StringUtils.defaultString(BeanUtils.getProperty(targetObject, "name"));

	System.out.println(targetObject.toString());
	System.out.println(form_cd);

	FormTypeEnum en = FormTypeEnum.IsChild(form_cd);

	if (en.isIsChild()) {// xml의 4레벨에 해당하는 반복데이터가 있을경우. 현재 데이터를 파싱하여 가져오고 있지만 실제 KET 연말정산 로직에 사용되지는 않고 있다.
	    Method invokeMethod = targetObject.getClass().getMethod("getMM_List", null);
	    ArrayList<AmtOxm> mmlist = (ArrayList<AmtOxm>) invokeMethod.invoke(targetObject, null);
	    for (Object child : mmlist) {
		// child.toString();
	    }
	}

	// 서식코드별 가변데이터들을 가공한다
	baseDto.setForm_cd(form_cd+"_"+Integer.valueOf(pk_helper));
	baseDto.setResid(resid);
	baseDto.setName(name);

	String site = Queryutil.getCommonInfoDEC(resid, "SITE");

	boolean goFlag = StringUtils.isNotEmpty(site);//site가 없다는 것은 국세청 pdf에는 있는 부양가족을 한국단자연말정산시스템에 입력하지 않았다는 뜻 따라서 없으면 패스한다

	if (goFlag) {
	    baseDto.setSite(site);

	    String TargetTable = ClassUtils.getShortClassName(TargetClass);// 서식코드별로 정의된 Class이름을 가져온다.Insert 대상 테이블로 매핑하기위함

	    sb = new StringBuffer();

	    StringBuffer fieldsb = new StringBuffer();

	    sb.append("INSERT INTO " + TargetTable + "@LEGACY(\n");

	    // KETYesoneBaseDTO 에 선언된 멤버변수를 가져와서 해당 TABLE컬럼과 매핑한다
	    Field[] basefield = baseDto.getClass().getDeclaredFields();

	    for (int i = 0; i < basefield.length; i++) {
		if (!"MM_List".equals(basefield[i].getName())) {
		    sb.append(basefield[i].getName() + ",\n");
		}
	    }

	    // 서식코드별로 정의된 Class에 선언된 멤버변수를 가져와서 해당 TABLE컬럼과 매핑한다
	    Field[] targetfield = targetObject.getClass().getDeclaredFields();

	    for (int i = 0; i < targetfield.length; i++) {
		if (!"serialVersionUID".equals(targetfield[i].getName())) {
		    fieldsb.append(targetfield[i].getName() + ",\n");
		}
	    }
	    sb.append(StringUtils.removeEnd(fieldsb.toString(), ",\n"));
	    sb.append(")\nVALUES (\n");

	    fieldsb = null;
	    fieldsb = new StringBuffer();

	    // KETYesoneBaseDTO 에 선언된 멤버변수의 값을 가져와서 해당 TABLE컬럼 Value에 매핑한다
	    for (int i = 0; i < basefield.length; i++) {
		if (!"MM_List".equals(basefield[i].getName())) {// 4레벨 반복데이터는 현재 사용하지 않고있음
		    sb.append("'" + StringUtils.defaultString(BeanUtils.getProperty(baseDto, basefield[i].getName())) + "',\n");
		}
	    }

	    // 서식코드별로 정의된 Class에 멤버변수의 값을 가져와서 해당 TABLE컬럼 Value에 매핑한다
	    for (int i = 0; i < targetfield.length; i++) {
		if (!"serialVersionUID".equals(targetfield[i].getName())) {// serialVersionUID는 insert대상이 아니므로.
		    fieldsb.append("'" + StringUtils.defaultString(BeanUtils.getProperty(targetObject, targetfield[i].getName())) + "',\n");
		}
	    }
	    sb.append(StringUtils.removeEnd(fieldsb.toString(), ",\n"));
	    sb.append(")\n");

	    System.out.println(sb);

	    pstmt = conn.prepareStatement(sb.toString());
	    pstmt.executeQuery();
	    PlmDBUtil.close(pstmt);
	}

	return baseDto;

    }

    public void insertLegacy(String form_cd, KETYesoneBaseDTO baseDto, String maxChasu) throws Exception {

	this.ParamNullCheck(baseDto);

	Queryutil.ExceuteInsert(baseDto, form_cd, maxChasu);

    }

    public void updateLegacy(String form_cd, KETYesoneBaseDTO baseDto, String maxChasu) throws Exception {

	this.ParamNullCheck(baseDto);

	Queryutil.ExceuteUpdate(baseDto, form_cd, maxChasu);

    }

    public void ParamNullCheck(KETYesoneBaseDTO baseDto) throws Exception {

	String emp_no = baseDto.getEmp_no();
	String year = baseDto.getYear();
	String site = baseDto.getSite();

	if (StringUtils.isEmpty(emp_no) || StringUtils.isEmpty(year) || StringUtils.isEmpty(site)) {
	    throw new Exception("Parameter is null!");
	}
    }

}
