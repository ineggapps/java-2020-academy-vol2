package d200410;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Ex15StreamFinalOperation {
	public static void main(String[] args) {
		// ��������
		IntStream s1 = IntStream.of(30, 90, 70, 10);
		System.out.println(s1.count());// ����

		IntStream s2 = IntStream.of(30, 90, 70, 10);
		System.out.println(s2.max().getAsInt());// �ִ�

		IntStream s3 = IntStream.of(30, 90, 70, 10);
		System.out.println(s3.sum());// �Ѱ�

		DoubleStream s4 = DoubleStream.of(40.6, 70.5, 80.7);
		System.out.println(s4.average().getAsDouble());// ���

		IntStream s5 = IntStream.of(30, 90, 70, 10);
		System.out.println(s5.anyMatch(n -> n > 80)); // �ϳ��� 80 �ʰ��ϴ� ���� "�ϳ���" ������ true
		
		IntStream s6 = IntStream.of(30, 90, 70, 10);
		System.out.println(s6.allMatch(n -> n > 80)); // ��Ұ� ��� 80 �ʰ��ϴ� ���̸� true, �ϳ��� �ƴϸ� false
	}
}
