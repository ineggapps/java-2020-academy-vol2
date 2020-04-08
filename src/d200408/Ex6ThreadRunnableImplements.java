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
		System.out.println("main() ����");
	}
}

//main()�� ���α׷� �������̸� main()�� ����ȴٰ� �ؼ� ���α׷��� ��� ����Ǵ� ���� �ƴϴ�.
//��� ���� �����尡 ����Ǿ�߸� ���α׷��� ����ȴ�.

//Thread �ۼ� ��� 
//#1: ThreadŬ������ ��ӹ޾� run()�޼��带 �������̵��Ѵ�.
//#2: �ۼ��ϰ��� �ϴ� Ŭ������ Runnable �������̽��� �����Ͽ� run()�޼��带 �������̵��Ѵ�.


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
