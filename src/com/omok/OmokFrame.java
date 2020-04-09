package com.omok;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class OmokFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected static final int NONE = 0;
	protected static final int BLACK = 1;
	protected static final int WHITE = 2;
	protected int board[][] = new int[19][19];
	protected int dolColor=NONE; // ���������� ������
	protected boolean bWhTurn; // ���� ���� ����
	protected boolean bGameMe=false, bGameOpponent=false; // ���ӻ���
	protected int panjeong;
	protected String userName, opponentUserName;
	protected BadukPan gp;
	
	protected int px, py; // ���� ���� ��ǥ
	
	public OmokFrame() {
		gameInit();
	}

	public void gameInit() {
		int x, y;
		for (x = 0; x < 19; x++)
			for (y = 0; y < 19; y++)
				board[x][y] = NONE;
		
		panjeong = NONE;
		
		px=-1;
		py=-1;
	}

	// ***************************************************************
	// ���� �÷���
	public void gamePlay(PointMsg pm, int dol, JTextArea ta) {
		int ax, ay;
		ax = pm.getX();
		ay = pm.getY();

		board[ax][ay] = dol;

		drawSite(ax, ay, board[ax][ay]);
		smallDrawSite(ax, ay, board[ax][ay], true);
		
		gamePanjeong(ax, ay);
		
		if(panjeong==NONE)
			return;

		if (panjeong == dolColor) {
			ta.append(userName+"���� �¸� �߽��ϴ�.\n");
			
		} else{
			ta.append(userName+"���� �� �߽��ϴ�.\n");
		}
		
		ta.setCaretPosition(ta.getDocument().getLength());
		bGameMe=false;
		bGameOpponent=false;
	}

	// ***************************************************************
	// ���� ����
	public void gamePanjeong(int ax, int ay) {
		int cnt;
		int i;
		int dol;

		dol = board[ax][ay];

		cnt = 1;
		for (i = 1; i < 6; i++) {
			if (ay - i < 0)
				break;
			if (board[ax][ay - i] == dol)
				cnt++;
			else
				break;
		}
		for (i = 1; i < 6; i++) {
			if (ay + i >= 19)
				break;
			if (board[ax][ay + i] == dol)
				cnt++;
			else
				break;
		}
		if (cnt == 5) {
			panjeong = dol;
			return;
		}

		cnt = 1;
		for (i = 1; i < 6; i++) {
			if (ax - i < 0)
				break;
			if (board[ax - i][ay] == dol)
				cnt++;
			else
				break;
		}
		for (i = 1; i < 6; i++) {
			if (ax + i >= 19)
				break;
			if (board[ax + i][ay] == dol)
				cnt++;
			else
				break;
		}
		if (cnt == 5) {
			panjeong = dol;
			return;
		}

		cnt = 1;
		for (i = 1; i < 6; i++) {
			if (ax - i < 0 || ay - i < 0)
				break;
			if (board[ax - i][ay - i] == dol)
				cnt++;
			else
				break;
		}
		for (i = 1; i < 6; i++) {
			if (ax + i >= 19 || ay + i >= 19)
				break;
			if (board[ax + i][ay + i] == dol)
				cnt++;
			else
				break;
		}
		if (cnt == 5) {
			panjeong = dol;
			return;
		}

		cnt = 1;
		for (i = 1; i < 6; i++) {
			if (ax + i >= 19 || ay - i < 0)
				break;
			if (board[ax + i][ay - i] == dol)
				cnt++;
			else
				break;
		}
		for (i = 1; i < 6; i++) {
			if (ax - i < 0 || ay + i >= 19)
				break;
			if (board[ax - i][ay + i] == dol)
				cnt++;
			else
				break;
		}
		if (cnt == 5) {
			panjeong = dol;
			return;
		}
	}

	// ***************************************************************
	// �� �׸���
	public void drawSite(int x, int y, int dol) {
		Graphics g = gp.getGraphics();
		
		// ���׸���
		if (dol == NONE)
			return;
		
		if (dol == BLACK) {
			g.setColor(new Color(0, 0, 0));
		} else {
			g.setColor(new Color(255, 255, 255));
		}
		g.fillOval(x * 20 + 2, y * 20 + 2, 16, 16);
	}
	
	// ������
	public void smallDrawSite(int x, int y, int dol, boolean bDraw) {
		Graphics g = gp.getGraphics();
		
		// ������ �ٽ� �׸���
		if(bDraw && px!=-1 && py!=-1) {
			if (dol == BLACK) {
				g.setColor(new Color(255, 255, 255));
			} else {
				g.setColor(new Color(0, 0, 0));
			}
			g.fillOval(px * 20 + 2, py * 20 + 2, 16, 16);
		}
		
		if (dol == NONE)
			return;
		
		// ���� ���� ���� �׸���
		if (dol == BLACK) {
			g.setColor(new Color(255, 255, 255));
		} else {
			g.setColor(new Color(0, 0, 0));
		}
		g.fillOval(x * 20 + 8, y * 20 + 8, 4, 4);
				
		px=x; py=y;		
	}
	
}
