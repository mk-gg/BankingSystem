package bank.container;

import bank.models.Customer;

import java.util.ArrayList;

public interface ICustomerContainer {
    ArrayList<Customer> all();

    ArrayList<Customer> active();

    ArrayList<Customer> inactive();

    ArrayList<Customer> dormant();

    ArrayList<Customer> closed();

    ArrayList<Customer> accounts(String acc);

    Customer find(int accNo);


    void update(Customer customer);

    void store(Customer customer);

    void delete(Customer customer);

    void activate(Customer customer);



}
