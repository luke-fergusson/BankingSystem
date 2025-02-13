/*

        Author: Luke Fergusson

 */
import java.text.DecimalFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class Bank {
    public static Date date;
    //to display the balance of accounts to 2dp
    final public static DecimalFormat df = new DecimalFormat("#.00");

    private static ArrayList<User> users = new ArrayList<>();
    private  ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        boolean originalEmail = true;
        boolean serviceStillInUser = true;
        //Users can only gain access if they sign up or login
        while (serviceStillInUser){
            if(menu().equalsIgnoreCase("sign up")) {
                signUp(originalEmail);
            }
            if(menu().equalsIgnoreCase("login")){
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
        String registered = sc.nextLine();
        return registered;
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
        String accountId = sc.nextLine();
        for(Account a: currentUser.getAccounts()){
            if(a.getAccountId().equals(accountId)){
                System.out.println("How much do you want to withdraw?");
                double withdrawAmount = sc.nextDouble();
                date = new Date();
                a.newTransaction(withdrawAmount, date.getTime(), Transaction.transactionType.WITHDRAWAL);
                a.withdraw(withdrawAmount);
            }
        }

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
            }else {
                System.out.println("Account number does not match");
            }
        }
        bankingOptions();
    }
    //generates a new user
    private static void signUp(boolean originalEmail) {
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email address: ");
        String email = sc.nextLine();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                System.out.println("That email already exists!");
                originalEmail = false;
            }
        }
        if (originalEmail) {
            System.out.println("Enter your password: ");
            String password = sc.nextLine();

            users.add(new User("00000001", name, email, password));
        }
        System.out.println("Sign up Successful");
    }
    //checks if inputted values are valid
    private static boolean login() {
        System.out.println("--------------------");
        System.out.println("Enter your email address: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        User u;
        for(User user : users) {
            if(Objects.equals(user.getEmail(), email)){
                if(user.getPassword().equals(password)) {
                    currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }
}