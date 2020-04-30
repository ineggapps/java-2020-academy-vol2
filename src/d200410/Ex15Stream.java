package d200410;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex15Stream {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("A", "B", "C");
		Stream<String> s1 = list.stream();// 리스트에서 스트림을 생성한다.
		s1.forEach(System.out::print);
		System.out.println();

		// 배열에서 스트림 생성
		Stream<String> s2 = Stream.of(new String[] { "A", "B", "C" });
		s2.forEach(System.out::print);
		System.out.println();

		// 특정 범위
		IntStream s3 = IntStream.range(1, 9);// 12345678
//		IntStream.range(startInclusive, endExclusive)
		s3.forEach(System.out::print);
		System.out.println();

		// 특정 범위
		IntStream s4 = IntStream.rangeClosed(1, 9);// 123456789 (endInclud)
//		IntStream.rangeClosed(startInclusive, endInclusive)
		s4.forEach(System.out::print);
		System.out.println();

		// Random클래스 이용
		IntStream s5 = new Random().ints(); // << 무한 개의 무작위 정수 생성
		s5.limit(5).forEach(System.out::println);
		System.out.println();

		IntStream s6 = new Random().ints(5); // << 5개의 무작위 정수 생성
		s6.limit(5).forEach(System.out::println);
		System.out.println();

		IntStream s7 = new Random().ints(5, 1, 10); // 1에서 9까지의 수 중에서 무작위로 5개 반환 (스트림으로)
//		new Random().ints(streamSize, randomNumberOrigin, randomNumberBound)
		s7.forEach(System.out::print);
		System.out.println();
		
		Stream<Integer> s8 = Stream.iterate(0, n->n+2);//무한루프로 빠지는 함수임
		s8.limit(4).forEach(System.out::print);//limit했으니까 괜찮아!

	}
}
