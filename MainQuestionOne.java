import java.util.Scanner;

// Custom Checked Exception
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(double requested, double balance) {
        super(String.format("Transaction Denied: Requested $%.2f, but your current balance is $%.2f.", requested, balance));
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.printf("Successfully withdrew $%.2f%n", amount);
    }

    public double getBalance() {
        return balance;
    }
}

public class MainQuestionOne {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(500.00); // Initial balance $500
        // FIXED: Changed System.穎n to System.in
        Scanner scanner = new Scanner(System.in); 

        System.out.println("Welcome to the ATM.");
        System.out.printf("Your current balance is: $%.2f%n", account.getBalance());
        System.out.print("Enter amount to withdraw: ");

        try {
            String input = scanner.nextLine();
            // This can throw NumberFormatException
            double amount = Double.parseDouble(input);

            // This can throw IllegalArgumentException or InsufficientFundsException
            account.withdraw(amount);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric input. Please enter numbers only.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // This block always executes
            System.out.printf("Final Account Balance: $%.2f%n", account.getBalance());
            scanner.close();
        }
    }
}