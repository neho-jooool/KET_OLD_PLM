package e3ps.bom.command.comparebom;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import e3ps.bom.command.comparebom.staticcompare.StaticComparePanel;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;

public class CompareProgressDialog extends JDialog implements Runnable
{
    private static final long serialVersionUID = 1L;
    private JProgressBar progress;
    private JButton cancelBtn;
    private JLabel messageLbl, timeLbl, elapseLbl;
    private Timer timer;

    private AbstractAIFUIApplication app;
    private Registry registry;

    private CompareFrame compareFrame;
    private StaticComparePanel staticComparePanel;

    private String initialMessage;
    private final static int TIME_UNIT = 100;

    public CompareProgressDialog(CompareFrame compareFrame, String message)
    {
        super(compareFrame, false);

        app = compareFrame.getApplication();
        registry = Registry.getRegistry(app);
        initialMessage = message;

        staticComparePanel = compareFrame.getStaticComparePanel();
        staticComparePanel.setCancelFlag(false);
    }

    public void run()
    {
        setGUI();
        setEvent();

        timer = new javax.swing.Timer(TIME_UNIT, new TimerListener());
        timer.start();
    }

    private void setGUI()
    {
        getContentPane().setLayout(null);
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(new Rectangle(9, 5, 310, 105));
        contentPanel.setLayout(null);

        messageLbl = new JLabel(initialMessage);
        timeLbl = new JLabel("Elapse Time :");
        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/, registry.getImageIcon("closeIcon"));
        elapseLbl = new JLabel("0");

        progress = new JProgressBar();

        messageLbl.setBounds(new Rectangle(9, 5, 310, 24));
        progress.setBounds(new Rectangle(9, 31, 290, 15));
        timeLbl.setBounds(new Rectangle(9, 48, 80, 22));
        elapseLbl.setBounds(new Rectangle(91, 48, 200, 22));
        cancelBtn.setBounds(new Rectangle(113, 72, 108, 24));

        contentPanel.add(messageLbl, null);
        contentPanel.add(progress, null);
        contentPanel.add(timeLbl, null);
        contentPanel.add(elapseLbl, null);
        contentPanel.add(cancelBtn, null);

        this.getContentPane().add(contentPanel,  null);

        setTitle(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00027")/*BOM 비교*/);
        setSize(335, 145);

        ScreenCenterer scent = new ScreenCenterer();
        Dimension dimCenter = new Dimension(scent.getCenterDim(this));

        setLocation(dimCenter.width, dimCenter.height);
        setVisible(true);
    }

    private void setEvent()
    {
        cancelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (cancelBtn.getText().equals(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00341")/*취소*/))
                    cancelOperation();
                closeOperation();
            }
        });
    }

    public void setMessage(String message)
    {
        messageLbl.setText(message);
    }

    private void cancelOperation()
    {
        staticComparePanel.setCancelFlag(true);
    }

    public void closeOperation()
    {
        timer.stop();
        dispose();
        setVisible(false);
    }

    public void complete()
    {
        cancelBtn.setText(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00268")/*완료*/);
        cancelBtn.setIcon(registry.getImageIcon("finditemIcon"));
        timer.stop();
        closeOperation();
    }

    class TimerListener implements ActionListener
    {
        int totalTime = 0;
        int progressCount = 0;

        public void actionPerformed(ActionEvent evt)
        {
            if (progressCount == 100)
            {
                progressCount = 0;
            }
            // Progress 산출..
            progress.setValue(progressCount);
            elapseLbl.setText(String.valueOf(totalTime / 10) + "s");

            progressCount++;
            totalTime++;
        }
    }
}
