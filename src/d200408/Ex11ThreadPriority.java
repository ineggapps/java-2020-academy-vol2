package d200408;

public class Ex11ThreadPriority {
	public static void main(String[] args) {
		MyThread8 t = new MyThread8();
		t.start();

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}

//		t.stop();//�����带 ������ �����Ŵ (InterruptedException ���ܰ� �߻����� �ʴ´�.)
		//stop() �޼���� ����� �������� �ʴ´�.
		
		// �����尡 sleep(), join(), wait() �����̸� InterruptedException ���ܰ� �߻��Ѵ�.
		t.interrupt();

		System.out.println("main() ����");

	}
}

class MyThread8 extends Thread {

	@Override
	public void run() {
		try {
			for (int i = 1; i <= 20; i++) {
				System.out.println(getName() + ": " + i);
				sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("InterruptedException �߻�");
		}
		System.out.println("Thread end...");
	}

}