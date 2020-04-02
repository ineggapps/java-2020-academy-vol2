package db.pscore;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

// ���̺��� ���� �����ϱ� ���� Ŭ����(�߰�, ����, ����Ȯ�� ���� ����)
public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	// ���̺� ����� Ÿ��Ʋ
	private String[] title;
	
	public MyTableModel(String[] title) {
		this.title=title;
	}

	@Override
	public int getColumnCount() {
		// �÷���(����)
		if(title==null)
			return 0;
		
		return title.length;
	}

	@Override
	public String getColumnName(int column) {
		// �÷���
		return title[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// ����Ŭ���� ���� ���ϰ�
		return false;
	}
}

class MyTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		if(column==0)
			setHorizontalAlignment(SwingConstants.LEFT);
		else if(column==1 || column==2)
			setHorizontalAlignment(SwingConstants.CENTER);
		else
			setHorizontalAlignment(SwingConstants.RIGHT);
		
		return this;
	}
}
