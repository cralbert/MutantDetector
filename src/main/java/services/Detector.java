package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Detector {

	private static final Pattern dnaPatter = Pattern.compile("([ATCG])\\1{3}");
	private int cantSecuensesValid;
	
	public Detector() {
		cantSecuensesValid = 2;
	}
	
	public boolean isMutant(String[] adn) {
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
