package alvesjv.campo.minado;

import alvesjv.campo.minado.modelo.Tabuleiro;
import alvesjv.campo.minado.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);		
		new TabuleiroConsole(tabuleiro);		
		
	}
}
