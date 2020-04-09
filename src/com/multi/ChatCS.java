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
 * 엄밀히 보면 Socket은 1:1을 1:N처럼 구현한 것이다.
 * UDP의 멀티캐스팅은 진짜 1:N이다.
 * 다만, TCP의 프로토콜 방식과는 다르게 데이터의 소실이 있을 수있으므로 수신하지 못할 수도 있다.
 * 
 * DatagramSocket : UDP 소켓으로 데이터 그램 패킷을 전송하거나 수신
 * DatagramPacket : UDP 프로토콜응 통해서 전송 될수 있는 데이터
 * MulticastSocket : 한번에 다수의 클라이언트에 데이터그램을 전송
 * 
 *  그룹 범위 지정 : D class(224.0.0.0~239.255.255.255)
 */
public class ChatCS extends JFrame implements Runnable, ActionListener{
	private static final long serialVersionUID = 1L;
	
	private MulticastSocket ms = null;
	private InetAddress xGroup = null;
	
	private String host = "230.0.0.1";
	private int port=5555;
	private String userName="자바";
	
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
		
		setTitle("멀티캐스팅 채팅");
		setSize(500, 550);
		setResizable(false);
		setVisible(true);
		
		tf.requestFocus();
	}
	
	public void setup() {
		try {
			xGroup = InetAddress.getByName(host);
			
			ms = new MulticastSocket(port);
			
			// 특정 그룹에 포함
			ms.joinGroup(xGroup);

			Thread t = new Thread(this);
			t.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			// 특정 그룹에서 빠저 나옴
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
				
				// 전송 받을 패킷
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				// 전송 받음
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
			// 전송할 패킷
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, xGroup, port);
			
			// 전송
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
