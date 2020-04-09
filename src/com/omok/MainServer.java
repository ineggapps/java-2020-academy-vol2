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
	// 생성자
	public MainServer() {
	}

	// ***************************************************************
	// 서버 시작
	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			System.out.println("서버 시작 !!!");

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

	// 작업자 스레드
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
				System.out.println(sc.getInetAddress().getHostAddress() + "접속 !!! ");
				
				Object ob = null;
				while((ob = ois.readObject()) != null) {
					if(ob instanceof UserMsg) {
						// 로그인 요청인 경우에 로그인 성공 또는 실패를 전송
						UserMsg user = (UserMsg)ob;
						userName = user.getUserName();
						
						// 로그인 요청
						if(user.getCode() == 1100) {
							boolean flag = false;
							for(SocketInfo si : list) {
								if(si.userName.equals(user.getUserName())) {
									flag = true;
									break;
								}
							}
							
							if(flag || userName == null) {
								// 로그인에 실패한 사실을 알려줌
								UserMsg loginFail = new UserMsg();
								loginFail.setCode(1120);
								loginFail.setUserName(user.getUserName());
								oos.writeObject(loginFail);
								
								sc.close();
								sc=null;
								break;
							}
							
							// 로그인에 성공한 사실을 알려줌
							UserMsg loginOk = new UserMsg();
							loginOk.setCode(1110);
							loginOk.setUserName(user.getUserName());
							oos.writeObject(loginOk);
							
							// 로그인에 성공 사용자에게 접속자 리스트 전송
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
							
							// 클라이언트 정보 저장
							SocketInfo info = new SocketInfo();
							info.userName = user.getUserName();
							info.opponentName=null;
							info.gameState=false;
							info.sock = sc;
							info.oos = oos;
							list.add(info);
							
							System.out.println(user.getUserName() + " 로그인 허용 !!!");
							
							// 다른 모든 클라이언트에게 접속 사실 알림
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
							
							// 로그 아웃 요청
						} else if(user.getCode() == 1300) {
							logout();
						
							// 상태방에게 게임을 요청
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
							
							// 게임요청에 대한 응답
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
									// 다른 사용자에게는 변경된 게임 상태 전송
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
							
							// 다시 한게임 요청
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
							
							// 다시 한게임 요청 응답
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
									// 다른 사용자에게는 변경된 게임 상태 전송
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
						
						// 채팅 인 경우
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
						
						// 게임 좌표인 경우
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
			// 다른 모든 클라이언트에게 로그아웃 사실 알림
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
						// 로그인 사용자와 상대 게임 사용자의 게임상태(대기중)를 다른 사용자에게 전송
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

				System.out.println(userName + " 퇴장 !!!");
			}
		}
		
	}

	// ***************************************************************
	// main()
	public static void main(String[] args) {
		new MainServer().serverStart();
	}

}