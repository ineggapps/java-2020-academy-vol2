package d200408;

public class Ex10ThreadDaemon {
	public static void main(String[] args) {
		MyThread6 t1 = new MyThread6();
		MyThread6 t2 = new MyThread6();
		MyThread6 t3 = new MyThread6();

		// ���� ������
		// ���� ������: �ٸ� �����忡 ������ �ִ� ������
		// ������ �޴� �����尡 ����Ǹ� ���� �����嵵 ����
		t1.setDaemon(true);
		t2.setDaemon(true);
		t3.setDaemon(true);

		t1.start();
		t2.start();
		t3.start();

		// join���� ���� ��쿡�� �ִ� 1���� ����� ����
		try {
			t1.join();// �����尡 ����� ������ ����ϱ�
//			t2.join();
//			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main() ����");
	}
}

class MyThread6 extends Thread {

	@Override
	public void run() {
		for (int i = 1; i <= 20; i++) {
			System.out.println(getName() + ": " + i);
			try {
				sleep((int) (Math.random() * 500));
			} catch (Exception e) {
			}
		}
	}

}
