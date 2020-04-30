package com.omok;
import java.io.Serializable;

// 게임중 마우스 좌표 전송을 위한 클래스. 객체를 전송하기 위해 직렬화함
public class PointMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 2101:게임돌 전송
	private int code;
	private String userName; // 게임 상대
	private int dol;
	private int x, y;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDol() {
		return dol;
	}
	public void setDol(int dol) {
		this.dol = dol;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
