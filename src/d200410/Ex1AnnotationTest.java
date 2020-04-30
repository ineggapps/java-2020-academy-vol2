package d200410;

import java.util.Date;

public class Ex1AnnotationTest {
	public static void main(String[] args) {
		User1 ob = new User1();
		System.out.println(ob);
		
		ob.sub();
	}
}

class User1 {
//	@Override <- 어노테이션을 붙이지 않으면 상속받는 메서드인지 불명확하여 오류가 발생하지 않음.
//	결론적으로 어노테이션을 언급하지 않으면 메서드 이름이 다르거나(대소문자까지 포함하여) 잘못 입력한 경우 알아차리기가 힘들다.
//	public String ToSTring() {
//		return "예제...";
//	}

//	@Override
//	public String TOString() { 어노테이션을 붙이면 메서드 이름이 틀리다고 컴파일러가 알려준다.
//		return "예제...";
//	}

	@Override
	public String toString() {
		return "올바른 예제...";
	}

	@Deprecated
	public void sub() {
		System.out.println("이 메서드 사용을 자제하시기 바랍니다.");
	}
	

//	@SuppressWarnings("deprecation")언급하면 느낌표가 사라진다. 하지만 불가피할경우에 사용하는 것이지 남용해서는 안 된다.
	public void sub2() {
		Date date = new Date();
//		@SuppressWarnings("deprecation") 메서드 안에 언급하는 방법도 있다.
		int y= date.getYear(); //Deprecated된 메서드를 호출하였음.
		System.out.println(y);
	}
}