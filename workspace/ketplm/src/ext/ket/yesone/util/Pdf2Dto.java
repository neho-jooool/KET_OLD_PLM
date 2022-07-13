package ext.ket.yesone.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.entity.KETYesoneDTO;
import ext.ket.yesone.xom.AmtOxm;

/* 연말정산 xml을 기준
 * 1. 서식코드만큼 자바 dto 오브젝트를 생성할 수 있는 .java 파일 생성
 * 2. 서식코드만큼 db table을 생성할 수 있는 스크립트 파일 생성
 * 3. xml오브젝트 필드 목록 파일 생성(xom 패키지에 있는 java object에 멤버변수생성목적)
 */

public class Pdf2Dto {

    final private String filePath = "D:\\ptc\\yesone\\dto";
    final private String sqlfilePath = "D:\\ptc\\yesone\\dto\\db";
    
    

    public void CreateJavaFile(KETYesoneDTO ketyesoneDTO) throws IOException, JDOMException, Exception {
	
	// String filePath = "D:\\ptc\\yesone\\dto";

	File dir = new File(filePath);

	if (!dir.isDirectory()) {
	    dir.mkdirs();
	}
	
	dir = new File(sqlfilePath);
	
	if (!dir.isDirectory()) {
	    dir.mkdirs();
	}

	String ClassName = null;

	final Object AoClazz = AmtOxm.class.getName();
	final Object BaseClazz = KETYesoneBaseDTO.class.getName();

	String strXml = ketyesoneDTO.getStrXml();
	Document doc = new SAXBuilder().build(new StringReader(strXml));

	Element root = doc.getRootElement();

	List<Element> formlist = root.getChildren("form");

	String form_cd = null;
	String attrName = null;

	TreeMap<String, String> ManOxmAttr = new TreeMap<String, String>();
	TreeMap<String, String> DataOxmAttr = new TreeMap<String, String>();
	TreeMap<String, String> DataOxmField = new TreeMap<String, String>();
	
	String dbfile = sqlfilePath + "\\YesoneDBscript.sql";
	
	File Dfile = new File(dbfile);
	BufferedWriter Dwriter = new BufferedWriter(new FileWriter(Dfile));

	for (Element formElemnet : formlist) {

	    form_cd = formElemnet.getAttributeValue("form_cd");// 서식코드

	    ClassName = this.getClassName(form_cd);// FormTypeEnum에 정의된 Class이름을 가져온다.. java파일명으로 사용함

	    String javafilePath = filePath + "\\" + ClassName + ".java";

	    File file = new File(javafilePath);
	    
	    Dwriter.append("ALTER TABLE KET."+ClassName+"\n");
            Dwriter.append("DROP PRIMARY KEY CASCADE;\n\n");
            Dwriter.append("DROP TABLE KET."+ClassName+" CASCADE CONSTRAINTS;\n\n");

	    Dwriter.append("CREATE TABLE KET."+ClassName+"\n");
	    Dwriter.append("(\n");
	    
	    /*
	     * 1.Enum에 정의된 클래스목록만큼 자바(dto) 파일을 생성한다
	     * 2.db table script 파일을 생성한다
	     */
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));

	    writer.append("package ext.ket.yesone.entity;\n\n");
	    writer.append("public class " + ClassName + " extends KETYesoneBaseDTO{\n\n");
	    writer.append("    private static final long serialVersionUID = 1L;\n\n");

	    List<Attribute> manlist = formElemnet.getChild("man").getAttributes(); // 2레벨 man의 attributelist

	    List<Attribute> attrlist = formElemnet.getChild("man").getChild("data").getAttributes(); // 3레벨 data의 attributelist

	    List<Element> datalist = formElemnet.getChild("man").getChild("data").getChildren(); // 4레벨 data의 필드 리스트

	    /*
	     * 1단계 man의 attribute를 가져온다
	     */
	    writer.append("//Start Man Attribute(ManOxm.java)\n\n");

	    Iterator<Attribute> manIter = manlist.iterator();
	    
	    String dbtype = "    VARCHAR2(200 BYTE),\n";
	    
	    String dbcolumn = "";
	    StringBuffer dbSpace = new StringBuffer();
	    
	    dbSpace.append("TABLESPACE KET_A\n");
	    dbSpace.append("PCTUSED    0\n");
            dbSpace.append("PCTFREE    10\n");
            dbSpace.append("INITRANS   1\n");
            dbSpace.append("MAXTRANS   255\n");
            dbSpace.append("STORAGE    (\n");
            dbSpace.append("            INITIAL          16672K\n");
            dbSpace.append("            MINEXTENTS       1\n");
            dbSpace.append("            MAXEXTENTS       UNLIMITED\n");
            dbSpace.append("            PCTINCREASE      0\n");
            dbSpace.append("            BUFFER_POOL      DEFAULT\n");
            dbSpace.append("           )\n");
            dbSpace.append("LOGGING\n");
            dbSpace.append("NOCOMPRESS\n");
            dbSpace.append("NOCACHE\n");
            dbSpace.append("NOPARALLEL\n");
            dbSpace.append("MONITORING;\n\n");
            
            StringBuffer dbIndex = new StringBuffer();
            String dbKey = this.getDbkey(form_cd);
            dbIndex.append("("+dbKey+")\n");
            
            dbIndex.append("LOGGING\n");
            dbIndex.append("TABLESPACE KET_A_IDX\n");
            dbIndex.append("PCTFREE    10\n");
            dbIndex.append("INITRANS   2\n");
            dbIndex.append("MAXTRANS   255\n");
            dbIndex.append("STORAGE    (\n");
            dbIndex.append("INITIAL          5056K\n");
            dbIndex.append("MINEXTENTS       1\n");
            dbIndex.append("MAXEXTENTS       UNLIMITED\n");
            dbIndex.append("PCTINCREASE      0\n");
            dbIndex.append("BUFFER_POOL      DEFAULT\n");
            dbIndex.append(")\n");
            dbIndex.append("NOPARALLEL;\n\n");
	    
	    while (manIter.hasNext()) {
		Attribute attr = (Attribute) manIter.next();
		ManOxmAttr.put(attr.getName(), "ManOxmAttr.txt");
		if (this.isBaseVali(attr.getName(), BaseClazz)) {// KETYesoneBaseDTO에 정의된 필드는 제외한다(모든 entity가 상속관계에 있다)
		    writer.append("    private String " + attr.getName() + ";\n\n");
		    dbcolumn += attr.getName()+dbtype;
		}
	    }
	    
	    
	    
	    
	    writer.append("//End Man Attribute(ManOxm.java)\n\n");
	    /*
	     * 2단계 data의 attribute를 가져온다
	     */
	    writer.append("//Start Data Attribute(DataOxm.java)\n\n");

	    Iterator<Attribute> attrIter = attrlist.iterator();

	    while (attrIter.hasNext()) {
		Attribute attr = (Attribute) attrIter.next();
		DataOxmAttr.put(attr.getName(), "DataOxmAttr.txt");
		writer.append("    private String " + attr.getName() + ";\n\n");
		dbcolumn += attr.getName()+dbtype;
	    }

	    writer.append("//End Data Attribute(DataOxm.java)\n\n");
	    /*
	     * 3단계 data의 자식 리스트를 가져온다
	     */

	    writer.append("//Start Data Field(DataOxm.java)\n\n");

	    for (Element dataElemnet : datalist) {
		DataOxmField.put(dataElemnet.getName(), "DataOxmField.txt");
		if (this.isBaseVali(dataElemnet.getName(), AoClazz)) {// AmtOxm에 정의된 필드는 제외한다. xml오브젝트로서, DataOxm에 리스트 형태의 멤버변수로 존재한다.
								      // xml파일을 xom패키지 하위의 클래스에 매핑할때 처리될것임
		    writer.append("    private String " + dataElemnet.getName() + ";\n\n");
		    dbcolumn += dataElemnet.getName()+dbtype;
		}
	    }

	    writer.append("//End Data Field(DataOxm.java)\n\n");
	    
	    dbcolumn = StringUtils.removeEnd(dbcolumn, ",\n");
	    
	    Dwriter.append("form_cd"+dbtype);
	    Dwriter.append("resid"+dbtype);
	    Dwriter.append("name"+dbtype);
	    Dwriter.append("chasu"+dbtype);
	    Dwriter.append("emp_no"+dbtype);
	    Dwriter.append("year"+dbtype);
	    Dwriter.append("site"+dbtype);
	    
	    Dwriter.append(dbcolumn+"\n");
	    Dwriter.append(")\n");
	    Dwriter.append(dbSpace);
	    
	    
	    Dwriter.append("CREATE UNIQUE INDEX KET.PK_"+ClassName+" ON KET."+ClassName+"\n");
	    Dwriter.append(dbIndex);
	   
	    
	    Dwriter.append("ALTER TABLE KET."+ClassName+" ADD (\n");
	    Dwriter.append("CONSTRAINT PK_"+ClassName+"\n");
            Dwriter.append("PRIMARY KEY\n");
            
            Dwriter.append("("+dbKey+")\n");
            
            Dwriter.append("USING INDEX KET.PK_"+ClassName+"\n");
            Dwriter.append("ENABLE VALIDATE);\n\n");
	    
	    /*
	     * 여기까지 xml의 모든 Attribute와 필드를 멤버변수로 write하는 작업임
	     */

	    /*
	     * 그리고 아래부터는 해당 멤버변수를 가지고 setter와 getter 메써드를 생성한다
	     */
	    manIter = manlist.iterator();
	    attrIter = attrlist.iterator();

	    while (manIter.hasNext()) {
		Attribute attr = (Attribute) manIter.next();
		attrName = attr.getName();
		if (this.isBaseVali(attr.getName(), BaseClazz)) {
		    writer.append(this.genGetSet("set", attrName));
		    writer.append(this.genGetSet("get", attrName));
		}
	    }

	    while (attrIter.hasNext()) {
		Attribute attr = (Attribute) attrIter.next();
		attrName = attr.getName();
		writer.append(this.genGetSet("set", attrName));
		writer.append(this.genGetSet("get", attrName));
	    }

	    for (Element dataElemnet : datalist) {
		attrName = dataElemnet.getName();
		if (this.isBaseVali(dataElemnet.getName(), AoClazz)) {
		    writer.append(this.genGetSet("set", attrName));
		    writer.append(this.genGetSet("get", attrName));
		}

	    }

	    writer.append("}");
	    writer.close();
	}
	Dwriter.close();
	ArrayList<TreeMap<String, String>> paramList = new ArrayList<TreeMap<String, String>>();

	paramList.add(ManOxmAttr);
	paramList.add(DataOxmAttr);
	paramList.add(DataOxmField);

	for (TreeMap<String, String> treeData : paramList) {
	    this.oxmFieldCreate(treeData);//XML 자바 오브젝트의 필드를 .txt로 떨군다.. 결과물을 가지고 각 자바 오브젝트에 필요한 부분은 복사해서 쓰면된다..
	}

    }

    public void oxmFieldCreate(TreeMap<String, String> treeData) throws Exception {

	File dir = new File(filePath + "\\xom");

	if (!dir.isDirectory()) {
	    dir.mkdirs();
	}

	Iterator<?> it = treeData.entrySet().iterator();

	String javafilePath = filePath + "\\xom\\";

	File file = null;
	String variable = null;
	String filename = null;
	int row = 0;
	BufferedWriter writer = null;
	while (it.hasNext()) {

	    @SuppressWarnings("unchecked")
	    Map.Entry<String, String> entry = (Entry<String, String>) it.next();

	    variable = (String) entry.getKey();

	    if (row == 0) {
		filename = (String) entry.getValue();
		javafilePath = javafilePath + filename;
		file = new File(javafilePath);
		writer = new BufferedWriter(new FileWriter(file));
	    }

	    if (StringUtils.contains(filename, "Attr")) {
		writer.append("@XmlAttribute(name=" + '"' + variable + '"' + ")\n");
		writer.append("private String " + variable + ";\n\n");
	    } else {
		writer.append("private String " + variable + ";\n\n");
	    }
	    writer.append("");

	    row++;

	}
	writer.close();

    }

    public String getClassName(String form_cd) {// enum에 정의된 클래스 네임 반환

	String EnumFormCode = null;
	String ClassName = null;
	FormTypeEnum[] arry = FormTypeEnum.values();

	for (FormTypeEnum item : arry) {
	    EnumFormCode = item.getFormCode();
	    if (form_cd.equals(EnumFormCode)) {
		ClassName = (String) item.getObject();
		ClassName = StringUtils.remove(ClassName, "ext.ket.yesone.entity.");
		break;
	    }
	}

	return ClassName;
    }

    public boolean isBaseVali(String attr, Object obj) throws Exception {// 필드 중복 여부 검증
	boolean check = true;

	String clzName = (String) obj;
	Class<?> TargetClass = Class.forName(clzName);
	Object targetObject = TargetClass.newInstance();
	Field[] field = targetObject.getClass().getDeclaredFields();
	for (int i = 0; i < field.length; i++) {
	    if (attr.equals(field[i].getName())) {
		check = false;
		break;
	    }
	}
	return check;
    }

    public StringBuffer genGetSet(String option, String attrName) {// setter와 getter 문자열 생성

	StringBuffer temp = new StringBuffer();

	if ("set".equals(option)) {
	    temp.append("    public void set" + StringUtils.upperCase(StringUtils.substring(attrName, 0, 1))
		    + StringUtils.substring(attrName, 1) + "(String " + attrName + ") {\n\n");
	    temp.append("        this." + attrName + " = " + attrName + ";\n\n");
	    temp.append("    }\n\n");
	}

	if ("get".equals(option)) {
	    temp.append("    public String get" + StringUtils.upperCase(StringUtils.substring(attrName, 0, 1))
		    + StringUtils.substring(attrName, 1) + "() {\n\n");
	    temp.append("        return " + attrName + ";\n\n");
	    temp.append("    }\n\n");
	}
	return temp;
    }
    
    public String getDbkey(String form_cd){
	String EnumFormCode = null;
	String dbKey = null;
	FormTypeEnum[] arry = FormTypeEnum.values();

	for (FormTypeEnum item : arry) {
	    EnumFormCode = item.getFormCode();
	    if (form_cd.equals(EnumFormCode)) {
		dbKey = (String) item.getDbkey();
		break;
	    }
	}

	return dbKey;
    }

}
