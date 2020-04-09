package com.omok;
import java.io.Serializable;

// ������ ���콺 ��ǥ ������ ���� Ŭ����. ��ü�� �����ϱ� ���� ����ȭ��
public class PointMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 2101:���ӵ� ����
	private int code;
	private String userName; // ���� ���
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
