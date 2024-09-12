package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Input;
import view.View_input;

public class Logic_input {
	private Input input;
	private View_input vi;
	private List<SimpleDateFormat> dateFormats = new ArrayList<>();
	private List<SimpleDateFormat> timeFormatters = new ArrayList<>();
	private Date newDate = null;
	private Date newHour = null;
	private String newPlaca = null;

	public Logic_input(View_input vi) {
		super();
		this.vi = vi;
	}


	public String validatePlaca (String placa) {
		String regex = "^[A-Za-z]{3}-?\\d{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(placa);
		if (matcher.matches())
			newPlaca = placa;
		return newPlaca;
	}

	private String normalizeAmPm(String hour) {
		hour = hour.toLowerCase().replace("p.m.", "PM").replace("pm", "PM").replace("P.M.", "PM");
		hour = hour.toLowerCase().replace("a.m.", "AM").replace("am", "AM").replace("A.M.", "AM");;
		return hour;
	}

	public Date hour (String hour) {
		hour = normalizeAmPm(hour);
		timeFormatters.add(new SimpleDateFormat("hh:mm a", Locale.ENGLISH));  
		timeFormatters.add(new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH));
		timeFormatters.add(new SimpleDateFormat("HH:mm"));
		timeFormatters.add(new SimpleDateFormat("HH:mm:ss"));
		timeFormatters.add(new SimpleDateFormat("hh.mm a", Locale.ENGLISH));  
		timeFormatters.add(new SimpleDateFormat("hh.mm.ss a", Locale.ENGLISH));
		timeFormatters.add(new SimpleDateFormat("HH.mm.ss"));
		timeFormatters.add(new SimpleDateFormat("HH.mm"));
		for (SimpleDateFormat format : timeFormatters) {
			try {
				newHour = format.parse(hour);
				break;
			} catch (ParseException e) {
			}
		}

		if (newHour == null) {
			System.out.println("Formato de hora no válido: " + hour);
		}

		return newHour;
	}
	public Date date (String date) {
		dateFormats.add(new SimpleDateFormat("dd-MM-yyyy"));
		dateFormats.add(new SimpleDateFormat("dd/MM/yyyy"));
		dateFormats.add(new SimpleDateFormat("dd.MM.yyyy"));
		dateFormats.add(new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH));
		dateFormats.add(new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
		dateFormats.add(new SimpleDateFormat("yyyy MM dd"));
		dateFormats.add(new SimpleDateFormat("yyyy/MM/dd"));
		dateFormats.add(new SimpleDateFormat("yyyy MMMM dd", Locale.ENGLISH));
		dateFormats.add(new SimpleDateFormat("yyyy MMM dd", Locale.ENGLISH));

		for (SimpleDateFormat format : dateFormats) {
			try {
				newDate = format.parse(date);
				break;
			} catch (ParseException e) {
			}
		}
		if (newDate == null) {
			System.out.println("Formato de fecha no válido: " + date);
		}
		return newDate;
	}

	public void ejecutar() {
		String placa;
		Date date;
		Date hour;
		do {
			placa = this.validatePlaca(vi.Placa());
		}while(newPlaca == null);

		do {
			date = this.date(vi.Fecha());
		}while (newDate == null);

		do {
			hour = this.hour(vi.Hora());
		}while (newHour == null);

		input = new Input(placa, date, hour);

		System.out.println(input.toString());

		this.output(input);
		
	}
	
	public void output(Input input) {
		if(this.DayOfWeek(input).equals("FREE") || this.hoursPicoPlaca(input).equals("FREE")) {
			System.out.println("There's not Pico&Placa, you can drive along");
		}else if(this.hoursPicoPlaca(input).equals("MORNING") && this.DayOfWeek(input).equals("RESTRICTED")) {
			System.out.println("Your car has Pico&Placa, wait until 9:30 AM");
		}else if(this.hoursPicoPlaca(input).equals("NIGHT") && this.DayOfWeek(input).equals("RESTRICTED")) {
			System.out.println("Your car has Pico&Placa, wait until 19:30 PM");
		}else {
			System.out.println("Error");
		}
	}

	public String DayOfWeek (Input i) {
		char lastD = i.getPlaca().charAt(i.getPlaca().length() - 1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(i.getDate());
		int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		if((lastD == '1' || lastD == '2') && diaSemana == Calendar.MONDAY) {
			return "RESTRICTED";
		}else if((lastD == '3' || lastD == '4') && diaSemana == Calendar.TUESDAY) {
			return "RESTRICTED";
		}else if((lastD == '5' || lastD == '6') && diaSemana == Calendar.WEDNESDAY) {
			return "RESTRICTED";
		}else if((lastD == '7' || lastD == '8') && diaSemana == Calendar.THURSDAY) {
			return "RESTRICTED";
		}else if((lastD == '9' || lastD == '0') && diaSemana == Calendar.FRIDAY) {
			return "RESTRICTED";
		}else {
			return "FREE";
		}		
	}

	public String hoursPicoPlaca (Input i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(i.getHour());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE); 

		Calendar BeginHourM = Calendar.getInstance();
		BeginHourM.set(Calendar.HOUR_OF_DAY, 7); 
		BeginHourM.set(Calendar.MINUTE, 0);

		Calendar EndHourM = Calendar.getInstance();
		EndHourM.set(Calendar.HOUR_OF_DAY, 9);
		EndHourM.set(Calendar.MINUTE, 30);

		Calendar BeginHourN = Calendar.getInstance();
		BeginHourN.set(Calendar.HOUR_OF_DAY, 16); 
		BeginHourN.set(Calendar.MINUTE, 0);

		Calendar EndHourN = Calendar.getInstance();
		EndHourN.set(Calendar.HOUR_OF_DAY, 19);
		EndHourN.set(Calendar.MINUTE, 30);

		Calendar hourNow = Calendar.getInstance();
		hourNow.set(Calendar.HOUR_OF_DAY, hour);
		hourNow.set(Calendar.MINUTE, min);

		if(hourNow.after(BeginHourM) && hourNow.before(EndHourM)) {
			return "MORNING";
		}else if (hourNow.after(BeginHourN) && hourNow.before(EndHourN)) {
			return "NIGHT";
		}else {
			return "FREE";
		}
	}

}
