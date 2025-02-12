public class Transaction {
    enum transactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        SETUP
    }
    private long timeStamp;
    private double amount;

    public Transaction(long timeStamp, double amount, transactionType type) {
        this.timeStamp = timeStamp;
        this.amount = amount;

    }
}
