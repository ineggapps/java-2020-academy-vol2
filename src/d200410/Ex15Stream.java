package d200410;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex15Stream {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("A", "B", "C");
		Stream<String> s1 = list.stream();// ����Ʈ���� ��Ʈ���� �����Ѵ�.
		s1.forEach(System.out::print);
		System.out.println();

		// �迭���� ��Ʈ�� ����
		Stream<String> s2 = Stream.of(new String[] { "A", "B", "C" });
		s2.forEach(System.out::print);
		System.out.println();

		// Ư�� ����
		IntStream s3 = IntStream.range(1, 9);// 12345678
//		IntStream.range(startInclusive, endExclusive)
		s3.forEach(System.out::print);
		System.out.println();

		// Ư�� ����
		IntStream s4 = IntStream.rangeClosed(1, 9);// 123456789 (endInclud)
//		IntStream.rangeClosed(startInclusive, endInclusive)
		s4.forEach(System.out::print);
		System.out.println();

		// RandomŬ���� �̿�
		IntStream s5 = new Random().ints(); // << ���� ���� ������ ���� ����
		s5.limit(5).forEach(System.out::println);
		System.out.println();

		IntStream s6 = new Random().ints(5); // << 5���� ������ ���� ����
		s6.limit(5).forEach(System.out::println);
		System.out.println();

		IntStream s7 = new Random().ints(5, 1, 10); // 1���� 9������ �� �߿��� �������� 5�� ��ȯ (��Ʈ������)
//		new Random().ints(streamSize, randomNumberOrigin, randomNumberBound)
		s7.forEach(System.out::print);
		System.out.println();
		
		Stream<Integer> s8 = Stream.iterate(0, n->n+2);//���ѷ����� ������ �Լ���
		s8.limit(4).forEach(System.out::print);//limit�����ϱ� ������!

	}
}
