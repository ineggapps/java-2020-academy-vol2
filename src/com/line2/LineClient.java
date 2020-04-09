package com.line2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
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

public class LineClient extends JFrame implements Runnable, ActionListener {
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
	
	public LineClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		
		tfIp = new JTextField(7);
		tfIp.setText("127.0.0.1");
		tfName = new JTextField(7);
		conn = new JButton("���� ����");
		conn.addActionListener(this);
		disConn = new JButton("���� ����");
		disConn.addActionListener(this);
		disConn.setEnabled(false);
		
		gameStart = new JButton("���ӽ���");
		gameStart.addActionListener(this);
		gameStart.setEnabled(false);
		gameStop = new JButton("��������");
		gameStop.addActionListener(this);
		gameStop.setEnabled(false);
		
		p.add(new JLabel("�����ּ�: ", JLabel.LEFT));
		p.add(tfIp);
		p.add(new JLabel(" �г���: ", JLabel.LEFT));
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
		gp.repaint();
		
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
		
		setTitle("�׸� ���߱� ����...");
		setResizable(false);
		setSize(700, 480);
		setVisible(true);
	}
	
	public void connInit(boolean bConn) {
		bGame=false;
		
		if(bConn) {
			list1.clear();
			list2.clear();

			gp.repaint();
			
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
		new LineClient();
	}
	
	public void connect() {
		try {
			sc=new Socket(host, port);
			System.out.println("������ ����...");
			
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
					
					if(login.getCode() == 110) { // �α��� ����
						taMsg.setText("������ �α��� �߽��ϴ�. !!!");
						
						connInit(true);
						
						tfMsg.requestFocus();
					
					} else if (login.getCode() == 111) { // �ٸ� Ŭ���̾�Ʈ�� �α��� �� ���
						taMsg.append("\r\n"+login.getUserName()+"���� �����߽��ϴ�. !!!");
						tfMsg.requestFocus();

					} else if (login.getCode() == 120 || login.getCode() == 121) { // �α��� ����
						if(login.getCode() == 120)
						   taMsg.setText("���̵� �ߺ����� �α��� ���� �߽��ϴ�. !!!");
						else 
						   taMsg.setText("���� �ʰ���  �α��� ���� �߽��ϴ�. !!!");
							
						sc.close();
						
						connInit(false);

						break;
					} else if (login.getCode() == 201) { // �ٸ� Ŭ���̾�Ʈ�� �α� �ƿ� �� ���
						taMsg.append("\r\n"+login.getUserName()+"���� �����߽��ϴ�. !!!");
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
					gp.paint(gp.getGraphics());
				}
				else if(data instanceof MsgGame) {
					MsgGame game=(MsgGame)data;
					
					if(game.getCode()==201) {
						// ���� ��û�� ������ ���
						bGame=true;
						list1.clear();
						list2.clear();
						
						gp.repaint();
						
						gameStart.setEnabled(false);
						gameStop.setEnabled(true);
						
						taMsg.append("\r\n ������ ���� �մϴ�.");
						
					} else if(game.getCode()==210) {
						// �ٸ� ����� ������(����)���� ���� ���� ���
						bGame=false;
						list1.clear();
						list2.clear();
						
						gp.repaint();
						
						gameStart.setEnabled(false);
						gameStop.setEnabled(false);
						
						taMsg.append("\r\n"+game.getUserName()+"���� ������ ���� �߽��ϴ�.\r\n���� �׸��� �׸����� ���纸���� !!!");
						
					} else if(game.getCode()==211) {
						// ���� ������ ���� ���� ���
						bGame=false;
						gameStart.setEnabled(true);
						gameStop.setEnabled(false);
						
						taMsg.append("\r\n"+game.getUserName()+"���� ������ ���� �߽��ϴ�.");
					}
					
				}
				
				taMsg.setCaretPosition(taMsg.getDocument().getLength());
			}
		} catch(Exception e) {
			System.out.println(e.toString());
			
			connInit(false);

			taMsg.append("\r\n���� ����. !!!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component ob = (Component)e.getSource();
		
		if(ob instanceof JTextField) {
			sendMsg();
		} else if(ob instanceof JButton) {
			// ���� ���� ��ư
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
				
				// ������ ����
				try {
					sc = new Socket(host, port);
					
					oos = new ObjectOutputStream(sc.getOutputStream());
					oos.flush(); // �߿�(���� �Ұ�)
					ois = new ObjectInputStream(sc.getInputStream());
					
					// �α��� ��û
					MsgLogin loginReq = new MsgLogin();
					loginReq.setCode(100);
					loginReq.setUserName(userName);
					oos.writeObject(loginReq);
					
					// ������ ����
					Thread th = new Thread(this);
					th.start();
				} catch(Exception ex) {
					System.out.println(ex.toString());

					connInit(false);
				}
				
			} else if(ob == disConn) {
				try {
					if(sc != null) {
						// ������ �α׾ƿ� ������ ����(������ �ʾƵ� ��)
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
	
	// ������ ä�� �޽��� ����
	private void sendMsg() {
		String msg =tfMsg.getText().trim(); 
		if(msg.equals(""))
			return;
		
		if(sc == null)
			return;
		
		// ������ �޽��� ����
		MsgChat chatMsg = new MsgChat();
		chatMsg.setUserName(userName);
		chatMsg.setMsg(msg);
		
		try {
			oos.writeObject(chatMsg);
			taMsg.append("\r\n����] "+msg);
		} catch(Exception e) {
			System.out.println(e.toString());
			
			connInit(false);
		}
		
		taMsg.setCaretPosition(taMsg.getDocument().getLength());
		
		tfMsg.setText("");
		tfMsg.requestFocus();
	}
	
	class Grimpan extends Canvas {
		private static final long serialVersionUID = 1L;

		public Grimpan() {
			setBackground(new Color(255, 255, 120));
			
	        addMouseListener(new MouseHandler());
	        addMouseMotionListener(new MouseMotionHandler());
	     }

	     public void paint(Graphics g) {
				// Graphics2D ��ü�� �� ��ȯ.
				Graphics2D g2=(Graphics2D)g;
				// ��Ƽ����̽�(�ε巴��)
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// ���β�. �Ǽ�
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

				gp.paint(gp.getGraphics());
			}catch(Exception e) {
				connInit(false);
			}
		}
	}

}

