package d200410;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Ex2AnnotationTest {
	@User2 // �ƹ� ���� ���� ������̼�
	public void execute() {
		System.out.println("������̼� ��� �׽�Ʈ");
	}

	public static void main(String[] args) {
		Ex2AnnotationTest e = new Ex2AnnotationTest();
		e.execute();
	}
}

//������̼��� ������ ����� ���� �幰���� ������ �����ӿ�ũ���� ���Ǵ� ����̹Ƿ�
//������ �����ϵ��� �Ѵ�.
//BuiltIn Annotation�� �����ؾ� �Ѵ�.
@Target(ElementType.METHOD)//�޼��忡 ���� �ſ���
@Retention(RetentionPolicy.RUNTIME)//��Ÿ�� ���� ���� ���� ������̼� ������ �����ǵ��� �ϰڽ��ϴ�.
@interface User2 {// ��...
}