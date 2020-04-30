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
	private JLabel lblLogin=new JLabel("로그인 하세요.", JLabel.CENTER);
	private JLabel lblGame=new JLabel("", JLabel.CENTER);

	private Socket sock = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;

	private String host;
	private int port = 7777;
	private boolean bLogin=false;

	// ***************************************************************
	// 생성자
	public MainClient() {
		// 툴바 생성
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

		// 접속자 리스트를 출력할 JTable
		addTable();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("오목게임");
		setSize(710, 590);
		setResizable(false);
		setVisible(true);

		// 화면의 중앙에 배치
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
	// 로그인
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
			
			// 서버에 접속하기
			sock = new Socket(host, port);
			
			oos = new ObjectOutputStream(sock.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(sock.getInputStream());
			
			// 서버에 로그인 요청
			UserMsg loginReq = new UserMsg();
			loginReq.setCode(1100);
			loginReq.setUserName(userName);
			oos.writeObject(loginReq);
			bLogin=true;

			// 스레드 생성
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			// System.out.println(e.toString());
			JOptionPane.showMessageDialog(this, "서버에 문제가 발생하여 프로그램을 종료 합니다.\n 잠시후 다시 실행 하세요 !!!");
			System.exit(0);
		}
		
	}

	// ***************************************************************
	// 로그아웃
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
		lblLogin.setText("로그인 하세요.");
		lblGame.setText("");
		
		// 테이블의 모든 목록 삭제
		int size = table.getRowCount();
		for (int index = size - 1; index >= 0; index--) {
			((MyTableModel) table.getModel()).removeRow(index);
		}

		tb.getComponent(0).setEnabled(true);
		tb.getComponent(1).setEnabled(false);
		
		gameInit();
	}

	// ***************************************************************
	// JTable 추가
	public void addTable() {
		String[] title = { "닉네임", "게임상태" };
		model = new MyTableModel(title);
		table = new JTable(model);
		table.addMouseListener(new MouseHandler());
		JScrollPane pane = new JScrollPane(table);

		JLabel lbl = new JLabel("대기중 닉네임을 더블클릭하면 게임을 시작합니다.");
		lbl.setBounds(400, 10, 300, 20);
		panel.add(lbl);
		pane.setBounds(400, 30, 300, 480);
		panel.add(pane);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 기본값인 자동크기변경을 OFF
		for (int i = 0; i < title.length; i++) { // 컬럼폭 변경
			TableColumn col = table.getColumnModel().getColumn(i);
			col.setPreferredWidth(130); // 지정 컬럼폭 변경
		}
	}

	// ***************************************************************
	// 툴바 생성
	public void createToolBar() {
		tb = new JToolBar("grade");
		tb.setBackground(Color.LIGHT_GRAY);

		JButton jb;

		jb = new JButton(" 로그인 ");
		jb.setActionCommand("Login");
		jb.addActionListener(this);
		tb.add(jb);

		jb = new JButton(" 로그아웃 ");
		jb.setActionCommand("Logout");
		jb.addActionListener(this);
		tb.add(jb);
		tb.addSeparator();

		jb = new JButton(" 종료 ");
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
	// 스레드 작업(서버에서 전송 받은 데이터를 받아 게임 및 채팅 처리)
	public void run() {
		try {
			while (bLogin) {
				Object ob = ois.readObject();
				if (ob instanceof UserMsg) {
					UserMsg user = (UserMsg) ob;
					
					if(user.getCode() == 1110) { // 로그인 성공
						lblLogin.setText(userName+"님 로그인");
						lblGame.setText("");
						tb.getComponent(0).setEnabled(false);
						tb.getComponent(1).setEnabled(true);
						
					} else if (user.getCode() == 1111 || user.getCode() == 1112) { // 로그인된 사용자 리스트
						String[] ss=new String[2];
						ss[0]=user.getUserName();
						if(user.getGameState())
							ss[1]=" 게임중";
						else
							ss[1]=" 대기중";
						
						((MyTableModel) table.getModel()).addRow(ss);

					} else if (user.getCode() == 1120) { // 로그에 실패 한 경우
						JOptionPane.showMessageDialog(this, "로그인에 실패 했습니다.\n 닉네임을 정확히 입력 하세요. !!!");
						disConnect();
						break;
						
					} else if (user.getCode() == 1210) { // 로그인 사용자의 게임 상태가 변경 된 경우
						for (int i = 0; i <table.getRowCount(); i++) {
							String s=table.getValueAt(i, 0).toString().trim();
							
							if(s.equals(user.getUserName())) {
								if(user.getGameState()) {
									((MyTableModel) table.getModel()).setValueAt(" 게임중", i, 1);
								} else {
									((MyTableModel) table.getModel()).setValueAt(" 대기중", i, 1);
								}
								break;
							}
						}
						
					} else if (user.getCode() == 1211) { // 상대방에게 게임을 요청 받은 경우
						int result = JOptionPane.showConfirmDialog(
								MainClient.this
								, user.getUserName()+ "님이 게임을 요청 했습니다.\n 한게임 하시겠습니까 ?", "확인"
								, JOptionPane.YES_NO_OPTION
								, JOptionPane.QUESTION_MESSAGE
							);
						
						UserMsg u = new UserMsg();
						u.setCode(1212);
						u.setUserName(userName);
						u.setOpponentUserName(user.getUserName());
						
						if(result==JOptionPane.YES_OPTION) {
							u.setGameState(true);
							// JTable 변경
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(user.getUserName())) {
									((MyTableModel) table.getModel()).setValueAt(" 게임중", i, 1);
									break;
								}
							}
							
							opponentUserName=user.getUserName();
							bWhTurn=false;
							dolColor=WHITE;
							bGameMe=true;
							bGameOpponent=true;
							
							lblGame.setText(opponentUserName+"님과 게임중. 돌:흰돌");
							
						} else {
							// 승락하지 않은 경우
							opponentUserName=null;
							dolColor=NONE;
							bGameMe=false;
							bGameOpponent=false;
							lblGame.setText("");
							
							u.setGameState(false);
						}
						oos.writeObject(u);
						
					} else if (user.getCode() == 1212) { // 상대방이 게임 요청에 대한 승락 여부
						if(user.getGameState()) {
							// 상대방이 요청을 승락 한 경우
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(user.getUserName())) {
									((MyTableModel) table.getModel()).setValueAt(" 게임중", i, 1);
									break;
								}
							}							
							opponentUserName=user.getUserName();
							bWhTurn=true;
							dolColor=BLACK;
							bGameMe=true;
							bGameOpponent=true;
							
							lblGame.setText(opponentUserName+"님과 게임중. 돌:검은돌");
						} else {
							lblGame.setText("");
							ta.append(user.getUserName() + "님이 게임을 거절 했습니다.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							opponentUserName=null;
							dolColor=NONE;
							bGameMe=false;
							bGameOpponent=false;
						}
						
					} else if (user.getCode() == 1221) { // 게임을 다시 한판에 대해 요청 받은 경우
						if(user.getGameState()==false) { 
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(opponentUserName)) {
									((MyTableModel) table.getModel()).setValueAt(" 대기중", i, 1);
									break;
								}
							}
							lblGame.setText("");
							ta.append(opponentUserName + "님이 게임을 종료 했습니다.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
						} else {
							int result = JOptionPane.showConfirmDialog(
									MainClient.this
									, "한 게임 더 하시겠습니까 ?", "확인"
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
									lblGame.setText(opponentUserName+"님과 게임중. 돌:흰돌");
								} else {
									bWhTurn=true;
									dolColor=BLACK;
									lblGame.setText(opponentUserName+"님과 게임중. 돌:검은돌");
								}
								bGameMe=true;
								bGameOpponent=true;
								ta.append(opponentUserName + "님과의 게임을 다시 시작 합니다.\n");
								ta.setCaretPosition(ta.getDocument().getLength());
								
							} else {
								// 승락하지 않은 경우
								u.setGameState(false);
								// JTable 변경
								for (int i = 0; i <table.getRowCount(); i++) {
									String s=table.getValueAt(i, 0).toString().trim();
									
									if(s.equals(opponentUserName)) {
										((MyTableModel) table.getModel()).setValueAt(" 대기중", i, 1);
										break;
									}
								}
								
								lblGame.setText("");
								ta.append(opponentUserName + "님과의 게임을 종료 했습니다.\n");
								ta.setCaretPosition(ta.getDocument().getLength());
								
								bGameMe=false;
								bGameOpponent=false;
								opponentUserName=null;
							}
							oos.writeObject(u);
						}
						
						gameInit();
						gp.repaint();
						
					} else if (user.getCode() == 1222) { // 게임을 다시 한판에 대해 요청 승락 여부
						if(user.getGameState()==false) {
							for (int i = 0; i <table.getRowCount(); i++) {
								String s=table.getValueAt(i, 0).toString().trim();
								
								if(s.equals(opponentUserName)) {
									((MyTableModel) table.getModel()).setValueAt(" 대기중", i, 1);
									break;
								}
							}
							lblGame.setText("");
							ta.append(opponentUserName + "님이 게임을 종료 했습니다.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
						} else {
							if(panjeong==dolColor) {
								bWhTurn=false;
								dolColor=WHITE;
								lblGame.setText(opponentUserName+"님과 게임중. 돌:흰돌");
							} else {
								bWhTurn=true;
								dolColor=BLACK;
								lblGame.setText(opponentUserName+"님과 게임중. 돌:검은돌");
							}
							bGameMe=true;
							bGameOpponent=true;
							ta.append(opponentUserName + "님과 게임을 다시 시작 합니다.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
						}
						
						gameInit();
						gp.repaint();
						
					} else if (user.getCode() == 1301) { // 다른 클라이언트가 로그 아웃 한 경우
						if(table.getRowCount()<1)
							return;
						
						// 상대방이 로그아웃 한 경우
						if(opponentUserName!=null&&user.getUserName().equals(opponentUserName)) {
							lblGame.setText("");
							ta.append(opponentUserName + "님이 로그아웃 되었습니다.\n");
							ta.setCaretPosition(ta.getDocument().getLength());
							
							gameInit();
							bGameMe=false;
							bGameOpponent=false;
							opponentUserName=null;
							gp.repaint();
						}
						
						// JTable에서 로그아웃 사용자 제거
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
				
				ta.append("보냄] "+s+"\n");
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
		
		JOptionPane.showMessageDialog(MainClient.this, "서버에 문제가 발생하여 프로그램을 종료 합니다.\n 잠시후 다시 실행 하세요 !!!");
		System.exit(0);
	}
	
	// ***************************************************************
	// 마우스 이벤트 처리
	class MouseHandler extends MouseAdapter {
		// 오목게임(바둑판을 클릭 한 경우)
		public void mousePressed(MouseEvent evt) {
			if(evt.getSource()==gp) {
				if (bGameMe == true && bGameOpponent == true) {
					int ax, ay;
					if (panjeong != NONE)
						return;
	
					// 돌 차례가 아닌 경우
					if (! bWhTurn)
						return;
	
					ax = evt.getX() / 20;
					ay = evt.getY() / 20;
	
					if ((ax < 0) || (ax >= 19) || (ay < 0) || (ay >= 19))
						return;
	
					if (board[ax][ay] != NONE)
						return;
	
					// 마우스 좌표 전송
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
						
						// 게임이 끝난 경우 재 게임 요청
						if(panjeong!=NONE) {
							int result = JOptionPane.showConfirmDialog(
									MainClient.this
									, "게임을 계속 하시겠습니까 ?", "확인"
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
								// 승락하지 않은 경우
								u.setGameState(false);
								// JTable 변경
								for (int i = 0; i <table.getRowCount(); i++) {
									String s=table.getValueAt(i, 0).toString().trim();
									
									if(s.equals(opponentUserName)) {
										((MyTableModel) table.getModel()).setValueAt(" 대기중", i, 1);
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
		
		// JTable에서 항목을 더블 클릭 한 경우
		public void mouseClicked(MouseEvent evt) {
			if(evt.getSource()==table) {
				if (table.getRowCount() < 1)
					return;
	
				if(bGameMe)
					return;
				
				if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
					// 더블클릭 할 경우 파일 내용 보기 대화상자
					if (evt.getClickCount() == 2) {
						String s1=table.getValueAt(table.getSelectedRow(), 0).toString().trim();
						String s2=table.getValueAt(table.getSelectedRow(), 1).toString().trim();
						
						if(s2.equals("게임중"))
							return;
						
						try {
							// 게임 요청
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
