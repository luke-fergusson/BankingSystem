import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Bank {
    private static ArrayList<User> users = new ArrayList<>();
    private  ArrayList<Account> accounts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Date date = new Date();

        boolean originalEmail = true;
        boolean serviceStillInUser = true;
        do{
            if(menu().equalsIgnoreCase("sign up")) {
                signUp(originalEmail);
            }
            if(menu().equalsIgnoreCase("login")){
                do {
                    if (login()) {
                        System.out.println("Login Successful");
                        serviceStillInUser = false;
                    } else {
                        System.out.println("Login Failed");
                    }
                }while (!login());
            }
        }while (serviceStillInUser);


    }
    private static String menu() {
        System.out.println("Do you want to login or sign up");
        String registered = sc.nextLine();
        return registered;
    }
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

            users.add(new User("1", name, email, password));
        }
    }
    private static boolean login() {
        System.out.println("Enter your email address: ");
        String email = sc.nextLine();
        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        User u;
        for(User user : users) {
            if(Objects.equals(user.getEmail(), email)){
                if(user.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}