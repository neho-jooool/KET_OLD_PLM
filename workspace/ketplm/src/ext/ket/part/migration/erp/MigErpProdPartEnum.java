package ext.ket.part.migration.erp;

public enum MigErpProdPartEnum {
    
    PART_NO(0),
    PART_NAME(1),
    spPartType(2),
    PART_UNIT(4), 
    spTotalWeight(7),
    spNetWeight(8),
    spScrabWeight(9),
    spProdSize(11),
    spScrabCode(12),
    spIsDeleted(13),
    spSeries(14),
    spWaterProof(15),
    spMater(18), // spMaterialInfo, spMaterNotFe, spMaterDie
    spColor(20),
    spPlating(21),
    spRepresentM(24),
    spIsYazaki(28),
    spYazakiNo(29);
    
    
    private int index;
    private MigErpProdPartEnum(int index){
	this.index = index;
    }
    
    public int getIndex(){
	return index;
    }

}
