/*

        Author: Luke Fergusson

 */
import java.text.DecimalFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class Bank {
    public static Date date;
    static final String Bank_DB = "jdbc:mysql://localhost:3306/BANK?useSSL=false&serverTimezone=UTC";

    static final String User_DB = "root";
    static final String Pass_DB = "!Testmysqlserver123";
    //to display the balance of accounts to 2dp
    final public static DecimalFormat df = new DecimalFormat("0.00");

    private static final Scanner sc = new Scanner(System.in);
    private static  User currentUser = new User(null, null, null, null);
    private static Account account =  new Account(null, 0.0);

    private static ArrayList<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        boolean serviceStillInUser = true;

        //Users can only gain access if they sign up or login
        while (serviceStillInUser){
            String service = menu();
            if(service.equalsIgnoreCase("sign up")) {
                signUp();
            }
            if(service.equalsIgnoreCase("login")){
                boolean loginComplete = false;
                while (!loginComplete){
                    if (login()) {
                        System.out.println("Login Successful");
                        loginComplete = true;
                        serviceStillInUser = false;
                    } else {
                        System.out.println("Login Failed");
                    }
                }
            }

        }
        bankingOptions();
    }
    //displays the menu
    private static String menu() {
        System.out.println("Do you want to login or sign up");
        return sc.nextLine();
    }
    //Allows user to select service they want
    private static void bankingOptions() {
        System.out.println("--------------------");
        System.out.println("1. Show balance");
        System.out.println("2. withdraw amount");
        System.out.println("3. Deposit");
        System.out.println("4. Exit");
        switch (sc.nextInt()){
            case 1 -> displayBalance();
            case 2 -> withdraw();
            case 3 -> deposit();
            case 4 -> {System.exit(0);}
        }

    }
    //displays all the users accounts and there balances
    private static void displayBalance() {
        System.out.println("--------------------");
        System.out.println("ID: " + currentUser.getId());
        System.out.println("Name: " + currentUser.getName());
        for(Account a: currentUser.getAccounts()){
            System.out.println("--------------------");
            System.out.println("Account number: " + a.getAccountId());
            System.out.println("Balance: £" + df.format(a.getBalance()));
            sc.nextLine();
            System.out.println("PRESS ENTER TO RETURN");
            sc.nextLine();

            bankingOptions();
        }

    }
    //removes money from a specific account
    private static void withdraw() {
        System.out.println("--------------------");
        System.out.println("Out of which account: ");
        sc.nextLine();
        String accountNumber = sc.nextLine();
        for(Account a: currentUser.getAccounts()){
            if(a.getAccountId().equals(accountNumber)){
                System.out.println("How much do you want to withdraw?");
                double withdrawAmount = sc.nextDouble();
                date = new Date();
                a.newTransaction(withdrawAmount, date.getTime(), Transaction.transactionType.WITHDRAWAL);
                if(a.withdraw(withdrawAmount)){
                    updateBalance(a);
                }

            }
            else {
                System.out.println("Invalid account number");
            }
        }
        bankingOptions();
    }
    //adds money to a specific account
    private static void deposit() {
        System.out.println("--------------------");
        System.out.println("Enter account number");
        sc.nextLine();
        String accountNumber = sc.nextLine();
        System.out.println("How much do you want to deposit: ");
        System.out.print("£");
        double amount = sc.nextDouble();
        for(Account a: currentUser.getAccounts()){
            if(a.getAccountId().equals(accountNumber)){
                date = new Date();
                a.newTransaction(amount, date.getTime() , Transaction.transactionType.DEPOSIT);
                a.deposit(amount);
                updateBalance(a);
            }else {
                System.out.println("Account number does not match");
            }
        }
        bankingOptions();
    }
    //generates a new user
    private static void signUp() {
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email address: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();
        int newId = newID();

        String queryNewUser = "insert into User(id, name, email, password)" +
                " values ('" + newId + "', '" + name + "', '" + email + "', '"+ password +"')";
        try {
            Connection conn = DriverManager.getConnection(Bank_DB, User_DB, Pass_DB);
            Statement stmt = conn.createStatement();
            int entryInserted = stmt.executeUpdate(queryNewUser);
            if (entryInserted > 0) {
                System.out.println("sign up Successful");
            }else {
                System.out.println("sign up Failed");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        currentUser.setId(Integer.toString(newId));
        currentUser.setName(name);
        currentUser.setEmail(email);
        currentUser.setPassword(password);

        String queryNewAccount = "insert into Accounts(accountID, id, balance)" +
                " values ('" + newID() + "', '" + currentUser.getId() + "', '"+ 0.00 +"')";
        try {
            Connection conn = DriverManager.getConnection(Bank_DB, User_DB, Pass_DB);
            Statement stmt = conn.createStatement();
            int entryInserted = stmt.executeUpdate(queryNewAccount);
            if (entryInserted > 0) {
                System.out.println("Account creation Successful");
            }else {
                System.out.println("Account creation Failed");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //checks if inputted values are valid
    private static boolean login() {
        System.out.println("--------------------");
        System.out.println("Enter your email address: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        String query = "SELECT * FROM USER WHERE EMAIL = '" + email + "'" + " AND PASSWORD = '" + password + "'";
        try {
            Connection conn = DriverManager.getConnection(Bank_DB, User_DB, Pass_DB);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                currentUser.setId(rs.getString("id"));
                currentUser.setName(rs.getString("name"));
                currentUser.setEmail(rs.getString("email"));
                getAccounts();
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void getAccounts(){
        String allAccounts = "SELECT * FROM Accounts WHERE id = '" + currentUser.getId() + "'" ;

        try {
            Connection conn = DriverManager.getConnection(Bank_DB, User_DB, Pass_DB);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(allAccounts);
            while (rs.next()) {
                account.setAccountId(rs.getString("accountID"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        currentUser.setAccounts(accounts);
        System.out.println(currentUser.getAccounts());
    }
    public static int newID(){
        return (int) (Math.random() * 100000000);
    }
    private static void updateBalance(Account account) {
        String queryNewBalance = "update Accounts set balance = " + "'" + account.getBalance() + "' where accountID = '" + account.getAccountId() + "'";
        try {
            Connection conn = DriverManager.getConnection(Bank_DB, User_DB, Pass_DB);
            Statement stmt = conn.createStatement();
            int entryInserted = stmt.executeUpdate(queryNewBalance);
            if (entryInserted > 0) {
                System.out.println("Transaction Successful");
            }else {
                System.out.println("Transaction Failed");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}