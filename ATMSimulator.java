import java.util.Scanner;

// Class representing the user's bank account
class BankAccount {
    private double balance;

    // Constructor to initialize the account with a starting balance
    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    // Method to add funds to the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("You have successfully deposited: $" + amount);
        } else {
            System.out.println("Deposit amount must be greater than zero.");
        }
    }

    // Method to withdraw funds from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("You have successfully withdrawn: $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient funds for this transaction.");
        } else {
            System.out.println("Withdrawal amount must be greater than zero.");
        }
    }

    // Method to get the current balance
    public double getBalance() {
        return balance;
    }
}

// Class representing the ATM machine
class ATM {
    private BankAccount account;
    private Scanner scanner;

    // Constructor to initialize the ATM with the user's bank account
    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    // Method to display the ATM options and handle user input
    public void start() {
        boolean continueTransaction = true;

        while (continueTransaction) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    continueTransaction = false;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    // Method to display the user's current balance
    private void checkBalance() {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    // Method to deposit money into the account
    private void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    // Method to withdraw money from the account
    private void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }
}

// Main class to run the ATM simulation
public class ATMSimulator {
    public static void main(String[] args) {
        // Create a bank account with an initial balance of 1000
        BankAccount userAccount = new BankAccount(1000.0);

        // Create an ATM connected to the user's account
        ATM atm = new ATM(userAccount);

        // Start the ATM
        atm.start();
    }
}

