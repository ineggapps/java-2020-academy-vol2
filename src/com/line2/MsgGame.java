package com.line2;

// 101 : ���� ��û(Ŭ���̾�Ʈ -> ����)
// 201 : ���� ��û OK(����->��û Ŭ���̾�Ʈ)
// 202 : ���� ��û ����(������ : ����->��û Ŭ���̾�Ʈ)
// 210 : ���� ������ �˸�(����->Ŭ���̾�Ʈ)

// 110 : ���� ���Ḧ �˸�(Ŭ���̾�Ʈ -> ����)
// 211 : ���� ���Ḧ �˸�(���� -> Ŭ���̾�Ʈ)

public class MsgGame extends MsgData {
	private static final long serialVersionUID = 1L; // static�� ����ȭ���� ����

	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
