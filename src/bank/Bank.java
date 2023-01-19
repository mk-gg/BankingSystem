package bank;

import bank.container.func.InputController;
import bank.manager.CustomerManager;
import bank.manager.OperationManager;
import bank.manager.StaffManager;
import bank.models.*;
import tableprinting.FlipTableConverters;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.lang.Math;

public class Bank {
    private static CustomerManager customermgr;
    private static OperationManager opermgr;
    private static StaffManager staffmgr;
    private static ArrayList<Customer> customers;
    private static ArrayList<Staff> staffs;
    private static ArrayList<Deposit> deposits;
    private static ArrayList<Withdraw> withdraws;
    private static ArrayList<Transfer> transfers;

    public static void initializeControls() throws IOException {
        customermgr = CustomerManager.getInstance();
        opermgr = OperationManager.getInstance();
        staffmgr = StaffManager.getInstance();
    }

    /**
        Menu Display
        Section
     */
    public static void Menu() {
        System.out.println("-=====================-");
        System.out.println("     Welcome to ");
        System.out.println("     SEVASTOLINK");
        System.out.println(" [1]  Login");
        System.out.println(" [2]  Exit system");
        System.out.println("-=====================-");
    }

    public static void CustomerMenu() {
        System.out.println("-=====================-");
        System.out.println(" [1]  Deposit Account");
        System.out.println(" [2]  Withdraw Account");
        System.out.println(" [3]  View Account");
        System.out.println(" [4]  Log Out");
        System.out.println("-=====================-");
    }

    public static void TellerMenu(int accID) {
        Staff staff;
        staff = staffmgr.findID(accID);
        System.out.println("-=====================-");
        System.out.println(" Welcome, " + staff.getName() + " !\n");
        System.out.println(" [1]  Transaction Section");
        System.out.println(" [2]  Log out\n");
        System.out.println("-=====================-");
    }

    public static void TransactionSect(int accID) {
        Staff staff;
        staff = staffmgr.findID((accID));
        System.out.println("-=====================-");
        System.out.println("  TRANSACTION SECTION");
        System.out.println(" [1]  Open Account             [6] Update Account");
        System.out.println(" [2]  Deposit Funds            [7] Back");
        System.out.println(" [3]  Withdraw Funds");
        System.out.println(" [4]  Transfer Funds");
        System.out.println(" [5]  View Account");
        System.out.println("-=====================-");
    }

    public static void TellerSect(int accID) {
        Staff staff;
        staff = staffmgr.findID((accID));
        System.out.println("-=====================-");
        System.out.println("    TELLER  SECTION");
        System.out.println(" [1]  Register Staff           [6] View Registered Accounts");
        System.out.println(" [2]  Update Account           [7] View Active Accounts");
        System.out.println(" [3]  View Account             [8] View Inactive Accounts");
        System.out.println(" [4]  Remove Account           [9] Back");
        System.out.println(" [5]  Activate Account");
        System.out.println("-=====================-");
    }

    public static void CustomerSect(int accID) {
        Staff staff;
        staff = staffmgr.findID((accID));
        System.out.println("-=====================-");
        System.out.println("   CUSTOMER  SECTION");
        System.out.println(" [1]  Update Account           [7]  View Inactive Accounts");
        System.out.println(" [2]  View Account             [8]  View Closed Accounts");
        System.out.println(" [3]  Remove Account           [9]  View Dormant Accounts");
        System.out.println(" [4]  Activate Account         [10] View Customers Bank Accounts");
        System.out.println(" [5]  View All Accounts        [11] Back");
        System.out.println(" [6]  View Active Accounts");
        System.out.println("-=====================-");
    }

    public static void LogHistorySect(int accID) {
        Staff staff;
        staff = staffmgr.findID((accID));
        System.out.println("-=====================-");
        System.out.println("  LOG HISTORY SECTION");
        System.out.println(" [1]  All Deposit History      [6] Customer Transfer Funds ");
        System.out.println(" [2]  All Withdraw History     [7] Back ");
        System.out.println(" [3]  All Transfer History  ");
        System.out.println(" [4]  Customer Deposit");
        System.out.println(" [5]  Customer Withdraw ");
        System.out.println("-=====================-");
    }

    public static void DepositSlip(String name, String acc, double amt){
        System.out.println("-=====================-");
        System.out.println("  DEPOSIT FUNDS SLIP  ");
        System.out.println(" CUSTOMER -");
        System.out.println(" [1] Name        : " + name);
        System.out.println(" [2] Account no. : " + acc);
        System.out.println(" FUNDS - ");
        System.out.println(" [3] Amount      : " + amt);
        System.out.println("\n [4] Enter all   ");
        System.out.println(" [5] Confirm");
        System.out.println(" [6] Exit");
        System.out.println("-=====================-");
    }

    public static void WithdrawSlip(String name, String acc, double amt){
        System.out.println("-=====================-");
        System.out.println("  WITHDRAW FUNDS SLIP  ");
        System.out.println(" CUSTOMER -");
        System.out.println(" [1] Name        : " + name);
        System.out.println(" [2] Account no. : " + acc);
        System.out.println(" FUNDS - ");
        System.out.println(" [3] Amount      : " + amt);
        System.out.println("\n [4] Enter all   ");
        System.out.println(" [5] Confirm");
        System.out.println(" [6] Exit");
        System.out.println("-=====================-");
    }

    public static void TransferFundsSlip(String name, String acc, double amt, String targetName, String targetNo) {
        System.out.println("-=====================-");
        System.out.println("  TRANSFER FUNDS SLIP");
        System.out.println(" FROM -");
        System.out.println(" [1] Name        : " + name);
        System.out.println(" [2] Account no. : " + acc);
        System.out.println(" TO - ");
        System.out.println(" [3] Name        : " + targetName);
        System.out.println(" [4] Account no. : " + targetNo);
        System.out.println(" FUNDS - ");
        System.out.println(" [5] Amount      : " + amt);
        System.out.println("\n [6] Enter all   ");
        System.out.println(" [7] Confirm");
        System.out.println(" [8] Exit");
        System.out.println("-=====================-");
    }

    public static void StaffMenu(int accID) {
        Staff staff;
        staff = staffmgr.findID(accID);
        customers = customermgr.getCustomers();
        staffs = staffmgr.getStaffs();
        ArrayList<Staff> staff_inactive = staffmgr.getInactiveStaffs();
        ArrayList<Staff> staff_active = staffmgr.getActiveStaffs();
        ArrayList<Customer> cust_active = customermgr.getActiveCustomers();
        ArrayList<Customer> cust_closed = customermgr.getClosedCustomers();
        ArrayList<Customer> cust_inactive = customermgr.getInactiveCustomers();
        ArrayList<Customer> cust_dormant = customermgr.getDormantCustomers();
        System.out.println("-=====================-");
        System.out.println(" Welcome, " + staff.getName() + " !\n");
        System.out.format("%-12s%-12s%-12s%-12s%-12s%-12s\n", "", "Total", "Active", "Inactive","Dormant", "Closed");
        System.out.format("%-12s%-12s%-12s%-12s%-12s%-12s\n", "Customer", customers.size(), cust_active.size(), cust_inactive.size(), cust_dormant.size(),cust_closed.size());
        System.out.format("%-12s%-12s%-12s%-12s\n", "Staffs", staffs.size(), staff_active.size(), staff_inactive.size());
        System.out.println(" [1]  Transaction Section");
        System.out.println(" [2]  Customer Section");
        System.out.println(" [3]  Teller Section");
        System.out.println(" [4]  Log History Section");
        System.out.println(" [5]  Log out");
        System.out.println("-=====================-");
    }


    /**
     * Teller Operations
     *
     * such as create, delete, update, activate
     */

    public static void registerStaff() {
        Staff staff = new Staff();
        staffmgr.store(staff);
    }

    public static void deleteStaff() {
        int accNo = InputController.getInteger("Enter account no");
        Staff staff;
        staff = staffmgr.findID(accNo);
        if (staff != null) {
            staffmgr.delete(staff);
        } else {
            System.out.println("Account not found");
        }
    }

    public static void updateStaff() {
        int accNum = InputController.getInteger("Enter account no");
        Staff staff;
        staff = staffmgr.findID(accNum);
        if (staff != null) {
            staffmgr.update(staff);
        } else {
            System.out.println("Account Not found");
        }
    }

    public static void searchStaff() {
        int accNo = InputController.getInteger("Enter account no");
        Staff staff;
        staff = staffmgr.findID(accNo);
        if (staff != null) {
            staffmgr.displayAccount(staff);
        } else {
            System.out.println("Account not found.");

        }
    }

    public static void activateStaff(){
        int accNo = InputController.getInteger("Enter account no");
        Staff staff;
        staff = staffmgr.findID(accNo);
        if (staff != null) {
            if (staff.getStatus() != "Active") {
                staffmgr.activate(staff);
            } else {
                System.out.println("Account is already Active!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    /**
     * Customer Operations
     *
     * such as search, update, delete, activate, create
     */


    public static void searchCustomer() throws ParseException{
        int accNo = InputController.getInteger("Enter account no");
        Customer customer;
        customer = customermgr.searchCustomer(accNo);
        if (customer != null) {
            customermgr.displayAccount(customer);
            if (customer.getAccType() == Customer.AccountType.SAVINGS){
                System.out.printf("Total balance: $%.2f\n", opermgr.getBalance(accNo));
            }else if(customer.getAccType() == Customer.AccountType.CREDITS){

                System.out.printf("Total loan   : $%.2f\n", opermgr.getLoanBalance(accNo));
                System.out.printf("Term Duration: %s\n", customer.getTerm() + " months");
                System.out.printf("Term Started : %s\n",getTermDate(accNo));
                System.out.printf("Term Due Date: %s\n",getDueDate(accNo));
            }

        } else {
            System.out.println("Account not found.");
        }
    }

    public static void updateCustomer() {
        int accNo = InputController.getInteger("Enter account no");
        Customer customer;
        customer = customermgr.searchCustomer(accNo);
        if (customer != null) {
            if (customer.getStatus() == "Active") {
                customermgr.update(customer);
            } else {
                System.out.println("Unable to edit credential!\nAccount needs to be Active!");
            }

        } else {
            System.out.println("Account Not found");
        }
    }

    public static void deleteCustomer() {
        int accNo = InputController.getInteger("Enter account no");
        Customer customer;
        customer = customermgr.searchCustomer(accNo);
        if (customer != null) {
            if (customer.getStatus() != "Closed") {
                customermgr.delete(customer);
            } else {
                System.out.println("Account is already closed!");
            }
        } else {
            System.out.println("Account not found");
        }

    }

    public static void activateCustomer() {
        int accNo = InputController.getInteger("Enter account no");
        Customer customer;
        customer = customermgr.searchCustomer(accNo);
        if (customer != null) {
            if (customer.getStatus() != "Active") {
                customermgr.activate(customer);
            } else {
                System.out.println("Account is already Active!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void openAccount() {
        Customer customer = new Customer();
        customermgr.store(customer);
        int term;
        boolean isCorrect = false;
        if (customer.getAccType() == Customer.AccountType.SAVINGS){
            System.out.println("Enter initial deposit [ Min. $500.00  Max. $20,000.00 ] ");
            double amt = InputController.getAmount("Enter amount");
            opermgr.makeDeposit(Integer.parseInt(customer.getAccNo()),amt);
        }else if(customer.getAccType() == Customer.AccountType.CREDITS){
            double interest = 5.0;
            System.out.println("Enter loan amount [ Min. $500.00  Max. $20,000.00 ]");
            double amt = InputController.getAmount("Enter amount");
            System.out.println("Enter how many months [ Min. 1  Max. 3 ]");
            term = InputController.getTerm("Enter the month");
            double rateOfInterest = interest / 1200;
            double numberOfPayments = term;

            double paymentAmount = (rateOfInterest * amt) / (1 - Math.pow(1 + rateOfInterest, numberOfPayments * -1));


            amt = paymentAmount * numberOfPayments;


            isCorrect = opermgr.makeDeposit(Integer.parseInt(customer.getAccNo()),Math.ceil(amt));

            if (isCorrect == true){
                customer.setTerm(Integer.toString(term));
            }
        }

        if (isCorrect == true){
            System.out.println("\nCustomer account created successfully!");

            System.out.println("Account Number : " + customer.getAccNo()  + "  UID : " + customer.getID());;
        }else{
            System.out.println("\nCustomer account was not made!");
        }


    }


    /**
     * Operational Methods
     *
     * such as (deposit,transfer,withdraw,view)
     */
    // For customer functions

    public static void searchAccount(int accNo) {
        Customer customer;
        customer = customermgr.searchCustomer(accNo);
        if (customer != null) {
            customermgr.displayAccount(customer);
            System.out.printf("Total balance: $%.2f\n", opermgr.getBalance(accNo));
        } else {
            System.out.println("Account not found.");
        }
    }

    public static boolean checkStatus(Customer customer){
        if ("Active".equalsIgnoreCase(customer.getStatus()) || "Inactive".equalsIgnoreCase(customer.getStatus())){
            return true;
        }


        return false;
    }

    public static boolean checkDeposit(int accNO, String name) {
        Customer customer;
        boolean isOperable = false;
        customer = customermgr.searchCustomer(accNO);
        if (customer != null) {
            isOperable = checkStatus(customer);
            if (Objects.equals(name, customer.getName())){
                if (isOperable){
                    if(customer.getAccType() == Customer.AccountType.SAVINGS){
                        double balance = opermgr.getBalance(accNO);
                        double prevDeposit = opermgr.getPrevDeposit(accNO);
                        System.out.printf("Total balance    : $%.2f\n", balance);
                        System.out.printf("Previous Deposit : $%.2f\n", prevDeposit);
                        return true;
                    }else{
                        double balance = opermgr.getLoanBalance(accNO);
                        double prevDeposit = opermgr.getPrevDeposit(accNO);
                        System.out.printf("Total Loan Amt   : $%.2f\n", balance);
                        System.out.printf("Previous Payment : $%.2f\n", prevDeposit);
                        return true;
                    }

                }else{
                    System.out.println("Account needs to be re-activated!");
                    return false;
                }
            }else{
                System.out.println("Account name not found!!");
                return false;
            }
        } else {
            System.out.println("Account Not found");
            return false;
        }
    }

    public static void doDeposit(int accNO){
        accNO = 0;
        String name = "";
        double amt = 0;
        main : while(true){
            DepositSlip(name, String.valueOf(accNO),amt);
            int num = InputController.getInteger("Choose (1 - 6)");
            switch (num){
                case 1:
                    name =  InputController.getPassInput("Enter Name");
                    break;
                case 2:
                    accNO = InputController.getInteger("Enter account no. ");
                    break;
                case 3:
                    amt = InputController.getAmount("Enter amount");
                    break;
                case 4:
                    lop : while (true){
                        char yes = InputController.getChar("Are you sure you want to enter all [Y/N]?");
                        switch(Character.toUpperCase(yes)) {
                            case 'Y':
                                name = InputController.getPassInput("Enter Name");
                                accNO = InputController.getInteger("Enter account no. ");
                                amt = InputController.getAmount("Enter amount");
                                break lop;
                            case 'N':
                                break lop;
                            default:
                                System.out.println("Invalid Input!");
                                break;
                        }
                    }
                    break;
                case 5:
                    Customer customer = customermgr.searchCustomer(accNO);
                    if (checkDeposit(accNO,name)){
                        lop : while (true){
                            char yes = InputController.getChar("Are you sure you want to deposit [Y/N]?");
                            switch(Character.toUpperCase(yes)) {
                                case 'Y':
                                    double balance = opermgr.getLoanBalance(accNO);
                                    balance -= amt;
                                    if (balance < 0){
                                        System.out.println("The amount is greater than the total loan! ");
                                        break;
                                    }else{
                                        opermgr.makeDeposit(accNO,amt);
                                        if (customer.getStatus() == "Inactive"){
                                            customermgr.activate(customer);
                                        }

                                        if (balance == 0){
                                            System.out.println("You have successfully paid the loan!");
                                            System.out.println("The account will now close!");
                                            customer.setStatus("Closed");
                                        }
                                        break main;
                                    }
                                case 'N':
                                    break lop;
                                default:
                                    System.out.println("Invalid Input!");
                                    break;
                            }
                        }
                    }
                    break;
                case 6:
                    System.out.println("The operation was canceled!");
                    break main;
                default:
                    System.out.println("Invalid Input!");
                    break;

            }
        }
    }

    public static void doTransfer(int tellerNo){
        boolean isCorrect1 = false;
        boolean isCorrect2 = false;
        String cName = "", tName = "", temp1 = "", temp2 = "";
        int cNo = 0, tNo = 0;
        double amt = 0;
            main : while(true){
                TransferFundsSlip(cName,temp1,amt,tName,temp2);
                int num = InputController.getInteger("Choose (1 - 7)");
                switch (num){
                    case 1:
                        cName = InputController.getPassInput("Enter Name");
                        break;
                    case 2:
                        cNo = InputController.getInteger("Enter account no. ");
                        break;
                    case 3:
                        tName = InputController.getPassInput("Enter Name");
                        break;
                    case 4:
                        tNo = InputController.getInteger("Enter account no.");
                        break;
                    case 5:
                        amt = InputController.getAmount("Enter amount");
                        break;
                    case 6:
                        lop : while (true){
                            char yes = InputController.getChar("Are you sure you want to enter all [Y/N]?");
                            switch(Character.toUpperCase(yes)) {
                                case 'Y':
                                    cName = InputController.getPassInput("Enter Name");
                                    cNo = InputController.getInteger("Enter account no. ");
                                    tName = InputController.getPassInput("Enter Name");
                                    tNo = InputController.getInteger("Enter account no.");
                                    amt = InputController.getAmount("Enter amount");
                                    break lop;
                                case 'N':
                                    break lop;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        }
                        break;
                    case 7:
                        Customer customer1 = customermgr.searchCustomer(cNo);
                        Customer customer2 = customermgr.searchCustomer(tNo);
                        if (customer1 != null){
                            if (Objects.equals(cName, customer1.getName()) && (customer1.getStatus().equals("Active")) || (customer1.getStatus().equals("Inactive"))){
                                if (customer1.getAccType() == Customer.AccountType.SAVINGS){
                                    isCorrect1 = true;
                                }else{
                                    System.out.println("Credit Accounts are unable to transfer funds");
                                    break;
                                }
                                if (!checkStatus(customer1)){
                                    System.out.println("Account needs to be active");
                                    break;
                                }
                            }
                        }

                        if (customer2 != null){
                            if ((tName.equals(customer2.getName()) && (customer2.getStatus().equals("Active"))) || (customer2.getStatus().equals("Inactive"))){
                                if (customer2.getAccType() == Customer.AccountType.SAVINGS){
                                    isCorrect2 = true;
                                }else{
                                    System.out.println("Credit Accounts are unable to transfer funds");
                                    break;
                                }

                                if (!checkStatus(customer1)){
                                    System.out.println("Account needs to be active");
                                    break;
                                }

                            }
                        }


                        if (isCorrect1 && isCorrect2){
                            double balance = opermgr.getBalance(cNo);
                            double prevTransfer = opermgr.getPrevTransfer(cNo);
                            System.out.printf("Total Balance     : $%.2f\n", balance);
                            System.out.printf("Previous Transfer : $%.2f\n", prevTransfer);
                            opermgr.makeTransfer(cNo,amt,tNo);
                            opermgr.makeDeposit(tNo,amt);
                            if (customer1.getStatus() == "Inactive"){
                                customermgr.activate(customer1);
                            }
                            return;
                        }else{
                            System.out.println("The inputted credentials are incorrect!");
                        }
                        break;
                    case 8:
                        System.out.println("The operation was canceled!");
                        break main;
                    default:
                        System.out.println("Invalid Input!");
                        break;
                }

                temp1 = Integer.toString(cNo);
                temp2 = Integer.toString(tNo);
        }

    }

    public static boolean checkWithdraw (int accNO, String name){
        Customer customer;
        customer = customermgr.searchCustomer(accNO);
        if (customer != null){
            if (Objects.equals(name, customer.getName())){
                if (customer.getAccType() == Customer.AccountType.SAVINGS){
                    if (customer.getStatus() != "Closed" && customer.getStatus() != "Dormant" ){

                        double balance = opermgr.getBalance(accNO);
                        double prevWithdraw = opermgr.getPrevWithdraw(accNO);

                        System.out.printf("Total Balance     : $%.2f\n", balance);
                        System.out.printf("Previous Withdraw : $%.2f\n",prevWithdraw);
                        return true;
                    }else {
                        System.out.println("Account needs to be activated!");
                        return false;
                    }
                }else{
                    System.out.println("Only Savings Account can Withdraw!");
                    return false;
                }

            }else{
                System.out.println("Account name not found!");
                return false;
            }

        }else{
            System.out.println("Account not found.");
            return false;
        }
    }

    public static void doWithdraw(int accNO){
        accNO = 0;
        String name = "";
        double amt = 0;
        main : while(true){
            WithdrawSlip(name, String.valueOf(accNO),amt);
            int num = InputController.getInteger("Choose (1 - 6)");
            switch (num){
                case 1:
                    name =  InputController.getPassInput("Enter Name");
                    break;
                case 2:
                    accNO = InputController.getInteger("Enter account no. ");
                    break;
                case 3:
                    amt = InputController.getAmount("Enter amount");
                    break;
                case 4:
                    lop : while (true){
                        char yes = InputController.getChar("Are you sure you want to enter all [Y/N]?");
                        switch(Character.toUpperCase(yes)) {
                            case 'Y':
                                name = InputController.getPassInput("Enter Name");
                                accNO = InputController.getInteger("Enter account no. ");
                                amt = InputController.getAmount("Enter amount");
                                break lop;
                            case 'N':
                                break lop;
                            default:
                                System.out.println("Invalid Input!");
                                break;
                        }
                    }
                    break;
                case 5:
                    Customer customer = customermgr.searchCustomer(accNO);
                    if (checkWithdraw(accNO,name)){
                        lop : while (true){
                            char yes = InputController.getChar("Are you sure you want to deposit [Y/N]?");
                            switch(Character.toUpperCase(yes)) {
                                case 'Y':
                                    opermgr.makeWithdraw(accNO,amt);
                                    if (customer.getStatus() == "Inactive"){
                                        customermgr.activate(customer);
                                    }
                                    break main;
                                case 'N':
                                    break lop;
                                default:
                                    System.out.println("Invalid Input!");
                                    break;
                            }
                        }
                    }
                    break;
                case 6:
                    System.out.println("The operation was canceled!");
                    break main;
                default:
                    System.out.println("Invalid Input!");
                    break;

            }
        }

    }

    public static void ViewCustomersAccounts() {
        int uid = InputController.getInteger("Enter UID : ");
        boolean isFound = false;
        for(Customer e : customers){
            if (uid ==  Integer.parseInt(e.getID())){
                isFound = true;
            }

        }

        if (isFound){
            if (customers.size() != 0) {
                customers = customermgr.getAccounts(Integer.toString(uid));
                System.out.println(" Total Customer : " + customers.size());
                String[] headers = {"ID", "Acc. No", "Name", "Address", "Gender", "Birthdate", "Age", "Acc. Type", "Status", "Balance" , "Term"};
                String[][] data = new String[customers.size()][11];
                IntStream.range(0, customers.size()).forEachOrdered(i -> {
                    Customer depo = customers.get(i);
                        double balance = opermgr.getBalance(Integer.parseInt(depo.getAccNo()));
                        data[i][0] = depo.getID();
                        data[i][1] = depo.getAccNo();
                        data[i][2] = depo.getName();
                        data[i][3] = depo.getAddress();
                        data[i][4] = String.valueOf(depo.getGender());
                        data[i][5] = depo.getBirthDate();
                        data[i][6] = depo.getAge();
                        data[i][7] = String.valueOf(depo.getAccType());
                        data[i][8] = depo.getStatus();
                        data[i][9] = String.valueOf(balance);
                        data[i][10] = depo.getTerm();
                });
                System.out.println(FlipTableConverters.fromObjects(headers, data));
            }else{
                System.out.println("There's no such account records found!");
            }
        }else{
            System.out.println("Account not found!");
        }
    }

    public static void ViewCustomers(int mode){
        if (mode == 1){
            customers = customermgr.getCustomers();
        }else if (mode == 2){
            customers = customermgr.getActiveCustomers();
        }else if (mode == 3){
            customers = customermgr.getInactiveCustomers();
        }else if (mode == 4){
            customers = customermgr.getClosedCustomers();
        }else if (mode == 5){
            customers = customermgr.getDormantCustomers();
        }

        if (customers.size() != 0){
            System.out.println(" Total Customer : " + customers.size());
            String[] headers = {"ID", "Acc. No", "Name", "Address", "Gender", "Birthdate", "Age", "Acc. Type", "Status", "Balance", "Term"};
            String[][] data = new String[customers.size()][11];
            IntStream.range(0, customers.size()).forEachOrdered(i -> {
                Customer depo = customers.get(i);
                double balance = opermgr.getBalance(Integer.parseInt(depo.getAccNo()));
                data[i][0] = depo.getID();
                data[i][1] = depo.getAccNo();
                data[i][2] = depo.getName();
                data[i][3] = depo.getAddress();
                data[i][4] = String.valueOf(depo.getGender());
                data[i][5] = depo.getBirthDate();
                data[i][6] = depo.getAge();
                data[i][7] = String.valueOf(depo.getAccType());
                data[i][8] = depo.getStatus();
                data[i][9] = String.valueOf(balance);
                data[i][10] = depo.getTerm();
            });
            System.out.println(FlipTableConverters.fromObjects(headers,data));
        }else{
            String search = "";
            if (mode == 1){
                search = "customers";
            }else if (mode ==2){
                search = "active";
            }else if (mode ==3){
                search = "inactive";
            }else if (mode ==4){
                search = "closed";
            }else if (mode == 5){
                search = "dormant";
            }
            System.out.println("There's no "+search+" record yet!");
        }
    }

    public static boolean isAccFound(int accNO){
        Customer customer;
        customer = customermgr.searchCustomer(accNO);
        if (customer != null){
            return true;
        }else{
            System.out.println("Account not found.");
            return false;
        }
    }


    /**
     *Menu choices for manager
     * @param acc, n
     * @throws ParseException
     */

    public static void ManagerChoices(int acc , int n) throws ParseException{
        if (n == 1){
            loop:
            while (true) {
                TransactionSect(acc);
                int num = InputController.getInteger("Choose (1 - 7)");
                switch (num) {
                    case 1:
                        openAccount();
                        break;
                    case 2:
                        doDeposit(0);
                        break;
                    case 3:
                        doWithdraw(0);
                        break;
                    case 4:
                        doTransfer(acc);
                        break;
                    case 5:
                        searchCustomer();
                        break;
                    case 6:
                        updateCustomer();
                        break;
                    case 7:
                        break loop;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        } else if (n == 2){
            loop:
            while (true) {
                CustomerSect(acc);
                int num = InputController.getInteger("Choose (1 - 11)");
                switch (num) {
                    case 1:
                        updateCustomer();
                        break;
                    case 2:
                        searchCustomer();
                        break;
                    case 3:
                        deleteCustomer();
                        break;
                    case 4:
                        activateCustomer();
                        break;
                    case 5:
                        ViewCustomers(1);
                        break;
                    case 6:
                        ViewCustomers(2);
                        break;
                    case 7:
                        ViewCustomers(3);
                        break;
                    case 8:
                         ViewCustomers(4);
                        break;
                    case 9:
                        ViewCustomers(5);
                        break;
                    case 10:
                        ViewCustomersAccounts();
                        break;
                    case 11:
                        break loop;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        }else if (n == 3){
            /**
                For Teller Section
             */
            loop:
            while (true) {
                TellerSect(acc);
                int num = InputController.getInteger("Choose (1 - 9)");
                switch (num) {
                    case 1:
                        registerStaff();
                        break;
                    case 2:
                        updateStaff();
                        break;
                    case 3:
                        searchStaff();
                        break;
                    case 4:
                        deleteStaff();
                        break;
                    case 5:
                        activateStaff();
                        break;
                    case 6:
                        staffs = staffmgr.getStaffs();
                        if (staffs.size() == 0){
                            System.out.println("Theres no staff's yet!");
                        }else{
                            System.out.println(" Total Customer : " + staffs.size());
                            String[] headers = { "Acc. No", "Name", "Address", "Gender", "Birthdate", "Age",  "Status"};
                            String[][] data = new String[staffs.size()][7];
                            IntStream.range(0, staffs.size()).forEachOrdered(i -> {
                                Staff depo = staffs.get(i);
                                data[i][0] = depo.getAccNo();
                                data[i][1] = depo.getName();
                                data[i][2] = depo.getAddress();
                                data[i][3] = String.valueOf(depo.getGender());
                                data[i][4] = depo.getBirthDate();
                                data[i][5] = depo.getAge();
                                data[i][6] = depo.getStatus();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }
                        break;
                    case 7:
                        staffs = staffmgr.getActiveStaffs();
                        if (staffs.size() == 0){
                            System.out.println("Theres no active staff's yet!");
                        }else{
                            System.out.println(" Total Customer : " + staffs.size());
                            String[] headers = { "Acc. No", "Name", "Address", "Gender", "Birthdate", "Age",  "Status"};
                            String[][] data = new String[staffs.size()][7];
                            IntStream.range(0, staffs.size()).forEachOrdered(i -> {
                                Staff depo = staffs.get(i);
                                data[i][0] = depo.getAccNo();
                                data[i][1] = depo.getName();
                                data[i][2] = depo.getAddress();
                                data[i][3] = String.valueOf(depo.getGender());
                                data[i][4] = depo.getBirthDate();
                                data[i][5] = depo.getAge();
                                data[i][6] = depo.getStatus();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }
                        break;
                    case 8:
                        staffs = staffmgr.getInactiveStaffs();
                        if (staffs.size() == 0){
                            System.out.println("Theres no inactive staff's yet!");
                        }else{
                            System.out.println(" Total Customer : " + staffs.size());
                            String[] headers = { "Acc. No", "Name", "Address", "Gender", "Birthdate", "Age",  "Status"};
                            String[][] data = new String[staffs.size()][7];
                            IntStream.range(0, staffs.size()).forEachOrdered(i -> {
                                Staff depo = staffs.get(i);
                                data[i][0] = depo.getAccNo();
                                data[i][1] = depo.getName();
                                data[i][2] = depo.getAddress();
                                data[i][3] = String.valueOf(depo.getGender());
                                data[i][4] = depo.getBirthDate();
                                data[i][5] = depo.getAge();
                                data[i][6] = depo.getStatus();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }
                        break;
                    case 9:
                        break loop;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        }else {
            loop:
            while (true) {
                LogHistorySect(acc);
                int num = InputController.getInteger("Choose (1 - 7)");
                switch (num) {
                    case 1:
                        deposits = opermgr.getAllDeposit();
                        if (deposits.size() == 0){
                            System.out.println("There's no deposit record history yet!");
                        }else{
                            String[] headers = {"Acc. No", "Amount", "Transaction Date"};
                            String[][] data = new String[deposits.size()][3];
                            IntStream.range(0, deposits.size()).forEachOrdered(i -> {
                                Deposit depo = deposits.get(i);
                                data[i][0] = depo.getAccNo();
                                data[i][1] = String.valueOf(depo.getAmt());
                                data[i][2] = depo.getDate();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }
                        break;
                    case 2:
                        withdraws = opermgr.getAllWithdraw();
                        if (withdraws.size() != 0){
                            String[] headers = {"Acc. No", "Amount", "Transaction Date"};
                            String[][] data = new String[withdraws.size()][3];
                            IntStream.range(0, withdraws.size()).forEachOrdered(i -> {
                                Withdraw depo = withdraws.get(i);
                                data[i][0] = depo.getAccNo();
                                data[i][1] = String.valueOf(depo.getAmt());
                                data[i][2] = depo.getDate();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }else{
                            System.out.println("There's no withdraw record history yet!");
                        }
                        break;
                    case 3:
                        transfers = opermgr.getAllTransfers();
                        if (transfers.size() != 0) {
                            String[] headers = {"From Acc. No", "Amount","To Acc. No" , "Transaction Date"};
                            String[][] data = new String[transfers.size()][4];
                            IntStream.range(0, transfers.size()).forEachOrdered(i -> {
                                Transfer depo = transfers.get(i);
                                data[i][0] = depo.getCustAccNo();
                                data[i][1] = String.valueOf(depo.getAmt());
                                data[i][2] = depo.getTargetAccNo();
                                data[i][3] = depo.getDate();
                            });
                            System.out.println(FlipTableConverters.fromObjects(headers,data));
                        }else{
                            System.out.println("There's no transfer record history yet!");
                        }
                        break;
                    case 4:
                        int accNo = InputController.getInteger("Enter account no");
                        if(isAccFound(accNo)){
                            deposits = opermgr.getAccDeposits(accNo);
                            if (deposits.size() !=0){
                                String[] headers = {"Acc. No", "Amount", "Transaction Date"};
                                String[][] data = new String[deposits.size()][3];
                                IntStream.range(0, deposits.size()).forEachOrdered(i -> {
                                    Deposit depo = deposits.get(i);
                                    data[i][0] = depo.getAccNo();
                                    data[i][1] = String.valueOf(depo.getAmt());
                                    data[i][2] = depo.getDate();
                                });
                                System.out.println(FlipTableConverters.fromObjects(headers,data));
                            }else{
                                System.out.println("No history deposit transaction found!");
                            }
                        }
                        break;
                    case 5:
                        int accno = InputController.getInteger("Enter account no");
                        if(isAccFound(accno)){
                            withdraws = opermgr.getAccWithdraws(accno);
                            if (withdraws.size() != 0){
                                String[] headers = {"Acc. No", "Amount", "Transaction Date"};
                                String[][] data = new String[withdraws.size()][3];
                                IntStream.range(0, withdraws.size()).forEachOrdered(i -> {
                                    Withdraw depo = withdraws.get(i);
                                    data[i][0] = depo.getAccNo();
                                    data[i][1] = String.valueOf(depo.getAmt());
                                    data[i][2] = depo.getDate();
                                });
                                System.out.println(FlipTableConverters.fromObjects(headers,data));
                            }else{
                                System.out.println("No history withdraw transaction found!");
                            }

                        }
                        break;
                    case 6:
                        int acC = InputController.getInteger("Enter account no");
                        if (isAccFound(acC)){
                            transfers = opermgr.getAccTransfers(acC);
                            if(transfers.size() != 0){
                                String[] headers = {"From Acc. No", "Amount","To Acc. No" , "Transaction Date"};
                                String[][] data = new String[transfers.size()][4];
                                IntStream.range(0, transfers.size()).forEachOrdered(i -> {
                                    Transfer depo = transfers.get(i);
                                    data[i][0] = depo.getCustAccNo();
                                    data[i][1] = String.valueOf(depo.getAmt());
                                    data[i][2] = depo.getTargetAccNo();
                                    data[i][3] = depo.getDate();
                                });
                                System.out.println(FlipTableConverters.fromObjects(headers,data));
                            }else{
                                System.out.println("No history transfer transaction found!");
                            }
                        }
                        break;
                    case 7:
                      break loop;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        }
    }


    /**
     * Menu choices for Teller
     * @param acc
     * @throws ParseException
     */
    public static void TellerChoices(int acc) throws ParseException{
        loop:
        while (true) {
            TransactionSect(acc);
            int num = InputController.getInteger("Choose (1 - 7)");
            switch (num) {
                case 1:
                    openAccount();
                    break;
                case 2:
                    doDeposit(0);
                    break;
                case 3:
                    doWithdraw(0);
                    break;
                case 4:
                    doTransfer(acc);
                    break;
                case 5:
                    searchCustomer();
                    break;
                case 6:
                    updateCustomer();
                    break;
                case 7:
                    break loop;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }


    /**
     * LOGIN SECTION
     *
     * check's the login credential of the user
     * @param accNO
     * @param pass
     * @return bool
     */
    public static boolean DoLogin(String accNO, String pass){
        if (customermgr.login(accNO,pass) || staffmgr.login(accNO,pass)){
            return true;
        }else{
            System.out.println("Incorrect account number or password!");
            return false;
        }
    }

    /**
     * Login Session
     * @throws ParseException
     */
    public static void Login() throws ParseException{
        String accNO = Integer.toString(InputController.getInteger("Enter account no"));
        String pass = InputController.getPassInput("Enter password ");
        if(DoLogin(accNO,pass)){
            Staff staff;
            staff = staffmgr.findID(Integer.parseInt(accNO));
            if ("Manager".equals(staff.getRole())) {
                loop:
                while (true) {
                    StaffMenu(Integer.parseInt(accNO));
                    int num = InputController.getInteger("Choose (1 - 5)");
                    switch (num) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            ManagerChoices(Integer.parseInt(accNO),num);
                            break;
                        case 5:
                            break loop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                }
            }else if ("Teller".equals(staff.getRole())) {
                loop:
                while (true) {
                    TellerMenu(Integer.parseInt(accNO));
                    int num = InputController.getInteger("Choose (1 - 2)");
                    switch (num) {
                        case 1:
                            TellerChoices(Integer.parseInt(accNO));
                            break;
                        case 2:
                            break loop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                }

            }
        }
    }


    /**
     * Closes file
     * upon program exit
     * @throws IOException
     */
    public static void CloseFile() throws IOException{
        staffs = staffmgr.getStaffs();
        customers = customermgr.getCustomers();
        deposits = opermgr.getAllDeposit();
        withdraws = opermgr.getAllWithdraw();
        transfers = opermgr.getAllTransfers();

        if (staffs != null ){
            try(BufferedWriter staffFile  = new BufferedWriter(new FileWriter("staffs.txt"))){
                for(Staff staff : staffs){
                    staffFile.write(staff.getAccNo()+"|"+staff.getName()+"|"+ staff.getAddress() + "|" + staff.getGender()+"|"+staff.getBirthDate()+"|"+staff.getAge()+"|"+staff.getRole()+"|"+staff.getStatus()+"|"+staff.getPassword()+"\n");
                }
            }
        }

        if (customers != null){
            try(BufferedWriter custFile = new BufferedWriter(new FileWriter("customers.txt"))){
                for(Customer customer : customers){
                    custFile.write(customer.getID() + "|" + customer.getAccNo() + "|" + customer.getName() +"|" + customer.getAddress() + "|" + customer.getGender() + "|" + customer.getBirthDate() + "|" + customer.getAge() + "|" + customer.getAccType() + "|" + customer.getStatus()  + "|"+ customer.getTerm()+"\n");
                }
            }
        }

        if (deposits != null){
            try(BufferedWriter depoFile = new BufferedWriter(new FileWriter("deposits.txt"))){
                for(Deposit deposit : deposits){
                    depoFile.write(deposit.getAccNo() +"|" + deposit.getAmt() + "|" + deposit.getDate()+"\n");
                }
            }
        }

        if (withdraws != null){
            try(BufferedWriter withdrawFile = new BufferedWriter(new FileWriter("withdraws.txt"))){
                for(Withdraw withdraw : withdraws){
                    withdrawFile.write(withdraw.getAccNo() +"|" + withdraw.getDate() + "|" + withdraw.getAmt()+"\n");
                }
            }
        }

        if (transfers != null){
            try(BufferedWriter transferFile = new BufferedWriter(new FileWriter("transfers.txt"))){
                for(Transfer transfer : transfers){
                    transferFile.write(transfer.getCustAccNo() +"|" + transfer.getDate() + "|" + transfer.getAmt() +"|"+ transfer.getTargetAccNo()+ "\n");
                }
            }
        }
    }

    /**
     * Gets the recent dates on
     * transactions (deposit, withdraw, transfer)
     * @param accNO
     * @param mode
     * @return
     * @throws ParseException
     */
    public static Date checkContainerDates(int accNO, int mode) throws  ParseException{
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss aaa");
        ArrayList<Date> dates = new ArrayList<>();

        ArrayList<Deposit> timeDeposit = opermgr.getAccDeposits(accNO);
        ArrayList<Withdraw> timeWithdraw = opermgr.getAccWithdraws(accNO);
        ArrayList<Transfer> timeTransfer = opermgr.getAccTransfers(accNO);

        if (mode == 1){
            if (!timeDeposit.isEmpty()){
                for(Deposit i : timeDeposit){
                    dates.add(dateFormat.parse(i.getDate()));
                }
                //dates.add(dateFormat.parse(timeDeposit.get(timeDeposit.size()-1).getDate()));
            }

            if (!timeWithdraw.isEmpty()){
                for(Withdraw i : timeWithdraw){
                    dates.add(dateFormat.parse(i.getDate()));
                }
                //dates.add(dateFormat.parse(timeWithdraw.get(timeWithdraw.size()-1).getDate()));
            }

            if (!timeTransfer.isEmpty()){
                for(Transfer i : timeTransfer){
                    dates.add(dateFormat.parse(i.getDate()));
                }

                //dates.add(dateFormat.parse(timeTransfer.get(timeTransfer.size()-1).getDate()));
            }
        }else if (mode == 2){
            if (!timeDeposit.isEmpty()){
                for(Deposit i : timeDeposit){
                    dates.add(dateFormat.parse(i.getDate()));
                }
                //dates.add(dateFormat.parse(timeDeposit.get(timeDeposit.size()-1).getDate()));
            }
        }


        Date recentTransaction = Collections.max(dates);

        return recentTransaction;
    }


    public static String getTermDate(int accNo) throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss aaa");
        Date date;
        ArrayList<Deposit> timeDeposit = opermgr.getAccDeposits(accNo);
        date = dateFormat.parse(timeDeposit.get(0).getDate());
        return dateFormat.format(date);
    }

    public static String getDueDate(int accNo) throws ParseException{
        Customer customer = customermgr.searchCustomer(accNo);
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss aaa");
        Date date;
        ArrayList<Deposit> timeDeposit = opermgr.getAccDeposits(accNo);
        date = dateFormat.parse(timeDeposit.get(0).getDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, Integer.parseInt(customer.getTerm()));
        Date duedate = calendar.getTime();
        return dateFormat.format(duedate);

    }

    /**
     * This will check and update the
     * state of the accounts if its inactive or
     * if the user account hasn't paid its due on time
     * @throws ParseException
     */
    public static void performCalculation() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy hh:mm:ss aaa");
        Date date = Calendar.getInstance().getTime();
        // Present time
        String present = dateFormat.format(date);
        // Second = present
        Date second = dateFormat.parse(present);
        // First = recent Transaction

        customers = customermgr.getCustomers();

        for(Customer i : customers){
            Date first;
            if (i.getAccType() == Customer.AccountType.SAVINGS){
                first = checkContainerDates(Integer.parseInt(i.getAccNo()),1);
            }else{
                first = checkContainerDates(Integer.parseInt(i.getAccNo()),2);
                Date presDate = dateFormat.parse(getTermDate(Integer.parseInt(i.getAccNo())));
                Date dueDate = dateFormat.parse(getDueDate(Integer.parseInt(i.getAccNo())));
                if (second.compareTo(dueDate) >0){
                    i.setStatus("Dormant");
                }else if (second.compareTo(dueDate) < 0 ) {
                    i.setStatus("Active");
                }
//            System.out.println("The account status has changed to " + i.getStatus());
            }

//            System.out.println("ID = " + i.getAccNo() + " " + first + " " + i.getAccType() + i.getStatus());
            long diffInMillies = Math.abs(second.getTime() - first.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff >365 && diff < 730){
                i.setStatus("Inactive");
            }else if (diff >= 730){
                i.setStatus("Dormant");
            }
        }

//        System.out.println("Perform Calculation = " + diff);
        System.out.println("The System is now ready to use!");

    }

    /**
     * Comparable for the dates
     * @param items
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T max(T... items) {
        return Collections.max(Arrays.asList(items));
    }


    /**
     * Main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        initializeControls();
        performCalculation();

        // Make a starting staff
        loop: while(true){

            Menu();
            int num = InputController.getInteger("Select Service [1 - 2]");
            switch (num){
                case 1:
                    Login();
                    break;
                case 2:
                    System.out.println("\nThank you for using the application!");
                    CloseFile();
                    break loop;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }// End main

}








