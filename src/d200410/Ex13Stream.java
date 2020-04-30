package d200410;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Ex13Stream {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("자바", "oracle", "웹");
		// 이터레이터 사용
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String s = it.next();
			System.out.println(s);
		}
		System.out.println("----------------------------");
		// 스트림 사용 (다만 스트림은 읽기전용이고 한 번만 사용할 수 있음)
		Stream<String> stream = list.stream();
		// forEach는 Consumer인터페이스의 메서드를 구현해야 함.
		
		// #1. 람다식
		stream.forEach(s -> System.out.println(s));
		
		// #2.메서드 참조 표기식
//		stream.forEach(System.out::println);
		
		// #3. 생략하지 않으면?
//		stream.forEach(new Consumer<String>() {
//			@Override
//			public void accept(String t) {
//				System.out.println(t);
//			}
//		});
	}
}
