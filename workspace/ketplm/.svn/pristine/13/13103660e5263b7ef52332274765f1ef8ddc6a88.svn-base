package e3ps.project.beans;

import e3ps.project.ProjectIssueAnswer;

public class IssueAnswerComparator implements java.util.Comparator {
	boolean isAsc;
	public IssueAnswerComparator(boolean isAsc){
		this.isAsc = isAsc;
	}
	public int compare(Object obj, Object obj1){
		ProjectIssueAnswerData id1 = (ProjectIssueAnswerData)obj;
		ProjectIssueAnswerData id2 = (ProjectIssueAnswerData)obj1;
		ProjectIssueAnswer ia1 = (ProjectIssueAnswer)id1.answer;
		ProjectIssueAnswer ia2 = (ProjectIssueAnswer)id2.answer;
		int result = 0;
		result = ia1.getCreateDate().compareTo(ia2.getCreateDate());
		if(isAsc){
			return -1 * result;
		}else{
			return result;
		}
	}
}
