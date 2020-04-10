package d200410;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/*
 ������ ���������� �̷��� ������� �����ڰ� �Է��� ������̼��� �Ӽ����� �м��Ͽ� �����Ѵ�.
 ������ ���� ���� ������̼Ǻ� ���ҿ� ���ؼ� �����ϴ� ���� �ֵ� ��ǥ
 * */

public class Ex3AnnotationCreate {
	public static void main(String[] args) {
		Method[] mm = User3.class.getDeclaredMethods();
		for (Method m : mm) {
			// User3Ŭ�������� MyAnno3 ������̼��� �����ϴ��� Ȯ��
			if (m.isAnnotationPresent(MyAnno3.class)) {
				// MyAnno3 ��ü ���
				MyAnno3 ma = m.getAnnotation(MyAnno3.class);
				// �޼��� �̸�
				System.out.print("[" + m.getName() + "] �� ");
				// ���м�
				for (int i = 0; i < ma.number(); i++) {
					System.out.print(ma.value());
				}
				System.out.println(" ... " + ma.number());
				// �޼��� ȣ��
				try {
					m.invoke(new User3());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

//������̼� �����
@Target({ ElementType.METHOD }) // {} ���� ���� ������ �� ����.
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno3 {
	String value() default "-";

	int number() default 15;
}

class User3 {
	@MyAnno3
	public void sub1() {
		System.out.println("���� 1...");
	}

	@MyAnno3("*")
	public void sub2() {
		System.out.println("���� 2...");
	}

	@MyAnno3(value = "#", number = 20)
	public void sub3() {
		System.out.println("���� 3...");
	}
}
