import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private byte[] salt;
    private static ArrayList<Account> accounts = new ArrayList<>();

    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getId(){return id;}
    public String getName(){return name;}
    public ArrayList<Account> getAccounts(){return accounts;}

    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setId(String id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setAccounts(ArrayList<Account> accounts){this.accounts = accounts;}
    public void setSalt(byte[] salt){this.salt = salt;}

    public User(String id, String name, String email, String password, byte[] salt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.salt = salt;
        accounts.add(new Account("#1", 0.0));

    }

}
