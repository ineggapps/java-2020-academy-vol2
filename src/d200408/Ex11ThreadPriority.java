package d200408;

public class Ex11ThreadPriority {
	public static void main(String[] args) {
		MyThread8 t = new MyThread8();
		t.start();

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}

//		t.stop();//스레드를 완전히 종료시킴 (InterruptedException 예외가 발생하지 않는다.)
		//stop() 메서드는 사용을 권장하지 않는다.
		
		// 스레드가 sleep(), join(), wait() 상태이면 InterruptedException 예외가 발생한다.
		t.interrupt();

		System.out.println("main() 종료");

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
			System.out.println("InterruptedException 발생");
		}
		System.out.println("Thread end...");
	}

}