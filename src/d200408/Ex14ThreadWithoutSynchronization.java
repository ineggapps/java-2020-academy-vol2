package d200408;

public class Ex14ThreadWithoutSynchronization {
	public static void main(String[] args) {
		DemoThread1 r = new DemoThread1();

		// Runnable�� ������ Ŭ������ü�� 1���� �����Ͽ� �� ���� �����尡 ������.
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);

		t1.start();
		t2.start();
		
		System.out.println("main() ����");
	}
}

class DemoThread1 implements Runnable {
	private int bank = 1000;// ���� ������ ���� �ܰ� 1000���� ����Ǿ� �ִٰ� ����

	@Override
	public void run() {
		int m = 600;// ������ �ݾ�
		int n;
		String msg;
		try {
			if (getMoney() >= m) {// ���࿡�� ����� �� �ִ� ���
				Thread.sleep(300);
				n = drawMoney(m);
				msg = "���� ����...";
			} else {// ����� �� ���� ���
				n = 0;
				msg = "���� ����.. �ܾ��� �����մϴ�.";
			}

			//BUT... �� �����尡 ���ÿ� ���� �ڿ��� �����ϸ鼭 ������ ����� ���� �ʴ´ٴ� ������ �ִ�.
			//�׷��� ����ȭ�� �ʿ��� ����!
			
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