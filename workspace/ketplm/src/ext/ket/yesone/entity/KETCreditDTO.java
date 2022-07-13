package ext.ket.yesone.entity;

public class KETCreditDTO extends KETYesoneBaseDTO {

	private static final long serialVersionUID = 1L;

	// Start Man Attribute(ManOxm.java)

	// End Man Attribute(ManOxm.java)

	// Start Data Attribute(DataOxm.java)

	private String dat_cd;

	private String busnid;

	private String trade_nm;

	private String use_place_cd;

	// End Data Attribute(DataOxm.java)

	// Start Data Field(DataOxm.java)

	private String sum;

	// End Data Field(DataOxm.java)

	public void setDat_cd(String dat_cd) {

		this.dat_cd = dat_cd;

	}

	public String getDat_cd() {

		return dat_cd;

	}

	public void setBusnid(String busnid) {

		this.busnid = busnid;

	}

	public String getBusnid() {

		return busnid;

	}

	public void setTrade_nm(String trade_nm) {

		this.trade_nm = trade_nm;

	}

	public String getTrade_nm() {

		return trade_nm;

	}

	public void setUse_place_cd(String use_place_cd) {

		this.use_place_cd = use_place_cd;

	}

	public String getUse_place_cd() {

		return use_place_cd;

	}

	public void setSum(String sum) {

		this.sum = sum;

	}

	public String getSum() {

		return sum;

	}

}