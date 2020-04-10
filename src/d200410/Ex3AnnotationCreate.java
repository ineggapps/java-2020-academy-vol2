package d200410;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/*
 스프링 과정에서는 이러한 방식으로 개발자가 입력한 어노테이션의 속성들을 분석하여 동작한다.
 스프링 과정 들어가면 어노테이션별 역할에 대해서 인지하는 것이 주된 목표
 * */

public class Ex3AnnotationCreate {
	public static void main(String[] args) {
		Method[] mm = User3.class.getDeclaredMethods();
		for (Method m : mm) {
			// User3클래스에서 MyAnno3 어노테이션이 존재하는지 확인
			if (m.isAnnotationPresent(MyAnno3.class)) {
				// MyAnno3 객체 얻기
				MyAnno3 ma = m.getAnnotation(MyAnno3.class);
				// 메서드 이름
				System.out.print("[" + m.getName() + "] ▶ ");
				// 구분선
				for (int i = 0; i < ma.number(); i++) {
					System.out.print(ma.value());
				}
				System.out.println(" ... " + ma.number());
				// 메서드 호출
				try {
					m.invoke(new User3());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

//어노테이션 만들기
@Target({ ElementType.METHOD }) // {} 여러 개를 지정할 수 있음.
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno3 {
	String value() default "-";

	int number() default 15;
}

class User3 {
	@MyAnno3
	public void sub1() {
		System.out.println("실행 1...");
	}

	@MyAnno3("*")
	public void sub2() {
		System.out.println("실행 2...");
	}

	@MyAnno3(value = "#", number = 20)
	public void sub3() {
		System.out.println("실행 3...");
	}
}
