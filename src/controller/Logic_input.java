package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private boolean PlacaValida = false;

	public Logic_input(View_input vi) {
		super();
		this.vi = vi;
	}

	public void ejecutar() {
		String placa = "";
		Date fecha;
		Date hora;
		do {
			 if(validatePlaca(vi.Placa()))
				 placa = vi.Placa();
		}while(PlacaValida == false);
		
		do {
			fecha = this.date(vi.Fecha());
		}while (newDate == null);
		do {
			hora = this.hour(vi.Hora());
		}while (newHour == null);

		input = new Input(placa, fecha, hora);

		System.out.println(input.toString());
	}
	
	public boolean validatePlaca (String placa) {
		String regex = "^[A-Z]{3}-?\\d{3,4}$";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(placa);
        if (matcher.matches())
        	PlacaValida = true;
        else
        	PlacaValida = false;
        return PlacaValida;
	}

	public Date hour (String hour) {
		timeFormatters.add(new SimpleDateFormat("hh:mm a", Locale.ENGLISH));  
		timeFormatters.add(new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH));
		timeFormatters.add(new SimpleDateFormat("HH:mm"));
		timeFormatters.add(new SimpleDateFormat("HH:mm:ss"));
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
		dateFormats.add(new SimpleDateFormat("dd MMM yyyy"));
		dateFormats.add(new SimpleDateFormat("dd MMMM yyyy"));
		dateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));

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

}
