package com.line2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LineClient1 extends JFrame implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField tfIp;
	private JTextField tfName;
	private JButton conn, disConn;
	private JTextArea taMsg;
	private JTextField tfMsg;
	
	private JButton gameStart, gameStop;
	
	private Grimpan gp;
	private Vector<MsgVertex> list1=new Vector<MsgVertex>();
	private Vector<MsgVertex> list2=new Vector<MsgVertex>();
	
	private int port=8000;
	private String host="127.0.0.1";
	private String userName;
	
	private Socket sc=null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private boolean bGame = false;
	
	public LineClient1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		
		tfIp = new JTextField(7);
		tfIp.setText("127.0.0.1");
		tfName = new JTextField(7);
		conn = new JButton("서버 접속");
		conn.addActionListener(this);
		disConn = new JButton("연결 해제");
		disConn.addActionListener(this);
		disConn.setEnabled(false);
		
		gameStart = new JButton("게임시작");
		gameStart.addActionListener(this);
		gameStart.setEnabled(false);
		gameStop = new JButton("게임종료");
		gameStop.addActionListener(this);
		gameStop.setEnabled(false);
		
		p.add(new JLabel("서버주소: ", JLabel.LEFT));
		p.add(tfIp);
		p.add(new JLabel(" 닉네임: ", JLabel.LEFT));
		p.add(tfName);
		p.add(conn);
		p.add(disConn);
		p.add(gameStart);
		p.add(gameStop);
		add(p, BorderLayout.NORTH);
		
		p = new JPanel();
		p.setLayout(null);
		
		gp=new Grimpan();
		gp.setBounds(10, 10, 380, 380);
		p.add(gp);
		
		taMsg = new JTextArea();
		taMsg.setEditable(false);
		JScrollPane sp=new JScrollPane(taMsg);
		sp.setBounds(400, 10, 280, 350);
		p.add(sp);
		
		tfMsg = new JTextField();
		tfMsg.addActionListener(this);
		tfMsg.setBounds(400, 365, 280, 25);
		p.add(tfMsg);
		
		add(p, BorderLayout.CENTER);
		
		setTitle("그림 맞추기 게임...");
		setResizable(false);
		setSize(700, 480);
		setVisible(true);
	}
	
	public void connInit(boolean bConn) {
		bGame=false;
		
		if(bConn) {
			list1.clear();
			list2.clear();

			gp.paintComponent(gp.getGraphics());
			
			gameStart.setEnabled(true);
			gameStop.setEnabled(false);
			
			conn.setEnabled(false);
			disConn.setEnabled(true);
		} else {
			sc = null;
			ois=null;
			oos=null;

			gameStart.setEnabled(false);
			gameStop.setEnabled(false);
			
			conn.setEnabled(true);
			disConn.setEnabled(false);
		}
	}
	
	public static void main(String[] args) {
		new LineClient1();
	}
	
	public void connect() {
		try {
			sc=new Socket(host, port);
			System.out.println("서버에 접속...");
			
			oos = new ObjectOutputStream(sc.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(sc.getInputStream());
			
			Thread t=new Thread(this);
			t.start();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	@Override
	public void run() {
		try {
			Object data = null;
			while((data = ois.readObject()) != null) {
				if(data instanceof MsgLogin) {
					MsgLogin login = (MsgLogin) data;
					
					if(login.getCode() == 110) { // 로그인 성공
						taMsg.setText("서버에 로그인 했습니다. !!!");
						
						connInit(true);
						
						tfMsg.requestFocus();
					
					} else if (login.getCode() == 111) { // 다른 클라이언트가 로그인 한 경우
						taMsg.append("\r\n"+login.getUserName()+"님이 입장했습니다. !!!");
						tfMsg.requestFocus();

					} else if (login.getCode() == 120 || login.getCode() == 121) { // 로그인 실패
						if(login.getCode() == 120)
						   taMsg.setText("아이디 중복으로 로그인 실패 했습니다. !!!");
						else 
						   taMsg.setText("정원 초과로  로그인 실패 했습니다. !!!");
							
						sc.close();
						
						connInit(false);

						break;
					} else if (login.getCode() == 201) { // 다른 클라이언트가 로그 아웃 한 경우
						taMsg.append("\r\n"+login.getUserName()+"님이 퇴장했습니다. !!!");
						tfMsg.requestFocus();
					}
				} 
				else if(data instanceof MsgChat) {
					MsgChat chatMsg = (MsgChat) data;
					taMsg.append("\r\n"+ chatMsg.getUserName() + "] " +chatMsg.getMsg());
					tfMsg.requestFocus();
				}
				else if(data instanceof MsgVertex) {
					MsgVertex v = (MsgVertex) data;
					list2.add(v);
					gp.paintComponent(gp.getGraphics());
				}
				else if(data instanceof MsgGame) {
					MsgGame game=(MsgGame)data;
					
					if(game.getCode()==201) {
						// 게임 요청이 수락된 경우
						bGame=true;
						list1.clear();
						list2.clear();
						
						// gp.paintComponent(gp.getGraphics());
						gp.backgroundClear(gp.getGraphics());
						
						gameStart.setEnabled(false);
						gameStop.setEnabled(true);
						
						taMsg.append("\r\n 게임을 시작 합니다.");
						
					} else if(game.getCode()==210) {
						// 다른 사람이 게임중(시작)임을 전달 받은 경우
						bGame=false;
						list1.clear();
						list2.clear();
						
						// gp.paintComponent(gp.getGraphics());
						gp.backgroundClear(gp.getGraphics());
						
						gameStart.setEnabled(false);
						gameStop.setEnabled(false);
						
						taMsg.append("\r\n"+game.getUserName()+"님이 게임을 시작 했습니다.\r\n무슨 그림을 그리는지 맞춰보세요 !!!");
						
					} else if(game.getCode()==211) {
						// 게임 중지를 전달 받은 경우
						bGame=false;
						gameStart.setEnabled(true);
						gameStop.setEnabled(false);
						
						taMsg.append("\r\n"+game.getUserName()+"님이 게임을 중지 했습니다.");
					}
					
				}
				
				taMsg.setCaretPosition(taMsg.getDocument().getLength());
			}
		} catch(Exception e) {
			System.out.println(e.toString());
			
			connInit(false);

			taMsg.append("\r\n접속 종료. !!!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component ob = (Component)e.getSource();
		
		if(ob instanceof JTextField) {
			sendMsg();
		} else if(ob instanceof JButton) {
			// 서버 연결 버튼
			if(ob == conn) {
				host = tfIp.getText().trim();
				if(host.equals("")) {
					tfIp.requestFocus();
					return;
				}
				
				userName = tfName.getText().trim();
				if(userName.equals("")) {
					tfName.requestFocus();
					return;
				}
				
				// 서버에 접속
				try {
					sc = new Socket(host, port);
					
					oos = new ObjectOutputStream(sc.getOutputStream());
					oos.flush(); // 중요(생략 불가)
					ois = new ObjectInputStream(sc.getInputStream());
					
					// 로그인 요청
					MsgLogin loginReq = new MsgLogin();
					loginReq.setCode(100);
					loginReq.setUserName(userName);
					oos.writeObject(loginReq);
					
					// 스레드 생성
					Thread th = new Thread(this);
					th.start();
				} catch(Exception ex) {
					System.out.println(ex.toString());

					connInit(false);
				}
				
			} else if(ob == disConn) {
				try {
					if(sc != null) {
						// 서버에 로그아웃 정보를 보냄(보내지 않아도 됨)
						sc.close();
					}
				} catch(Exception ex) {
					System.out.println(ex.toString());
				}
				
				connInit(false);
				
			} else if(ob == gameStart) {
				if(sc == null)
					return;
				
				try {
					MsgGame game=new MsgGame();
					game.setCode(101);
					game.setUserName(userName);
					oos.writeObject(game);
				}catch(Exception ex) {
					connInit(false);
				}
				
			} else if(ob == gameStop) {
				if(sc == null)
					return;
				
				try {
					bGame=false;
					
					gameStart.setEnabled(true);
					gameStop.setEnabled(false);
					
					MsgGame game=new MsgGame();
					game.setCode(110);
					game.setUserName(userName);
					oos.writeObject(game);
					
				}catch(Exception ex) {
					connInit(false);
				}
			}
			
		}
	}
	
	// 서버에 채팅 메시지 전송
	private void sendMsg() {
		String msg =tfMsg.getText().trim(); 
		if(msg.equals(""))
			return;
		
		if(sc == null)
			return;
		
		// 서버에 메시지 전송
		MsgChat chatMsg = new MsgChat();
		chatMsg.setUserName(userName);
		chatMsg.setMsg(msg);
		
		try {
			oos.writeObject(chatMsg);
			taMsg.append("\r\n보냄] "+msg);
		} catch(Exception e) {
			System.out.println(e.toString());
			
			connInit(false);
		}
		taMsg.setCaretPosition(taMsg.getDocument().getLength());
		
		tfMsg.setText("");
		tfMsg.requestFocus();
	}
	
	class Grimpan extends JPanel {
		private static final long serialVersionUID = 1L;

		public Grimpan() {
			setBackground(new Color(255, 255, 120));

	        addMouseListener(new MouseHandler());
	        addMouseMotionListener(new MouseMotionHandler());
	     }

		@Override
	    public void paintComponent(Graphics g) {
			 // super.paintComponent(g); // 배경색으로 화면 지우기
			
			// Graphics2D 객체로 형 변환.
			Graphics2D g2=(Graphics2D)g;
			// 안티얼라이싱(부드럽게)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 선두께. 실선
			g2.setStroke(new BasicStroke(3.0f));

			for(int i=0; i<list1.size(); i++) {
	    		 if(list1.get(i).draw)
	    			 g2.drawLine(list1.get(i-1).p.x, list1.get(i-1).p.y, list1.get(i).p.x, list1.get(i).p.y);
	    	 }
	    	 for(int i=0; i<list2.size(); i++) {
	    		 if(list2.get(i).draw)
	    			 g2.drawLine(list2.get(i-1).p.x, list2.get(i-1).p.y, list2.get(i).p.x, list2.get(i).p.y);
	    	 }
	     }

		public void backgroundClear(Graphics g) {
			super.paintComponent(g);
		}
	}
	
	class MouseHandler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent evt) {
			try {
				if(! bGame)
					return;
				
				Point p=new Point(evt.getX(), evt.getY());
				MsgVertex v=new MsgVertex(p, false);
				list1.add(v);
				
				if(oos==null)
					return;
				
				oos.writeObject(v);
			}catch(Exception e) {
				connInit(false);
			}
		}
		@Override
		public void mouseReleased(MouseEvent evt) {
		}
	}
	
	class MouseMotionHandler extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent evt) {
			try {
				if(! bGame)
					return;
				
				Point p=new Point(evt.getX(), evt.getY());
				MsgVertex v=new MsgVertex(p, true);
				list1.add(v);
			
				if(oos==null)
					return;
				
				oos.writeObject(v);

				gp.paintComponent(gp.getGraphics());
			}catch(Exception e) {
				connInit(false);
			}
		}
	}

}

