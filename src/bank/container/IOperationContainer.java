package bank.container;

import bank.models.Deposit;
import bank.models.Transfer;
import bank.models.Withdraw;

import java.util.ArrayList;

public interface IOperationContainer {
    void deposit(int accNo, double amt);
    void withdraw(int customerID, double amt);
    void transfer(int customerID, double amt,int targetID);


    ArrayList<Transfer> transfers();
    ArrayList<Deposit> deposits();
    ArrayList<Withdraw> withdraws();

    ArrayList<Transfer> transfers(int fromID);
    ArrayList<Deposit> deposits(int customerID);
    ArrayList<Withdraw> withdraws(int customerID);
}
