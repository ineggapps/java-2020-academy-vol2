package d200410;

//���ٿ��� �Ű����� �����ϴ� ��� �˾ƺ���
public class Ex8LambdaParameter {
	public static void main(String[] args) {
		Demo4 u = (a, b) -> {
			int c = a + b;
			return a + b;
		}; 
		Demo4 v = (a, b) -> a + b; // �� �� �ȿ��� ��ȯ���� �������Ǵ� ��� return�� ����
		int x = u.add(5, 10);
		System.out.println(x);
		int z = v.add(10, 1);
		System.out.println(z);
	}
}

@FunctionalInterface
interface Demo4 {
	public int add(int a, int b);
}