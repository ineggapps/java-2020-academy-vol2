package d200408;

import java.io.Serializable;

public class UserVO implements Serializable {
	/**
	 * but... 직렬화 대상에서 제외되는 것들 (method, static 변수, transient 변수)
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private transient String tel; //transient 키워드를 적으면 저장되지 않는 것을 확인할 수 있다. (EX3, EX4)
//	private String tel; 

	public UserVO() {
	}

	public UserVO(String name, int age, String tel) {
		this.name = name;
		this.age = age;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
