package d200408;

public class Ex7ThreadRunnableAnonymousClassImplements {
	// �͸� Ŭ������ Runnable ����
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			//������� �ʿ������� ��ȸ������ �� ���� ����� ���� �͸�Ŭ���� ���� ����� �̿��Ͽ� �����带 ������ �� �ִ�.
			@Override
			public void run() {
				try {
					for (int i = 1; i <= 20; i++) {
						System.out.println(Thread.currentThread() + ": " + i);
						Thread.sleep(500);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Thread t = new Thread(r);
		t.start();
		System.out.println("main() ����");
	}
}
