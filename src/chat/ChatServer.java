package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//1:N 통신. 서버용...
public class ChatServer {
	private Vector<Socket> list = new Vector<Socket>();
	private ServerSocket ss = null;
	private int port = 8000;

	public ChatServer() {

	}

	// 내부 클래스
	class WorkerThread extends Thread {
		//스레드 객체 1개당 소켓 1개를 담당
		private Socket sc = null;

		public WorkerThread(Socket sc) {
			this.sc = sc;
		}

		// 모든 클라이언트에게 메시지 보내는 메서드
		public void sendMessage(String s) {
			//해당 소켓에서 입,퇴장 발생 혹은 메시지가 전송되면 소켓 리스트를 불러와서 하나씩 메시지를 전달한다.
			for (Socket sk : list) {
				try {
					if (sk == sc) {
						continue; // 자신은 제외하고 보낸다.
					}
					PrintWriter pw = new PrintWriter(sk.getOutputStream(), true);
					pw.println(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void run() {
			//1. 담당하는 소켓을 list에 저장. 
			//2. 접속 사실을 다른 클라이언트에게 알림
			//3. 담당하는 소켓이 연결이 끊어진다면 IOException이 발생하므로 여기서 퇴장 처리
			String ip = null;
			String s = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				ip = sc.getInetAddress().getHostAddress();
				// 접속한 클라이언트 소켓을 벡터에 저장한다. (그래야 나중에 반복문으로 순회하면서 모든 접속한 사용자에게 대화내용을 전송해 주겠지?)
				list.add(sc);
				// 다른 클라이언트에 접속 사실을 알린다.
				s = "[" + ip + "] 에서 접속하셨습니다.";
				sendMessage(s);
				System.out.println(s);
				// 클라이언트가 보낸 정보를 받는다.
				while ((s = br.readLine()) != null) {
					// 다른 클라이언트에 메시지를 전송한다.
					sendMessage(s);
					System.out.println(ip + "의 " + s + "무한루프!!!");
				}
			} catch (IOException e) {
				// 클라이언트 접속이 해제되면 IOException이 발생한다.
				// 클라이언트가 퇴장한 사실을 다른 클라이언트에 알린다.
				s = "[" + ip + "] 도망감.";
				sendMessage(s);
				// 클라이언트를 벡터에서 제거하낟.
				list.remove(sc);
				sc = null;
				System.out.println(s);
			}
		}
	}

	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			System.out.println("서버 시작...");
			while (true) {
				System.out.println("소켓 대기 중");
				Socket sc = ss.accept();
				System.out.println("소켓 연결됨");
				//소켓이 연결되면 스레드가 생성 (스레드별 각각 소켓 1개를 담당하면서 채팅 내용을 전송한다고 보면 됨)
				WorkerThread t = new WorkerThread(sc);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ChatServer().serverStart();

	}
}
