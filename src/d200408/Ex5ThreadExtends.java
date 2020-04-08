package d200408;

public class Ex5ThreadExtends {
	public static void main(String[] args) {
		MyThread1 t = new MyThread1();
		t.start();
		System.out.println("main ����...");
	}
}

//main()�� ���α׷� �������̸� main()�� ����ȴٰ� �ؼ� ���α׷��� ��� ����Ǵ� ���� �ƴϴ�.
//��� ���� �����尡 ����Ǿ�߸� ���α׷��� ����ȴ�.

//Thread �ۼ� ��� 
//#1: ThreadŬ������ ��ӹ޾� run()�޼��带 �������̵��Ѵ�.
//#2: �ۼ��ϰ��� �ϴ� Ŭ������ Runnable �������̽��� �����Ͽ� run()�޼��带 �������̵��Ѵ�.

class MyThread1 extends Thread {//#1
	@Override
	public void run() {
		int i = 0;
		try {
			while (i < 20) {
				i++;
				System.out.println(getName() + ":" + i);
				sleep(90);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}