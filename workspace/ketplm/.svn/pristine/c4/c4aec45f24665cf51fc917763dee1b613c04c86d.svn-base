package e3ps.bom.common.jprogressbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class UDProgressBar
{
    public static boolean processStop = false;
    private JComponent gpane;
    private JFrame desktop;
    private JPanel mainPNL;
    private JPanel statusPNL;
    private JPanel progressPanel;
    private JProgressBar jProgressBar;
    private Registry appReg;
    private String where;

    public UDProgressBar(AbstractAIFUIApplication app, String where)
    {
        desktop = app.getDesktop();
        this.where = where;
        appReg = Registry.getRegistry(app);
        makeUI();
    }

    private void makeUI()
    {
        mainPNL = new JPanel();
        progressPanel = new JPanel();
        statusPNL = new JPanel();
        statusPNL.setBackground(Color.black);
        statusPNL.setLayout(null);
        JLabel label1 = new JLabel(appReg.getImageIcon("processtitleIcon"));
        JLabel label2 = new JLabel(appReg.getImageIcon("processbodyIcon"));
        label1.setBounds(new Rectangle(1, 1, 310, 20));
        label2.setBounds(new Rectangle(1, 21, 310, 80));
        progressPanel.setLayout(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEtchedBorder());
        progressPanel.setBounds(new Rectangle(1, 101, 310, 30));
        jProgressBar = new JProgressBar();
        jProgressBar.setBorder(BorderFactory.createLineBorder(Color.black));
        jProgressBar.setPreferredSize(new Dimension(335, 25));
        jProgressBar.setStringPainted(false);
        progressPanel.add(jProgressBar, null);
        statusPNL.add(label1);
        statusPNL.add(label2);
        statusPNL.add(progressPanel);
        setBounds(312, 132, where);
        gpane = (JComponent)desktop.getRootPane().getGlassPane();
        gpane.setLayout(null);
        gpane.add(statusPNL);
        gpane.setVisible(true);
    }

    public void startProgressBar()
    {
        try
        {
            jProgressBar.setIndeterminate(true);
        }
        catch(Exception e)
        {
            Kogger.debug(getClass(), e.toString() + "Thread start");
        }
    }

    public void stopProgressBar()
    {
        jProgressBar.setIndeterminate(false);
        gpane.setVisible(false);
        gpane.removeAll();
    }

    public void setBounds(int width, int height, String where)
    {
        int value = 0;
        if(where.equals("transfer"))
		{
            value = 9;
		}
        else
		{
            value = 2;
		}

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int i = (dim.width - width) / 2;
        int j = (dim.height - height) / value;
        statusPNL.setBounds(i, j, width, height);
    }

}
