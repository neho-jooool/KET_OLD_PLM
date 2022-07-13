package e3ps.edm.clients.batch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.common.message.KETMessageService;
import e3ps.edm.clients.batch.util.FontList;
import e3ps.edm.clients.batch.util.ScreenCenterer;
import e3ps.edm.clients.batch.util.TableSorter;

public class EDMProjectSearchDialog extends JDialog implements ClipboardOwner
{
	private static final long serialVersionUID = 1L;
    private BtnListener btnListener;
    JTable tblList = new JTable();
    private TableSorter sorter;
    private EDMProjectSearchTableData dataList;
    boolean keyCheck = false;

    private Vector<String> statusVec = new Vector<String>();
    private Hashtable<String, String> statusHash = new Hashtable<String, String>();
    private String div_type = "";
    private String project_type = "";

	EPMLoadContext loadctx;

    private JTextField projectNumberTf;
    private JTextField projectNameTf;
    private JTextField projectGubunTf;

	private JComboBox stateCmb;

	private JLabel searchResultLbl;

    private JButton searchBtn;
    private JButton clearBtn;
    private JButton okBtn;
    private JButton closeBtn;

    private boolean isOK = false;
    private int intRowCount = -1;
	private String aryResult [] = new String[6];
	Vector vecModel = new Vector();

	private Clipboard clipboard;

    class BtnListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("Search"))
			{
                searchBtn_process();
			}
			else if (e.getActionCommand().equals("Clear"))
			{
                clearBtn_process();
            }
			else if (e.getActionCommand().equals("OK"))
			{
                okBtn_process();
			}
			else if (e.getActionCommand().equals("Close"))
			{
                disposeScreen();
            }
		}
	}

    public EDMProjectSearchDialog( String divType, String title, String userID, String language )
	{
        super((JFrame)null, title, true);

        this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				disposeScreen();
			}
		});

		try
		{

			if( divType.equalsIgnoreCase("C") ) // 자동차사업부
			{
				this.div_type = "1";
				this.project_type = "2";
			}
			else if( divType.equalsIgnoreCase("E") ) // 전자사업부
			{
				this.div_type = "2";
				this.project_type = "4";
			}

System.out.println( "========> div_type : " + div_type );
System.out.println( "========> project_type : " + project_type );

			loadctx = new EPMLoadContext(userID);

			setStatusHash();
			setStatusVec();

			vecModel = new Vector();
	        dataList = new EDMProjectSearchTableData(vecModel);

		    jInit();

            setSize(550,400);
            setResizable(false);

            ScreenCenterer scent = new ScreenCenterer();
            Dimension dimCenter = new Dimension(scent.getCenterDim(this));
            setLocation(dimCenter.width, dimCenter.height);
            setVisible(true);

			projectNumberTf.requestFocus();
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
    }

	private void jInit() throws Exception
	{
		try
		{
			btnListener = new BtnListener();

			// Search Condition Panel /////////////////////////////////////////////////
			JPanel topPanel = new JPanel();
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
			topPanel.setBorder(new TitledBorder(new LineBorder(Color.gray, 1), KETMessageService.service.getString("e3ps.message.ket_message", "03059")/*프로젝트 검색*/ + " ", 0, 0, FontList.defaultFont));
			topPanel.setFont(FontList.defaultFont);

			// 첫번째 검색 라인
			JPanel panel01 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JLabel projectNumberLbl = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "03114")/*프로젝트번호*/ + " : ");
			projectNumberLbl.setPreferredSize( new Dimension(100, 22) );
			projectNumberLbl.setHorizontalAlignment(SwingConstants.RIGHT);
			projectNumberLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
			projectNumberLbl.setFont(FontList.defaultFont);

			panel01.add(projectNumberLbl);

			projectNumberTf = new JTextField(13);
			projectNumberTf.addActionListener( new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					searchBtn_process();
				}
			});

//			projectNumberTf.addKeyListener(new KeyAdapter()
//			{
//				public void keyTyped(KeyEvent keyevent)
//				{
//					char c = keyevent.getKeyChar();
//					int cCode = (int)c;
//
//					if ( cCode >= 97 && cCode <= 122 )
//					{
//						keyCheck = true;
//					}
//				}
//				public void keyReleased(KeyEvent keyevent)
//				{
//					if ( keyCheck ) {
//						JTextField tfl = (JTextField)keyevent.getSource();
//						int pos = tfl.getCaretPosition();
//
//						String s = tfl.getText();
//						tfl.setText(s.toUpperCase());
//						tfl.setCaretPosition(pos);
//						keyCheck = false;
//					}
//				}
//			});

			panel01.add(projectNumberTf);

			JLabel projectNameLbl = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : ");
			projectNameLbl.setPreferredSize( new Dimension(100, 22) );
			projectNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
			projectNameLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
			projectNameLbl.setFont(FontList.defaultFont);

			panel01.add(projectNameLbl);

			projectNameTf = new JTextField(13);
			projectNameTf.addActionListener( new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					searchBtn_process();
				}
			});

//			projectNameTf.addKeyListener(new KeyAdapter()
//			{
//				public void keyTyped(KeyEvent keyevent)
//				{
//					char c = keyevent.getKeyChar();
//					int cCode = (int)c;
//
//					if ( cCode >= 97 && cCode <= 122 )
//					{
//						keyCheck = true;
//					}
//				}
//				public void keyReleased(KeyEvent keyevent)
//				{
//					if ( keyCheck )
//					{
//						JTextField tfl = (JTextField)keyevent.getSource();
//						int pos = tfl.getCaretPosition();
//
//						String s = tfl.getText();
//						tfl.setText(s.toUpperCase());
//						tfl.setCaretPosition(pos);
//						keyCheck = false;
//					}
//				}
//			});

			panel01.add(projectNameTf);
			topPanel.add(panel01);

			// 두번째 검색 라인
			JPanel panel02 = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JLabel projectTypeLbl = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "00969")/*구분*/ + " : ");
			projectTypeLbl.setPreferredSize( new Dimension(100, 22) );
			projectTypeLbl.setHorizontalAlignment(SwingConstants.RIGHT);
			projectTypeLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
			projectTypeLbl.setFont(FontList.defaultFont);

			panel02.add(projectTypeLbl);

			projectGubunTf = new JTextField(13);
			projectGubunTf.setEditable(false);
			projectGubunTf.setText(KETMessageService.service.getString("e3ps.message.ket_message", "02630")/*제품프로젝트*/);
			projectGubunTf.setBackground(Color.LIGHT_GRAY);

			panel02.add(projectGubunTf);

			JLabel statusLbl = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "01760")/*상태*/ + " : ");
			statusLbl.setPreferredSize( new Dimension(100, 22) );
			statusLbl.setHorizontalAlignment(SwingConstants.RIGHT);
			statusLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
			statusLbl.setFont(FontList.defaultFont);

			panel02.add(statusLbl);

			stateCmb = new JComboBox(statusVec);
			stateCmb.setSelectedIndex(0);
			stateCmb.setPreferredSize(new Dimension(150,22));
			stateCmb.setBackground(Color.white);
			stateCmb.setFont(FontList.defaultFont);

			panel02.add(stateCmb);
			topPanel.add(panel02);

			// Search / Clear 버튼 라인
			JPanel panel03 = new JPanel( new FlowLayout(FlowLayout.CENTER) );

			clearBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "02819")/*초기화*/);
			clearBtn.setFont(FontList.defaultFont);
			clearBtn.setActionCommand("Clear");
			clearBtn.addActionListener(btnListener);
			clearBtn.setMargin(new Insets(0,5,0,5));

			panel03.add(clearBtn);

			searchBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "00705")/*검색*/);
			searchBtn.setFont(FontList.defaultFont);
			searchBtn.setActionCommand("Search");
			searchBtn.addActionListener(btnListener);
			searchBtn.setMargin(new Insets(0,5,0,5));

			panel03.add(searchBtn);
			topPanel.add(panel03);

			this.getContentPane().add(topPanel, BorderLayout.NORTH);

			// Search Result Panel /////////////////////////////////////////////////
			JPanel centerPanel = new JPanel(new BorderLayout());
			JPanel labelPane = new JPanel(new FlowLayout(FlowLayout.LEFT));

			JLabel cmtLbl = new JLabel(KETMessageService.service.getString("e3ps.message.ket_message", "03429")/*검색 결과*/ + " : ");
			cmtLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
			cmtLbl.setFont(FontList.defaultFont);

			searchResultLbl = new JLabel("" + vecModel.size());
			searchResultLbl.setFont(FontList.defaultFont);
			searchResultLbl.setForeground(Color.red);
			JLabel cmt2Lbl = new JLabel(" " + KETMessageService.service.getString("e3ps.message.ket_message", "00581")/*개*/);
			cmt2Lbl.setFont(FontList.defaultFont);

			labelPane.add(cmtLbl);
			labelPane.add(searchResultLbl);
			labelPane.add(cmt2Lbl);

			tblList.setAutoCreateColumnsFromModel(false);
			tblList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			sorter = new TableSorter(dataList);
			tblList.setModel(sorter);
			sorter.addMouseListenerToHeaderInTable(tblList);
			tblList.repaint();

			for(int k = 0; k < EDMProjectSearchTableData.clmModelData.length; k++)
			{
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(EDMProjectSearchTableData.clmModelData[k].intAlignment);
				TableColumn column = new TableColumn(k, EDMProjectSearchTableData.clmModelData[k].intWidth, renderer, null);
				tblList.addColumn(column);

				TableColumnModel columnModel = tblList.getColumnModel();
				TableColumn _column = columnModel.getColumn(k);
				_column.setCellRenderer(renderer);
			}

			JTableHeader headerModel = tblList.getTableHeader();
			headerModel.setUpdateTableInRealTime(false);

			JScrollPane ps = new JScrollPane();
			ps.getViewport().add(tblList);

			tblList.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

					StringSelection contents = new StringSelection(tblList.getValueAt(tblList.getSelectedRow(),tblList.getSelectedColumn()).toString());
					clipboard.setContents(contents, contents);
				}

				public void mouseClicked(MouseEvent e)
				{
					if(e.getClickCount() >= 2)
					{
						intRowCount = tblList.getSelectedRow();
						aryResult[0] = (String)tblList.getValueAt(intRowCount, 0);
						aryResult[1] = (String)tblList.getValueAt(intRowCount, 1);
						aryResult[2] = (String)tblList.getValueAt(intRowCount, 2);
						aryResult[3] = (String)tblList.getValueAt(intRowCount, 3);
						aryResult[4] = (String)tblList.getValueAt(intRowCount, 4);
						aryResult[5] = (String)tblList.getValueAt(intRowCount, 5);

						isOK = true;

						disposeScreen();
					}
				}
			});

			tblList.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e)
				{
					intRowCount = tblList.getSelectedRow();
					aryResult[0] = (String)tblList.getValueAt(intRowCount, 0);
					aryResult[1] = (String)tblList.getValueAt(intRowCount, 1);
					aryResult[2] = (String)tblList.getValueAt(intRowCount, 2);
					aryResult[3] = (String)tblList.getValueAt(intRowCount, 3);
					aryResult[4] = (String)tblList.getValueAt(intRowCount, 4);
					aryResult[5] = (String)tblList.getValueAt(intRowCount, 5);
				}
			});

			TableColumnModel columnModel = tblList.getColumnModel();
			TableColumn column = columnModel.getColumn(5);
			column.setWidth(0);
			column.setMinWidth(0);
			column.setMaxWidth(0);
			column.setResizable(false);
//
//			column = columnModel.getColumn(6);
//			column.setWidth(0);
//			column.setMinWidth(0);
//			column.setMaxWidth(0);
//			column.setResizable(false);
//
//			column = columnModel.getColumn(7);
//			column.setWidth(0);
//			column.setMinWidth(0);
//			column.setMaxWidth(0);
//			column.setResizable(false);

			centerPanel.add(labelPane, BorderLayout.NORTH);
			centerPanel.add(ps, BorderLayout.CENTER);
			this.getContentPane().add(centerPanel, BorderLayout.CENTER);

			// Button Panel /////////////////////////////////////////////////
			JPanel btnFlowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			okBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/);
			okBtn.setFont(FontList.defaultFont);
			okBtn.setActionCommand("OK");
			okBtn.addActionListener(btnListener);
			okBtn.setMargin(new Insets(0,5,0,5));
			btnFlowPanel.add(okBtn);

			closeBtn = new JButton(KETMessageService.service.getString("e3ps.message.ket_message", "01197")/*닫기*/);
			closeBtn.setFont(FontList.defaultFont);
			closeBtn.setActionCommand("Close");
			closeBtn.addActionListener(btnListener);
			closeBtn.setMargin(new Insets(0,5,0,5));
			btnFlowPanel.add(closeBtn);

			this.getContentPane().add(btnFlowPanel, BorderLayout.SOUTH);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }

	private String checkNull( String str )
	{
		if(str == null) return "";
		return str;
	}

    private Vector<EDMProjectSearchData> getSearchData()
    {
		Vector<EDMProjectSearchData> returnVec = new Vector<EDMProjectSearchData>();

		try
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			HashMap<String, String> mapData = new HashMap<String, String>();

			String state = getStatusKey((String)stateCmb.getModel().getSelectedItem());

System.out.println( "======> State : " + state );

			mapData.put( "pjtNo", checkNull(projectNumberTf.getText()).trim() );
			mapData.put( "pjtName", checkNull(projectNameTf.getText()).trim() );
			mapData.put( "pjtType", project_type );
			mapData.put( "pjtState", checkNull(state) );
			mapData.put( "dType", div_type );
			mapData.put( "command" , "search");

			Vector<Hashtable<String, String>> queryResult = new Vector<Hashtable<String, String>>();
			queryResult = loadctx.find( mapData );

System.out.println( "======> queryResult Size : " + queryResult.size() );

			for(int i=0; i<queryResult.size(); i++)
			{
				Hashtable<String, String> hashSearchReult = new Hashtable<String, String>();
				hashSearchReult = (Hashtable<String, String>)queryResult.elementAt(i);

				returnVec.addElement( new EDMProjectSearchData( hashSearchReult.get("projectNumber")
						                                                     , hashSearchReult.get("projectName")
						                                                     , hashSearchReult.get("planStartDate")
						                                                     , hashSearchReult.get("planEndDate")
						                                                     , hashSearchReult.get("status")
						                                                     , hashSearchReult.get("oid")
				));
			}
		}
		catch(Exception ex)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			ex.printStackTrace();
		}
		finally
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		return returnVec;
	}

	private void searchBtn_process()
	{
//		String projectNumber = projectNumberTf.getText() == null ? "" : projectNumberTf.getText().trim();
//		String projectName = projectNameTf.getText() == null ? "" : projectNameTf.getText().trim();

//		if(projectNumber.equals("") && projectName.equals(""))
//		{
//			JOptionPane.showMessageDialog( this, "프로젝트번호 또는 프로젝트명은 반드시 입력해야 합니다.", "확인", JOptionPane.INFORMATION_MESSAGE );
//
//			projectNumberTf.requestFocus();
//		}
//		else
//		{
			vecModel = getSearchData();

	        searchResultLbl.setText("" + vecModel.size());

			dataList = new EDMProjectSearchTableData(vecModel);

			sorter = new TableSorter(dataList);
			tblList.setModel(sorter);
			sorter.addMouseListenerToHeaderInTable(tblList);
			tblList.repaint();
//		}
    }

    private void clearBtn_process()
	{
		projectNumberTf.setText("");
		projectNameTf.setText("");
		stateCmb.setSelectedIndex(0);
    }

    private void okBtn_process()
	{
        if(intRowCount == -1)
        {
			JOptionPane.showMessageDialog( this, KETMessageService.service.getString("e3ps.message.ket_message", "03111")/*프로젝트를 선택해 주십시오*/,
			        KETMessageService.service.getString("e3ps.message.ket_message", "03226")/*확인*/, JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
            isOK = true;

			disposeScreen();
        }
    }

	public String[] getSelectedColumnData()
	{
		return aryResult;
	}

    public boolean getOK()
    {
        return isOK;
    }

	private void disposeScreen()
	{
		int count = getComponentCount();

		for( int i = 0; i < count; i++ )
		{
	      	Component c = getComponent(i);

	  		this.remove(c);
	  		c = null;
		}
		super.dispose();
		System.gc();
  	}

	private void setStatusHash()
	{
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02485")/*전체*/, "" );
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02726")/*진행*/, "PROGRESS" );
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02356")/*일정변경*/, "PLANCHANGE" );
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02171")/*완료*/, "COMPLETED" );
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02695")/*중지*/, "STOPED" );
		statusHash.put( KETMessageService.service.getString("e3ps.message.ket_message", "02887")/*취소*/, "WITHDRAWN" );
	}

	private void setStatusVec()
	{
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02485")/*전체*/ );
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02726")/*진행*/ );
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02356")/*일정변경*/ );
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02171")/*완료*/ );
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02695")/*중지*/ );
		statusVec.addElement( KETMessageService.service.getString("e3ps.message.ket_message", "02887")/*취소*/ );
	}

	private String getStatusKey( String value )
	{
		String returnStr = "";

		if( statusHash.containsKey(value) )
		{
			returnStr = statusHash.get( value );
		}

		return returnStr;
	}

	public void lostOwnership(Clipboard clip, Transferable transferable)
	{

	}
}
