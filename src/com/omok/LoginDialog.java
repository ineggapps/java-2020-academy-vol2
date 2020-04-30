package com.omok;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// 로그인 대화상자
public class LoginDialog extends JDialog {
		private static final long serialVersionUID = 1L;

		private JTextField tfHost, tfName;
	    
		private JButton but1, but2;
		
		private String host = "127.0.0.1";
		private String userName="";
		
		public LoginDialog(JFrame frame) {
			super(frame, true);
			// setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent evt) {
					host="";
					userName="";
					dispose();
				}
			});
			
			setLayout(null);
			JLabel lbl;
			
			lbl= new JLabel("서버주소 : ");
			lbl.setBounds(20, 10, 70, 20);
			add(lbl);
			tfHost = new JTextField(host);
			tfHost.setBounds(90, 10, 100, 20);
			add(tfHost);
			
			lbl= new JLabel("닉네임 : ");
			lbl.setBounds(20, 40, 70, 20);
			add(lbl);
			tfName = new JTextField();
			tfName.setBounds(90, 40, 100, 20);
			add(tfName);
			
			but1 = new JButton("로그인");
			but1.addActionListener(new ActionHandler());
			but1.setBounds(25, 70, 80, 20);
			add(but1);

			but2 = new JButton("취소");
			but2.addActionListener(new ActionHandler());
			but2.setBounds(110, 70, 80, 20);
			add(but2);		
			
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setTitle("로그인");
			setSize(250, 150);
			
			// 화면의 중앙에 배치
			// setLocationRelativeTo(parent);
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        Dimension frameSize = getSize();
	        if (frameSize.height > screenSize.height) {
	              frameSize.height = screenSize.height;
	        }
	        if (frameSize.width > screenSize.width) {
	              frameSize.width = screenSize.width;
	        }
	        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);		
		}
		
		class ActionHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource()==but1) {
					host=tfHost.getText().trim();
					if(host.length()==0) {
						JOptionPane.showMessageDialog(LoginDialog.this, "서버 주소를 정확히 입력 하세요.", "에러",
								JOptionPane.INFORMATION_MESSAGE);
						tfHost.requestFocus();
						return;
					}
					
					userName=tfName.getText().trim();
					if(userName.length()==0) {
						JOptionPane.showMessageDialog(LoginDialog.this, "닉네임을 정확히 입력 하세요.", "에러",
								JOptionPane.INFORMATION_MESSAGE);
						tfName.requestFocus();
						return;
					}
					dispose();
					
				} else if(evt.getSource()==but2) {
					host="";
					userName="";
					dispose();
				}
			}
		}

		public String getHost() {
			return host;
		}

		public String getUserName() {
			return userName;
		}
}
