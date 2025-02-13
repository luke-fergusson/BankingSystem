import java.util.ArrayList;

public class Account {
    private String accountId;
    private double balance;
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public String getAccountId() {return accountId;}
    public double getBalance() {return balance;}

    public Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;

    }

    public void newTransaction(double amount, long time, Transaction.transactionType type) {
        transactions.add(new Transaction(time, amount, type));
    }

    public void deposit(double amount) {
        balance += amount;
    }
    public void withdraw(double amount) {
        balance -= amount;
        if(balance < 0){
            System.out.println("Insufficient funds");
            balance += amount;
        }
    }
}
