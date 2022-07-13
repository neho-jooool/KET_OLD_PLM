package e3ps.project.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import wt.util.WTException;
import e3ps.project.E3PSProjectHelper;
import ext.ket.shared.log.Kogger;


public class E3PSProjectTemplateDataLoader {

	public static void main(String[] args) {
        if ( args.length != 2  ) {
        	Kogger.debug(E3PSProjectTemplateDataLoader.class, "사용법 : JELProjectTemplateDataLoader [type] [FullPathName]");
			Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:D/d  Department");
			Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:P/p  People");
			Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:T/t  TempleteProject");
			Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:ca/CA  CommonAddress");
		} else {
		    E3PSProjectTemplateDataLoader loader = new E3PSProjectTemplateDataLoader();
			boolean checkFile = loader.checkFile(args[1]);
			if ( !checkFile ) return;
			
			if ( args[0].equalsIgnoreCase("T") ) {
			    loader.makeTempleteProject(args[1]);
			} else {
				Kogger.debug(E3PSProjectTemplateDataLoader.class, "사용법 : JELProjectTemplateDataLoader [type] [FullPathName]");
				Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:D/d  Department");
				Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:P/p  People");
				Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:T/t  TempleteProject");
				Kogger.debug(E3PSProjectTemplateDataLoader.class, "          [type]:ca/CA  CommonAddress");
				System.exit(0);
			}
		}
    }
    
    private void makeTempleteProject(String fileName) {		
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 템플릿 프로젝트 생성 시작 !!!!!!!!!!!!!!!!!!!!" );
		Kogger.debug(getClass(), "");
		
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String lineStr = "";
			Vector dataVec = new Vector();
			while ( (lineStr=br.readLine()) != null ) {
				if ( !lineStr.startsWith("#") && !lineStr.trim().equals("") ) {
				    Kogger.debug(getClass(), "Reading Line :  " + lineStr);
				    dataVec.add(lineStr);
				}
			}
			Kogger.debug(getClass(), "");
			Hashtable hash = new Hashtable();
			hash.put("vec", dataVec);
			Kogger.debug(getClass(), "dataVec.size()<<<< "+dataVec.size());
			if ( dataVec.size() > 0 ) E3PSProjectHelper.service.loadTemplateProject(hash);
			
			br.close();
			fr.close();
			
			Kogger.debug(getClass(), "");
			Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 템플릿 프로젝트 생성 완료 !!!!!!!!!!!!!!!!!!!!" );		
		} catch (FileNotFoundException e) {
			Kogger.debug(getClass(), file + " : 해당 파일을 찾을 수 없습니다. 체크하시기 바랍니다.");
		} catch (IOException e) {
			Kogger.debug(getClass(), file + " : 템플릿 프로젝트 생성중 오류가 발생했습니다.");
		} catch (WTException e) {
			Kogger.debug(getClass(), file + " : 템플릿 프로젝트 생성중 오류가 발생했습니다.");
        } finally {
			System.exit(0);
		}
	}
    
    private boolean checkFile(String fileName) {
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 파라미터 파일 체크 시작 !!!!!!!!!!!!!!!!!!!!" );
		Kogger.debug(getClass(), "");
		
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);	
			Kogger.debug(getClass(), file + " : 파일 체크 완료.");
		} catch (FileNotFoundException e) {
			Kogger.debug(getClass(), file + " : 해당 파일을 찾을 수 없습니다. 체크하시기 바랍니다.");
		}
		
		Kogger.debug(getClass(), "");
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 파라미터 파일 체크 끝 !!!!!!!!!!!!!!!!!!!!" );
		Kogger.debug(getClass(), "");
		return true;
	}
}
