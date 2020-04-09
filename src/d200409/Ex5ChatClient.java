package d200409;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//1:1 Ŭ���̾�Ʈ 
//Ex4ChatServer ���� �����ϰ� Ex5ChatClient����!
//�� �߿� �ϳ� ���� �� ���� ���� �ٽ� Server���� �����ϱ�
public class Ex5ChatClient extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField();

	private Socket sc = null;
	private String host = "127.0.0.1";
	private int port = 8000;
	private String nickName = "���̾�";

	public Ex5ChatClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ta.setEditable(false);
		// ��ũ�ѹ� �����
		JScrollPane sp = new JScrollPane(ta);
		add(sp, BorderLayout.CENTER);

		tf.addActionListener(this);
		add(tf, BorderLayout.SOUTH);

		setTitle("ä�� - Ŭ���̾�Ʈ (" + nickName + ")");
		setSize(500, 500);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Ex5ChatClient().connect();
	}

	public void connect() {
		// ������ ����
		try {
			sc = new Socket(host, port);
			ta.setText("������ ����!!\n");
			Thread t = new Thread(this);
			t.start();
		} catch (Exception e) {
			sc = null;
			System.out.println(e.toString());// ������ �׾� ���� ��
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
					return;
				}
				// ������ ���� ������
				PrintWriter pw = new PrintWriter(sc.getOutputStream(), true);
				pw.println(nickName + " > " + s);

				ta.append("�� > " + s);
				ta.setCaretPosition(ta.getDocument().getLength());
				tf.setText("");
				tf.requestFocus();
				//
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}
		}
	}

	@Override
	public void run() {
		String s;
		try {
			if (sc == null) {
				return;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			while ((s = br.readLine()) != null) {
				ta.append(s + "\n");
			}
		} catch (Exception e) {
			s = "������ �������� ���̽��ϴ�.\n";
			ta.append(s);
			sc = null;
			System.out.println(e.toString());
		}
	}

}
