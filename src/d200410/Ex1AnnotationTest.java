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
//	@Override <- ������̼��� ������ ������ ��ӹ޴� �޼������� �Ҹ�Ȯ�Ͽ� ������ �߻����� ����.
//	��������� ������̼��� ������� ������ �޼��� �̸��� �ٸ��ų�(��ҹ��ڱ��� �����Ͽ�) �߸� �Է��� ��� �˾������Ⱑ �����.
//	public String ToSTring() {
//		return "����...";
//	}

//	@Override
//	public String TOString() { ������̼��� ���̸� �޼��� �̸��� Ʋ���ٰ� �����Ϸ��� �˷��ش�.
//		return "����...";
//	}

	@Override
	public String toString() {
		return "�ùٸ� ����...";
	}

	@Deprecated
	public void sub() {
		System.out.println("�� �޼��� ����� �����Ͻñ� �ٶ��ϴ�.");
	}
	

//	@SuppressWarnings("deprecation")����ϸ� ����ǥ�� �������. ������ �Ұ����Ұ�쿡 ����ϴ� ������ �����ؼ��� �� �ȴ�.
	public void sub2() {
		Date date = new Date();
//		@SuppressWarnings("deprecation") �޼��� �ȿ� ����ϴ� ����� �ִ�.
		int y= date.getYear(); //Deprecated�� �޼��带 ȣ���Ͽ���.
		System.out.println(y);
	}
}