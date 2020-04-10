package d200410;

import java.util.function.Supplier;

public class Ex12LambdaInConstructor {
	public static void main(String[] args) {
		// Supplier�� �Ű������� ���� ��ȯ���� ����. �̹� ���ǵ� ������ �������̽� (���ø� ������ ����)
		Supplier<Object> o1 = () -> new Object();
		/*
		 public interface Supplier<T> {
	    	T get();
		}
		 * */
		Object ob1 = o1.get();
		System.out.println(ob1);

		////////// �� �����ϰ�!!
		// ������ �޼��� ����
		Supplier<Object> o2 = Object::new;//���� �ҽ� �ڵ带 �� �����ϰ� �ٿ���!?
		Object ob2 = o2.get();
		System.out.println(ob2);
	}
}
