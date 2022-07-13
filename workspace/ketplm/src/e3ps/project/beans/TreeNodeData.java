package e3ps.project.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import wt.util.WTPropertyVetoException;

public interface TreeNodeData extends Serializable{
	public Object getData();
	public int getDuration() ;
	public Timestamp getPlanStartDate() ;
	public Timestamp getPlanEndDate();
	public boolean isNotPlanTask();
	
	public int getStdWork();
	public void setStdWork(int stdWork);
}
