package d200410;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Ex14StreamOperation {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("X", "A", "T", "A", "G", "T", "Y", "D", "K");
		Stream<String> stream = list.stream();
		// �ߺ�����, 3����, ���� �߰������� ����
		// ���� ������ ����Ǳ� ���� �߰� ������ ������ ���� �ʴ´�. (=> ���� ����)
		stream.distinct().limit(3).sorted().forEach(System.out::print);// ���������� forEach

		// ���� ������ �� �� ���� ����� �� �ִ�.
		// �ּ� Ǯ�� ����: stream.forEach(System.out::println);// IllegalStateException
		// ���������� �̹� �����Ͽ����Ƿ� �����Ͱ� �������.
		// stream has already been operated upon or closed.
	}
}
