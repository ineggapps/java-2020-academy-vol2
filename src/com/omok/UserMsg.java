package com.omok;

import java.io.Serializable;

// ���� ��û�� ���� Ŭ����. ��ü�� �����ϱ� ���� ����ȭ��

/*
 * 1100  client -> server : �α��� ��û
 *  
 * 1110  server -> client : �α��� ����
 * 1111  server -> client : �α��� ���� ����ڿ��� �α��ε� ����Ʈ ����
 * 1112  server -> client : �α��� ����� �ٸ� Ŭ���̾�Ʈ���� �˸�
 * 1120  server -> client : �α��� ����(�г��� �ߺ�����)
 * 1210  server -> client : �α��� ������� ���ӻ��� ����(����)
 * 1211  client -> client(server) / client(server)->client : ���濡�� ���� ��û
 * 1212  client -> client(server) / client(server)->client : ���濡�� ���� ��û �¶� ����
 * 1221  client -> client(server) / client(server)->client : ���濡�� ������ �ٽ� �Ѱ��� �� �������� ����
 * 1222  client -> client(server) / client(server)->client : ���濡�� ������ �ٽ� �Ѱ��� �� �������� ���� �¶� ����
 * 1300  client -> server : �α׾ƿ�
 * 1301  server -> client : �α׾ƿ� ����� �ٸ� Ŭ���̾�Ʈ���� �˸�
 */

public class UserMsg implements Serializable {
	private static final long serialVersionUID = 1L; // static�� ����ȭ���� ����
	
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
