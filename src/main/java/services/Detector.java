package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.util.StringUtils;

public class Detector {

	private static String DNA_SECUENSE = "ATCG";
	private static Pattern dnaPatter = Pattern.compile("([ATCG])\\1{3}");
	private static int CANT_SECUENSES_VALID = 2;
	private static int CANT_DNA_REPETED = 3;
	private static Detector instance;
	
	private int cantSecuensesValid;
	
	public static final Detector instance() {
		if (instance == null) {
			instance = new Detector();
		}
		return instance;
	}
	
	private Detector() {
	}
	
	public String secuense() {
		return DNA_SECUENSE;
	}
	
	public Integer cant() {
		return CANT_SECUENSES_VALID;
	}
	
	public Integer repetitions() {
		return CANT_DNA_REPETED + 1;
	}
	
	public void defineDna(String dna) {
		// TODO: valid that dna is alpha characters
		if (!StringUtils.isNullOrEmpty(dna)) {
			DNA_SECUENSE = dna;
			dnaPatter = Pattern.compile("([" + DNA_SECUENSE + "])\\1{" + CANT_DNA_REPETED + "}");
		}
	}
	
	public void defineCantSecuense(int cant) {
		if (cant > 0) {
			CANT_SECUENSES_VALID = cant;
		}
	}
	
	public void defineDnaRepited(int cant) {
		if (cant > 0) {
			CANT_DNA_REPETED = cant - 1;
			dnaPatter = Pattern.compile("([" + DNA_SECUENSE + "])\\1{" + CANT_DNA_REPETED + "}");
		}
	}
	
	public boolean isMutant(String[] adn) {
		cantSecuensesValid = CANT_SECUENSES_VALID;
		char[][] matrix = convertToMatrix(adn);
		return horizontalEvaluation(matrix)
				|| verticalEvaluation(matrix)
				|| diagonalEvaluation(matrix);
	}

	private char[][] convertToMatrix(String[] secuenses) {
		char[][] matrix = new char[secuenses.length][];
		int index = 0;
		for (String secuence : secuenses) {
			matrix[(index++)] = secuence.toCharArray();
		}
		return matrix;
	}

	private boolean horizontalEvaluation(char[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			if (evaluateSecuense(String.valueOf(matrix[i]))) {
				return true;
			}
		}
		return false;
	}

	private boolean verticalEvaluation(char[][] matrix) {
		return horizontalEvaluation(trasposeMatrix(matrix));
	}

	public static char[][] trasposeMatrix(char[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		char[][] trasposedMatrix = new char[n][m];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				trasposedMatrix[x][y] = matrix[y][x];
			}
		}
		return trasposedMatrix;
	}

	private boolean evaluateSecuense(String secuense) {
		Matcher matcher = dnaPatter.matcher(secuense);
		while (matcher.find()) {
			cantSecuensesValid--;
			secuense = secuense.replace(matcher.group(), "");
			matcher = dnaPatter.matcher(secuense);
		}
		return cantSecuensesValid <= 0;
	}

	private boolean diagonalEvaluation(char[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			String secuense = "";
			//Evalucacion de derecha a izquierda, lado de arriba
			for (int l = 0; l <= i; l++) {
				secuense += matrix[l][i - l];
			}
			System.out.println(secuense);
			if (evaluateSecuense(secuense)) {
				return true;
			}
			//Evaluacion de izquierda a derecha, lado izquierdo
			secuense = "";
			for (int l = 0; l + i < matrix[i].length; l++) {
				secuense += matrix[i + l][l];
			}
			System.out.println(secuense);
			if (evaluateSecuense(secuense)) {
				return true;
			}
			if (i != 0) {
				//Evaluacion de derecha a izquierda, lado derecho
				int j = matrix[i].length - 1;
				secuense = "";
				for (int l = 0; i + l < matrix[i].length; l++) {
					secuense += matrix[i + l][j - l];
				}
				System.out.println(secuense);
				if (evaluateSecuense(secuense)) {
					return true;
				}
				// Evalucacion de izquierda a derecha, lado arriba
				j -= i;
				secuense = "";
				for (int l = 0; j + l < matrix[i].length; l++) {
					secuense += matrix[l][j + l];
				}
				System.out.println(secuense);
				if (evaluateSecuense(secuense)) {
					return true;
				}
			}
		}
		return false;
	}
}
