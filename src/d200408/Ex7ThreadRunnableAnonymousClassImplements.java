package d200408;

public class Ex7ThreadRunnableAnonymousClassImplements {
	// 익명 클래스로 Runnable 구현
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			//스레드는 필요하지만 일회성으로 한 번만 사용할 때는 익명클래스 구현 방식을 이용하여 스레드를 실행할 수 있다.
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
		System.out.println("main() 종료");
	}
}
