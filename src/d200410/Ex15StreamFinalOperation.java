package d200410;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Ex15StreamFinalOperation {
	public static void main(String[] args) {
		// 최종연산
		IntStream s1 = IntStream.of(30, 90, 70, 10);
		System.out.println(s1.count());// 개수

		IntStream s2 = IntStream.of(30, 90, 70, 10);
		System.out.println(s2.max().getAsInt());// 최댓값

		IntStream s3 = IntStream.of(30, 90, 70, 10);
		System.out.println(s3.sum());// 총계

		DoubleStream s4 = DoubleStream.of(40.6, 70.5, 80.7);
		System.out.println(s4.average().getAsDouble());// 평균

		IntStream s5 = IntStream.of(30, 90, 70, 10);
		System.out.println(s5.anyMatch(n -> n > 80)); // 하나라도 80 초과하는 수가 "하나라도" 있으면 true
		
		IntStream s6 = IntStream.of(30, 90, 70, 10);
		System.out.println(s6.allMatch(n -> n > 80)); // 요소가 모두 80 초과하는 수이면 true, 하나라도 아니면 false
	}
}
