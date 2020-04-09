package com.line2;

// 채팅 메시지
public class MsgChat extends MsgData {
	private static final long serialVersionUID = 2L;
	
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
