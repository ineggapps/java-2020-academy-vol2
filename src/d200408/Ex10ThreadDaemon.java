package d200408;

public class Ex10ThreadDaemon {
	public static void main(String[] args) {
		MyThread6 t1 = new MyThread6();
		MyThread6 t2 = new MyThread6();
		MyThread6 t3 = new MyThread6();

		// 독립 스레드
		// 데몬 스레드: 다른 스레드에 도움을 주는 스레드
		// 도움을 받는 스레드가 종료되면 데몬 스레드도 종료
		t1.setDaemon(true);
		t2.setDaemon(true);
		t3.setDaemon(true);

		t1.start();
		t2.start();
		t3.start();

		// join하지 않을 경우에는 최대 1번만 실행될 것임
		try {
			t1.join();// 스레드가 종료될 때까지 대기하기
//			t2.join();
//			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main() 종료");
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
