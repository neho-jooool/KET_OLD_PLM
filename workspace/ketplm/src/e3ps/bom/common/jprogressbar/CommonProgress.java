package e3ps.bom.common.jprogressbar;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;

public class CommonProgress extends JDialog implements Runnable {

	private JProgressBar progress;
	private JLabel messageLbl, timeLbl, elapseLbl;
	private Timer timer;

	private Registry registry;

	private String initialMessage;
	private final static int TIME_UNIT = 100;

	public CommonProgress(JFrame frame, AbstractAIFUIApplication app, String message) {
		super(frame, false);

		registry = Registry.getRegistry(app);
		initialMessage = message;
	}

	public void run() {
		setGUI();

		timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
		timer.start();
	}

	private void setGUI() {
		getContentPane().setLayout(null);
		JPanel contentPanel = new JPanel();
		contentPanel.setBounds(new Rectangle(9, 5, 310, 105));
		contentPanel.setLayout(null);

		messageLbl = new JLabel(initialMessage);
//		timeLbl = new JLabel("Elapse Time :");
//		elapseLbl = new JLabel("0");

		progress = new JProgressBar();

		messageLbl.setBounds(new Rectangle(9, 5, 310, 24));
		progress.setBounds(new Rectangle(9, 31, 290, 15));
//		timeLbl.setBounds(new Rectangle(9, 48, 80, 22));
//		elapseLbl.setBounds(new Rectangle(91, 48, 200, 22));

		contentPanel.add(messageLbl, null);
		contentPanel.add(progress, null);
//		contentPanel.add(timeLbl, null);
//		contentPanel.add(elapseLbl, null);

		this.getContentPane().add(contentPanel, null);

		setTitle("In Progress");
		setSize(335, 145);

		ScreenCenterer scent = new ScreenCenterer();
		Dimension dimCenter = new Dimension(scent.getCenterDim(this));

		setLocation(dimCenter.width, dimCenter.height);
		setVisible(true);
	}

	public void setMessage(String message) {
		messageLbl.setText(message);
	}

	public void stop() {
		timer.stop();
		dispose();
		setVisible(false);
	}

	class TimerListener implements ActionListener {
		int totalTime = 0;

		int progressCount = 0;

		public void actionPerformed(ActionEvent evt) {
			if (progressCount == 100) {
				progressCount = 0;
			}
			// Progress 산출..
			progress.setValue(progressCount);
			// elapseLbl.setText(String.valueOf(totalTime / 10) + "s");

			progressCount++;
			totalTime++;
		}
	}
}
