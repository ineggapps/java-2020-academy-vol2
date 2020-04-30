package d200408;

public class Ex8ThreadWithLambda {
	public static void main(String[] args) {
		Runnable r = () -> {//Lambda 람다식 함수 
			//람다식: JDK8부터 가능한 함수 지향적인 익명함수 구현 
			//javascript의 arrow function과 비슷하게 생겼구나!
			try {
				for (int i = 1; i <= 20; i++) {
					System.out.println(Thread.currentThread() + ": " + i);
					Thread.sleep(500);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Thread t = new Thread(r);
		t.start();
		System.out.println("main() 종료");
	}
}
