package e3ps.project.sap;
import com.sap.mw.jco.JCO;


public class MoldPriceData {
	
	public String date;
	public String price;
	public String workId;
	public String number;
	public String content;
	
	
	public MoldPriceData(JCO.Table reDatas){
	   this.price = reDatas.getString("WTGBTR");
	   this.date = reDatas.getString("BUDAT");
	   this.workId = reDatas.getString("ARBPL");
	   this.number = reDatas.getString("BELNR");
	   this.content = reDatas.getString("KTEXT");
	}
	
}
