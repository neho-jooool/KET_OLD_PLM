package ext.ket.part.migration.lov;

public enum MigPartLovEnum {
    
    GUBUN(0),
    CODETYPE_KR(1),
    CODETYPE_EN(2),
    CODETYPE_CODE(3), 
    CODE_CODE(4),
    CODE_NAME(5),
    CODE_PARENT_CODE(6),
    CODE_SHORT_NAME(7),
    CODE_DESC(8),
    CODE_NAME_KR(9),
    CODE_NAME_EN(10),
    CODE_NAME_CN(11),
    CODE_SORTING(12);
    
    private int index;
    private MigPartLovEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
