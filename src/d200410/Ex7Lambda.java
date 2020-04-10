package d200410;

//람다 문법을 사용하는 다양한 예...
public class Ex7Lambda {
	public static void main(String[] args) {
		// 매개변수가 1개이고 반환형이 void인 람다식
		Demo3 u = (int a) -> {
			int n = a + 10;
			System.out.println(n);
		};
		u.write(1);
		////////////////////////////////////////////
		Demo3 v = (a) -> {
			System.out.println("매개변수 a가 한 개이고 반환형이 void인 람다 => " + a);
		};
		v.write(1);
		// 매개변수형(int)는 생략하여 지정할 수 있고, 매개변수가 1개인 경우에는 괄호도 생략할 수 있다.
		Demo3 d = a -> {
			System.out.println("매개변수 a가 한 개이고 반환형이 void인 람다 => " + a);
		};
		d.write(12);
		////////////////////////////////////////////
		Demo3 z = a -> System.out.println("한 줄 안에 끝내는 경우 {}도 생략이 가능하다");
		z.write(20);
	}
}

@FunctionalInterface // 나는 람다 쓸 거야!
interface Demo3 {
	public void write(int a);
//	public void write(int a, int b);//어노테이션을 붙이면 하나의 메서드만 존재해야 한다고 알려준다.
}