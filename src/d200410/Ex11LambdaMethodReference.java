package d200410;

public class Ex11LambdaMethodReference {
	public static void main(String[] args) {
		String s = String.valueOf(1);
		System.out.println(s);

		Conversion cc = (n) -> String.valueOf(n); // ���ٽ��� �̿��ϴϱ� �������̽� ������ �� �ٷ� Ȯ �پ���!
		System.out.println(cc.convert(50));

		Conversion cc2 = String::valueOf; // ���ٽ��� �̿������� ������ �ϳ��� �Լ��� �ҷ��� ��ȯ�ߴµ�.. �׷� �̷��� �� �ٿ��� �� �� ����.
		// ������ �Ű����������� ������ �����ϳ�? ��ŷ..
		System.out.println(cc2.convert(1010));

		String s1 = convert(100, (n) -> String.valueOf(n)); // ���ٽ��� �̿��ϴϱ� �͸� Ŭ���� ���� ����� ����������!
		System.out.println(s1);

		String s2 = convert(200, String::valueOf); // ���� �����ϰ� ���� ���!!!!
		System.out.println(s2);

		// ������ �ڵ尡 ��ƴٸ�. ������ ����.
		String s3 = convert(300, new Conversion() {
			@Override
			public String convert(Integer number) {
				return String.valueOf(number);
			}
		});
		System.out.println(s3);
	}

	public static String convert(Integer number, Conversion f) {
		return f.convert(number);
	}
}

interface Conversion {
	public String convert(Integer number);
}