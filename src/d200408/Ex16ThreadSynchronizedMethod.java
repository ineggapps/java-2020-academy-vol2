package d200408;

public class Ex16ThreadSynchronizedMethod {
	public static void main(String[] args) {
		// ����ȭ���� ������ ���� ����� �ùٸ��� �ʰ� ��µȴ�.
		ShareData1 sd = new ShareData1();
		UpThread1 t1 = new UpThread1(sd, "up");
		DownThread1 t2 = new DownThread1(sd, "down");

		t1.start();
		t2.start();
		System.out.println("main() ����");
	}
}

class ShareData1 {
	private int data = 100;

//	public void up(String title) { //����ȭ���� ������ �ùٸ��� ���� �ᱣ���� ���
	public synchronized void up(String title) { // ����ȭ �޼��� ���
		System.out.print(title + ": " + data);
		data++;
		System.out.println(" �� ���� ��: " + data);
	}

//	public void down(String title) { //����ȭ���� ������ �ùٸ��� ���� �ᱣ���� ���
	public synchronized void down(String title) { // ����ȭ �޼��� ���
		System.out.print(title + ": " + data);
		data--;
		System.out.println(" �� ���� ��: " + data);
	}
}

class UpThread1 extends Thread {
	private ShareData1 share;
	private String title;

	public UpThread1(ShareData1 share, String title) {
		this.share = share;
		this.title = title;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			try {
				sleep(100);
				share.up(title);
			} catch (Exception e) {
			}
		}
	}
}

class DownThread1 extends Thread {
	private ShareData1 share;
	private String title;

	public DownThread1(ShareData1 share, String title) {
		this.share = share;
		this.title = title;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			try {
				sleep(100);
				share.down(title);
			} catch (Exception e) {
			}
		}
	}
}