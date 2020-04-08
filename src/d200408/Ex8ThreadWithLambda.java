package d200408;

public class Ex8ThreadWithLambda {
	public static void main(String[] args) {
		Runnable r = () -> {//Lambda ���ٽ� �Լ� 
			//���ٽ�: JDK8���� ������ �Լ� �������� �͸��Լ� ���� 
			//javascript�� arrow function�� ����ϰ� ���屸��!
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
		System.out.println("main() ����");
	}
}
