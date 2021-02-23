package alvesjv.campo.minado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import alvesjv.campo.minado.excessao.ExplosaoException;


public class Tabuleiro {
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<Campo>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			
			campos.stream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
			
		} catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void alterarMarcacao(int linha, int coluna) {
		campos.stream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.alterarMarcacao());
	}

	private void sortearMinas() {
		
		long minasArmadas = 0;
		Predicate<Campo> minado =  c -> c.isMinado();
		do {			
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}

	private void associarVizinhos() {
		
		for(Campo c1 : campos ) {
			for(Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void gerarCampos() {
		// linha i
		//coluna j
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				campos.add(new Campo(i, j));
			}
		}
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int i = 0; i < colunas; i++) {
			sb.append(" ");
			sb.append(i);
			sb.append(" ");
		}
		sb.append("\n");
		
		int x = 0; 
		for (int i = 0; i < linhas; i++) {
			
			sb.append(i);
			sb.append(" ");
			
			for (int j = 0; j < colunas; j++) {
				sb.append(" ");
				sb.append(campos.get(x));
				sb.append(" ");
				x++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
