package com.line2;

// 101 : 게임 요청(클라이언트 -> 서버)
// 201 : 게임 요청 OK(서버->요청 클라이언트)
// 202 : 게임 요청 실패(게임중 : 서버->요청 클라이언트)
// 210 : 게임 시작을 알림(서버->클라이언트)

// 110 : 게임 종료를 알림(클라이언트 -> 서버)
// 211 : 게임 종료를 알림(서버 -> 클라이언트)

public class MsgGame extends MsgData {
	private static final long serialVersionUID = 1L; // static은 직렬화에서 제외

	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
