package d200408;

public class Ex14ThreadWithoutSynchronization {
	public static void main(String[] args) {
		DemoThread1 r = new DemoThread1();

		// Runnable을 구현한 클래스객체를 1개를 생성하여 두 개의 스레드가 공유함.
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);

		t1.start();
		t2.start();
		
		System.out.println("main() 종료");
	}
}

class DemoThread1 implements Runnable {
	private int bank = 1000;// 현재 은행의 통장 잔고에 1000원이 저축되어 있다고 생각

	@Override
	public void run() {
		int m = 600;// 인출할 금액
		int n;
		String msg;
		try {
			if (getMoney() >= m) {// 은행에서 출금할 수 있는 경우
				Thread.sleep(300);
				n = drawMoney(m);
				msg = "인출 성공...";
			} else {// 출금할 수 없는 경우
				n = 0;
				msg = "인출 실패.. 잔액이 부족합니다.";
			}

			//BUT... 두 스레드가 동시에 같은 자원을 접근하면서 연산이 제대로 되지 않는다는 단점이 있다.
			//그래서 동기화가 필요한 것임!
			
			System.out.println(msg + ", 인출금액: " + n + ", 잔고: " + getMoney());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMoney() {
		return bank;
	}

	private int drawMoney(int m) {
		bank -= m;
		return bank;
	}
}