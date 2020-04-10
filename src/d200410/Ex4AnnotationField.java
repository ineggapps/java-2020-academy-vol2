package d200410;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class Ex4AnnotationField {
	public static void main(String[] args) {
		MyContainer mc = new MyContainer();
		try {
			User4 user = mc.get(User4.class);
			/*
			 MyContainer���� ������̼��� value�Ӽ��� �̿��Ͽ� String�� �������� �������־���.
			 �׷��Ƿ� Integer���� price���� �ƹ� ���� ������� ����.
			 ������̼��� ��� ����ϴ��� ������ �����ϸ� ��.
			 * */
			System.out.println(user.getSubject1());//�ڹ�
			System.out.println(user.getSubject2());//������
			System.out.println(user.getPrice());//null
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno4 {
	String value() default "�ڹ�";
}

class User4 {
	@MyAnno4
	private String subject1;

	@MyAnno4("������")
	private String subject2;

	@MyAnno4
	private Integer price;

	public String getSubject1() {
		return subject1;
	}

	public String getSubject2() {
		return subject2;
	}

	public Integer getPrice() {
		return price;
	}

}

class MyContainer {
	private <T> T invokeAnn(T t) throws IllegalArgumentException, IllegalAccessException {
		Field[] ff = t.getClass().getDeclaredFields();
		for (Field f : ff) {
			MyAnno4 ma = f.getAnnotation(MyAnno4.class);
			if (ma != null && f.getType() == String.class) {
				f.setAccessible(true);// private �ʵ峪 �޼��� ������ �����ϵ��� ����
				f.set(t, ma.value());
			}
		}
		return t;
	}

	@SuppressWarnings("unchecked") //���׸� ĳ���� ��� ����
	public <T> T get(Class<?> cls) throws InstantiationException, IllegalAccessException {
		T t = (T) cls.newInstance(); //���׸��� ĳ������ �� �ϴµ� �Ұ����� ��찡 �߻��� �� ����.
		t = invokeAnn(t);
		return t;
	}
}