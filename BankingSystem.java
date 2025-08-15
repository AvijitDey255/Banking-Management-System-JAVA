import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Account {
    String accountNumber;
    String username;
    String password;
    double balance;
    String lastLoginDate; 

    public Account(String accountNumber, String username, String password, double balance, String lastLoginDate) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String toString() {
        return "AccountNumber=" + accountNumber +
               ", Username=" + username +
               ", Password=" + password +
               ", Balance=" + balance +
               ", LastLogin=" + lastLoginDate;
    }
}

public class BankingSystem {
    static HashMap<String, Account> accounts = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static Account currentUser = null;
    static String FILE_NAME = "details.txt";
    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        loadAccounts();

        while (true) {
            if (currentUser == null) {
                System.out.println("\n=== Banking Management System ===");
                System.out.println("1. Sign Up (New User)");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        saveAccounts();
                        System.out.println("Thank you for using the system!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } else {
                showUserMenu();
            }
        }
    }

    static void loadAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 5) {
                    String accNo = data[0].split("=")[1];
                    String user = data[1].split("=")[1];
                    String pass = data[2].split("=")[1];
                    double bal = Double.parseDouble(data[3].split("=")[1]);
                    String lastLogin = data[4].split("=")[1];
                    accounts.put(user, new Account(accNo, user, pass, bal, lastLogin));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing accounts found. Please sign up first.");
        } catch (IOException e) {
            System.out.println("Error reading accounts file.");
        }
    }

    static void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accounts.values()) {
                bw.write(acc.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts file.");
        }
    }

    static void signUp() {
        System.out.print("Enter new username: ");
        String username = sc.nextLine();

        if (accounts.containsKey(username)) {
            System.out.println("Username already exists! Try another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String accountNumber = String.valueOf(1000 + accounts.size() + 1);
        String todayDate = dateFormat.format(new Date());
        Account newAcc = new Account(accountNumber, username, password, 0.0, todayDate);
        accounts.put(username, newAcc);
        saveAccounts();
        System.out.println("Account created successfully! Your account number: " + accountNumber);
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Account acc = accounts.get(username);
        if (acc != null && acc.password.equals(password)) {
            currentUser = acc;
            currentUser.lastLoginDate = dateFormat.format(new Date()); 
            saveAccounts();
            System.out.println("\nLogin successful!");
            System.out.println("Account Number: " + currentUser.accountNumber);
            System.out.println("Current Balance: Rs " + currentUser.balance);
            System.out.println("Last Login Date: " + currentUser.lastLoginDate);
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    static void logout() {
        currentUser = null;
        System.out.println("\nLogged out successfully.");
    }

    static void showUserMenu() {
        System.out.println("\n=== Welcome " + currentUser.username + " ===");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Your Balance: Rs " + currentUser.balance);
                break;
            case 2:
                deposit();
                break;
            case 3:
                withdraw();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    static void deposit() {
        System.out.print("Enter amount to deposit: Rs ");
        double amount = sc.nextDouble();
        if (amount > 0) {
            currentUser.balance += amount;
            System.out.println("Deposit successful! New Balance: Rs " + currentUser.balance);
            saveAccounts();
        } else {
            System.out.println("Invalid amount!");
        }
    }

    static void withdraw() {
        System.out.print("Enter amount to withdraw: Rs ");
        double amount = sc.nextDouble();
        if (amount > 0 && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            System.out.println("Withdrawal successful! New Balance: Rs " + currentUser.balance);
            saveAccounts();
        } else {
            System.out.println("Invalid amount or insufficient balance!");
        }
    }
}
