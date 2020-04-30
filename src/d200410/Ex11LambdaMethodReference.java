package d200410;

public class Ex11LambdaMethodReference {
	public static void main(String[] args) {
		String s = String.valueOf(1);
		System.out.println(s);

		Conversion cc = (n) -> String.valueOf(n); // 람다식을 이용하니까 인터페이스 구현이 한 줄로 확 줄었음!
		System.out.println(cc.convert(50));

		Conversion cc2 = String::valueOf; // 람다식을 이용했지만 어차피 하나의 함수만 불러서 반환했는데.. 그럼 이렇게 더 줄여서 쓸 수 있음.
		// 심지어 매개변수까지도 생략이 가능하네? 쇼킹..
		System.out.println(cc2.convert(1010));

		String s1 = convert(100, (n) -> String.valueOf(n)); // 람다식을 이용하니까 익명 클래스 구현 방법이 간결해졌음!
		System.out.println(s1);

		String s2 = convert(200, String::valueOf); // 아주 간결하게 줄인 결과!!!!
		System.out.println(s2);

		// 생략된 코드가 어렵다면. 나열해 본다.
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