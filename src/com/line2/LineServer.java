package com.line2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class LineServer {
	private Vector<SocketInfo> client = new Vector<>();
	private ServerSocket ss=null;
	private int port=8000;
	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			System.out.println("���� ���� !!!");
			
			Socket sc = null;
			while(true) {
				sc = ss.accept();
				
				WorkerThread th = new WorkerThread(sc);
				th.start();
			}
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String[] args) {
		LineServer ob = new LineServer();
		ob.serverStart();
	}
	
	// �۾��� ������
	class WorkerThread extends Thread {
		private Socket sc;
		public WorkerThread(Socket sc) {
			this.sc = sc;
		}
		
		public void run() {
			String userName = null;
			
			try {
				ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());
				oos.flush();
				ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
				System.out.println(sc.getInetAddress().getHostAddress() + "���� !!! ");
				
				Object data = null;
				while((data = ois.readObject()) != null) {
					if(data instanceof MsgLogin) {
						// �α��� ��û�� ��쿡 �α��� ���� �Ǵ� ���и� ����
						MsgLogin login = (MsgLogin)data;
						userName = login.getUserName();
						if(login.getCode() == 100) {
							boolean flag = false;
							for(SocketInfo si : client) {
								if(si.userName.equals(login.getUserName())) {
									flag = true;
									break;
								}
							}
							
							if(client.size()>=5) {
								// ���� �ʰ� ����� �˷���
								MsgLogin loginFail = new MsgLogin();
								loginFail.setCode(121);
								loginFail.setUserName(login.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							if(flag || userName == null) {
								// �α��ο� ������ ����� �˷���
								MsgLogin loginFail = new MsgLogin();
								loginFail.setCode(120);
								loginFail.setUserName(login.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							// �α��ο� ������ ����� �˷���
							MsgLogin loginOk = new MsgLogin();
							loginOk.setCode(110);
							loginOk.setUserName(login.getUserName());
							oos.writeObject(loginOk);
							Thread.sleep(10);
							
							for(SocketInfo si : client) {
								if(si.bGame) {
									// �������� ��� �������� ����� �˸�
									MsgGame ob=new MsgGame();
									ob.setCode(210);
									ob.setUserName(si.userName);
									oos.writeObject(ob);
									Thread.sleep(10);
									break;
								}
							}
							
							// Ŭ���̾�Ʈ ���� ����
							SocketInfo info = new SocketInfo();
							info.userName = login.getUserName();
							info.sock = sc;
							info.oos = oos;
							client.add(info);
							
							System.out.println(login.getUserName() + " �α��� ��� !!!");
							
							// �ٸ� ��� Ŭ���̾�Ʈ���� ���� ��� �˸�
							MsgLogin loginUser = new MsgLogin();
							loginUser.setCode(111);
							loginUser.setUserName(login.getUserName());
							
							sendMsg(loginUser);
							
						}
					}
					else if(data instanceof MsgChat) {
						MsgChat chatMsg = (MsgChat)data;
						
						// �ٸ� Ŭ���̾�Ʈ���� ä�� �޽����� ����
						sendMsg(chatMsg);
					}
					else if(data instanceof MsgGame) {
						MsgGame game = (MsgGame)data;
						
						if(game.getCode()==101) {
							// ���� ��û�� ���� ���
							boolean flag = false;
							for(SocketInfo si : client) {
								if(si.bGame==true) {
									flag = true;
									break;
								}
							}
							
							if(flag) {
								// �������̶� ��û ���и� ����
								MsgGame ob=new MsgGame();
								ob.setCode(202);
								ob.setUserName(game.getUserName());
								oos.writeObject(ob);
							} else {
								// ���� ��û ������ ����
								MsgGame ob=new MsgGame();
								ob.setCode(201);
								ob.setUserName(game.getUserName());
								oos.writeObject(ob);
								
								// �ٸ� Ŭ���̾�Ʈ���� ���� ������ �˸�
								ob.setCode(210);
								sendMsg(ob);
							}
							
						} else if(game.getCode()==110) {
							// ���� ���Ḧ ���� ���� ���
							for(SocketInfo si : client) {
								if(si.sock==sc) {
									si.bGame=false;
									break;
								}
							}
							
							// �ٸ� Ŭ���̾�Ʈ���� ���� ���Ḧ ����
							MsgGame ob=new MsgGame();
							ob.setCode(211);
							ob.setUserName(game.getUserName());
							sendMsg(ob);
							
						}
						
					}
					else if(data instanceof MsgVertex) {
						MsgVertex vtx = (MsgVertex)data;
						
						// �ٸ� Ŭ���̾�Ʈ���� ������ ����
						sendMsg(vtx);
					}
					
				} // while_end
			} catch(Exception e) {
				// �ٸ� ��� Ŭ���̾�Ʈ���� �α׾ƿ� ��� �˸�
				MsgLogin logoutUser = new MsgLogin();
				logoutUser.setCode(201);
				logoutUser.setUserName(userName);
				
				System.out.println(userName + " ���� !!!");
				
				// ���� ����� �ٸ� Ŭ���̾�Ʈ���� ����
				sendMsg(logoutUser);
				
				SocketInfo info = null;
				for(SocketInfo si : client) {
					if(si.sock == sc) {
						info = si;
						break;
					}
				}
				
				if(info != null ) {
					if(info.bGame) {
						MsgGame ob=new MsgGame();
						ob.setCode(211);
						ob.setUserName(info.userName);
						sendMsg(ob);
					}
					
					info.sock = null;
					info.oos = null;
					client.remove(info);
				}
				// System.out.println(e.toString());
			}
		}
		
		public void sendMsg(MsgData data) {
			for(SocketInfo si : client) {
				try {
					if(si.sock == sc)
						continue;
				
					si.oos.writeObject(data);
					Thread.sleep(15);
				}catch(Exception e) {
				}
			}
		} // sendMsg()_end
		
	} // WorkerThread_end
}

