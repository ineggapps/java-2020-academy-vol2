package db.pscore;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ScoreInsertDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField[] tf=new JTextField[6];
	private JButton btn1, btn2;
	private ScoreFrame frame;
	private ScoreDAO dao;
	
	public ScoreInsertDialog(JFrame frame) {
		super(frame, true);
		
		this.frame=(ScoreFrame)frame;
		this.dao=this.frame.getScoreDAO();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setLayout(null);
		String title[]={"�й� :", "�̸� :", "������� :",
				"���� :", "���� :", "���� :"};
		for(int i=0; i<title.length; i++) {
			JLabel lbl=new JLabel(title[i]);
			lbl.setBounds(20, (i+1)*30, 80, 22);
			add(lbl);
			
			tf[i]=new JTextField();
			tf[i].addKeyListener(new KeyHandler());
			tf[i].setBounds(100, (i+1)*30, 110, 22);
			add(tf[i]);
		}
		
		btn1=new JButton("�߰�");
		btn1.addKeyListener(new KeyHandler());
		btn1.addActionListener(this);
		btn1.setBounds(30, 220, 80, 22);
		add(btn1);
		
		btn2=new JButton("����");
		btn2.addActionListener(this);
		btn2.setBounds(115, 220, 80, 22);
		add(btn2);
		
		setTitle("�ڷ� �Է�");
		setSize(250, 300);
		// ����â �߾ӿ�
		setLocationRelativeTo(this.frame);
		// setLocationRelativeTo(null); // ȭ�� �߾ӿ�
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			insert();
		} else if(e.getSource()==btn2) {
			dispose();
		}
	}
	
	private void insert() {
		String s;
		
		try {
			for(int i=0; i<tf.length; i++) {
				s=tf[i].getText().trim();
				if(s.length()==0) {
					tf[i].requestFocus();
					return;
				}
			}
			
			// ���̺� ������ �߰�
			ScoreDTO dto=new ScoreDTO();
			dto.setHak(tf[0].getText().trim());
			dto.setName(tf[1].getText().trim());
			dto.setBirth(tf[2].getText().trim());
			dto.setKor(Integer.parseInt(tf[3].getText().trim()));
			dto.setEng(Integer.parseInt(tf[4].getText().trim()));
			dto.setMat(Integer.parseInt(tf[5].getText().trim()));
			
			int result=dao.insertScore(dto);
			
			if(result==1) {
				dto.setTot(dto.getKor()+dto.getEng()+dto.getMat());
				dto.setAve(dto.getTot()/3);
				frame.tableInsertRow(dto);
			
				// frame.listScoreAll();
			} else {
				JOptionPane.showMessageDialog(this, "�й� �ߺ� �Ǵ� �ε����ͷ� ���Ͽ� �߰��� ���� �߽��ϴ�.");
				return;
			}
			
			for(int i=0; i<tf.length; i++)
				tf[i].setText("");
			tf[0].requestFocus();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "������ �߰��� ���� �߽��ϴ�.");
		}
	}
	
	class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			Component comp=(Component)e.getSource();
			int code=e.getKeyCode();
			
			if(code==KeyEvent.VK_ENTER) {
				if(comp instanceof JTextField) {
					for(int i=0; i<tf.length; i++) {
						if(i==5&&tf[5]==comp) {
							btn1.requestFocus();
							return;
						} else if(comp==tf[i]){
							tf[i+1].requestFocus();
							return;
						}
					}
				} else if(comp==btn1) {
					insert();
				}
			}
		}
	}
}
