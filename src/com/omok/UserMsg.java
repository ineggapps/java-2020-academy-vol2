package com.omok;

import java.io.Serializable;

// 각종 요청을 위한 클래스. 객체를 전송하기 위해 직렬화함

/*
 * 1100  client -> server : 로그인 요청
 *  
 * 1110  server -> client : 로그인 성공
 * 1111  server -> client : 로그인 성공 사용자에게 로그인된 리스트 전송
 * 1112  server -> client : 로그인 사실을 다른 클라이언트에게 알림
 * 1120  server -> client : 로그인 실패(닉네임 중복으로)
 * 1210  server -> client : 로그인 사용자중 게임상태 전송(변경)
 * 1211  client -> client(server) / client(server)->client : 상대방에게 게임 요청
 * 1212  client -> client(server) / client(server)->client : 상대방에게 게임 요청 승락 여부
 * 1221  client -> client(server) / client(server)->client : 상대방에게 게임을 다시 한게임 할 것인지를 전송
 * 1222  client -> client(server) / client(server)->client : 상대방에게 게임을 다시 한게임 할 것인지에 대한 승락 여부
 * 1300  client -> server : 로그아웃
 * 1301  server -> client : 로그아웃 사실을 다른 클라이언트에게 알림
 */

public class UserMsg implements Serializable {
	private static final long serialVersionUID = 1L; // static은 직렬화에서 제외
	
	private int code;
	private String userName, opponentUserName;
	private boolean gameState;
	
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
	public String getOpponentUserName() {
		return opponentUserName;
	}
	public void setOpponentUserName(String opponentUserName) {
		this.opponentUserName = opponentUserName;
	}
	public boolean getGameState() {
		return gameState;
	}
	public void setGameState(boolean gameState) {
		this.gameState = gameState;
	}
}
