package d200409;

public class Ex1ThreadSynchronizedNotify {
	public static void main(String[] args) {
		MyThread atm = new MyThread();
		Thread t1 = new Thread(atm,"atm1");
		Thread t2 = new Thread(atm,"atm2");
		
		t1.start();
		t2.start();
		
		
	}
}

class MyThread implements Runnable {
	private int money = 10000;

	public int getMoney() {
		return money;
	}

	public void drawMoney(int m) {
		System.out.print(Thread.currentThread().getName() + " �� ");

		if (getMoney() >= m) {
			money -= m;
			System.out.println("�ܾ�: " + getMoney() + "��");
		} else {
			System.out.println("�ܾ� ����... /_ \\");
		}
	}

	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				if (getMoney() <= 0) {
					//#2. deadlock�� �ɸ��� �ʵ��� ������ Thread�� ����� �ڵ�
					this.notifyAll();//��� ������ Thread�� ��� �����...
					break;
				}
				drawMoney(1000);
				if (getMoney() % 2000 == 0) {
					try {
						//#1. deadlock�ɸ��� �ڵ�... ���� ����� ���� �����尡 ���� �� ����.
						//����� �ʴ� Thread�� ������ ���� ���¿��� ����� ����.
						//���ѷ����� ���� �Ͱ� ���������� ����...
						this.wait();// 2000�� ����� �� wait.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					this.notify(); // wait�� �����.
				}
			}
		}
	}

}