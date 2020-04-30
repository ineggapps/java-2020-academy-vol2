package com.line2;

import java.awt.Point;

// 선그리기 정점
public class MsgVertex extends MsgData {
	private static final long serialVersionUID = 1L;
	
	Point p;
	boolean draw;
	public MsgVertex(Point p, boolean draw) {
		this.p=p;
		this.draw=draw;
	}

}
