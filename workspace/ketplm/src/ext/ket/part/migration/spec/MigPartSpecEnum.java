package ext.ket.part.migration.spec;

public enum MigPartSpecEnum {
    
    ATTR_NAME(1),
    ATTR_CODE(2),
    SEND_ERP(5),
    COLUMN_NAME(6), 
    ATTR_INPUT_TYPE(7),
    MULTI_SELECT(8),
    NUMBER_CODETYPE_CODE(9),
    NUMBER_CODETYPE_NAME(10),
    ENUM_ASIS(11),
    NUMBERING_REL(12),
    NUMBER_TYPE(13),
    NOT_NULL(14),
    DESCRIPTION(15),
    COMMENT(22);
    
    private int index;
    private MigPartSpecEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
