package bank.manager;

import bank.container.func.OperationContainer;
import bank.models.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * OPERATION CONTROLLER
 *
 * acts a middle man between main and Container
 */

public class OperationManager {
    private static OperationManager operationManager;
    private final OperationContainer operationContainer;
    private ArrayList<Withdraw> withdraws;
    private ArrayList<Deposit>  deposits;
    private ArrayList<Transfer> transfers;

    private OperationManager() throws IOException {
        this.operationContainer = new OperationContainer();
        operationContainer.TransactionFiles();
    }



    public double getBalance(int accNo){
        double balance = 0;
        withdraws = this.operationContainer.withdraws(accNo);
        deposits = this.operationContainer.deposits(accNo);
        transfers = this.operationContainer.transfers(accNo);

        for(int i = 0; i < deposits.size(); i++)
            balance += deposits.get(i).getAmt();
        for(int i = 0; i < withdraws.size(); i++)
            balance -= withdraws.get(i).getAmt();
        for(int i = 0; i < transfers.size(); i++){
            balance -= transfers.get(i).getAmt();
        }


        return balance;
    }

    public double getLoanBalance(int accNo){
        double balance = 0;
        deposits = this.operationContainer.deposits(accNo);

        for(int i = 0; i < deposits.size(); i++){
            if (i == 0){
                balance += deposits.get(i).getAmt();
            }else{
                balance -= deposits.get(i).getAmt();
            }

        }
            return balance;
    }

    public ArrayList<Transfer> getAccTransfers(int acc){
        transfers = this.operationContainer.transfers(acc);
        return transfers;
    }
    public ArrayList<Deposit> getAccDeposits(int acc){
        deposits = this.operationContainer.deposits(acc);
        return deposits;
    }

    public ArrayList<Withdraw> getAccWithdraws(int acc){
        withdraws = this.operationContainer.withdraws(acc);
        return withdraws;
    }

    public boolean makeDeposit(int accNo, double amt ){
        if (amt == 0){
            System.out.println("Invalid amount!");
            return false;
        }

        if (amt >= 500 && amt != 0){
            this.operationContainer.deposit(accNo,amt);
            System.out.println("Deposit was made successfully!");
            return true;
        }
        System.out.println("The amount is less than 500!");
        return false;
    }

    public boolean makeWithdraw(int accNo, double amt){
        if (amt == 0){
            System.out.println("Invalid amount!");
        }
        if (amt <= getBalance(accNo) && amt != 0 && (getBalance(accNo)-amt > 500)){
            this.operationContainer.withdraw(accNo,amt);
            System.out.println("Withdraw was made successfully!");
            return true;
        }
        System.out.println("This account has insufficient balance!");
        return false;
    }

    public boolean makeTransfer(int accNo, double amt, int targetNo){
        if ( amt == 0){
            System.out.println("Invalid amount!");
            return false;
        }
        if (amt <= getBalance(accNo) && amt != 0){
            this.operationContainer.transfer(accNo,amt,targetNo);
            System.out.println("Transfer was made successfully!");
            return true;
        }else{
            System.out.println("This account has insufficient balance!");
            return false;
        }
    }

    public double getPrevDeposit(int accNo){
        deposits = this.operationContainer.deposits(accNo);

        if(deposits.size() > 1){
            return deposits.get(deposits.size()-1).getAmt();
        }
        return 0;
    }

    public double getPrevWithdraw(int accNo){
        withdraws = this.operationContainer.withdraws(accNo);
        if (withdraws.size() > 1){
            return withdraws.get(withdraws.size() -1).getAmt();
        }
        return 0;
    }

    public double getPrevTransfer(int accNo){
        transfers = this.operationContainer.transfers(accNo);
        if (transfers.size() > 1){
            return transfers.get(transfers.size() -1).getAmt();
        }
        return 0;
    }



    public ArrayList<Deposit> getAllDeposit(){
        deposits = this.operationContainer.deposits();
        return deposits;
    }

    public ArrayList<Withdraw> getAllWithdraw(){
        withdraws = this.operationContainer.withdraws();
        return withdraws;
    }

    public ArrayList<Transfer> getAllTransfers(){
        transfers = this.operationContainer.transfers();
        return transfers;
    }

    public static OperationManager getInstance() throws IOException{
        if(operationManager == null){
            operationManager = new OperationManager();
        }
        return operationManager;
    }



}
