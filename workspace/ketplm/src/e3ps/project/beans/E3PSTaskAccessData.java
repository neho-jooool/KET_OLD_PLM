package e3ps.project.beans;

public class E3PSTaskAccessData {
	boolean isContinue = false; 	// 태스크 진행 여부
	boolean isComplete = false; 	// 태스크 완료 여부
	boolean isStop = false;			// 태스크 중지 여부
	boolean isCompleting = false; 	// 태스크 완료진행 여부
	boolean isLast = false; 		// 작업 가능 Task 여부
	
	public boolean isComplete() { return isComplete; }
	public boolean isCompleting() { return isCompleting; }
	public boolean isStop() { return isStop; }
	public boolean isContinue() { return isContinue; }
	public boolean isLast() { return isLast; }
	public void setComplete(boolean isComplete) { this.isComplete = isComplete; }
	public void setCompleting(boolean isCompleting) { this.isCompleting = isCompleting; }
	public void setStop(boolean isStop) { this.isStop = isStop; }
	public void setContinue(boolean isContinue) { this.isContinue = isContinue; }
	public void setLast(boolean isLast) { this.isLast = isLast; }
}
