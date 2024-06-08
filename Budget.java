import java.util.Scanner;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Budget {
	
	//Static constants to use within methods.
	final static Scanner scan = new Scanner(System.in);
	final static NumberFormat fmt = NumberFormat.getCurrencyInstance();
	final static DecimalFormat dfmt = new DecimalFormat("0.##");
	
	//This method dynamically gets a name from scanner input.
	public static String getName(String foo) {
		System.out.print("Please enter a " + foo + " name: " );
		String name = scan.nextLine().split(" ")[0];
		return name;
	}
	
	//This method asks the user if they want to start budgeting or quit the program.
	public static boolean startBudget() {
		System.out.print("\nENTER to start app / Q to Quit: ");
		String restart = scan.nextLine();
		System.out.print("\n");
		if (restart.trim().toLowerCase().equals("q")) {
			return false;
		}
		return true;
	}
	
	//This method uses a loop to get user input of a number and check its validity.
	public static double getNum(String msg) {
		double num = -1.0;
		while (num < 0) {
			try {
				System.out.print(msg);
				num = scan.nextDouble();
				if (num < 0) {
					System.out.println("Please enter a number greater than or equal to zero!");
				}
				scan.nextLine();
			}
			catch (Exception e) {
				System.out.println("Please enter a valid number!");
				scan.nextLine();
			}
		}	
		return num;
	}
	
	//This method is used to display all important data to the user.
	public static void displayExpenses(double starting, double total, ArrayList<String> nameList, ArrayList<Double> numList) {
		System.out.println("\nEXPENSES LIST:");
		for (int i = 0; i < nameList.size(); i++) {
			System.out.println(nameList.get(i) + ": " + fmt.format(numList.get(i)));
		}
		System.out.println("\nSTARTING BALANCE: " + fmt.format(starting));
		System.out.println("TOTAL COST: " + fmt.format(total) + "\n");
	}
	
	
	public static void main(String[] args) {
		scan.useDelimiter("\\R");
		System.out.println("Welcome to the Budget Application!\n");
		
		//Create start boolean to check if user want.
		Boolean start = startBudget();
		//Budget loop starts and will continue until user changes start boolean.
		while (start) {
			//Outer Loop Starts.
			String firstName = getName("first");
			String lastName = getName("last");
			System.out.println("\nLets start a budget for " + firstName + " " + lastName + ".");
			
			//Create empty ArrayLists to store data. Total number of expenses is unknown so I chose lists instead of arrays.
			ArrayList<String> expenseNames = new ArrayList<String>();
			ArrayList<Double> expenseCosts = new ArrayList<Double>();
			
			//Collect starting balance data and also total set to 0.0;
			double startingBalance = getNum("\nEnter your total budget for the month: ");
			double total = 0.0;
			double finalCalc = startingBalance;
					
			//Create boolean for checking if the user wanting to add more items.
			boolean collectDataBool = true;
			//This loop will collect all expense data.
			while (collectDataBool) {
				//Inner Loop Starts.
				
				//Collect name and cost of item.
				System.out.print("\nEnter the name of an expense: ");
				String currentExpense = scan.nextLine();
				expenseNames.add(currentExpense);
				double price = getNum("Enter the cost for " + currentExpense + ": ");
				expenseCosts.add(price);
				finalCalc -= price;
				
				if (finalCalc >= 0) {
					System.out.println("Remaining balance is " + fmt.format(finalCalc));
				}
				else {
					System.out.println("Currently over budget by " + fmt.format(finalCalc * -1));
				}
				
				//Add to running total
				total += price;
				
				//Create String for comparison which would change collectDataBool from true to false.
				String moreExpenses = "";
				System.out.println("\n");
				//This loop validates user input is either y/n to see if the user wants to add more items
				while (!moreExpenses.equals("y") && !moreExpenses.equals("n")) {
					System.out.print("Would you like to enter more expenses (y/n), or SHOW expenses (s): ");
					moreExpenses = scan.nextLine();
					moreExpenses.trim().toLowerCase();
					//This if-statement will display all data if true.
					if (moreExpenses.equals("s")) {
						displayExpenses(startingBalance, total, expenseNames, expenseCosts);
					}
				}
				
				//This will end the inner loop.
				if (moreExpenses.equals("n")) {
					collectDataBool = false;
				}
				//Inner Loops Ends.
			}
			
			//This lets the user know that all data has been collected.
			System.out.println("\nCALCULATING BUDGET. Press ENTER to continue: ");
			scan.nextLine();
			
			//display all data and do final calculation.
			displayExpenses(startingBalance, total, expenseNames, expenseCosts);
			
			//display final messages.
			if (finalCalc < 0) {
				System.out.println("\n" + firstName + " " + lastName + " is " + fmt.format(finalCalc * -1) + " over budget.");
				System.out.println("Please plan accordingly so you aren't homeless and in debt!");
			}
			else if (finalCalc > 0) {
				System.out.println("\n" + firstName + " " + lastName + " is " + fmt.format(finalCalc) + " under budget.");
				System.out.println("Congratulations! Your finances are doing great!");}
			else {
				
				System.out.println("\n" + firstName + " " + lastName + " is " + fmt.format(finalCalc * -1) + " under budget.");
				System.out.println("You are living paycheck to paycheck. Be careful not to over spend!");
			}
			//This will check if the user wants to use the budget app again.
			start = startBudget();
			//Outer Loop Ends.
		}	
		
		System.out.println("\nProgram Terminated");
		scan.close();
	}
}
