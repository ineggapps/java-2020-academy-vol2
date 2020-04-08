package d200408;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Ex13TimerEx {
	public static void main(String[] args) {
		System.out.println("main() ȣ�� �ð� " + String.format("%1$tT %1$tA %1$tT", new Date()));
		new MyTimer();
	}
}

class MyTimer {
	public MyTimer() {
		// TimerTask: Timer�� ���� �ݺ� �����ϵ��� �½�ũ�Ǵ� ������
		// BUT 0.001�� ������ ������ �߻��� �� �ִٴ� ���� �����Ѵ�.
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				printTimes();
			}
		};

		Timer t = new Timer();
		// ���� �ð����� 1�ʸ��� �� ���� �ݺ� �����Ѵ�.
//		t.schedule(task, new Date(), 1000);//������� 1�ʸ��� ����
//		t.schedule(task, 100);// 100ms �� �� ���� ����
		t.schedule(task, 2000, 1000);// 2�� �� 1�ʸ��� �ݺ��Ͽ� ����
//		t.cancel();//Ÿ�̸� ����

		// ������ 0�� 0�� 0:000�ʿ� �����Ͽ� �Ϸ翡 �� ���� ����
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1); // ��Ȯ�� 24�ð� �̷��� �̵�
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