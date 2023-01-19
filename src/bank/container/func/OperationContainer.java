/**
    This class will do the operations such as
    deposit and withdraw
 */
package bank.container.func;

import bank.container.IOperationContainer;
import bank.models.Customer;
import bank.models.Deposit;
import bank.models.Transfer;
import bank.models.Withdraw;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OperationContainer  implements IOperationContainer {
    private ArrayList<Deposit> deposits = new ArrayList<>();
    private ArrayList<Withdraw> withdraws = new ArrayList<>();
    private ArrayList<Transfer> transfers = new ArrayList<>();


    public void TransactionFiles() throws IOException{
        transfers();
        withdraws();
        deposits();



        File depo = new File("deposits.txt");
        if(!depo.exists()){
            System.out.println("File [deposits.txt] not found! ");
            depo.createNewFile();
        }else{
            System.out.println("File [deposits.txt] detected!");
            try{
                BufferedReader depoFile = new BufferedReader(new FileReader("deposits.txt"));
                String input;
                while((input = depoFile.readLine()) != null){
                    String[] data = input.split("\\|");
                    String accN = data[0];
                    String amt = data[1];
                    String date = data[2];
                    Deposit x = new Deposit(accN,Double.parseDouble(amt),date);
                    deposits.add(x);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        File withd = new File("withdraws.txt");
        if(!withd.exists()){
            System.out.println("File [withdraws.txt] not found! ");
            withd.createNewFile();
        }else{
            System.out.println("File [withdraws.txt] detected!");
            try{
                BufferedReader withFile = new BufferedReader(new FileReader("withdraws.txt"));
                String input;
                while((input = withFile.readLine()) != null){
                    String[] data = input.split("\\|");
                    String accN = data[0];
                    String amt = data[2];
                    String date = data[1];
                    Withdraw x = new Withdraw(accN,date,Double.parseDouble(amt));
                    withdraws.add(x);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        File transf = new File("transfers.txt");
        if(!transf.exists()){
            System.out.println("File [transfers.txt] not found! ");
            transf.createNewFile();
        }else{
            System.out.println("File [transfers.txt] detected!");
            try{
                BufferedReader transfFile = new BufferedReader(new FileReader("transfers.txt"));
                String input;
                while((input = transfFile.readLine()) != null){
                    String[] data = input.split("\\|");
                    String accN = data[0];
                    String amt = data[2];
                    String date = data[1];
                    String targetAcc = data[3];
                    Transfer x = new Transfer(accN,date,Double.parseDouble(amt),targetAcc);
                    transfers.add(x);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }



    }

    @Override
    public void transfer(int customerID, double amt,int targetID ) {
        transfers().add(transferMapper(Integer.toString(customerID),amt, Integer.toString(targetID)));
    }

    @Override
    public void deposit(int accNo, double amt) {
        deposits.add(depositMapper(Integer.toString(accNo),amt));
    }

    @Override
    public void withdraw(int customerID, double amt) {
        withdraws.add(withdrawMapper(Integer.toString(customerID),amt));
    }



    @Override
    public ArrayList<Transfer> transfers(){
        if (transfers == null) transfers = new ArrayList<>();
        return transfers;
    }

    // Get all deposits object
    @Override
    public ArrayList<Deposit> deposits() {
        if (deposits == null) deposits = new ArrayList<>();
        return deposits;
    }

    // Get all withdraws object
    @Override
    public ArrayList<Withdraw> withdraws() {
        if(withdraws == null) withdraws = new ArrayList<>();
        return withdraws;
    }

    /**
        Get all deposits from a specific customer
        given by an customer id
     */

    @Override
    public ArrayList<Transfer> transfers(int customerID){
        if (transfers == null) transfers = new ArrayList<>();
        ArrayList<Transfer> temp = new ArrayList<>();
        for(Transfer i : transfers){
            if (customerID == Integer.parseInt(i.getCustAccNo())){
                temp.add(i);
            }
        }
        return temp;
    }


    @Override
    public ArrayList<Deposit> deposits(int customerID) {
        if (deposits == null) deposits = new ArrayList<>();
        ArrayList<Deposit> temp = new ArrayList<>();
        for(Deposit i : deposits){
            if (customerID == Integer.parseInt(i.getAccNo())){
                temp.add(i);
            }
        }
        return temp;
    }

    /*
    Get all withdraws from a specific customer
    given by an customer id
    */

    @Override
    public ArrayList<Withdraw> withdraws(int customerID) {
        if(withdraws == null) withdraws = new ArrayList<>();
        ArrayList<Withdraw> temp = new ArrayList<>();
        for(Withdraw i : withdraws){
            if (customerID == Integer.parseInt(i.getAccNo())){
                temp.add(i);
            }
        }

        return temp;
    }

    /*
        Get the current date
     */
    private String getDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss aaa");
        String strDate = dateFormat.format(date);

        return strDate;
    }

    /*

     */
    private Deposit depositMapper(String accNo, double amt){
        return new Deposit(accNo,amt,getDate());
    }

    private Transfer transferMapper(String accNo, double amt, String accN){
        return new Transfer(accNo,getDate(),amt,accN);
    }
    private Withdraw withdrawMapper(String accNo, double amt){
        return new Withdraw(accNo,getDate(),amt);
    }



}
