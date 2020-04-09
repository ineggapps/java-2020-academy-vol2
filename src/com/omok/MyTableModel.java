package com.omok;

import javax.swing.table.DefaultTableModel;

// 테이블 생성후 추가, 삭제, 개수확인등을 위한 모델(모양) 클래스
public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	protected String title[]; // 테이블 헤더
	
	public MyTableModel(String[] title) {
		this.title = title;
	}

	// 컬럼의 개수
	@Override
	public int getColumnCount() {
		if(title == null)
			return 0;
		
		return title.length;
	}

	// 컬럼의 헤더 제목
	@Override
	public String getColumnName(int column) {
		if(title == null || title.length == 0)
			return null;
		
		return title[column];
	}

	// 마우스 더블클릭시 편집하지 못하게
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
