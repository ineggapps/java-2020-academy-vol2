package com.line2;

import java.io.ObjectOutputStream;
import java.net.Socket;

// 서버용
// 클라이언트의 소켓 클래스
public class SocketInfo {
	String userName;
	Socket sock;
	boolean bGame;
	ObjectOutputStream oos;
}
