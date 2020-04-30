package com.line2;

//서버, 클라이언트 공용

// 객체직렬화
/*
 * 객체 직렬화는 메모리에 생성된 클래스 객체의 멤버 변수의 현재 상태를 그대로 보존
 * 헤서 파일에 저장하거나 네트워크를 통해 전달할 수 있는 기능
 */

/*
 * 100  client -> server : 로그인 요청
 *  
 * 110  server -> client : 로그인 성공
 * 111  server -> client : 로그인 사실을 다른 클라이언트에게 알림
 * 120  server -> client : 로그인 실패(닉네임 중복으로)
 * 121  server -> client : 로그인 실패(정원초과)
 * 201  server -> client : 로그아웃 사실을 다른 클라이언트에게 알림
 */

public class MsgLogin extends MsgData {
	private static final long serialVersionUID = 1L; // static은 직렬화에서 제외
	
	private int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
