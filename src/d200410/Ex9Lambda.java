package d200410;

public class Ex9Lambda {
	public static void main(String[] args) {
		Sample5 ss = new Sample5();
		ss.using();
	}
}

@FunctionalInterface
interface Demo5 {
	public void fun();
}

class Sample5 {
	int x = 10;

	class Inner {
		int y = 20;

		public void sub() {
			int z = 30;
			int x = 11;
			Demo5 u = () -> {// 람다 안에서의 this는 람다 클래스 자신을 가리키는 게 아니다.
				// 람다에서 this는 익명객체를 가리키지 않고 람다식을 구현한 클래스인 Inner클래스를 가리킨다. /_ \
				System.out.println(this);
				System.out.println(this.y); // 람다식에서 람다 클래스 자신이 아니라 Inner클래스라는 것을 증명..!!
				System.out.println(x);// 제일 가까운 범위부터 x변수를 찾아서 출력하겠지?
				System.out.println(Sample5.this.x); // Sample5클래스의 객체의 필드를 가리킴
				System.out.println(z);
			};
//			z=50;//람다식에서 오류가 발생한다. 왜냐하면 람다식에 사용된 로컬 변수는 final속성을 가지기 때문에 수정할 수 없다.
			u.fun();
		}
	}

	public void using() {
		Inner ob = new Inner();
		ob.sub();
	}
}
