package com.omok;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.TableColumn;

public class MainClient extends OmokFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JTextArea ta;
	private JTextField tf;
	private JTable table;
	private MyTableModel model;
	private JToolBar tb;
	private JLabel lblLogin=new JLabel("�α��� �ϼ���.", JLabel.CENTER);
	private JLabel lblGame=new JLabel("", JLabel.CENTER);

	private Socket sock = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	private String host;
	private int port = 7777;
	private boolean bLogin=false;

	// ***************************************************************
	// ������
	public MainClient() {
		// ���� ����
		createToolBar();

		panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		panel.setLayout(null);

		gp = new BadukPan(this);
		gp.addMouseListener(new MouseHandler());
		gp.setBounds(10, 10, 380, 380);
		panel.add(gp);
		gp.repaint();

		ta = new JTextArea();
		ta.setEditable(false);
		JScrollPane pane = new JScrollPane(ta);
		pane.setBounds(10, 400, 380, 90);
		panel.add(pane);

		tf = new JTextField();
		tf.addActionListener(this);
		tf.setBounds(10, 495, 380, 20);
		panel.add(tf);

		// ������ ����Ʈ�� ����� JTable
		addTable();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("�������");
		setSize(710, 590);
		setResizable(false);
		setVisible(true);

		// ȭ���� �߾ӿ� ��ġ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	// ***************************************************************
	// �α���
	private void login() {
		try {
			LoginDialog dlg = new LoginDialog(this);
			dlg.setVisible(true);
	
			if (dlg.getUserName().length() == 0 || dlg.getHost().length() == 0) {
				return;
			}
			host = dlg.getHost();
			userName = dlg.getUserName();
			opponentUserName=null;
			
			// ������ �����ϱ�
			sock = new Socket(host, port);
			
			oos = new ObjectOutputStream(sock.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(sock.getInputStream());
			
			// ������ �α��� ��û
			UserMsg loginReq = new UserMsg();
			loginReq.setCode(1100);
			loginReq.setUserName(userName);
			oos.writeObject(loginReq);
			bLogin=true;

			// ������ ����
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			// System.out.println(e.toString());
			JOptionPane.showMessageDialog(this, "������ ������ �߻��Ͽ� ���α׷��� ���� �մϴ�.\n ����� �ٽ� ���� �ϼ��� !!!");
			System.exit(0);
		}
		
	}

	// ***************************************************************
	// �α׾ƿ�
	private void logout() {
		try {
			UserMsg u = new UserMsg();
			u.setCode(1300);
			u.setUserName(userName);

			oos.writeObject(u);
		} catch (Exception e) {
		}
		
		disConnect();
	}
	
	private void disConnect() {
		sock = null;
		userName = null;
		opponentUserName=null;
		host = null;
		oos = null;
		ois = null;
		bLogin=false;
		bGameMe=false;
		bGameOpponent=false;
		lblLogin.setText("�α��� �ϼ���.");
		lblGame.setText("");
		
		// ���̺��� ��� ��� ����
		int size = table.getRowCount();
		for (int index = size - 1; index >= 0; index--) {
			((MyTableModel) table.getModel()).removeRow(index);
		}

		tb.getComponent(0).setEnabled(true);
		tb.getComponent(1).setEnabled(false);
		
		gameInit();
	}

	// ***************************************************************
	// JTable �߰�
	public void addTable() {
		String[] title = { "�г���", "���ӻ���" };
		model = new MyTableModel(title);
		table = new JTable(model);
		table.addMouseListener(new MouseHandler());
		JScrollPane pane = new JScrollPane(table);

		JLabel lbl = new JLabel("����� �г����� ����Ŭ���ϸ� ������ �����մϴ�.");
		lbl.setBounds(400, 10, 300, 20);
		panel.add(lbl);
		pane.setBounds(400, 30, 300, 480);
		panel.add(pane);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // �⺻���� �ڵ�ũ�⺯���� OFF
		for (int i = 0; i < title.length; i++) { // �÷��� ����
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setPreferredWidth(130); // ���� �÷��� ����
		}
	}

	// ***************************************************************
	// ���� ����
	public void createToolBar() {
		tb = new JToolBar("grade");
		tb.setBackground(Color.LIGHT_GRAY);

		JButton jb;

		jb = new JButton(" �α��� ");
		jb.setActionCommand("Login");
		jb.addActionListener(this);
		tb.add(jb);

		jb = new JButton(" �α׾ƿ� ");
		jb.setActionCommand("Logout");
		jb.addActionListener(this);
		tb.add(jb);
		tb.addSeparator();

		jb = new JButton(" ���� ");
		jb.setActionCommand("Exit");
		jb.addActionListener(this);
		tb.add(jb);
		
		tb.addSeparator();
		tb.add(lblLogin);

		tb.addSeparator();
		tb.add(lblGame);

		add(tb, BorderLayout.NORTH);
		
		tb.getComponent(1).setEnabled(false);
	}

	// ***************************************************************
	// main()
	public static void main(String[] args) {
		new MainClient();
	}

	// ***************************************************************
	// ������ �۾�(�������� ���� ���� �����͸� �޾� ���� �� ä�� ó��)
	public void run() {
		try {
			while (bLogin) {
				Object ob = ois.readObject();
				if (ob instanceof UserMsg) {
					UserMsg user = (UserMsg) ob;
					
					if(user.getCode() == 1110) { // �α��� ����
						lblLogin.setText(userName+"�� �α���");
						lblGame.setText("");
						tb.getComponent(0).setEnabled(false);
						tb.getComponent(1).setEnabled(true);
						
					} else if (user.getCode() == 1111 || user.getCode() == 1112) { // �α��ε� ����� ����Ʈ
						String[] ss=new String[2];
						ss[0]=user.getUserName();
						if(user.getGameState())
							ss[1]=" ������";
						else
							ss[1]=" �����";
						
						((MyTableModel) table.getModel()).addRow(ss);

					} else if (user.getCode() == 1120) { // �α׿� ���� �� ���
						JOptionPane.showMessageDialog(this, "�α��ο� ���� �߽��ϴ�.\n �г����� ��Ȯ�� �Է� �ϼ���. !!!");
						disConnect();
						break;
						
					} else if (user.getCode() == 1210) { // �α��� ������� ���� ���°� ���� �� ���
						for (int i = 0; i <table.getRowCount(); i++) {
							String s=table.getValueAt(i, 0).toString().trim();
							
							if(s.equals(user.getUserName())) {
								if(user.getGameState()) {
									((MyTableModel) table.getModel()).setValueAt(" ������", i, 1);
								} else {
									((MyTableModel) table.getModel()).setValueAt(" �����", i, 1);
								}
								break;
							}
						}
						
					} else if (user.getCode() == 1211) { // ���濡�� ������ ��û ���� ���
						int result = JOptionPane.showConfirmDialog(
								MainClient.this
								, user.getUserName()+ "���� ������ ��û �߽��ϴ�.\n �Ѱ��� �Ͻðڽ��ϱ� ?", "Ȯ��"
								, JOptionPane.YES_NO_OPTION
								, JOptionPane.QUESTION_MESSAGE
							);
						
						UserMsg u = new UserMsg();
						u.setCode(1212);
						u.setUserName(userName);
						u.setOpponentUserName(user.getUserName());
						
						if(result==JOptionPane.YES_OPTION) {
							u.setGameState(true);
							// JTable ����
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(user.getUserName())) {
									((MyTableModel) table.getModel()).setValueAt(" ������", i, 1);
									break;
								}
							}
							
							opponentUserName=user.getUserName();
							bWhTurn=false;
							dolColor=WHITE;
							bGameMe=true;
							bGameOpponent=true;
							
							lblGame.setText(opponentUserName+"�԰� ������. ��:��");
							
						} else {
							// �¶����� ���� ���
							opponentUserName=null;
							dolColor=NONE;
							bGameMe=false;
							bGameOpponent=false;
							lblGame.setText("");
							
							u.setGameState(false);
						}
						oos.writeObject(u);
						
					} else if (user.getCode() == 1212) { // ������ ���� ��û�� ���� �¶� ����
						if(user.getGameState()) {
							// ������ ��û�� �¶� �� ���
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(user.getUserName())) {
									((MyTableModel) table.getModel()).setValueAt(" ������", i, 1);
									break;
								}
							}							
							opponentUserName=user.getUserName();
							bWhTurn=true;
							dolColor=BLACK;
							bGameMe=true;
							bGameOpponent=true;
							
							lblGame.setText(opponentUserName+"�԰� ������. ��:������");
						} else {
							lblGame.setText("");
							ta.append(user.getUserName() + "���� ������ ���� �߽��ϴ�.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							opponentUserName=null;
							dolColor=NONE;
							bGameMe=false;
							bGameOpponent=false;
						}
						
					} else if (user.getCode() == 1221) { // ������ �ٽ� ���ǿ� ���� ��û ���� ���
						if(user.getGameState()==false) { 
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(opponentUserName)) {
									((MyTableModel) table.getModel()).setValueAt(" �����", i, 1);
									break;
								}
							}
							lblGame.setText("");
							ta.append(opponentUserName + "���� ������ ���� �߽��ϴ�.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
						} else {
							int result = JOptionPane.showConfirmDialog(
									MainClient.this
									, "�� ���� �� �Ͻðڽ��ϱ� ?", "Ȯ��"
									, JOptionPane.YES_NO_OPTION
									, JOptionPane.QUESTION_MESSAGE
								);
							
							UserMsg u = new UserMsg();
							u.setCode(1222);
							u.setUserName(userName);
							u.setOpponentUserName(opponentUserName);
							
							if(result==JOptionPane.YES_OPTION) {
								u.setGameState(true);
								
								if(panjeong==dolColor) {
									bWhTurn=false;
									dolColor=WHITE;
									lblGame.setText(opponentUserName+"�԰� ������. ��:��");
								} else {
									bWhTurn=true;
									dolColor=BLACK;
									lblGame.setText(opponentUserName+"�԰� ������. ��:������");
								}
								bGameMe=true;
								bGameOpponent=true;
								ta.append(opponentUserName + "�԰��� ������ �ٽ� ���� �մϴ�.\n");
								ta.setCaretPosition(ta.getDocument().getLength());
								
							} else {
								// �¶����� ���� ���
								u.setGameState(false);
								// JTable ����
								for (int i = 0; i <table.getRowCount(); i++) {
									String s=table.getValueAt(i, 0).toString().trim();
									
									if(s.equals(opponentUserName)) {
										((MyTableModel) table.getModel()).setValueAt(" �����", i, 1);
										break;
									}
								}
								
								lblGame.setText("");
								ta.append(opponentUserName + "�԰��� ������ ���� �߽��ϴ�.\n");
								ta.setCaretPosition(ta.getDocument().getLength());
								
								bGameMe=false;
								bGameOpponent=false;
								opponentUserName=null;
							}
							oos.writeObject(u);
						}
						
						gameInit();
						gp.repaint();
						
					} else if (user.getCode() == 1222) { // ������ �ٽ� ���ǿ� ���� ��û �¶� ����
						if(user.getGameState()==false) {
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(opponentUserName)) {
									((MyTableModel) table.getModel()).setValueAt(" �����", i, 1);
									break;
								}
							}
							lblGame.setText("");
							ta.append(opponentUserName + "���� ������ ���� �߽��ϴ�.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
						} else {
							if(panjeong==dolColor) {
								bWhTurn=false;
								dolColor=WHITE;
								lblGame.setText(opponentUserName+"�԰� ������. ��:��");
							} else {
								bWhTurn=true;
								dolColor=BLACK;
								lblGame.setText(opponentUserName+"�԰� ������. ��:������");
							}
							bGameMe=true;
							bGameOpponent=true;
							ta.append(opponentUserName + "�԰� ������ �ٽ� ���� �մϴ�.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
						}
						
						gameInit();
						gp.repaint();
						
					} else if (user.getCode() == 1301) { // �ٸ� Ŭ���̾�Ʈ�� �α� �ƿ� �� ���
						if(table.getRowCount()<1)
							return;
						
						// ������ �α׾ƿ� �� ���
						if(opponentUserName!=null&&user.getUserName().equals(opponentUserName)) {
							lblGame.setText("");
							ta.append(opponentUserName + "���� �α׾ƿ� �Ǿ����ϴ�.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							gameInit();
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
							gp.repaint();
						}
						
						// JTable���� �α׾ƿ� ����� ����
						for (int i = 0; i <table.getRowCount(); i++) {
							String s=table.getValueAt(i, 0).toString().trim();
							
							if(s.equals(user.getUserName())) {
								((MyTableModel) table.getModel()).removeRow(i);
								break;
							}
						}
					}
					
				} else if (ob instanceof PointMsg) {
					PointMsg pm = (PointMsg) ob;

					if (pm.getCode() == 2101) {
						gamePlay(pm, pm.getDol(), ta);
						bWhTurn = !bWhTurn;
						
					} 
					
				} else if (ob instanceof ChatMsg) {
					ChatMsg chat = (ChatMsg) ob;
					String s = chat.getMsg();
					ta.append(s + "\n");
					ta.setCaretPosition(ta.getDocument().getLength());
				}
				
			}
		} catch (Exception e) {
			// System.out.println(e.toString());
			serverError();
		}
	} // end_public void run()

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("Login")) {
			login();
		} else if(evt.getActionCommand().equals("Logout")) {
			logout();
		} else if(evt.getActionCommand().equals("Exit")) {
			System.exit(0);
		} else if(evt.getSource()==tf) {
			String s=tf.getText().trim();
			
			if(s.length()==0)
				return;
			
			if(sock==null || bLogin==false || bGameMe==false)
				return;
			
			try {
				ChatMsg chat=new ChatMsg();
				chat.setUserName(opponentUserName);
				chat.setMsg(userName+"] "+s);
				oos.writeObject(chat);
				
				ta.append("����] "+s+"\n");
				ta.setCaretPosition(ta.getDocument().getLength());
				
				tf.setText("");
				tf.requestFocus();
			} catch (Exception e) {
				serverError();
			}
			
		}
	}
	
	public void serverError() {
		sock = null;
		userName = null;
		opponentUserName = null;
		host = null;
		oos = null;
		ois = null;
		bLogin=false;
		lblGame.setText("");
		
		JOptionPane.showMessageDialog(MainClient.this, "������ ������ �߻��Ͽ� ���α׷��� ���� �մϴ�.\n ����� �ٽ� ���� �ϼ��� !!!");
		System.exit(0);
	}
	
	// ***************************************************************
	// ���콺 �̺�Ʈ ó��
	class MouseHandler extends MouseAdapter {
		// �������(�ٵ����� Ŭ�� �� ���)
		public void mousePressed(MouseEvent evt) {
			if(evt.getSource()==gp) {
				if (bGameMe == true && bGameOpponent == true) {
					int ax, ay;
					if (panjeong != NONE)
						return;
	
					// �� ���ʰ� �ƴ� ���
					if (! bWhTurn)
						return;
	
					ax = evt.getX() / 20;
					ay = evt.getY() / 20;
	
					if ((ax < 0) || (ax >= 19) || (ay < 0) || (ay >= 19))
						return;
	
					if (board[ax][ay] != NONE)
						return;
	
					// ���콺 ��ǥ ����
					try {
						PointMsg pm = new PointMsg();
						pm.setCode(2101);
						pm.setUserName(opponentUserName);
						pm.setX(ax);
						pm.setY(ay);
						pm.setDol(dolColor);
					
						oos.writeObject(pm);
						gamePlay(pm, dolColor, ta);
						bWhTurn = !bWhTurn;
						
						// ������ ���� ��� �� ���� ��û
						if(panjeong!=NONE) {
							int result = JOptionPane.showConfirmDialog(
									MainClient.this
									, "������ ��� �Ͻðڽ��ϱ� ?", "Ȯ��"
									, JOptionPane.YES_NO_OPTION
									, JOptionPane.QUESTION_MESSAGE
								);
							
							UserMsg u = new UserMsg();
							u.setCode(1221);
							u.setUserName(userName);
							u.setOpponentUserName(opponentUserName);
							
							if(result==JOptionPane.YES_OPTION) {
								u.setGameState(true);
							} else {
								// �¶����� ���� ���
								u.setGameState(false);
								// JTable ����
								for (int i = 0; i <table.getRowCount(); i++) {
									String s=table.getValueAt(i, 0).toString().trim();
									
									if(s.equals(opponentUserName)) {
										((MyTableModel) table.getModel()).setValueAt(" �����", i, 1);
										break;
									}
								}
								
								lblGame.setText("");
								gameInit();
								bGameMe=false;
								bGameOpponent=false;
								opponentUserName=null;
								gp.repaint();
							}
							oos.writeObject(u);
						}
					} catch (Exception e) {
						// System.out.println(e.toString());
						serverError();
					}
				}
			} //end_if(evt.getSource()==gp)
		} // end_public void mousePressed(MouseEvent evt)
		
		// JTable���� �׸��� ���� Ŭ�� �� ���
		public void mouseClicked(MouseEvent evt) {
			if(evt.getSource()==table) {
				if (table.getRowCount() < 1)
					return;
	
				if(bGameMe)
					return;
				
				if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
					// ����Ŭ�� �� ��� ���� ���� ���� ��ȭ����
					if (evt.getClickCount() == 2) {
						String s1=table.getValueAt(table.getSelectedRow(), 0).toString().trim();
						String s2=table.getValueAt(table.getSelectedRow(), 1).toString().trim();
						
						if(s2.equals("������"))
							return;
						
						try {
							// ���� ��û
							UserMsg u = new UserMsg();
							u.setCode(1211);
							u.setGameState(true);
							u.setUserName(userName);
							u.setOpponentUserName(s1);
							oos.writeObject(u);
						} catch (Exception e) {
							// System.out.println(e.toString());
							serverError();
						}
					} // end_if (evt.getClickCount() == 2)
				}
			} // end_if(evt.getSource()==table)
		}		
	} // end_class MouseHandler
	
} // end_class MainClient
