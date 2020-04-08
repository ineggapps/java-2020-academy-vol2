package d200408;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Ex13TimerEx {
	public static void main(String[] args) {
		System.out.println("main() 호출 시간 " + String.format("%1$tT %1$tA %1$tT", new Date()));
		new MyTimer();
	}
}

class MyTimer {
	public MyTimer() {
		// TimerTask: Timer에 의해 반복 실행하도록 태스크되는 스케줄
		// BUT 0.001초 정도의 오차는 발생할 수 있다는 것을 유의한다.
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				printTimes();
			}
		};

		Timer t = new Timer();
		// 현재 시간부터 1초마다 한 번씩 반복 실행한다.
//		t.schedule(task, new Date(), 1000);//현재부터 1초마다 실행
//		t.schedule(task, 100);// 100ms 후 한 번만 실행
		t.schedule(task, 2000, 1000);// 2초 후 1초마다 반복하여 실행
//		t.cancel();//타이머 중지

		// 다음날 0시 0분 0:000초에 시작하여 하루에 한 번만 실행
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1); // 정확히 24시간 미래로 이동
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		t.schedule(task, cal.getTime(), 1000 * 60 * 60 * 24);

	}

	private void printTimes() {
		Calendar cal = Calendar.getInstance();
		String s = String.format("%1$tT %1$tA %1$tT", cal);
		System.out.println(s);
	}

}