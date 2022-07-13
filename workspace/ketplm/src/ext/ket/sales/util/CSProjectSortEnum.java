package ext.ket.sales.util;

import org.apache.commons.lang.StringUtils;

public enum CSProjectSortEnum {


    SALES1("영업1센터", "영업1팀","1"), 
    SALES2("영업1센터", "영업2팀","2"), 
    SALES3("영업2센터", "영업3팀","3"), 
    SALES4("영업2센터", "영업4팀","4"), 
    SALES5("영업3센터", "영업5팀","5"), 
    SALES6("영업3센터", "영업6팀","6"), 
    SALES7("해외관리팀", "해외관리팀","7"),
    SALES8("Global사업부", "EU/NA팀","8"),
    SALES9("Global사업부", "CS팀","9"),
    SALES10("Global사업부", "EU팀","10"),
    SALES11("Global사업부", "NA팀","11"),
    SALES12("Global사업부", "China팀","12"),
    SALES13("Global사업부", "Japan팀","13"),
    SALES14("Global사업부", "북미지사","14"),
    SALES15("Global사업부", "상해지사","15"),
    SALES16("Global사업부", "중국친환경영업팀","16");

    
    private String center;
    private String team;
    private String sortNo;
    
    private CSProjectSortEnum(String center, String team, String sortNo){
	this.center = center;
	this.team = team;
	this.sortNo = sortNo;
    }
    	
    public static CSProjectSortEnum toSortTypeEnumByCenter(String center){
	if(StringUtils.isEmpty(center)) return null;
	
	CSProjectSortEnum ret = null;
	
	CSProjectSortEnum[] arry = CSProjectSortEnum.values();
	for( CSProjectSortEnum item : arry){
	    if(center.equalsIgnoreCase(item.getCenter())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }
    
    public static CSProjectSortEnum toSortTypeEnumByTeam(String team){
	if(StringUtils.isEmpty(team)) return null;
	
	CSProjectSortEnum ret = null;
	
	CSProjectSortEnum[] arry = CSProjectSortEnum.values();
	for( CSProjectSortEnum item : arry){
	    if(team.equalsIgnoreCase(item.getTeam())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }
    
    public static CSProjectSortEnum toTeamTypeEnumBySort(String sortNo){
	if(StringUtils.isEmpty(sortNo)) return null;
	
	CSProjectSortEnum ret = null;
	
	CSProjectSortEnum[] arry = CSProjectSortEnum.values();
	for( CSProjectSortEnum item : arry){
	    if(sortNo.equalsIgnoreCase(item.getSortNo())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }

    public String getCenter() {
        return center;
    }

    public String getTeam() {
        return team;
    }
    
    public String getSortNo() {
        return sortNo;
    }
    
}
