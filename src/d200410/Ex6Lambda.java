package d200410;

public class Ex6Lambda {
	public static void main(String[] args) {
		//�Ű������� ����, ��ȯ���� void�� ���ٽ�...
		Demo2 ob = () -> {//�ҽ��� �̷��� �������� �� �ִٴ�!
			System.out.println("�ȳ� ���پ�!");
			//������ ���ٽ��� ����� �� �ִ� ������ �������̴�.
			// �������̽����� �޼���� 1���� �����ؾ� �Ѵ�.
		};
		ob.write();
	}
}

interface Demo2 {
	public void write();
}
