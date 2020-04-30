package d200408;

import java.util.Calendar;

public class Ex9Thread {
	public static void main(String[] args) {
		Thread t = new Thread(new MyThread5());
		t.start();
		System.out.println("main() 종료");
	}
}

class MyThread5 implements Runnable {

	@Override
	public void run() {
		while (true) {
			Calendar cal = Calendar.getInstance();
			System.out.printf("%tF %tA %tT\n", cal, cal, cal);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}

}
