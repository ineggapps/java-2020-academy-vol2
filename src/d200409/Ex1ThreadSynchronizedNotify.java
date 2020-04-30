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
		System.out.print(Thread.currentThread().getName() + " ▶ ");

		if (getMoney() >= m) {
			money -= m;
			System.out.println("잔액: " + getMoney() + "원");
		} else {
			System.out.println("잔액 부족... /_ \\");
		}
	}

	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				if (getMoney() <= 0) {
					//#2. deadlock에 걸리지 않도록 나머지 Thread를 깨우는 코드
					this.notifyAll();//대기 상태인 Thread를 모두 깨운다...
					break;
				}
				drawMoney(1000);
				if (getMoney() % 2000 == 0) {
					try {
						//#1. deadlock걸리는 코드... 아직 깨어나지 않은 스레드가 생길 수 있음.
						//깨어나지 않는 Thread는 영원히 대기된 상태에서 깨어나지 않음.
						//무한루프에 빠진 것과 마찬가지인 상태...
						this.wait();// 2000의 배수일 때 wait.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					this.notify(); // wait를 깨운다.
				}
			}
		}
	}

}