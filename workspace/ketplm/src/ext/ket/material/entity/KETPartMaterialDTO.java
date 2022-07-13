package ext.ket.material.entity;

import e3ps.common.util.CommonUtil;
import e3ps.project.material.MoldMaterial;
import ext.ket.shared.dto.BaseDTO;

public class KETPartMaterialDTO extends BaseDTO{
    private static final long serialVersionUID = 1L;
    
    //검색조건 start
    
    private String gubun;
    private String spMaterMaker;
    private String spMaterMaker2;
    private String spMaterType;
    private String spMaterialInfo;
    private String spMaterNotFe;
    
    //검색조건 end
    
    private String oid;
    private String spec_no;
    private String maker;
    private String type;
    private String temper;
    private String s_gravity;
    private String m_elasticity;
    private String e_conductivity;
    private String hardness;
    private String strength;
    private String elong;
    private String formability;
    private String residual_stress;
    private String t_melt;
    private String t_soft;
    private String t_conductivity;
    private String r_y;
    private String r_m;
    private String r_d;
    private String createDate;
    private String modifyDate;
    private String grade;
    private String melt_point;
    private String m_index;
    private String m_shrinkage;
    private String absorb;
    private String t_strength;
    private String f_stringth;
    private String f_modulus;
    private String i_strength;
    private String h_dis_temp_18;
    private String h_dis_temp_4;
    private String flammability;
    private String s_flow;
    private String memo;
    private String jsonData;
    private String price;
    
    public KETPartMaterialDTO(){
	
    }
    
    public KETPartMaterialDTO(MoldMaterial mater) throws Exception {
	this.oid = CommonUtil.getOIDString(mater);
	
	this.spec_no = mater.getSpec_no();
	this.maker = (mater.getMaterialMaker()).getName();
	this.type = (mater.getMaterialType()).getName();
	this.temper          = mater.getTemper(); 
	this.s_gravity       = mater.getS_gravity();
	this.m_elasticity    = mater.getM_elasticity();
	this.e_conductivity  = mater.getE_conductivity();
	this.hardness        = mater.getHardness();
	this.strength        = mater.getStrength();
	this.elong           = mater.getElong();
	this.formability     = mater.getFormability();
	this.residual_stress = mater.getResidual_stress();
	this.t_melt          = mater.getT_melt();
	this.t_soft          = mater.getT_soft();
	this.t_conductivity  = mater.getT_conductivity();
	this.r_y             = mater.getR_y();
	this.r_m             = mater.getR_m();
	this.r_d             = mater.getR_d();
	this.grade           = mater.getGrade();
	this.melt_point      = mater.getMelt_point();
	this.m_index         = mater.getM_index();
	this.m_shrinkage     = mater.getM_shrinkage();
	this.absorb          = mater.getAbsorb();
	this.t_strength      = mater.getT_strength();
	this.f_stringth      = mater.getF_stringth();
	this.f_modulus       = mater.getF_modulus();
	this.i_strength      = mater.getI_strength();
	this.h_dis_temp_18   = mater.getH_dis_temp_18();
	this.h_dis_temp_4    = mater.getH_dis_temp_4();
	this.flammability    = mater.getFlammability();
	this.s_flow          = mater.getS_flow();
	this.memo            = mater.getMemo();
	this.price = mater.getPrice();

    }
    
    public String getSpec_no() {
        return spec_no;
    }
    public void setSpec_no(String spec_no) {
        this.spec_no = spec_no;
    }
    public String getMaker() {
        return maker;
    }
    public void setMaker(String maker) {
        this.maker = maker;
    }
    public String getTemper() {
        return temper;
    }
    public void setTemper(String temper) {
        this.temper = temper;
    }
    public String getS_gravity() {
        return s_gravity;
    }
    public void setS_gravity(String s_gravity) {
        this.s_gravity = s_gravity;
    }
    public String getM_elasticity() {
        return m_elasticity;
    }
    public void setM_elasticity(String m_elasticity) {
        this.m_elasticity = m_elasticity;
    }
    public String getE_conductivity() {
        return e_conductivity;
    }
    public void setE_conductivity(String e_conductivity) {
        this.e_conductivity = e_conductivity;
    }
    public String getHardness() {
        return hardness;
    }
    public void setHardness(String hardness) {
        this.hardness = hardness;
    }
    public String getStrength() {
        return strength;
    }
    public void setStrength(String strength) {
        this.strength = strength;
    }
    public String getElong() {
        return elong;
    }
    public void setElong(String elong) {
        this.elong = elong;
    }
    public String getFormability() {
        return formability;
    }
    public void setFormability(String formability) {
        this.formability = formability;
    }
    public String getResidual_stress() {
        return residual_stress;
    }
    public void setResidual_stress(String residual_stress) {
        this.residual_stress = residual_stress;
    }
    public String getT_melt() {
        return t_melt;
    }
    public void setT_melt(String t_melt) {
        this.t_melt = t_melt;
    }
    public String getT_soft() {
        return t_soft;
    }
    public void setT_soft(String t_soft) {
        this.t_soft = t_soft;
    }
    public String getT_conductivity() {
        return t_conductivity;
    }
    public void setT_conductivity(String t_conductivity) {
        this.t_conductivity = t_conductivity;
    }
    public String getR_y() {
        return r_y;
    }
    public void setR_y(String r_y) {
        this.r_y = r_y;
    }
    public String getR_m() {
        return r_m;
    }
    public void setR_m(String r_m) {
        this.r_m = r_m;
    }
    public String getR_d() {
        return r_d;
    }
    public void setR_d(String r_d) {
        this.r_d = r_d;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getMelt_point() {
        return melt_point;
    }
    public void setMelt_point(String melt_point) {
        this.melt_point = melt_point;
    }
    public String getM_index() {
        return m_index;
    }
    public void setM_index(String m_index) {
        this.m_index = m_index;
    }
    public String getM_shrinkage() {
        return m_shrinkage;
    }
    public void setM_shrinkage(String m_shrinkage) {
        this.m_shrinkage = m_shrinkage;
    }
    public String getAbsorb() {
        return absorb;
    }
    public void setAbsorb(String absorb) {
        this.absorb = absorb;
    }
    public String getT_strength() {
        return t_strength;
    }
    public void setT_strength(String t_strength) {
        this.t_strength = t_strength;
    }
    public String getF_stringth() {
        return f_stringth;
    }
    public void setF_stringth(String f_stringth) {
        this.f_stringth = f_stringth;
    }
    public String getF_modulus() {
        return f_modulus;
    }
    public void setF_modulus(String f_modulus) {
        this.f_modulus = f_modulus;
    }
    public String getI_strength() {
        return i_strength;
    }
    public void setI_strength(String i_strength) {
        this.i_strength = i_strength;
    }
    public String getH_dis_temp_18() {
        return h_dis_temp_18;
    }
    public void setH_dis_temp_18(String h_dis_temp_18) {
        this.h_dis_temp_18 = h_dis_temp_18;
    }
    public String getH_dis_temp_4() {
        return h_dis_temp_4;
    }
    public void setH_dis_temp_4(String h_dis_temp_4) {
        this.h_dis_temp_4 = h_dis_temp_4;
    }
    public String getFlammability() {
        return flammability;
    }
    public void setFlammability(String flammability) {
        this.flammability = flammability;
    }
    public String getS_flow() {
        return s_flow;
    }
    public void setS_flow(String s_flow) {
        this.s_flow = s_flow;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getSpMaterMaker() {
        return spMaterMaker;
    }

    public void setSpMaterMaker(String spMaterMaker) {
        this.spMaterMaker = spMaterMaker;
    }

    public String getSpMaterMaker2() {
        return spMaterMaker2;
    }

    public void setSpMaterMaker2(String spMaterMaker2) {
        this.spMaterMaker2 = spMaterMaker2;
    }

    public String getSpMaterType() {
        return spMaterType;
    }

    public void setSpMaterType(String spMaterType) {
        this.spMaterType = spMaterType;
    }

    public String getSpMaterialInfo() {
        return spMaterialInfo;
    }

    public void setSpMaterialInfo(String spMaterialInfo) {
        this.spMaterialInfo = spMaterialInfo;
    }

    public String getSpMaterNotFe() {
        return spMaterNotFe;
    }

    public void setSpMaterNotFe(String spMaterNotFe) {
        this.spMaterNotFe = spMaterNotFe;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
