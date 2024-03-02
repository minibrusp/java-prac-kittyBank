package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("Database connection established!");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    try (
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static Account getAccount(int accountId) {
    String sql = "select * from accounts where id = ?";
    Account account = null;

    try (
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setInt(1, accountId);

      try (ResultSet resultSet = statement.executeQuery()) {
        account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return account;
  }

  public static void main(String[] args) {
    // connect();

    // Scanner scanner = new Scanner(System.in);

    // String username = null;
    // String password = null;

    // System.out.println("Enter username");
    // username = scanner.next();

    // System.out.println("Enter password");
    // password = scanner.next();

    // Customer customer = null;

    // try {
    // customer = Authenticator.login(username, password);
    // } catch (LoginException e) {
    // System.out.println("There was an error: " + e.getMessage());
    // }

    // if (customer != null && customer.isAuthenticated()) {
    // System.out.println(customer.getName());

    // Account account = getAccount(customer.getAccountId());
    // System.out.println(account.getBalance());
    // }

    // scanner.close();

    // Customer customer = getCustomer("twest8o@friendfeed.com");
    // Customer customer = getCustomer(username);
    // System.out.println(customer.getName());

    // Account account = getAccount(customer.getAccountId());
    // System.out.println(account);
  }

}
