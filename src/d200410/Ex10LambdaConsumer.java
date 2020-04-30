package d200410;

import java.util.function.Consumer;

public class Ex10LambdaConsumer {
	public static void main(String[] args) {
		Consumer<Long> c = (t) -> System.out.println(t);
		c.accept(100L);
	}
}
