package com.omok;

import java.io.ObjectOutputStream;
import java.net.Socket;

// 서버용
// 클라이언트의 소켓 및 닉네임 저장용 클래스
public class SocketInfo {
	Socket sock;
	String userName;        // 로그인 이름
	String opponentName; // 게임 상대방 이름
	boolean gameState;   // 게임상태
	ObjectOutputStream oos;
}
