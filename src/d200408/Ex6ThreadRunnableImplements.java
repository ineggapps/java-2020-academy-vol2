package d200408;

public class Ex6ThreadRunnableImplements {
	public static void main(String[] args) {
		Thread t = new Thread(new MyThread2());
		t.start();
		try {
			for(int i=1;i<=20;i++) {
				System.out.println(Thread.currentThread().getName()+": "+i);
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("main() 종료");
	}
}

//main()은 프로그램 진입점이며 main()이 종료된다고 해서 프로그램이 모두 종료되는 것은 아니다.
//모든 독립 스레드가 종료되어야만 프로그램이 종료된다.

//Thread 작성 방법 
//#1: Thread클래스를 상속받아 run()메서드를 오버라이딩한다.
//#2: 작성하고자 하는 클래스를 Runnable 인터페이스를 구현하여 run()메서드를 오버라이딩한다.


class MyThread2 implements Runnable {//#2

	@Override
	public void run() {
		int i = 0;
		try {
			while (i < 20) {
				i++;
				System.out.println(Thread.currentThread().getName() + ": " + i);
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
