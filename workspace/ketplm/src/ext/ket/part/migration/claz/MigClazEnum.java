package ext.ket.part.migration.claz;

public enum MigClazEnum {
    
    KEY_CODE(0),
    PARENT(1),
    LEV1(2),
    LEV2(3), 
    LEV3(4), 
    CLAZ_ENG(5),
    CLAZ_CH(6),
    CLASS_PART_TYPE(7),
    EPM_CODE(8),
    PRODG_CODE(9),
    PRODG_DESC(10),
    ERP_FERT_CODE(11),
    ERP_FERT_DESC(12),
    BIGO(15),
    NUMBERING_CODE(16),
    SORTING(17),
    USE_AT(18),
    CATALOGUE_CODE(19),
    PART_CLAZ_TYPE(20), //partClassificType
    PART_NAMING_CODE(21),
    PART_NAMING_CALL(22),
    ERP_CLAZ_NO(23),
    BIZ_NAME(24),
    CLAZ_NO(25),
    NUMBERING_CHARICS(26);
    
    private int index;
    private MigClazEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
