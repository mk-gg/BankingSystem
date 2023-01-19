package bank.manager;

import bank.container.func.CustomerContainer;
import bank.models.Customer;
import bank.util.Auth;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private static CustomerManager customerManager;
    private final CustomerContainer customerContainer;
    private List<Customer> customers;

    private CustomerManager() throws IOException {
        this.customerContainer = new CustomerContainer();
        customerContainer.CustomerFile();
    }

    public static CustomerManager getInstance() throws IOException{
        if (customerManager == null)
            customerManager = new CustomerManager();

        return customerManager;
    }

    public boolean login(String accNo, String pass) {
        customers = customerContainer.all();
        for (Customer i : customers) {
            Customer temp = i;
            if (temp.getAccNo().equals(accNo) && temp.getPassword().equals(pass)) {
                Auth.setAccNo(accNo);
                Auth.setName(temp.getName());
                Auth.setType("customer");
                return true;
            }
        }
        return false;
    }

    public ArrayList<Customer> getCustomers() {
        return customerContainer.all();
    }

    public ArrayList<Customer> getActiveCustomers() {return customerContainer.active();}

    public ArrayList<Customer> getClosedCustomers() {return customerContainer.closed();}

    public ArrayList<Customer> getInactiveCustomers(){return customerContainer.inactive();}

    public ArrayList<Customer> getDormantCustomers() {return customerContainer.dormant();}

    public ArrayList<Customer> getAccounts(String acc){return customerContainer.accounts(acc);}


    public void store(Customer customer) {

        customerContainer.store(customer);
    }

    public Customer searchCustomer(int accNo) {
        return customerContainer.find(accNo);
    }

    public void update(Customer customer) {
        customerContainer.update(customer);
    }

    public void delete(Customer customer) {
        customerContainer.delete(customer);
    }

    public void activate(Customer customer) { customerContainer.activate(customer);}



    public void displayAccount(Customer customer) {
        System.out.println("Account No   : " + customer.getAccNo());
        System.out.println("Name         : " + customer.getName());
        System.out.println("Address      : " + customer.getAddress());
        System.out.println("Gender       : " + customer.getGender());
        System.out.println("Birth date   : " + customer.getBirthDate());
        System.out.println("Age          : " + customer.getAge());
        System.out.println("Status       : " + customer.getStatus());
        System.out.println("Account Type : " + customer.getAccType());
    }

}
