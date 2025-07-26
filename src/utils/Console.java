package utils;

import java.util.Scanner;

public class Console {
	private static final Scanner scanner = new Scanner(System.in);
	  
	public static String inputString(String message) {
		afficherMessage(message);
	    return scanner.nextLine();
	}
	
	public static int inputInteger(String message) {
		afficherMessage(message);
		int val = scanner.nextInt();
		scanner.nextLine();
		return val;
	}
	
	public static double inputDouble(String message) {
		afficherMessage(message);
		double val = scanner.nextDouble();
	    scanner.nextLine();
	    return val;
	}
	
	public static void afficherMessage(String msg) {
		System.out.print("\n" + msg);
	}
}
