package d200408;

public class Ex12ThreadInterrupt {
	public static void main(String[] args) {
		MyThread7 t1 = new MyThread7();
		MyThread7 t2 = new MyThread7();
		MyThread7 t3 = new MyThread7();

		t1.setPriority(Thread.MAX_PRIORITY); // t1�������� �켱������ �ְ�� ������. ������� 10��.
		t3.setPriority(Thread.MIN_PRIORITY);// t3�������� �켱������ ������ ������. ������� 1�̴�.
		
		t1.start();
		t2.start();// �⺻ �켱������ 5�̴�.
		t3.start();

		System.out.println("main() ����");

	}
}

class MyThread7 extends Thread {

	@Override
	public void run() {
		for (int i = 1; i <= 20; i++) {
			try {
				System.out.println(getName() + ": " + i);
				sleep(500);
			} catch (Exception e) {
			}
		}
	}

}