package d200409;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//1:1 클라이언트 
//Ex4ChatServer 먼저 실행하고 Ex5ChatClient실행!
//둘 중에 하나 종료 시 전부 끄고 다시 Server부터 실행하기
public class Ex4ChatServer extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField();

	private ServerSocket ss = null;
	private int port = 8000;
	private Socket sc = null;// 접속한 클라이언트

	public Ex4ChatServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ta.setEditable(false);
		// 스크롤바 만들기
		JScrollPane sp = new JScrollPane(ta);
		add(sp, BorderLayout.CENTER);

		tf.addActionListener (this);
		add(tf, BorderLayout.SOUTH);

		setTitle("채팅 - 서버");
		setSize(500, 500);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		Ex4ChatServer ob = new Ex4ChatServer();
		ob.serverStart();
	}

	public void serverStart() {
		try {
			ss = new ServerSocket(port);
			ta.setText("서버 시작...");
			sc = ss.accept(); // 클라이언트 접속을 기다림 (대기) => blocking 상태로 들어 감
			// 클라이언트가 접속하면
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf) {
			String s = tf.getText().trim();
			if (s.length() == 0) {
				return;
			}
			try {
				// 전송
				if (sc == null) {
					// 연결된 상태가 아니면 전송할 대상이 없으므로...
					return;
				}
				// 클라이언트에 정보를 보낸다.
				PrintWriter pw = new PrintWriter(sc.getOutputStream(), true);
				pw.println("서버 > " + s + "\n");
				ta.append("보냄 | " + s + "\n");
				ta.setCaretPosition(ta.getDocument().getLength());
				tf.setText("");
				tf.requestFocus();
				//
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		String s;
		String ip = null;

		try {
			if (sc == null) {
				// 소켓이 null인 경우 연결이 되지 않았다는 의미이므로..
				return;
			}
			// 접속한 클라이어느에서 입력 스트림을 구해 클라이언트 정보를 읽어온다.
			BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			// 접속한 클라이언트 아이피 주소
			ip = sc.getInetAddress().getHostAddress();
			ta.append("[" + ip + "] 에서 접속...\n");
			// 클라이언트가 보낸 정보 받기
			while ((s = br.readLine()) != null) {
				ta.append(s + "\n");
			}
		} catch (IOException e) {
			// 클라이언트가 접속을 중지하면 IOException이 발생한다.
			ta.append("\n[" + ip + "] 님 퇴장...\n");
			sc = null;
		}
	}

}
