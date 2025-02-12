import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private static ArrayList<Account> accounts = new ArrayList<>();

    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getId(){return id;}
    public String getName(){return name;}
    public ArrayList<Account> getAccounts(){return accounts;}

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        accounts.add(new Account("#1", 0.0));

    }

}
