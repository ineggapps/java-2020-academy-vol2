package d200410;

public class Ex5BeforeLambda {
	public static void main(String[] args) {
		Demo1 ob1 = new DemoImpl1();
		ob1.write();
		
		Demo1 ob2 = new Demo1() {
			@Override
			public void write() {
				System.out.println("익명으로 구현..!!");
			}
		};
		ob2.write();
	}
}

interface Demo1 {
	public void write();
}

class DemoImpl1 implements Demo1 {
	@Override
	public void write() {
		System.out.println("테스트...");
	}
}