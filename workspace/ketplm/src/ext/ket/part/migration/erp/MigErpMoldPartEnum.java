package ext.ket.part.migration.erp;

public enum MigErpMoldPartEnum {
    
    PART_NO(0),
    PART_NAME(1),
    PART_UNIT(2), 
    spPartType(3),
    spMaterDie(5), // spMaterialInfo, spMaterNotFe, spMaterDie
    spIsDeleted(6);
    
    private int index;
    private MigErpMoldPartEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
