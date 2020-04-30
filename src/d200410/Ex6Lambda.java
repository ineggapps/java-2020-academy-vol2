package d200410;

public class Ex6Lambda {
	public static void main(String[] args) {
		//매개변수가 없고, 반환형이 void인 람다식...
		Demo2 ob = () -> {//소스가 이렇게 간단해질 수 있다니!
			System.out.println("안녕 람다야!");
			//하지만 람다식을 사용할 수 있는 조건은 제한적이다.
			// 인터페이스에서 메서드는 1개만 존재해야 한다.
		};
		ob.write();
	}
}

interface Demo2 {
	public void write();
}
