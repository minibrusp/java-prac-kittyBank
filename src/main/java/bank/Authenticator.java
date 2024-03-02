package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {
  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);

    if (customer == null) {
      throw new LoginException("Incorrect login credentials");
    }

    if (password.equals(customer.getPassword())) {
      customer.setAuthenticated(true);
      return customer;
    } else
      throw new LoginException("Incorrect login credentials");
  }

  public static void logout(Customer customer) {
    customer.setAuthenticated(false);
  }
}
