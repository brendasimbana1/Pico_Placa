package main;
import controller.Logic_input;
import view.View_input;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		View_input vi = new View_input();
		Logic_input li = new Logic_input(vi);
		li.ejecutar();

	}

}
