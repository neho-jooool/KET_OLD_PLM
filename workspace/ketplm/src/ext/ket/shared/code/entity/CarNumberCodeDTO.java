package ext.ket.shared.code.entity;

import e3ps.common.code.NumberCode;
import ext.ket.shared.dto.BaseDTO;

public class CarNumberCodeDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6601818615160308186L;
    private String	    code	     = "";
    private String	    name	     = "";
    private NumberCode	numberCode       = null;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public NumberCode getNumberCode() {
	return numberCode;
    }

    public void setNumberCode(NumberCode numberCode) {
	this.numberCode = numberCode;
    }

}
