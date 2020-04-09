package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//1:N ���. ������...
public class ChatServer {
	private Vector<Socket> list = new Vector<Socket>();
	private ServerSocket ss = null;
	private int port = 8000;

	public ChatServer() {

	}

	// ���� Ŭ����
	class WorkerThread extends Thread {
		//������ ��ü 1���� ���� 1���� ���
		private Socket sc = null;

		public WorkerThread(Socket sc) {
			this.sc = sc;
		}

		// ��� Ŭ���̾�Ʈ���� �޽��� ������ �޼���
		public void sendMessage(String s) {
			//�ش� ���Ͽ��� ��,���� �߻� Ȥ�� �޽����� ���۵Ǹ� ���� ����Ʈ�� �ҷ��ͼ� �ϳ��� �޽����� �����Ѵ�.
			for (Socket sk : list) {
				try {
					if (sk == sc) {
						continue; // �ڽ��� �����ϰ� ������.
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
			//1. ����ϴ� ������ list�� ����. 
			//2. ���� ����� �ٸ� Ŭ���̾�Ʈ���� �˸�
			//3. ����ϴ� ������ ������ �������ٸ� IOException�� �߻��ϹǷ� ���⼭ ���� ó��
			String ip = null;
			String s = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				ip = sc.getInetAddress().getHostAddress();
				// ������ Ŭ���̾�Ʈ ������ ���Ϳ� �����Ѵ�. (�׷��� ���߿� �ݺ������� ��ȸ�ϸ鼭 ��� ������ ����ڿ��� ��ȭ������ ������ �ְ���?)
				list.add(sc);
				// �ٸ� Ŭ���̾�Ʈ�� ���� ����� �˸���.
				s = "[" + ip + "] ���� �����ϼ̽��ϴ�.";
				sendMessage(s);
				System.out.println(s);
				// Ŭ���̾�Ʈ�� ���� ������ �޴´�.
				while ((s = br.readLine()) != null) {
					// �ٸ� Ŭ���̾�Ʈ�� �޽����� �����Ѵ�.
					sendMessage(s);
					System.out.println(ip + "�� " + s + "���ѷ���!!!");
				}
			} catch (IOException e) {
				// Ŭ���̾�Ʈ ������ �����Ǹ� IOException�� �߻��Ѵ�.
				// Ŭ���̾�Ʈ�� ������ ����� �ٸ� Ŭ���̾�Ʈ�� �˸���.
				s = "[" + ip + "] ������.";
				sendMessage(s);
				// Ŭ���̾�Ʈ�� ���Ϳ��� �����ϳ�.
				list.remove(sc);
				sc = null;
				System.out.println(s);
			}
		}
	}

	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			System.out.println("���� ����...");
			while (true) {
				System.out.println("���� ��� ��");
				Socket sc = ss.accept();
				System.out.println("���� �����");
				//������ ����Ǹ� �����尡 ���� (�����庰 ���� ���� 1���� ����ϸ鼭 ä�� ������ �����Ѵٰ� ���� ��)
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
