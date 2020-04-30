package d200410;

//람다에서 매개변수 접근하는 방법 알아보기
public class Ex8LambdaParameter {
	public static void main(String[] args) {
		Demo4 u = (a, b) -> {
			int c = a + b;
			return a + b;
		}; 
		Demo4 v = (a, b) -> a + b; // 한 줄 안에서 반환까지 마무리되는 경우 return을 생략
		int x = u.add(5, 10);
		System.out.println(x);
		int z = v.add(10, 1);
		System.out.println(z);
	}
}

@FunctionalInterface
interface Demo4 {
	public int add(int a, int b);
}