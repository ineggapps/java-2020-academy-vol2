package d200410;

//���� ������ ����ϴ� �پ��� ��...
public class Ex7Lambda {
	public static void main(String[] args) {
		// �Ű������� 1���̰� ��ȯ���� void�� ���ٽ�
		Demo3 u = (int a) -> {
			int n = a + 10;
			System.out.println(n);
		};
		u.write(1);
		////////////////////////////////////////////
		Demo3 v = (a) -> {
			System.out.println("�Ű����� a�� �� ���̰� ��ȯ���� void�� ���� => " + a);
		};
		v.write(1);
		// �Ű�������(int)�� �����Ͽ� ������ �� �ְ�, �Ű������� 1���� ��쿡�� ��ȣ�� ������ �� �ִ�.
		Demo3 d = a -> {
			System.out.println("�Ű����� a�� �� ���̰� ��ȯ���� void�� ���� => " + a);
		};
		d.write(12);
		////////////////////////////////////////////
		Demo3 z = a -> System.out.println("�� �� �ȿ� ������ ��� {}�� ������ �����ϴ�");
		z.write(20);
	}
}

@FunctionalInterface // ���� ���� �� �ž�!
interface Demo3 {
	public void write(int a);
//	public void write(int a, int b);//������̼��� ���̸� �ϳ��� �޼��常 �����ؾ� �Ѵٰ� �˷��ش�.
}