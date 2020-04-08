package d200408;

public class Ex15ThreadWithSynchronizedBlock {
	public static void main(String[] args) {
		DemoThread2 r = new DemoThread2();

		// Runnable�� ������ Ŭ������ü�� 1���� �����Ͽ� �� ���� �����尡 ������.
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);

		t1.start();
		t2.start();

		System.out.println("main() ����");
	}
}

class DemoThread2 implements Runnable {
	private int bank = 1000;// ���� ������ ���� �ܰ� 1000���� ����Ǿ� �ִٰ� ����

	@Override
	public void run() {
		int m = 600;// ������ �ݾ�
		int n = 0;
		String msg = null;
		try {
			//����ȭ ������� ���ÿ� ���� ���� �����尡 �����Ͽ� ������ ��ġ�� �ʵ��� ���Ѵ�. => ������� ��ȣ������� ��!
			synchronized (this) { 
				if (getMoney() >= m) {// ���࿡�� ����� �� �ִ� ���
					Thread.sleep(300);
					n = drawMoney(m);
					msg = "���� ����...";
				} else {// ����� �� ���� ���
					n = 0;
					msg = "���� ����.. �ܾ��� �����մϴ�.";
				}
			}

			System.out.println(msg + ", ����ݾ�: " + n + ", �ܰ�: " + getMoney());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMoney() {
		return bank;
	}

	private int drawMoney(int m) {
		bank -= m;
		return bank;
	}
}