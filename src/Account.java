import java.util.ArrayList;

public class Account {
    private String accountId;
    private double balance;
    private static ArrayList<Transaction> transactions = new ArrayList<>();


    public Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;

    }
}
