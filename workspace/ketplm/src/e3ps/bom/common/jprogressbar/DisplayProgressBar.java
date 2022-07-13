package e3ps.bom.common.jprogressbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import ext.ket.shared.log.Kogger;

public class DisplayProgressBar extends JPanel
{
    JDialog frame;
    JPanel progressPanel;
    JLabel lbl;
    private JProgressBar jProgressBar;
    Thread runner;

    public DisplayProgressBar()
    {
        runner = null;
    }

    public void startProgressBar()
    {
        try
        {
            runner = new Thread()
			{
                public void run()
                {
                    frame = new JDialog(new JFrame(), "JProgressBar");
                    progressPanel = new JPanel();
                    progressPanel.setLayout(new BorderLayout());
                    progressPanel.setBorder(BorderFactory.createEtchedBorder());
                    progressPanel.setBounds(new Rectangle(1, 101, 310, 30));
                    jProgressBar = new JProgressBar();
                    jProgressBar.setBorder(BorderFactory.createLineBorder(Color.black));
                    jProgressBar.setPreferredSize(new Dimension(285, 22));
                    jProgressBar.setStringPainted(false);
                    lbl = new JLabel("                                        Saving...                                        ");
                    progressPanel.add(jProgressBar, "Center");
                    progressPanel.add(lbl, "South");
                    frame.getContentPane().add(progressPanel, "Center");
                    frame.setBounds(500, 350, 300, 85);
                    frame.setModal(true);
                    jProgressBar.setIndeterminate(true);
                    frame.setVisible(true);
                }
            };
            runner.start();
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

    public void stopProgressBar()
    {
        try
        {
            jProgressBar.setIndeterminate(false);
            frame.setVisible(false);
        }
        catch(Exception ex)
        {
            Kogger.error(getClass(), ex);
        }
    }

}
