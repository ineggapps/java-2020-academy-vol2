package com.line2;

import java.awt.Point;

// ���׸��� ����
public class MsgVertex extends MsgData {
	private static final long serialVersionUID = 1L;
	
	Point p;
	boolean draw;
	public MsgVertex(Point p, boolean draw) {
		this.p=p;
		this.draw=draw;
	}

}
