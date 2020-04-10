package d200410;

public class Ex9Lambda {
	public static void main(String[] args) {
		Sample5 ss = new Sample5();
		ss.using();
	}
}

@FunctionalInterface
interface Demo5 {
	public void fun();
}

class Sample5 {
	int x = 10;

	class Inner {
		int y = 20;

		public void sub() {
			int z = 30;
			int x = 11;
			Demo5 u = () -> {// ���� �ȿ����� this�� ���� Ŭ���� �ڽ��� ����Ű�� �� �ƴϴ�.
				// ���ٿ��� this�� �͸�ü�� ����Ű�� �ʰ� ���ٽ��� ������ Ŭ������ InnerŬ������ ����Ų��. /_ \
				System.out.println(this);
				System.out.println(this.y); // ���ٽĿ��� ���� Ŭ���� �ڽ��� �ƴ϶� InnerŬ������� ���� ����..!!
				System.out.println(x);// ���� ����� �������� x������ ã�Ƽ� ����ϰ���?
				System.out.println(Sample5.this.x); // Sample5Ŭ������ ��ü�� �ʵ带 ����Ŵ
				System.out.println(z);
			};
//			z=50;//���ٽĿ��� ������ �߻��Ѵ�. �ֳ��ϸ� ���ٽĿ� ���� ���� ������ final�Ӽ��� ������ ������ ������ �� ����.
			u.fun();
		}
	}

	public void using() {
		Inner ob = new Inner();
		ob.sub();
	}
}
