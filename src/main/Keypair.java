package main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Keypair {

	public String key;
	public Object value;
	
	public Keypair (String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getValue () {
		if (this.value instanceof Date) {
			Date date = (Date) this.value;
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			String formatted = format1.format(cal.getTime());
			
			return "TO_DATE('" + formatted +"', 'yyyy/MM/dd')";
		}
		else{
			return (String) "'" + this.value + "'";
		}
	}
}
