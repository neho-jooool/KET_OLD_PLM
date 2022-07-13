/**
 * @(#)	CresynLoadUser.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */
package e3ps.groupware.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import wt.fc.PersistenceHelper;
import ext.ket.shared.log.Kogger;


public class CresynLoadUser {
	public static void main(String[] args) {
		if ( args.length != 2  ) {
			Kogger.debug(CresynLoadUser.class, "사용법 : cresynloaddata [type] [FullPathName]");
			Kogger.debug(CresynLoadUser.class, "          [type]:D/d  Department");
			Kogger.debug(CresynLoadUser.class, "          [type]:P/p  People");
		} else {
			CresynLoadUser loader = new CresynLoadUser();
			boolean checkFile = loader.checkFile(args[1]);
			if ( !checkFile ) return;
			
			if ( args[0].equalsIgnoreCase("D") ) {
				loader.makeDepartment(args[1]);
			} else if ( args[0].equalsIgnoreCase("P") ) {
				loader.linkPeople(args[1]);
			} else {
				Kogger.debug(CresynLoadUser.class, "사용법 : cresynloaddata [type] [FullPathName]");
				Kogger.debug(CresynLoadUser.class, "          [type]:D/d  Department");
				Kogger.debug(CresynLoadUser.class, "          [type]:P/p  People");
				Kogger.debug(CresynLoadUser.class, "          [type]:T/t  TempleteProject");
				System.exit(0);
			}
		}
	}
	
	private void makeDepartment(String fileName) {		
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 부서 생성 시작 !!!!!!!!!!!!!!!!!!!!" );
		Kogger.debug(getClass(), "");
		
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String lineStr = "";
			while ( (lineStr=br.readLine()) != null ) {
			    if ( !lineStr.startsWith("#") ) {
					StringTokenizer st = new StringTokenizer(lineStr,",");
					Kogger.debug(getClass(), "부서정보 : " + lineStr + "생성중");
					try {
//						Department dept = Department..newDepartment();
//						while ( st.hasMoreElements() ) {
//							String type = st.nextToken();
//							String code = st.nextToken();
//							String name = st.nextToken();
//							String parentCode = (st.hasMoreElements())?st.nextToken():"";
//							if ( type.equalsIgnoreCase("department") ) {
//								dept.setCode(code);
//								dept.setName(name);
//								if ( parentCode != null && !parentCode.trim().equals("") ) {
//									dept.setParent(DepartmentHelper.manager.getDepartment(parentCode));								
//								}
//								PersistenceHelper.manager.save(dept);
//							}
//						}
						Kogger.debug(getClass(), "부서정보 : " + lineStr + " 생성완료");
					} catch (Exception e1) {
						Kogger.debug(getClass(), "부서정보 : " + lineStr + " 부서 생성중 오류가 발생했습니다.");
					}
			    }
			}

			br.close();
			fr.close();
			
			Kogger.debug(getClass(), "");
			Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 부서 생성 완료 !!!!!!!!!!!!!!!!!!!!" );		
		} catch (FileNotFoundException e) {
			Kogger.debug(getClass(), file + " : 해당 파일을 찾을 수 없습니다. 체크하시기 바랍니다.");
		} catch (IOException e) {
			Kogger.debug(getClass(), file + " : 부서 생성중 오류가 발생했습니다.");
		} finally {
			System.exit(0);
		}		
	}
	
	private void linkPeople(String fileName) {
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 부서 와 사원 링크 생성 시작 !!!!!!!!!!!!!!!!!!!!" );
		Kogger.debug(getClass(), "");
		
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String lineStr = "";
			while ( (lineStr=br.readLine()) != null ) {
			    if ( !lineStr.startsWith("#") ) {
					StringTokenizer st = new StringTokenizer(lineStr,",");
					Kogger.debug(getClass(), "부서 와 사원 링크 정보 : " + lineStr + "생성중");
					try {					
						while ( st.hasMoreElements() ) {
							String type = st.nextToken();
							if ( type.equalsIgnoreCase("user") ) {
								String id = st.nextToken();
								People peo = PeopleHelper.manager.getPeople(id);
								if ( peo == null ) {
									Kogger.debug(getClass(), "부서 와 사원 링크 정보 : " + lineStr + " 부서 와 사원 링크 생성중 오류가 발생했습니다. 관련 People객체가 없습니다.");
									break;
								} else {
									String duty = (st.hasMoreElements())?st.nextToken():"";
									String duty_code = (st.hasMoreElements())?st.nextToken():"";
									String deptCode = (st.hasMoreElements())?st.nextToken():"";					
									if ( deptCode != null && !deptCode.trim().equals("") ) {
										Department dept = DepartmentHelper.manager.getDepartment(deptCode);
										peo.setDepartment(dept);
									}
									peo.setDuty(duty);
									peo.setDutyCode(duty_code);
									PersistenceHelper.manager.modify(peo);
								}	
							}					
						}
						Kogger.debug(getClass(), "부서 와 사원 링크 정보 : " + lineStr + " 생성완료");
					} catch (Exception e1) {
						Kogger.debug(getClass(), "부서 와 사원 링크 정보 : " + lineStr + " 부서 와 사원 링크 생성중 오류가 발생했습니다.");
					}
			    }
			}
			
			br.close();
			fr.close();
			
			Kogger.debug(getClass(), "");
			Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 부서 와 사원 링크 생성 완료 !!!!!!!!!!!!!!!!!!!!" );		
		} catch (FileNotFoundException e) {
			Kogger.debug(getClass(), file + " : 해당 파일을 찾을 수 없습니다. 체크하시기 바랍니다.");
		} catch (IOException e) {
			Kogger.debug(getClass(), file + " : 부서 와 사원 링크 생성중 오류가 발생했습니다.");
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
		} catch (FileNotFoundException e) {
			Kogger.debug(getClass(), file + " : 해당 파일을 찾을 수 없습니다. 체크하시기 바랍니다.");
		}
		
		Kogger.debug(getClass(), "");
		Kogger.debug(getClass(), "!!!!!!!!!!!!!!!!!!!! 파라미터 파일 체크 끝 !!!!!!!!!!!!!!!!!!!!" );
		return true;
	}
}
