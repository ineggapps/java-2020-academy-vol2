package com.multi;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * ������ ���� Socket�� 1:1�� 1:Nó�� ������ ���̴�.
 * UDP�� ��Ƽĳ������ ��¥ 1:N�̴�.
 * �ٸ�, TCP�� �������� ��İ��� �ٸ��� �������� �ҽ��� ���� �������Ƿ� �������� ���� ���� �ִ�.
 * 
 * DatagramSocket : UDP �������� ������ �׷� ��Ŷ�� �����ϰų� ����
 * DatagramPacket : UDP ���������� ���ؼ� ���� �ɼ� �ִ� ������
 * MulticastSocket : �ѹ��� �ټ��� Ŭ���̾�Ʈ�� �����ͱ׷��� ����
 * 
 *  �׷� ���� ���� : D class(224.0.0.0~239.255.255.255)
 */
public class ChatCS extends JFrame implements Runnable, ActionListener{
	private static final long serialVersionUID = 1L;
	
	private MulticastSocket ms = null;
	private InetAddress xGroup = null;
	
	private String host = "230.0.0.1";
	private int port=5555;
	private String userName="�ڹ�";
	
	private JTextArea ta;
	private JTextField tf;
	
	public ChatCS() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ta = new JTextArea();
		ta.setEditable(false);
		JScrollPane sp=new JScrollPane(ta);
		add(sp, BorderLayout.CENTER);
		
		tf = new JTextField();
		add(tf, BorderLayout.SOUTH);
		tf.addActionListener(this);
		
		setTitle("��Ƽĳ���� ä��");
		setSize(500, 550);
		setResizable(false);
		setVisible(true);
		
		tf.requestFocus();
	}
	
	public void setup() {
		try {
			xGroup = InetAddress.getByName(host);
			
			ms = new MulticastSocket(port);
			
			// Ư�� �׷쿡 ����
			ms.joinGroup(xGroup);

			Thread t = new Thread(this);
			t.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			// Ư�� �׷쿡�� ���� ����
			ms.leaveGroup(xGroup);
			
			ms.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		ms=null;

	}
	
	public void run() {
		try {
			if(ms==null)
				return;

			while(true){
				byte [] buffer = new byte[256];
				
				// ���� ���� ��Ŷ
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				// ���� ����
				ms.receive(packet);
				String str = new String(packet.getData()).trim();
				ta.append(str + "\n");
				ta.setCaretPosition(ta.getDocument().getLength());
			}
		} catch(IOException e) {
			disConnection();
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String str = tf.getText().trim();
		
		if(str.length()==0)
			return;

		if(ms==null)
			return;
		
		byte buffer[] = (userName + "] " + str).getBytes();
		
		try {
			// ������ ��Ŷ
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, xGroup, port);
			
			// ����
			ms.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tf.setText("");
		tf.requestFocus();
	}
	
	public static void main(String[] args) {
		new ChatCS().setup();
	}
}
