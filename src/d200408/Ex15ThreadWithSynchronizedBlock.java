package d200408;

public class Ex15ThreadWithSynchronizedBlock {
	public static void main(String[] args) {
		DemoThread2 r = new DemoThread2();

		// Runnable을 구현한 클래스객체를 1개를 생성하여 두 개의 스레드가 공유함.
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);

		t1.start();
		t2.start();

		System.out.println("main() 종료");
	}
}

class DemoThread2 implements Runnable {
	private int bank = 1000;// 현재 은행의 통장 잔고에 1000원이 저축되어 있다고 생각

	@Override
	public void run() {
		int m = 600;// 인출할 금액
		int n = 0;
		String msg = null;
		try {
			//동기화 블록으로 동시에 여러 개의 스레드가 접근하여 연산을 망치지 않도록 감싼다. => 전산용어로 상호배제라고 함!
			synchronized (this) { 
				if (getMoney() >= m) {// 은행에서 출금할 수 있는 경우
					Thread.sleep(300);
					n = drawMoney(m);
					msg = "인출 성공...";
				} else {// 출금할 수 없는 경우
					n = 0;
					msg = "인출 실패.. 잔액이 부족합니다.";
				}
			}

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