package view;

import java.util.Scanner;

public class View_input {
	private Scanner scanner;

	public View_input() {
		this.scanner = new Scanner(System.in);
	}

	public String Placa() {
		System.out.print("Ingrese su placa: ");
		return scanner.nextLine();
	}

	public String Fecha() {
		System.out.print("Ingrese la fecha actual: ");
		return scanner.nextLine();
	}

	public String Hora() {
		System.out.print("Ingrese la hora actual: ");
		return scanner.nextLine();
	}

	public void mostrarResultado() {
		System.out.println("a");
	}


}
