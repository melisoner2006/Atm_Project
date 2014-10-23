/*
 * @auth: 		Melis Oner
 * @date: 		10-14-2014
 * @version:	1
 * @assignment description:
 * 		This program imitates a real world ATM device and its functionalities
 * 		with using basic programming concepts:
 * 
 * @main steps:
 * 
 * @purpose:
 *  
 * 		Is to see how to use the basic programming concepts we learn in CPS180 
 * 		in a real world problem.
 * 		And to get familiar with these concepts:
 *  		Scan console input
 * 			Scan file input
 * 			Search data in a file
 * 			Writing to a file 
 * 			Overwriting files
 * 			Loops - for, while
 * 			Control statements - if/else, switch/case
 * 		
 */

package melisoner.cps180.labexamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		boolean isYes = false;
		int accountNumber = 0;
		String name = new String();
		String pin = new String();

		displayMessage("Welcome to the XYZ bank!\nType yes to continue:");
		String response = scanUserResponse();
		isYes = isResponseYes(response);

		if (isYes) {
			name = askUserName();
			pin = askUserPin();
			accountNumber = checkUserCredentials(name, pin);

			if (accountNumber != 0) {
				displayMessage("Welcome " + name + "\n");
				int opNum = selectOperation();
				while (opNum != -1) {

					switch (opNum) {
					case 1:
						displayMyBalance(accountNumber);
						break;
					case 2:
						withdrawCash(accountNumber);
						break;
					case 3:
						makeDeposit();
						break;
					case 4:
						ChangeMyPin();
						break;

					}
					
					opNum = selectOperation();
				}
			} else {
				displayMessage("Your name and pin didn't match or couldn't be found in the database.");
				callCustomerService();
			}
		} else {
			displayMessage("Oops...Something went wrong");
		}

	}

	private static void makeDeposit() {
		displayMessage("This operation is not allowed.");
		callCustomerService();
	}

	private static void ChangeMyPin() {
		displayMessage("This operation is not allowed.");
		callCustomerService();
	}

	private static void withdrawCash(int accNum){
		displayMessage("Please enter the amount you want to withdraw:");
		double amountTaken = Double.parseDouble(scanUserResponse());

		File input = new File("accounts_database.txt");
		File temp = new File("temp.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(temp);
			Scanner fileScanner = new Scanner(input);
			
			int accountNumber;
			double amount;
			
			while (fileScanner.hasNext()) {
				accountNumber = fileScanner.nextInt();
				amount = fileScanner.nextDouble();
				if (accountNumber == accNum) {
					amount = amount - amountTaken;
				}
				pw.println(accountNumber+"\t"+amount);
			}

			fileScanner.close();
			pw.close();
			
			if(input.exists()){
				System.out.println("EXISTS");
				System.out.println(input.delete());
			}
			
			System.out.println(temp.renameTo(input));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void displayMyBalance(int accountNumber)
			throws FileNotFoundException {
		File file = new File("accounts_database.txt");
		Scanner fileScanner = new Scanner(file);
		double amount = 0;
		while (fileScanner.hasNext()) {

			if (fileScanner.nextInt() == accountNumber) {
				amount = fileScanner.nextDouble();
				writeToTheReceit(amount);
			} else {
				amount = fileScanner.nextDouble();
			}
		}

	}

	private static void writeToTheReceit(double amount)
			throws FileNotFoundException {
		File receipt = new File("receipt.txt");
		PrintWriter pr = new PrintWriter(receipt);
		Date today = new Date();
		pr.println(today);
		pr.println("-------------------------------");
		pr.println("Your balance is: " + amount);
		pr.close();
		displayMessage("Your balance is: " + amount
				+ "\nDon't forget your receipt!");

	}

	private static int selectOperation() {
		System.out.println("Please select your operation");
		System.out
				.println("1. Display my balance\n"
						+ "2. Withdraw cash\n"
						+ "3. Make a deposit\n"
						+ "4. Change my pin\n"
						+ "Enter -1 to exit the menu.\n");

		return Integer.parseInt(scanUserResponse());

	}

	private static void callCustomerService() {
		String csPhoneNumber = "(989)100-2367";
		System.out
				.println("Please call our customer service: " + csPhoneNumber);

	}

	private static int checkUserCredentials(String name, String pin)
			throws FileNotFoundException {
		File file = new File("names_database.txt");
		Scanner fileScanner = new Scanner(file);
		String name2, pin2;
		int id = 0;
		while (fileScanner.hasNext()) {
			id = fileScanner.nextInt();
			name2 = fileScanner.next();
			pin2 = fileScanner.next();

			if (name2.equals(name) && pin2.equals(pin)) {
				return id;
			}
		}
		fileScanner.close();
		return 0;
	}

	private static String askUserPin() {
		System.out.println("Please enter your pin:");
		return scanUserResponse();
	}

	private static String askUserName() {
		System.out.println("Please enter your name:");
		return scanUserResponse();
	}

	private static boolean isResponseYes(String response) {
		if (response.equalsIgnoreCase("yes")) {
			return true;
		} else {
			return false;
		}
	}

	private static String scanUserResponse() {
		Scanner keyboard = new Scanner(System.in);
		String result = keyboard.nextLine();
		return result;

	}

	private static void displayMessage(String message) {
		System.out.println(message);
	}

}
