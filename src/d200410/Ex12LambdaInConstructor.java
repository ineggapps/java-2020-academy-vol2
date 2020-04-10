package d200410;

import java.util.function.Supplier;

public class Ex12LambdaInConstructor {
	public static void main(String[] args) {
		// Supplier은 매개변수가 없고 반환값이 있음. 이미 정의된 람다형 인터페이스 (템플릿 정도로 생각)
		Supplier<Object> o1 = () -> new Object();
		/*
		 public interface Supplier<T> {
	    	T get();
		}
		 * */
		Object ob1 = o1.get();
		System.out.println(ob1);

		////////// 더 간단하게!!
		// 생성자 메서드 참조
		Supplier<Object> o2 = Object::new;//위의 소스 코드를 더 간단하게 줄였네!?
		Object ob2 = o2.get();
		System.out.println(ob2);
	}
}
