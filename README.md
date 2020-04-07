# java-2020-academy-vol2

JAVA

## 1. JDBC

- [x] 오라클과 연동하기
- [x] Statement
- [x] PreparedStatement
- [x] CallableStatement

## 2. I/O

### 2.1. 스트림

#### 2.1.1. 바이트 입출력 스트림

```JAVA
InputStream = System.in;
OutputStream = System.out;
```

##### 2.1.1.1. 1byte 입력 받기

> 1byte를 입력 받아 ASCII 코드를 반환한다.

```JAVA
int data = System.in.read(); //한글은 두 번 입력을 받아야 한다.
char ch = (char)System.in.read(); //한글은 정상적으로 입력되지 않는다.
```

##### 2.1.1.2. 1byte 출력하기

```JAVA
int data = 65;
System.out.write(data); //하위 1byte 출력
System.out.flush(); // 버퍼 내용을 출력 장치로 보낸다.
```

#### 2.1.2. 문자 입출력 스트림

##### 2.1.2.1. byte => 문자 스트림으로 변환

```JAVA
Reader rd = new InputStreamReader(System.in);
Writer wt = new OutputStreamWriter(System.out);
```

##### 2.1.2.2. 한 문자 입력 받기(한 문자 이볅받아 ASCII 코드 반환)

```JAVA
Reader rd = new InputStreamReader(System.in);
Writer wt = new OutputStreamWriter(System.out);
int data = rd.read();//한글도 한 번에 입력받는다.
```

##### 2.1.2.3. 문자출력하기

```JAVA
wt.write(data); //문자 출력
wt.flush();
System.out.println((char)data); //한글도 정상 출력
```

#### 2.1.3. 파일 입출력 스트림 ★

```java
FileInputStream fis = new FileInputStream("파일명.확장자"); //매개변수에 파일객체가 와도 무방하다.
//파일이 없으면 FileNotFoundException 예외를 던진다.
FileOutputStream fos = new FileOutputStream("파일명.확장자"); //매개변수에 파일객체가 와도 무방하다.
//파일이 있으면 지우고 만들며, 없으면 새로 만듦.

byte[] b = new byte[2048]; //버퍼의 크기 지정
int len;

while((len=fis.read(b))!=-1){
    fos.write(b, 0, len);
}
fos.flush();
fis.close();
fos.close();
```
