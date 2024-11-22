package view;

import java.util.Locale;

import model.entidades.Entidade;
import model.entidades.Funcionario;

public class SistemaClinico {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		
		clearScreen();
		System.out.println("====================================");
		System.out.println("     Sistema Clínico Veterinário     ");
		System.out.println("====================================");
		
		Entidade usuario = Entidade.loginUser();

		boolean continuar;

		do {
			continuar = usuario.operacoes();
		} while (continuar);
	}

	public static void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}
}
