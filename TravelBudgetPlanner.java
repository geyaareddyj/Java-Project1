import java.util.*;        // CO6: Collections, Generics
import java.io.*;          // CO6: File I/O

// ======================= EXPENSE ABSTRACTION =======================
// CO4 & CO5: Abstract class (Abstraction + Inheritance)
abstract class Expense {

    protected double amount;

    // Constructor
    Expense(double amount) {
        this.amount = amount;
    }

    // Abstract method (Polymorphism)
    abstract double getAmount();
}

// ======================= TRAVEL EXPENSE ============================
// CO5: Inheritance + Polymorphism
class TravelExpense extends Expense {

    private Date dateRecorded;

    TravelExpense(double amount) {
        super(amount);
        this.dateRecorded = new Date();
    }

    @Override
    double getAmount() {
        return amount;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }
}

// ======================= MAIN PLANNER CLASS ========================
public class TravelBudgetPlanner {

    // ======================= GLOBAL VARIABLES ======================
    // CO1: Fundamental programming constructs
    static Scanner sc = new Scanner(System.in);

    // CO2: One-dimensional array
    static double[] expenses = new double[200];
    static int expenseCount = 0;

    // CO6: Collections with Generics
    static List<Expense> expenseList = new ArrayList<>();

    static double budget = 0.0;

    // ======================= MAIN METHOD ============================
    public static void main(String[] args) {

        displayWelcome();

        budget = getInitialBudget();

        int choice;

        // CO1: Iteration
        do {
            displayMenu();
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    viewRemainingBudget();
                    break;

                case 3:
                    analyzeExpenses();
                    break;

                case 4:
                    displayExpenseList();
                    break;

                case 5:
                    saveBudget(budget);
                    break;

                case 6:
                    loadBudget();
                    break;

                case 7:
                    System.out.println("Exiting application.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }

    // ======================= DISPLAY METHODS =======================

    static void displayWelcome() {
        System.out.println("=====================================");
        System.out.println("   TRAVEL BUDGET PLANNER SYSTEM");
        System.out.println("=====================================");
    }

    static void displayMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1. Add Expense");
        System.out.println("2. View Remaining Budget");
        System.out.println("3. Analyze Total Expense");
        System.out.println("4. Display All Expenses");
        System.out.println("5. Save Budget to File");
        System.out.println("6. Load Budget from File");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    // ======================= BUDGET METHODS ========================

    static double getInitialBudget() {
        System.out.print("Enter initial travel budget: ");
        double value = sc.nextDouble();

        if (value <= 0) {
            System.out.println("Budget must be positive.");
            return getInitialBudget();
        }
        return value;
    }

    static void viewRemainingBudget() {
        System.out.println("Remaining Budget: " + budget);
    }

    // ======================= EXPENSE METHODS =======================

    static void addExpense() {

        System.out.print("Enter expense amount: ");
        double amt = sc.nextDouble();

        if (amt <= 0) {
            System.out.println("Expense must be positive.");
            return;
        }

        if (amt > budget) {
            System.out.println("Expense exceeds available budget.");
            return;
        }

        // CO2: Array usage
        expenses[expenseCount++] = amt;

        // CO5: Polymorphic object
        expenseList.add(new TravelExpense(amt));

        budget -= amt;

        System.out.println("Expense added successfully.");
    }

    static void analyzeExpenses() {
        // CO3: Recursive computation
        double total = totalExpense(expenseCount);
        System.out.println("Total Expenses: " + total);
    }

    static void displayExpenseList() {

        if (expenseList.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\n--- Expense History ---");

        int index = 1;

        // CO6: Enhanced for-loop with collection
        for (Expense e : expenseList) {
            System.out.println(index + ". Amount: " + e.getAmount());
            index++;
        }
    }

    // ======================= RECURSION ==============================
    // CO3: Recursive function
    static double totalExpense(int n) {
        if (n == 0) {
            return 0;
        }
        return expenses[n - 1] + totalExpense(n - 1);
    }
    // ======================= FILE HANDLING ==========================
    // CO6: File I/O + Exception Handling

    static void saveBudget(double budget) {
        try (FileWriter fw = new FileWriter("budget.txt")) {

            fw.write(budget + "\n");
            fw.write("Total Expenses: " + totalExpense(expenseCount));
            fw.write("\nExpense Count: " + expenseCount);

            System.out.println("Budget saved to file.");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadBudget() {

        try (Scanner fileScanner = new Scanner(new File("budget.txt"))) {

            if (fileScanner.hasNextDouble()) {
                budget = fileScanner.nextDouble();
                System.out.println("Budget loaded successfully.");
                System.out.println("Current Budget: " + budget);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No saved budget file found.");
        }
    }
}


