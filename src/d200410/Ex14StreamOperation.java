package d200410;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Ex14StreamOperation {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("X", "A", "T", "A", "G", "T", "Y", "D", "K");
		Stream<String> stream = list.stream();
		// 중복제거, 3개만, 정렬 중간연산을 지정
		// 최종 연산이 수행되기 전에 중간 연산은 수행이 되지 않는다. (=> 지연 연산)
		stream.distinct().limit(3).sorted().forEach(System.out::print);// 최종연산은 forEach

		// 최종 연산은 단 한 번만 사용할 수 있다.
		// 주석 풀고 실행: stream.forEach(System.out::println);// IllegalStateException
		// 최종연산을 이미 수행하였으므로 데이터가 사라졌다.
		// stream has already been operated upon or closed.
	}
}
