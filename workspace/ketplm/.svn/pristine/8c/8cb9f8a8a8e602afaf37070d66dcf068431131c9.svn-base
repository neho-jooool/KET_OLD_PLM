package e3ps.edm.clients.batch;

import java.awt.Component;

import javax.swing.JOptionPane;

import e3ps.common.message.KETMessageService;
import e3ps.edm.clients.batch.progress.ProgressMonitor;
import e3ps.edm.clients.batch.progress.ProgressUtil;

public class HeavyProgressBar {

	// Must be volatile:
	private volatile boolean stop = false;

	private int min = 1;
	private int max = 100;

	private Component component;
	private String comment;
	private boolean indeterminate;
	private int milliSecondsToWait;

	public HeavyProgressBar(Component _component, String _comment,
			boolean _indeterminate, int _min, int _max, int _milliSecondsToWait) {

		component = _component;
		comment = _comment;
		indeterminate = _indeterminate;
		min = _min;
		max = _max;
		milliSecondsToWait = _milliSecondsToWait;
	}

	public void go() {
		new Thread(new Runnable() {
			public void run(){
				ProgressMonitor monitor = ProgressUtil.createModalProgressMonitor(
						component, max, indeterminate, milliSecondsToWait);
				monitor.start(comment);
				try {
					while(true) {
						fetchRecord(100);
						monitor.setCurrent(comment, min);
						if(stop) { break; }
					}

					fetchRecord(100);
					monitor.setCurrent(comment, max);

				} finally{
		        	// to ensure that progress dlg is closed in case of any exception
		            if(monitor.getCurrent() != monitor.getTotal()) {
		            	fetchRecord(100);
		            	monitor.setCurrent(null, max);
		                //monitor.setCurrent(null, monitor.getTotal());
		            }
		        }
	        }
		}).start();
	}

	public synchronized void fetchRecord(int index){
        try{
        	Thread.sleep(index);
            //Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

	public synchronized void setComment(String _comment) {
		comment = _comment;
	}

	public synchronized void setStop() {
		stop = true;
	}

	public synchronized void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(component,msg, KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/,
    				JOptionPane.INFORMATION_MESSAGE);
	}

}
