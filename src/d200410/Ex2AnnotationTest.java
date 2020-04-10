package d200410;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Ex2AnnotationTest {
	@User2 // 아무 역할 없는 어노테이션
	public void execute() {
		System.out.println("어노테이션 사용 테스트");
	}

	public static void main(String[] args) {
		Ex2AnnotationTest e = new Ex2AnnotationTest();
		e.execute();
	}
}

//어노테이션을 실제로 만드는 일은 드물지만 스프링 프레임워크에서 사용되는 방식이므로
//원리를 이해하도록 한다.
//BuiltIn Annotation을 삽입해야 한다.
@Target(ElementType.METHOD)//메서드에 붙일 거에요
@Retention(RetentionPolicy.RUNTIME)//런타임 실행 중인 동안 어노테이션 정보가 보관되도록 하겠습니다.
@interface User2 {// 끝...
}