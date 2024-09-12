package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Input {
	private String placa;
	private Date date;
	private Date hour;

	public Input(String placa, Date date, Date hour) {
		super();
		this.placa = placa;
		this.date = date;
		this.hour = hour;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy"); // Para la fecha
		SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH); // Para la hora
		String Date = dateFormat.format(date);
		String Hour = hourFormat.format(hour);

		return "Input [placa=" + placa + ", date=" + Date + ", hour=" + Hour + "]";
	}

}
