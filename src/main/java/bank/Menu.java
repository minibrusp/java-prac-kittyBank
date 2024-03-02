package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("\nMewwwwwwwww Meow to Kitty Bank");
    System.out.println("\nSample Account username: twest8o@friendfeed.com pass: KyWrO9(HcR9 ");
    System.out.println("or view resources bank.db w/ an sqlite viewer \n");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();

  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;

    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }

    return customer;
  }

  public void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("\n===============================================");
      System.out.println("Please select on of the following options: ");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check Balance");
      System.out.println("4: Exit");
      System.out.println("===============================================");

      selection = scanner.nextInt();
      double amount = 0;

      switch (selection) {
        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please trry again");
          }
          break;
        case 2:
          System.out.println("How much would you like to withdraw");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking @ Kitty Bank! meeewwwwwwwww ~");
          break;
        default:
          System.out.println("Invalid option. Please try again");
      }
    }

  }

}
