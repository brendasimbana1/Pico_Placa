package view;

import java.util.Scanner;

public class View_input {
	private Scanner scanner;

	public View_input() {
		this.scanner = new Scanner(System.in);
	}

	public String Placa() {
		System.out.print("Enter your license plate: ");
		return scanner.nextLine();
	}

	public String Fecha() {
		System.out.print("Enter the current date: ");
		return scanner.nextLine();
	}

	public String Hora() {
		System.out.print("Enter the current hour: ");
		return scanner.nextLine();
	}

}
