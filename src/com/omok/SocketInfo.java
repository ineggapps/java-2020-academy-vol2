package com.omok;

import java.io.ObjectOutputStream;
import java.net.Socket;

// ������
// Ŭ���̾�Ʈ�� ���� �� �г��� ����� Ŭ����
public class SocketInfo {
	Socket sock;
	String userName;        // �α��� �̸�
	String opponentName; // ���� ���� �̸�
	boolean gameState;   // ���ӻ���
	ObjectOutputStream oos;
}
