package e3ps.bom.command.bomdetails;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.ScreenCenterer;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class BOMDetailsDialog extends  AbstractAIFDialog
{
	private JTextField createdByTfl = null;
	private JTextField createdDateTfl = null;
	private JTextField gItemCodeTfl = null;
	private JTextField gDescTfl = null;
	private JTextField gUomTfl = null;
	private JTextField gUitTfl = null;
	private JTextField gStatusTfl = null;
	private JTextField rItemCodeTfl = null;
	private JTextField rDescTfl = null;

	private JButton closeBtn = null;

    private BtnListener btnListener;

	private JFrame frmDesktop;
	AbstractAIFUIApplication app;
	BOMRegisterApplicationPanel pnl;
	private BOMRegisterDesktop desktop;
	Registry appReg;

	String orgCode = "";
	String orgName = "";

	class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Close"))
			{
				closeBtn_process();
			}
		};
	}

	public BOMDetailsDialog()
	{
	}

	public BOMDetailsDialog(JFrame frame, AbstractAIFUIApplication app)
	{
		super(frame, true);
		initialize(frame,app);

		setSize(500, 380);

		ScreenCenterer scent = new ScreenCenterer();
		Dimension dimCenter = new Dimension(scent.getCenterDim(this));
		setLocation(dimCenter.width, dimCenter.height);
		setVisible(true);
	}

	private void initialize(JFrame frame, AbstractAIFUIApplication app)
	{
		try
		{
			frmDesktop = frame;
			this.app = app;
			appReg = Registry.getRegistry(app);
			desktop = (BOMRegisterDesktop)frame;
			pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();

			setTitle("BOM Details");
			setResizable(false);

			orgCode = BOMBasicInfoPool.getOrgCode().trim();
			orgName = BOMBasicInfoPool.getOrgName().trim();

			setContentInit();
		}
		catch (Throwable ex)
		{
			Kogger.error(getClass(), ex);
		}
	}

	private void setContentInit() throws Exception
	{
		try
		{
			Registry appReg = Registry.getRegistry(app);

			btnListener = new BtnListener();

			// Owner 정보 Display Panel
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JLabel createdByLbl = new JLabel("  Created By : ");
			createdByLbl.setFont(FontList.defaultFont);
			createdByLbl.setEnabled(false);
			topPanel.add(createdByLbl);

			createdByTfl = new JTextField(10);
			createdByTfl.setEnabled(false);
			createdByTfl.setText(BOMBasicInfoPool.getPublicOwnerName());
			createdByTfl.setFont(FontList.defaultFont);
			topPanel.add(createdByTfl);

			JLabel createdDateLbl = new JLabel("    Created : ");
			createdDateLbl.setFont(FontList.defaultFont);
			createdDateLbl.setEnabled(false);
			topPanel.add(createdDateLbl);

			createdDateTfl = new JTextField(15);
			createdDateTfl.setEnabled(false);
			createdDateTfl.setText(BOMBasicInfoPool.getPublicOwnerDate());
			createdDateTfl.setFont(FontList.defaultFont);
			topPanel.add(createdDateTfl);

			this.getContentPane().add(topPanel, BorderLayout.NORTH);

			// General Panel /////////////////////////////////////////////////
			JPanel generalPanel = new JPanel();
			generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
			generalPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " General BOM ", 0, 0, FontList.defaultFont));

			// 첫번째 검색 라인
			JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel gItemCodeLbl = new JLabel(" Product / Item Code : ");
			gItemCodeLbl.setFont(FontList.defaultFont);
			gItemCodeLbl.setEnabled(false);
			panel01.add(gItemCodeLbl);

			gItemCodeTfl = new JTextField("", 15);
			gItemCodeTfl.setFont(FontList.defaultFont);
			gItemCodeTfl.setEnabled(false);
			gItemCodeTfl.setText(BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim().substring(3));
			panel01.add(gItemCodeTfl);

			generalPanel.add(panel01);

			// 두번째 검색 라인
			JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel gDescLbl = new JLabel("             Description : ");
			gDescLbl.setFont(FontList.defaultFont);
			gDescLbl.setEnabled(false);
			panel02.add(gDescLbl);

			gDescTfl = new JTextField(35);
			gDescTfl.setFont(FontList.defaultFont);
			gDescTfl.setEnabled(false);
			gDescTfl.setText(BOMBasicInfoPool.getPublicModelDesc());
			panel02.add(gDescTfl);

			generalPanel.add(panel02);

			// 세번째 검색 라인
			JPanel panel03 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel gUomLbl = new JLabel("                      UOM : ");
			gUomLbl.setFont(FontList.defaultFont);
			gUomLbl.setEnabled(false);
			panel03.add(gUomLbl);

			gUomTfl = new JTextField(35);
			gUomTfl.setFont(FontList.defaultFont);
			gUomTfl.setEnabled(false);
			gUomTfl.setText(BOMBasicInfoPool.getPublicModelUom());
			panel03.add(gUomTfl);

			generalPanel.add(panel03);

			// 네번째 검색 라인
			JPanel panel04 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel gUitLbl = new JLabel("        User Item Type : ");
			gUitLbl.setFont(FontList.defaultFont);
			gUitLbl.setEnabled(false);
			panel04.add(gUitLbl);

			gUitTfl = new JTextField(35);
			gUitTfl.setFont(FontList.defaultFont);
			gUitTfl.setEnabled(false);
			gUitTfl.setText(BOMBasicInfoPool.getPublicModelUserItemType());
			panel04.add(gUitTfl);

			generalPanel.add(panel04);

			// 다섯번째 검색 라인
			JPanel panel05 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel gStatus = new JLabel("                    Status : ");
			gStatus.setFont(FontList.defaultFont);
			gStatus.setEnabled(false);
			panel05.add(gStatus);

			gStatusTfl = new JTextField(35);
			gStatusTfl.setFont(FontList.defaultFont);
			gStatusTfl.setEnabled(false);
			gStatusTfl.setText(BOMBasicInfoPool.getPublicModelStatus());
			panel05.add(gStatusTfl);

			generalPanel.add(panel05);

			// Reference Panel /////////////////////////////////////////////////
			JPanel referencePanel = new JPanel();
			referencePanel.setLayout(new BoxLayout(referencePanel, BoxLayout.Y_AXIS));
			referencePanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), " Reference BOM ", 0, 0, FontList.defaultFont));
			referencePanel.setFont(FontList.defaultFont);

			// 첫번째 검색 라인
			JPanel panel06 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel rItemCodeLbl = new JLabel("Reference Product Code : ");
			rItemCodeLbl.setFont(FontList.defaultFont);
			rItemCodeLbl.setEnabled(false);
			panel06.add(rItemCodeLbl);

			rItemCodeTfl = new JTextField("", 15);
			rItemCodeTfl.setFont(FontList.defaultFont);
			rItemCodeTfl.setEnabled(false);
			String rItemCodeStr = "";
			if(BOMBasicInfoPool.getPublicBasicModelName().trim().length() > 3)
			{
				rItemCodeStr = BOMBasicInfoPool.getPublicBasicModelName().trim().substring(3);
			}
			else
			{
				rItemCodeStr = "";
			}
			rItemCodeTfl.setText(BOMBasicInfoPool.getPublicBasicModelName() == null ? "" : rItemCodeStr);
			panel06.add(rItemCodeTfl);

			referencePanel.add(panel06);

			// 두번째 검색 라인
			JPanel panel07 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel rDescLbl = new JLabel("                   Description : ");
			rDescLbl.setFont(FontList.defaultFont);
			rDescLbl.setEnabled(false);
			panel07.add(rDescLbl);

			rDescTfl = new JTextField(32);
			rDescTfl.setFont(FontList.defaultFont);
			rDescTfl.setEnabled(false);
			rDescTfl.setText(BOMBasicInfoPool.getPublicBasicModelDesc());
			panel07.add(rDescTfl);

			referencePanel.add(panel07);

			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
			centerPanel.add(generalPanel);
			centerPanel.add(referencePanel);

			this.getContentPane().add(centerPanel, BorderLayout.CENTER);

			// Button Panel /////////////////////////////////////////////////
			JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			closeBtn = new JButton("Close", appReg.getImageIcon("closeIcon"));
			closeBtn.setFont(FontList.defaultFont);
			closeBtn.setActionCommand("Close");
			closeBtn.addActionListener(btnListener);
			closeBtn.setMargin(new Insets(0,5,0,5));
			btnFlowPanel.add(closeBtn);

			this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
    }

	private void closeBtn_process()
	{
		try
		{
			int count = getComponentCount();
			for(int i=0; i<count; i++)
			{
				Component c = getComponent(i);
				this.remove(c);
				c = null;
			}

			super.dispose();
			System.gc();
			return;
		}
		catch (Throwable ex)
		{
			Kogger.error(getClass(), ex);
		}
	}
}
