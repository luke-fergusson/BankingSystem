public class Transaction {
    enum transactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        SETUP
    }
    long timeStamp;
    float amount;

    public Transaction(long timeStamp, float amount, transactionType type) {
        this.timeStamp = timeStamp;
        this.amount = amount;

    }
}
