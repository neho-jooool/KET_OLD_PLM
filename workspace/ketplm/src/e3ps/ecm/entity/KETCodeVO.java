
package e3ps.ecm.entity;

import java.util.ArrayList;
public class KETCodeVO extends GeneralVO {
	private static final long serialVersionUID = 9133312735524822921L;
	private ArrayList<KETCodeDetailVO> ketCodeDetailVOList;//ECO 검색상세 List
	private int totalCount;//전체자료수
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public ArrayList<KETCodeDetailVO> getKetCodeDetailVOList() {
		return ketCodeDetailVOList;
	}
	public void setKetCodeDetailVOList(
			ArrayList<KETCodeDetailVO> ketCodeDetailVOList) {
		this.ketCodeDetailVOList = ketCodeDetailVOList;
	}
	
}
