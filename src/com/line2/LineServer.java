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
			System.out.println("서버 시작 !!!");
			
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
	
	// 작업자 스레드
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
				System.out.println(sc.getInetAddress().getHostAddress() + "접속 !!! ");
				
				Object data = null;
				while((data = ois.readObject()) != null) {
					if(data instanceof MsgLogin) {
						// 로그인 요청인 경우에 로그인 성공 또는 실패를 전송
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
								// 정원 초과 사실을 알려줌
								MsgLogin loginFail = new MsgLogin();
								loginFail.setCode(121);
								loginFail.setUserName(login.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							if(flag || userName == null) {
								// 로그인에 실패한 사실을 알려줌
								MsgLogin loginFail = new MsgLogin();
								loginFail.setCode(120);
								loginFail.setUserName(login.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							// 로그인에 성공한 사실을 알려줌
							MsgLogin loginOk = new MsgLogin();
							loginOk.setCode(110);
							loginOk.setUserName(login.getUserName());
							oos.writeObject(loginOk);
							Thread.sleep(10);
							
							for(SocketInfo si : client) {
								if(si.bGame) {
									// 게임중인 경우 게임중인 사실을 알림
									MsgGame ob=new MsgGame();
									ob.setCode(210);
									ob.setUserName(si.userName);
									oos.writeObject(ob);
									Thread.sleep(10);
									break;
								}
							}
							
							// 클라이언트 정보 저장
							SocketInfo info = new SocketInfo();
							info.userName = login.getUserName();
							info.sock = sc;
							info.oos = oos;
							client.add(info);
							
							System.out.println(login.getUserName() + " 로그인 허용 !!!");
							
							// 다른 모든 클라이언트에게 접속 사실 알림
							MsgLogin loginUser = new MsgLogin();
							loginUser.setCode(111);
							loginUser.setUserName(login.getUserName());
							
							sendMsg(loginUser);
							
						}
					}
					else if(data instanceof MsgChat) {
						MsgChat chatMsg = (MsgChat)data;
						
						// 다른 클라이언트에게 채팅 메시지를 전송
						sendMsg(chatMsg);
					}
					else if(data instanceof MsgGame) {
						MsgGame game = (MsgGame)data;
						
						if(game.getCode()==101) {
							// 게임 요청을 받은 경우
							boolean flag = false;
							for(SocketInfo si : client) {
								if(si.bGame==true) {
									flag = true;
									break;
								}
							}
							
							if(flag) {
								// 게임중이라 요청 실패를 전송
								MsgGame ob=new MsgGame();
								ob.setCode(202);
								ob.setUserName(game.getUserName());
								oos.writeObject(ob);
							} else {
								// 게임 요청 성공을 전송
								MsgGame ob=new MsgGame();
								ob.setCode(201);
								ob.setUserName(game.getUserName());
								oos.writeObject(ob);
								
								// 다른 클라이언트에게 게임 시작을 알림
								ob.setCode(210);
								sendMsg(ob);
							}
							
						} else if(game.getCode()==110) {
							// 게임 종료를 전송 받은 경우
							for(SocketInfo si : client) {
								if(si.sock==sc) {
									si.bGame=false;
									break;
								}
							}
							
							// 다른 클라이언트에게 게임 종료를 전송
							MsgGame ob=new MsgGame();
							ob.setCode(211);
							ob.setUserName(game.getUserName());
							sendMsg(ob);
							
						}
						
					}
					else if(data instanceof MsgVertex) {
						MsgVertex vtx = (MsgVertex)data;
						
						// 다른 클라이언트에게 정점을 전송
						sendMsg(vtx);
					}
					
				} // while_end
			} catch(Exception e) {
				// 다른 모든 클라이언트에게 로그아웃 사실 알림
				MsgLogin logoutUser = new MsgLogin();
				logoutUser.setCode(201);
				logoutUser.setUserName(userName);
				
				System.out.println(userName + " 퇴장 !!!");
				
				// 퇴장 사실을 다른 클라이언트에게 전송
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

