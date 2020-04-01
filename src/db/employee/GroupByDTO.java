package db.employee;

public class GroupByDTO {
	private String year;
	private String month;
	private int sum;
	private int average;
	
	/**
	 * 
	 */
	public GroupByDTO() {
	}

	/**
	 * @param year
	 * @param month
	 * @param sum
	 * @param average
	 */
	public GroupByDTO(String year, String month, int sum, int average) {
		this.year = year;
		this.month = month;
		this.sum = sum;
		this.average = average;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}

}
