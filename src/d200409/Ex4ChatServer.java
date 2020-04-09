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

//1:1 Ŭ���̾�Ʈ 
//Ex4ChatServer ���� �����ϰ� Ex5ChatClient����!
//�� �߿� �ϳ� ���� �� ���� ���� �ٽ� Server���� �����ϱ�
public class Ex4ChatServer extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField();

	private ServerSocket ss = null;
	private int port = 8000;
	private Socket sc = null;// ������ Ŭ���̾�Ʈ

	public Ex4ChatServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ta.setEditable(false);
		// ��ũ�ѹ� �����
		JScrollPane sp = new JScrollPane(ta);
		add(sp, BorderLayout.CENTER);

		tf.addActionListener (this);
		add(tf, BorderLayout.SOUTH);

		setTitle("ä�� - ����");
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
			ta.setText("���� ����...");
			sc = ss.accept(); // Ŭ���̾�Ʈ ������ ��ٸ� (���) => blocking ���·� ��� ��
			// Ŭ���̾�Ʈ�� �����ϸ�
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
				// ����
				if (sc == null) {
					// ����� ���°� �ƴϸ� ������ ����� �����Ƿ�...
					return;
				}
				// Ŭ���̾�Ʈ�� ������ ������.
				PrintWriter pw = new PrintWriter(sc.getOutputStream(), true);
				pw.println("���� > " + s + "\n");
				ta.append("���� | " + s + "\n");
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
				// ������ null�� ��� ������ ���� �ʾҴٴ� �ǹ��̹Ƿ�..
				return;
			}
			// ������ Ŭ���̾������ �Է� ��Ʈ���� ���� Ŭ���̾�Ʈ ������ �о�´�.
			BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			// ������ Ŭ���̾�Ʈ ������ �ּ�
			ip = sc.getInetAddress().getHostAddress();
			ta.append("[" + ip + "] ���� ����...\n");
			// Ŭ���̾�Ʈ�� ���� ���� �ޱ�
			while ((s = br.readLine()) != null) {
				ta.append(s + "\n");
			}
		} catch (IOException e) {
			// Ŭ���̾�Ʈ�� ������ �����ϸ� IOException�� �߻��Ѵ�.
			ta.append("\n[" + ip + "] �� ����...\n");
			sc = null;
		}
	}

}
