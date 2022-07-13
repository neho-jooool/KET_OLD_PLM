package ext.ket.part.migration.base;

public enum PartPropEnum {

    MAST_OID(0),
    REV_OID(1),
    ITER_OID(2),
    PART_NO(3),
    PART_NAME(4),
    PART_REV(5),
//    결재상태,6 
//    부품유형 7
//    자재그룹 8
    spPartType(9),
    PART_CLAZ(10),
    PART_DEV_LEVEL(11),
    spPartRevision(12);
    
    private int index;
    private PartPropEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
