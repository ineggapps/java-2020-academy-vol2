package com.omok;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer {
	private Vector<SocketInfo> list = new Vector<SocketInfo>();
	private ServerSocket ss = null;
	private int port = 7777;

	// ***************************************************************
	// ������
	public MainServer() {
	}

	// ***************************************************************
	// ���� ����
	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			System.out.println("���� ���� !!!");

			Socket sc = null;
			while (true) {
				sc = ss.accept();

				WorkerThread th = new WorkerThread(sc);
				th.start();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// �۾��� ������
	class WorkerThread extends Thread {
		private Socket sc;
		private String userName=null;

		public WorkerThread(Socket sc) {
			this.sc = sc;
		}
		
		public void run() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());
				oos.flush();
				ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
				System.out.println(sc.getInetAddress().getHostAddress() + "���� !!! ");
				
				Object ob = null;
				while((ob = ois.readObject()) != null) {
					if(ob instanceof UserMsg) {
						// �α��� ��û�� ��쿡 �α��� ���� �Ǵ� ���и� ����
						UserMsg user = (UserMsg)ob;
						userName = user.getUserName();
						
						// �α��� ��û
						if(user.getCode() == 1100) {
							boolean flag = false;
							for(SocketInfo si : list) {
								if(si.userName.equals(user.getUserName())) {
									flag = true;
									break;
								}
							}
							
							if(flag || userName == null) {
								// �α��ο� ������ ����� �˷���
								UserMsg loginFail = new UserMsg();
								loginFail.setCode(1120);
								loginFail.setUserName(user.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							// �α��ο� ������ ����� �˷���
							UserMsg loginOk = new UserMsg();
							loginOk.setCode(1110);
							loginOk.setUserName(user.getUserName());
							oos.writeObject(loginOk);
							
							// �α��ο� ���� ����ڿ��� ������ ����Ʈ ����
							for(SocketInfo si : list) {
								if(si.sock == sc)
									continue;
								
								UserMsg u=new UserMsg();
								u.setCode(1111);
								u.setUserName(si.userName);
								u.setGameState(si.gameState);
								oos.writeObject(u);
								Thread.sleep(10);
							}
							
							// Ŭ���̾�Ʈ ���� ����
							SocketInfo info = new SocketInfo();
							info.userName = user.getUserName();
							info.opponentName=null;
							info.gameState=false;
							info.sock = sc;
							info.oos = oos;
							list.add(info);
							
							System.out.println(user.getUserName() + " �α��� ��� !!!");
							
							// �ٸ� ��� Ŭ���̾�Ʈ���� ���� ��� �˸�
							UserMsg u = new UserMsg();
							u.setCode(1112);
							u.setUserName(user.getUserName());
							u.setGameState(false);
							
							for(SocketInfo si : list) {
								if(si.sock == sc)
									continue;
								
								si.oos.writeObject(u);
								Thread.sleep(10);
							}
							
							// �α� �ƿ� ��û
						} else if(user.getCode() == 1300) {
							logout();
						
							// ���¹濡�� ������ ��û
						} else if(user.getCode() == 1211) {
							for(SocketInfo si : list) {
								if(si.sock == sc) {
									if(user.getGameState())
										si.opponentName=user.getOpponentUserName();
									else
										si.opponentName=null;
									
									continue;
								}
								
								if(si.userName.equals(user.getOpponentUserName())) {
									si.oos.writeObject(user);
									break;
								}
							}
							
							// ���ӿ�û�� ���� ����
						} else if(user.getCode() == 1212) {
							for(SocketInfo si : list) {
								if(si.sock == sc) {
									if(user.getGameState())
										si.opponentName=user.getOpponentUserName();
									else
										si.opponentName=null;
									
									continue;
								}
								
								if(si.userName.equals(user.getOpponentUserName())) {
									si.oos.writeObject(user);
								} else {
									// �ٸ� ����ڿ��Դ� ����� ���� ���� ����
									Thread.sleep(10);
									UserMsg u = new UserMsg();
									u.setCode(1210);
									u.setUserName(user.getUserName());
									u.setGameState(user.getGameState());
									si.oos.writeObject(u);
									
									Thread.sleep(10);
									u = new UserMsg();
									u.setCode(1210);
									u.setUserName(user.getOpponentUserName());
									u.setGameState(user.getGameState());
									si.oos.writeObject(u);
								}
							}
							
							// �ٽ� �Ѱ��� ��û
						} else if(user.getCode() == 1221) {
							for(SocketInfo si : list) {
								if(si.sock == sc) {
									if(user.getGameState())
										si.opponentName=user.getOpponentUserName();
									else
										si.opponentName=null;
									continue;
								}
								
								if(si.userName.equals(user.getOpponentUserName())) {
									si.oos.writeObject(user);
									break;
								}
							}
							
							// �ٽ� �Ѱ��� ��û ����
						} else if(user.getCode() == 1222) {
							for(SocketInfo si : list) {
								if(si.sock == sc) {
									if(user.getGameState())
										si.opponentName=user.getOpponentUserName();
									else
										si.opponentName=null;
									continue;
								}
								
								if(si.userName.equals(user.getOpponentUserName())) {
									si.oos.writeObject(user);
								} else if(user.getGameState()==false){
									// �ٸ� ����ڿ��Դ� ����� ���� ���� ����
									Thread.sleep(10);
									UserMsg u = new UserMsg();
									u.setCode(1210);
									u.setUserName(user.getUserName());
									u.setGameState(user.getGameState());
									si.oos.writeObject(u);
									
									Thread.sleep(10);
									u = new UserMsg();
									u.setCode(1210);
									u.setUserName(user.getOpponentUserName());
									u.setGameState(user.getGameState());
									si.oos.writeObject(u);
								}
							}
						}
						
						// ä�� �� ���
					} else if(ob instanceof ChatMsg) {
						ChatMsg chatMsg = (ChatMsg)ob;
						
						for(SocketInfo si : list) {
							if(si.sock == sc)
								continue;
														
							if(chatMsg.getUserName().equals(si.userName)) {
								si.oos.writeObject(chatMsg);
								break;
							}
						}
						
						// ���� ��ǥ�� ���
					} else if(ob instanceof PointMsg) {
						PointMsg pointMsg=(PointMsg)ob;
						
						for(SocketInfo si : list) {
							if(si.sock == sc)
								continue;
														
							if(pointMsg.getUserName().equals(si.userName)) {
								si.oos.writeObject(pointMsg);
								break;
							}
						}
					}
					
				} // while_end
			} catch(Exception e) {
				logout();
				
				// System.out.println(e.toString());
			}
		}
		
		private void logout() {
			// �ٸ� ��� Ŭ���̾�Ʈ���� �α׾ƿ� ��� �˸�
			UserMsg u = new UserMsg();
			u.setCode(1301);
			u.setUserName(userName);
			
			SocketInfo info = null;
			try {
				for(SocketInfo si : list) {
					if(si.sock == sc) {
						info = si;
						continue;
					}
					
					if(si.oos != null)
						si.oos.writeObject(u);
					
					Thread.sleep(10);
				}
			} catch(Exception e1) {}
			
			if(info != null ) {
				if(info.opponentName!=null) {
					try {
						// �α��� ����ڿ� ��� ���� ������� ���ӻ���(�����)�� �ٸ� ����ڿ��� ����
						UserMsg uu = new UserMsg();
						uu.setCode(1210);
						uu.setUserName(info.opponentName);
						uu.setGameState(false);
						for(SocketInfo si : list) {
							if(si.sock == sc) {
								continue;
							}
							
							if(si.oos != null)
								si.oos.writeObject(uu);
							
							Thread.sleep(10);
						}
					} catch (Exception e) {
					}
				}
				
				info.sock = null;
				info.oos = null;
				list.remove(info);

				System.out.println(userName + " ���� !!!");
			}
		}
		
	}

	// ***************************************************************
	// main()
	public static void main(String[] args) {
		new MainServer().serverStart();
	}

}