package learning;

import java.util.*;

//Interface
interface Loanable {
 void applyLoan(double amount);
}

//Abstract Class
abstract class Account {
 private String accountNumber;
 private String holderName;
 protected double balance;

 public Account(String accountNumber, String holderName, double balance) {
     this.accountNumber = accountNumber;
     this.holderName = holderName;
     this.balance = balance;
 }

 public String getAccountNumber() {
     return accountNumber;
 }

 public String getHolderName() {
     return holderName;
 }

 public double getBalance() {
     return balance;
 }

 public void deposit(double amount) {
     if (amount > 0) {
         balance += amount;
         System.out.println("Deposited: " + amount);
     } else {
         System.out.println("Invalid deposit amount.");
     }
 }

 public void withdraw(double amount) throws Exception {
     if (amount <= 0) {
         throw new Exception("Invalid withdrawal amount.");
     }
     if (amount > balance) {
         throw new Exception("Insufficient balance.");
     }
     balance -= amount;
     System.out.println("Withdrawn: " + amount);
 }

 public abstract void calculateInterest();

 public void display() {
     System.out.println("Account No: " + accountNumber);
     System.out.println("Holder: " + holderName);
     System.out.println("Balance: " + balance);
 }
}

//Savings Account
class SavingsAccount extends Account implements Loanable {
 private double interestRate;

 public SavingsAccount(String accNo, String name, double balance, double interestRate) {
     super(accNo, name, balance);
     this.interestRate = interestRate;
 }

 @Override
 public void calculateInterest() {
     double interest = balance * interestRate / 100;
     balance += interest;
     System.out.println("Savings Interest Added: " + interest);
 }

 @Override
 public void applyLoan(double amount) {
     System.out.println("Loan of " + amount + " approved for Savings Account.");
 }
}

//Current Account
class CurrentAccount extends Account implements Loanable {
 private double overdraftLimit;

 public CurrentAccount(String accNo, String name, double balance, double overdraftLimit) {
     super(accNo, name, balance);
     this.overdraftLimit = overdraftLimit;
 }

 @Override
 public void withdraw(double amount) throws Exception {
     if (amount <= 0) {
         throw new Exception("Invalid withdrawal amount.");
     }
     if (amount > balance + overdraftLimit) {
         throw new Exception("Overdraft limit exceeded.");
     }
     balance -= amount;
     System.out.println("Withdrawn with overdraft: " + amount);
 }

 @Override
 public void calculateInterest() {
     System.out.println("No interest for Current Account.");
 }

 @Override
 public void applyLoan(double amount) {
     System.out.println("Business Loan of " + amount + " approved for Current Account.");
 }
}

//Bank Class
class Bank {
 private List<Account> accounts = new ArrayList<>();

 public void addAccount(Account account) {
     accounts.add(account);
     System.out.println("Account added successfully.");
 }

 public Account findAccount(String accNo) {
     for (Account acc : accounts) {
         if (acc.getAccountNumber().equals(accNo)) {
             return acc;
         }
     }
     return null;
 }

 public void displayAllAccounts() {
     for (Account acc : accounts) {
         acc.display();
         System.out.println("----------------------");
     }
 }
}

//Main Class
public class BankManagementSystem {

 public static void main(String[] args) {
     Scanner sc = new Scanner(System.in);
     Bank bank = new Bank();

     while (true) {
         System.out.println("\n===== Bank Menu =====");
         System.out.println("1. Create Savings Account");
         System.out.println("2. Create Current Account");
         System.out.println("3. Deposit");
         System.out.println("4. Withdraw");
         System.out.println("5. Calculate Interest");
         System.out.println("6. Apply Loan");
         System.out.println("7. Display All Accounts");
         System.out.println("8. Exit");
         System.out.print("Choose option: ");

         int choice = sc.nextInt();
         sc.nextLine();

         try {
             switch (choice) {
                 case 1:
                     System.out.print("Enter Account No: ");
                     String sAccNo = sc.nextLine();
                     System.out.print("Enter Name: ");
                     String sName = sc.nextLine();
                     System.out.print("Enter Balance: ");
                     double sBal = sc.nextDouble();
                     System.out.print("Enter Interest Rate: ");
                     double rate = sc.nextDouble();
                     bank.addAccount(new SavingsAccount(sAccNo, sName, sBal, rate));
                     break;

                 case 2:
                     System.out.print("Enter Account No: ");
                     String cAccNo = sc.nextLine();
                     System.out.print("Enter Name: ");
                     String cName = sc.nextLine();
                     System.out.print("Enter Balance: ");
                     double cBal = sc.nextDouble();
                     System.out.print("Enter Overdraft Limit: ");
                     double limit = sc.nextDouble();
                     bank.addAccount(new CurrentAccount(cAccNo, cName, cBal, limit));
                     break;

                 case 3:
                     System.out.print("Enter Account No: ");
                     String dAcc = sc.nextLine();
                     Account depositAcc = bank.findAccount(dAcc);
                     if (depositAcc != null) {
                         System.out.print("Enter Amount: ");
                         double amt = sc.nextDouble();
                         depositAcc.deposit(amt);
                     } else {
                         System.out.println("Account not found.");
                     }
                     break;

                 case 4:
                     System.out.print("Enter Account No: ");
                     String wAcc = sc.nextLine();
                     Account withdrawAcc = bank.findAccount(wAcc);
                     if (withdrawAcc != null) {
                         System.out.print("Enter Amount: ");
                         double wAmt = sc.nextDouble();
                         withdrawAcc.withdraw(wAmt);
                     } else {
                         System.out.println("Account not found.");
                     }
                     break;

                 case 5:
                     System.out.print("Enter Account No: ");
                     String iAcc = sc.nextLine();
                     Account interestAcc = bank.findAccount(iAcc);
                     if (interestAcc != null) {
                         interestAcc.calculateInterest();
                     } else {
                         System.out.println("Account not found.");
                     }
                     break;

                 case 6:
                     System.out.print("Enter Account No: ");
                     String lAcc = sc.nextLine();
                     Account loanAcc = bank.findAccount(lAcc);
                     if (loanAcc != null && loanAcc instanceof Loanable) {
                         System.out.print("Enter Loan Amount: ");
                         double loanAmt = sc.nextDouble();
                         ((Loanable) loanAcc).applyLoan(loanAmt);
                     } else {
                         System.out.println("Loan not available for this account.");
                     }
                     break;

                 case 7:
                     bank.displayAllAccounts();
                     break;

                 case 8:
                     System.out.println("Thank you for using Bank System.");
                     System.exit(0);

                 default:
                     System.out.println("Invalid choice.");
             }
         } catch (Exception e) {
             System.out.println("Error: " + e.getMessage());
             sc.nextLine();
         }
     }
 }
}
