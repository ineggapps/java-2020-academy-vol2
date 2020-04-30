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
			 MyContainer에서 어노테이션의 value속성을 이용하여 String의 변숫값만 지정해주었다.
			 그러므로 Integer형인 price에는 아무 값도 들어있지 않음.
			 어노테이션을 어떻게 사용하는지 정도만 이해하면 됨.
			 * */
			System.out.println(user.getSubject1());//자바
			System.out.println(user.getSubject2());//스프링
			System.out.println(user.getPrice());//null
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno4 {
	String value() default "자바";
}

class User4 {
	@MyAnno4
	private String subject1;

	@MyAnno4("스프링")
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
				f.setAccessible(true);// private 필드나 메서드 접근이 가능하도록 설정
				f.set(t, ma.value());
			}
		}
		return t;
	}

	@SuppressWarnings("unchecked") //제네릭 캐스팅 경고 무시
	public <T> T get(Class<?> cls) throws InstantiationException, IllegalAccessException {
		T t = (T) cls.newInstance(); //제네릭은 캐스팅을 못 하는데 불가피한 경우가 발생할 수 있음.
		t = invokeAnn(t);
		return t;
	}
}