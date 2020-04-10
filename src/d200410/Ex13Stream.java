package d200410;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Ex13Stream {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("�ڹ�", "oracle", "��");
		// ���ͷ����� ���
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			String s = it.next();
			System.out.println(s);
		}
		System.out.println("----------------------------");
		// ��Ʈ�� ��� (�ٸ� ��Ʈ���� �б������̰� �� ���� ����� �� ����)
		Stream<String> stream = list.stream();
		// forEach�� Consumer�������̽��� �޼��带 �����ؾ� ��.
		
		// #1. ���ٽ�
		stream.forEach(s -> System.out.println(s));
		
		// #2.�޼��� ���� ǥ���
//		stream.forEach(System.out::println);
		
		// #3. �������� ������?
//		stream.forEach(new Consumer<String>() {
//			@Override
//			public void accept(String t) {
//				System.out.println(t);
//			}
//		});
	}
}
