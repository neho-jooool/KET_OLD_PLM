package e3ps.bom.command.managecoworker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.util.BorderList;
import e3ps.bom.common.util.FontList;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.framework.util.Separator;
import e3ps.bom.framework.util.VerticalLayout;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class FindUserDialog extends AbstractAIFDialog
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JPanel jpanel;
    private JPanel topPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    private JTable table = new JTable();

    private JTextField nameTfl, deptTfl;
    private JButton searchBtn, okBtn, cancelBtn;

    private String m_userName;
    private String m_userDept = "";
    private int m_selectedRow;


    private int intRowCount;
    private String aryResult [] = new String[4];
    private boolean isOK = false;

    FindUserTableData findUserData = new FindUserTableData();

    private AbstractAIFUIApplication app;
    private Registry appReg;

    private JFrame desktop;
    private String m_dialogTitle;

    public FindUserDialog(String name, String dept, AbstractAIFUIApplication app, String title)
    {
        super(true);

        table = new JTable();
        aryResult = new String[4];
        isOK = false;
        findUserData = new FindUserTableData();
        m_userName = null;
        m_dialogTitle=title;
        m_selectedRow = -1;
        m_userName = name;
        m_userDept = dept;
        m_dialogTitle=title;
        app = app;
        appReg = Registry.getRegistry(app);
        desktop = ((AbstractAIFUIApplication)app).getDesktop();

        initializeDialog();
    }

    private void initializeDialog()
    {
        setSize(520, 300);
        setTitle(m_dialogTitle);
        setResizable(false);

        jpanel = new JPanel(new VerticalLayout(5, 2, 2, 2, 2));
        getContentPane().add(jpanel);

        topPanel = new JPanel();

        JLabel nameLbl = new JLabel(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00270")/*이름*/ + " : ");
        nameLbl.setFont(FontList.defaultFont);
        topPanel.add(nameLbl);

        nameTfl = new JTextField(14);
        nameTfl.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearTable();
                m_userName = getNameinTextfield();
                m_userDept = getDeptinTextfield();
                getUserData(m_userName, m_userDept);
            }
        });
        nameTfl.setText(m_userName);
        topPanel.add(nameTfl);

        JLabel deptLbl = new JLabel("      " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00171")/*부서*/ + " : ");
        deptLbl.setFont(FontList.defaultFont);
        topPanel.add(deptLbl);

        deptTfl = new JTextField(14);
        deptTfl.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearTable();
                m_userName = getNameinTextfield();
                m_userDept = getDeptinTextfield();
                getUserData(m_userName, m_userDept);
            }
        });
        deptTfl.setText(m_userDept);
        topPanel.add(deptTfl);

        searchBtn = new JButton(appReg.getImageIcon("searchIcon"));
        searchBtn.setMargin(new Insets(0, 0, 0, 0));
        searchBtn.setBorder(BorderList.emptyBorder1);
        searchBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                clearTable();
                m_userName = getNameinTextfield();
                m_userDept = getDeptinTextfield();
                getUserData(m_userName, m_userDept);
             }
         });
        topPanel.add(searchBtn);

        findUserData.vecWorkerData.removeAllElements();

        tablePanel = new JPanel(new BorderLayout());
        table.setAutoCreateColumnsFromModel(false);
        table.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(findUserData);

        for(int k = 0; k < FindUserTableData.clmWorkerData.length; k++)
        {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(FindUserTableData.clmWorkerData[k].intAlignment);
            TableColumn column = new TableColumn(k, FindUserTableData.clmWorkerData[k].intWidth, renderer, null);
            table.addColumn(column);
        }

        JTableHeader headerWorker = table.getTableHeader();
        headerWorker.setUpdateTableInRealTime(false);

        table.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                m_selectedRow = table.getSelectedRow();
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() >= 2)
                {
                    m_selectedRow = table.getSelectedRow();
                    okBtn_process(false);
                }
            }
        });

        JScrollPane spWorker = new JScrollPane();
        spWorker.getViewport().add(table);
        tablePanel.add(spWorker);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn column = columnModel.getColumn(0);
        column.setWidth(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setResizable(false);

        tablePanel.setPreferredSize(new Dimension(490,250));

        okBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00311")/*적용*/, appReg.getImageIcon("okIcon"));
        okBtn.setMargin(new Insets(0, 5, 0, 5));
        okBtn.setFont(FontList.defaultFont);
        okBtn.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                okBtn_process(false);
            }
        });

        cancelBtn = new JButton(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00128")/*닫기*/, appReg.getImageIcon("closeIcon"));
        cancelBtn.setMargin(new Insets(0, 5, 0, 5));
        cancelBtn.setFont(FontList.defaultFont);
        cancelBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                setVisible(false);
                dispose();
            }
        });

        buttonPanel = new JPanel();
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);

        jpanel.add("top.nobind",topPanel);
        jpanel.add("top.center",new Separator());
        jpanel.add("unbound.bind",tablePanel);
        jpanel.add("bottom.nobind.center.bind",new Separator());
        jpanel.add("bottom.nobind.center.center",buttonPanel);

        pack();
        centerToScreen(1.0D,1.0D);

        getUserData(m_userName, m_userDept);
    }

    private String getNameinTextfield()
    {
        if(nameTfl.getText() !=null)
            return nameTfl.getText();
        else
            return "";
    }

    private String getDeptinTextfield()
    {
        if(deptTfl.getText() !=null)
            return deptTfl.getText();
        else
            return "";
    }

    private void clearTable()
    {
        findUserData.vecWorkerData.removeAllElements();
        m_selectedRow = -1;
    }

    private void getUserData(String name, String dept)
    {
        String compair = "";
        String compair2 = "";
        if(name.indexOf("*")>-1)
        {
            name = name.replace("*", "%");
            compair = " like ";
        }else{
            compair = " = ";
        }

        if(dept.indexOf("*")>-1)
        {
            dept = dept.replace("*", "%");
            compair2 = " like ";
        }else{
            compair2 = " = ";
        }

        DBConnectionManager res = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String SQL = "";
        try
        {
            Vector userInfo = new Vector();
            String orgCode = BOMBasicInfoPool.getOrgCode() == null ? "" : BOMBasicInfoPool.getOrgCode().trim();

            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));

            stmt = conn.createStatement();
/*
            SQL = " SELECT                                                                        ";
            SQL += "    plm_id userId                                                            ";
            SQL += " ,    usr_name userName                                                ";
            SQL += " ,    usr_email userMail                                                    ";
            SQL += " ,    usr_department userDept                                            ";
            SQL += " FROM                                                                        ";
            SQL += "    (SELECT * FROM lsisgrouporglink l                            ";
            SQL += "    WHERE l.orgcode =    '" + orgCode + "') lk,                    ";
            SQL += "    (SELECT * FROM ept_user u                                    ";
        if((!name.trim().equals("")) && (!dept.trim().equals("")))
        {
            SQL += "    WHERE u.usr_name LIKE '" + name.trim() + "'                ";
            SQL += "    AND u.usr_department LIKE '" + dept.trim() + "'            ";
        }
        else if((!name.trim().equals("")) && (dept.trim().equals("")))
        {
            SQL += "    WHERE u.usr_name LIKE '" + name.trim() + "'                ";
        }
        else if((name.trim().equals("")) && (!dept.trim().equals("")))
        {
            SQL += "    WHERE u.usr_department LIKE '" + dept.trim() + "'        ";
        }
            SQL += "    ) usr                                                                        ";
            SQL += " WHERE lk.deptcd = usr.deptcd                                    ";
*/
//shin....
          SQL  = "select userId,userName,userMail,userDept from          \n";
          SQL += "(                                                                 \n";
          SQL += "       select p.id as userId                               \n";
          SQL += "       ,p.name as userName                             \n";
          SQL += "       ,p.email as userMail                               \n";
          SQL += "       ,t.code as codeDept                               \n";
          SQL += "       ,t.name as userDept                               \n";
          SQL += "       from department t, people p                    \n";
          SQL += "       where p.ida3c4 = t.ida2a2(+)                    \n";
          SQL += ") u                                                          \n";

          if( ((!name.trim().equals("")) && (!dept.trim().equals(""))) || ((name.trim().equals("")) && (dept.trim().equals(""))) )
            {
                SQL += "    WHERE u.userName "+compair+" '" + name.trim() + "'        \n";
                SQL += "    AND u.userDept "+compair2+" '" + dept.trim() + "'            \n";
            }
            else if((!name.trim().equals("")) && (dept.trim().equals("")))
            {
                SQL += "    WHERE u.userName "+compair+" '" + name.trim() + "'        \n";
            }
            else if((name.trim().equals("")) && (!dept.trim().equals("")))
            {
                SQL += "    WHERE u.userDept "+compair2+" '" + dept.trim() + "'         \n";
            }

Kogger.debug(getClass(), "===>> SQL : " + SQL);

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                findUserData.vecWorkerData.addElement(new FindUserData(Utility.checkNVL(rs.getString("userId")), Utility.checkNVL(rs.getString("userName")), Utility.checkNVL(rs.getString("userMail")), Utility.checkNVL(rs.getString("userDept"))));
                findUserData.fireTableRowsInserted(0, findUserData.getRowCount()-1);

                userInfo.addElement(rs.getString("userId"));
            }

            table.clearSelection();
            m_selectedRow = -1;
            table.repaint();

            //ket에서는 한명이라도 일단 검색창에서 선택하도록 함.
            /*if(userInfo.size() == 1)
            {
                m_selectedRow=0;
                okBtn_process(true);
            }
            else
            {
                setVisible(true);
            }*/
            setVisible(true);
        }
        catch( Exception wt )
        {
            Kogger.error(getClass(), wt);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(appReg.getString("plm"), conn);
                }
            }
        }

   }

    private void okBtn_process(boolean  rc)
    {
        if(m_selectedRow == -1)
        {
            MessageBox messagebox = new MessageBox(desktop, messageRegistry.getString("selectRow12"), "Select Error", 0);
            messagebox.setModal(true);
            messagebox.setVisible(true);
            return;
        }
        else
        {
            isOK = true;

            if(!rc)
            {
                intRowCount = table.getSelectedRow();
            }
            else
            {
                intRowCount = 0;
            }

            aryResult[0] = (String)table.getValueAt(intRowCount, 0);
            aryResult[1] = (String)table.getValueAt(intRowCount, 1);
            aryResult[2] = (String)table.getValueAt(intRowCount, 2);
            aryResult[3] = (String)table.getValueAt(intRowCount, 3);

            setVisible(false);
            dispose();
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

}
