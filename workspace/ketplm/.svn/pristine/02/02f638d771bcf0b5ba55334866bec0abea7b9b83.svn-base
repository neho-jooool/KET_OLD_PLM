echo -------------------------------------------------------------------
echo  제품컨테이너 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\product\ket_product.xml -u wcadmin -p plm1154 -CONT_PATH /wt.inf.container.OrgContainer=ket

echo -------------------------------------------------------------------
echo. 관리 도메인 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\domain\ket_domains.xml -u wcadmin -p plm1154 -CONT_PATH /wt.inf.container.OrgContainer=ket/wt.pdmlink.PDMLinkProduct=KET

echo -------------------------------------------------------------------
echo  폴더 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\folder\ket_folders.xml -u wcadmin -p plm1154 -CONT_PATH /wt.inf.container.OrgContainer=ket/wt.pdmlink.PDMLinkProduct=KET

echo -------------------------------------------------------------------
echo. 팀 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\team\ket_teams.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  IBA 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\iba\ket_edm_AttributeDefinitions.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\iba\ket_part_AttributeDefinitions.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  워크플로우 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\workflow\KET_ASSIGN_PMS_WF.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\workflow\KET_COMMON_WF.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\workflow\KET_DRR_WF.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\workflow\KET_TODO_WF.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  라이프사이클 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_COMMON_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_DRR_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_ECA_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_ECO_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_EPM_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_EWR_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_MOLD_PMS_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_PART_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_PRODUCT_PMS_LC.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\lifecycle\KET_REVIEW_PMS_LC.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  객체초기화규칙 수정
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\oir\EPMDoc_Rule.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\oir\WTDocument_Rule.xml -u wcadmin -p plm1154
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\oir\WTPart_Rule.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  사용자 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\user\ket_users.xml -u wcadmin -p plm1154 -CONT_PATH /wt.inf.container.OrgContainer=ket

rem echo -------------------------------------------------------------------
rem echo  그룹 생성
rem echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\group\ket_groups.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  Department 로딩
echo -------------------------------------------------------------------
call windchill e3ps.load.groupware.LoadDepartment wcadmin plm1154 D:\ptc\Windchill\loadFiles\ket\groupware\Department.xls

echo -------------------------------------------------------------------
echo  People 로딩
echo -------------------------------------------------------------------
call windchill e3ps.load.groupware.LoadPeople wcadmin plm1154 D:\ptc\Windchill\loadFiles\ket\groupware\People.xls

echo -------------------------------------------------------------------
echo  ACL 생성
echo -------------------------------------------------------------------
call windchill wt.load.LoadFromFile -d %WT_HOME%\loadFiles\ket\acl\ket_acl.xml -u wcadmin -p plm1154

echo -------------------------------------------------------------------
echo  NumberCode 로딩
echo -------------------------------------------------------------------
call windchill e3ps.load.code.KETStdCodeLoad wcadmin plm1154

echo 원재료 로딩
echo -------------------------------------------------------------------
call windchill e3ps.project.material.load.MaterialLoad wcadmin plm1154

echo 기계설비 로딩
echo -------------------------------------------------------------------
call windchill e3ps.project.machine.load.MachineLoad wcadmin plm1154

echo 이벤트 로딩
echo -------------------------------------------------------------------
call windchill e3ps.project.outputtype.load.EventLoad wcadmin plm1154

echo 차종  로딩
echo -------------------------------------------------------------------
call windchill e3ps.project.outputtype.load.CarLoad wcadmin plm1154

echo 제품프로젝트  로딩
echo -------------------------------------------------------------------
REM call windchill e3ps.load.project.ProductProjectDataLoader 제품프로젝트_V2.5.xls wcadmin plm1154
REM call windchill e3ps.load.project.ProductProjectDataLoader 제품프로젝트_V2.0_전자.xls wcadmin plm1154

echo 제품Item  로딩
echo -------------------------------------------------------------------
REM call windchill e3ps.load.project.ItemInfoDataLoader 제품Item_V2.0_전자.xls wcadmin plm1154

echo 프로젝트비용관리  로딩
echo -------------------------------------------------------------------
REM call windchill e3ps.load.project.CostInfoDataLoader 프로젝트비용관리_V2.0_전자.xls wcadmin plm1154

echo 프로젝트 산출문서 마이그레션 로딩 (테스트용)
echo -------------------------------------------------------------------
REM call windchill e3ps.load.dms.ProjectDocumentDataLoader wcadmin plm1154 문서_Migration_Test.xls

echo 금형프로젝트  로딩
echo -------------------------------------------------------------------
#REM call windchill e3ps.load.project.MoldProjectDataLoader 마이그레이션 제외

echo 부품번호  로딩
echo -------------------------------------------------------------------
REM call windchill e3ps.load.part.NewPartListDataLoader Terminal_DIE_List_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader TERMINAL_판매(ST71~ST78)_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader TERMINAL_부품(ST79), 구매부품(R60)_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader OEM_DIE_List_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader Housing(MG61~MG68)_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader MOLD_DIE_List_정리완료.xls wcadmin plm1154
REM call windchill e3ps.load.part.NewPartListDataLoader 시험금형-List_정리완료.xls wcadmin plm1154

echo 도면 마이그레이션  로딩
echo -------------------------------------------------------------------
echo 도면 제품  마이그레이션  로딩
REM call windchill e3ps.load.migration.edm.EDMMigDataLoader_pro wcadmin wcadmin 파일명

echo 도면 금형  마이그레이션  로딩
REM call edmLoader.bat -P 파일명
