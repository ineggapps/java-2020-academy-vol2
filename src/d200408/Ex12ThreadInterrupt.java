package d200408;

public class Ex12ThreadInterrupt {
	public static void main(String[] args) {
		MyThread7 t1 = new MyThread7();
		MyThread7 t2 = new MyThread7();
		MyThread7 t3 = new MyThread7();

		t1.setPriority(Thread.MAX_PRIORITY); // t1스레드의 우선순위를 최고로 지정함. 상숫값은 10임.
		t3.setPriority(Thread.MIN_PRIORITY);// t3스레드의 우선순위를 최저로 지정함. 상숫값은 1이다.
		
		t1.start();
		t2.start();// 기본 우선순위는 5이다.
		t3.start();

		System.out.println("main() 종료");

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