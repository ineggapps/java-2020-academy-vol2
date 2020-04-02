package db.pscore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;

public class ScoreFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton btn1, btn2, btn3, btn4, btn5;
	
	private JComboBox<String> cb;
	private JTextField tf;
	private JButton btnSearch;
	
	private JTable table;
	
	private ScoreDAO dao=new ScoreDAOImpl();
	
	public ScoreFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(5, 1));
		
		btn1=new JButton("�߰�");
		btn1.setBackground(Color.WHITE);
		btn1.setOpaque(true); // ������ �ֱ�����(��������)
		btn1.addActionListener(this);
		p.add(btn1);
		
		btn2=new JButton("����");
		btn2.setBackground(Color.WHITE);
		btn2.setOpaque(true);
		btn2.addActionListener(this);
		p.add(btn2);

		btn3=new JButton("����");
		btn3.setBackground(Color.WHITE);
		btn3.setOpaque(true);
		btn3.addActionListener(this);
		p.add(btn3);
		
		btn4=new JButton("���ΰ�ħ");
		btn4.setBackground(Color.WHITE);
		btn4.setOpaque(true);
		btn4.addActionListener(this);
		p.add(btn4);
		
		btn5=new JButton("����");
		btn5.setBackground(Color.WHITE);
		btn5.setOpaque(true);
		btn5.addActionListener(this);
		p.add(btn5);

		add(p, BorderLayout.EAST);
		
		p=new JPanel();
		String []data={"�й��˻�", "�̸��˻�"};
		cb=new JComboBox<String>(data);
		cb.setBackground(Color.WHITE);
		cb.setOpaque(true);
		p.add(cb);
		
		tf=new JTextField(15);
/*
		tf.setBorder(BorderFactory.createCompoundBorder(
				tf.getBorder(), 
		        BorderFactory.createEmptyBorder(3, 3, 3, 3)));
*/		
		LineBorder border = new LineBorder(new Color(100,100,100), 1);
		tf.setBorder(BorderFactory.createCompoundBorder(
				border, 
		        BorderFactory.createEmptyBorder(3, 3, 3, 3)));
		p.add(tf);
		
		btnSearch=new JButton(" �˻� ");
		btnSearch.setBackground(Color.WHITE);
		btnSearch.setOpaque(true);
		btnSearch.addActionListener(this);
		p.add(btnSearch);
		
		add(p, BorderLayout.SOUTH);
		
		// ���̺� �߰�
		addTable();
		
		listScoreAll();
		
		setTitle("����ó��");
		setSize(670, 500);
		setResizable(false);
		setVisible(true);
	}
	
	private void addTable() {
		String[] title={"�й�", "�̸�", "�������",
				"����", "����", "����", "����", "���"};
		
		MyTableModel model=new MyTableModel(title);
		table=new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		add(sp, BorderLayout.CENTER);
		
		// �ڵ� ũ�� ���� OFF
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// �÷��� ����
		for(int i=0; i<title.length; i++) {
			TableColumn col=
					table.getColumnModel().getColumn(i);
			if(i==1)
				col.setPreferredWidth(100);
			else if(i==2)
				col.setPreferredWidth(80);
			else
				col.setPreferredWidth(60);
		}
		
		// �÷� ���� ��� ����
		MyTableCellRenderer render = new MyTableCellRenderer();
		table.setDefaultRenderer(table.getColumnClass(0), render);
	}
	
	public ScoreDAO getScoreDAO() {
		return dao;
	}
	
	public void tableInsertRow(String[] items) {
		// ���̺� �ڷ� �߰�
		((MyTableModel)table.getModel()).addRow(items);
	}

	public void tableInsertRow(ScoreDTO dto) {
		// ���̺� �ڷ� �߰�
		String[] items=new String[8];
		
		items[0]=dto.getHak();
		items[1]=dto.getName();
		items[2]=dto.getBirth();
		items[3]=Integer.toString(dto.getKor());
		items[4]=Integer.toString(dto.getEng());
		items[5]=Integer.toString(dto.getMat());
		items[6]=Integer.toString(dto.getTot());
		items[7]=Integer.toString(dto.getAve());
		
		((MyTableModel)table.getModel()).addRow(items);
	}
	
	public void tableUpdateRow(String[] items, int row) {
		// �ش� ���� ������ ����
		for(int i=0; i<items.length; i++) {
			((MyTableModel)table.getModel()).setValueAt(items[i], row, i);
		}
	}

	public void tableUpdateRow(ScoreDTO dto, int row) {
		// �ش� ���� ������ ����
		String[] items=new String[8];
		
		items[0]=dto.getHak();
		items[1]=dto.getName();
		items[2]=dto.getBirth();
		items[3]=Integer.toString(dto.getKor());
		items[4]=Integer.toString(dto.getEng());
		items[5]=Integer.toString(dto.getMat());
		items[6]=Integer.toString(dto.getTot());
		items[7]=Integer.toString(dto.getAve());
		
		for(int i=0; i<items.length; i++) {
			((MyTableModel)table.getModel()).setValueAt(items[i], row, i);
		}
	}
	
	public void tableRemoveRow(int row) {
		// ���̺��� Ư�� �� �����
		((MyTableModel)table.getModel()).removeRow(row);
	}
	
	public void tableRemoveAll() {
		// ���̺��� ��� �� �����
		int size=table.getRowCount();
		for(int i=0; i<size; i++)
			((MyTableModel)table.getModel()).removeRow(0);
	}
	
	public void tableInsertList(List<ScoreDTO> list) {
		// ���̺��� ��� �ڷḦ ����� List �����͸� �߰��ϱ�
		tableRemoveAll();
	
		for(ScoreDTO dto : list) {
			tableInsertRow(dto);
		}
	}
	
	public void listScoreAll() {
		List<ScoreDTO> list=dao.listScore();
		tableInsertList(list);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			// �߰�
			new ScoreInsertDialog(this);
			
		} else if(e.getSource()==btn2) {
			// ����
			int idx=table.getSelectedRow();
			if(idx==-1) {
				JOptionPane.showMessageDialog(this,
						"������ �ڷḦ ���� �����ϼ���.");
				return;
			}
			
			int cnt=((MyTableModel)table.getModel()).getColumnCount();
			String[] items=new String[cnt];
			for(int i=0; i<cnt; i++)
				items[i]=table.getValueAt(idx, i).toString();
			
			new ScoreUpdateDialog(this, items, idx);
			
		} else if(e.getSource()==btn3) {
			// ����
			int idx=table.getSelectedRow();
			if(idx==-1) {
				JOptionPane.showMessageDialog(this,
						"������ �ڷḦ ���� �����ϼ���.");
				return;
			}
			
			int result;
			result=JOptionPane.showConfirmDialog(this,
					"������ �ڷḦ �����Ͻðڽ��ϱ� ?",
					"Ȯ��",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(result==JOptionPane.YES_OPTION) {
				// ���̺� ���� �����
				String hak=table.getValueAt(idx, 0).toString();
				dao.deleteScore(hak);
				tableRemoveRow(idx);
			}
			
		} else if(e.getSource()==btn4) {
			// ���� ��ħ
			listScoreAll();
			
			cb.setSelectedIndex(0);
			tf.setText("");
			
		} else if(e.getSource()==btn5) {
			// ����
			int result = JOptionPane.showConfirmDialog(this,
					"�����Ͻðڽ��ϱ� ?", "Ȯ��",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}			
		} else if(e.getSource()==btnSearch) {
			// �˻�
			String searchValue;
			
			int idx=cb.getSelectedIndex();
			searchValue=tf.getText().trim();
			
			if(searchValue.length()==0)
				return;

			if(idx==0) {
				// �й��˻�
				ScoreDTO dto=dao.readScore(searchValue);
				tableRemoveAll();

				if(dto!=null) {
					tableInsertRow(dto);
				}
			} else {
				// �̸��˻�
				List<ScoreDTO> list=dao.listScore(searchValue);
				tableInsertList(list);
			}
		}
	}
}
