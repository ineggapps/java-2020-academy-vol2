package d200409;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Ex3ChatForm extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea ta = new JTextArea();
	private JTextField tf = new JTextField();

	public Ex3ChatForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ta.setEditable(false);
		//��ũ�ѹ� �����
		JScrollPane sp = new JScrollPane(ta);
		add(sp, BorderLayout.CENTER);

		tf.addActionListener(this);
		add(tf, BorderLayout.SOUTH);

		setTitle("ä��-");
		setSize(500, 500);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Ex3ChatForm();
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
				ta.append("�� > " + s + "\n");
				ta.setCaretPosition(ta.getDocument().getLength());
				tf.setText("");
				tf.requestFocus();
				// 
			} catch (Exception e2) {

			}
		}
	}

	@Override
	public void run() {

	}

}
