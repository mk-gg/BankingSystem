package bank.container.func;

import bank.container.ICustomerContainer;
import bank.models.Customer;
import bank.models.Deposit;
import bank.models.Staff;

import java.io.*;
import java.lang.reflect.Array;
import java.time.Year;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerContainer implements ICustomerContainer {
    private ArrayList<Customer> customers = new ArrayList<>();

    public void CustomerFile() throws IOException{
        all();
        File f = new File("customers.txt");
        if(!f.exists()){
            System.out.println("File [customers.txt] not found! ");
            f.createNewFile();
        }else{
            System.out.println("File [customers.txt] detected!");
            try{
                BufferedReader staffFile = new BufferedReader(new FileReader("customers.txt"));
                String input;
                while((input = staffFile.readLine()) != null){
                    String[] data = input.split("\\|");
                    String uid = data[0];
                    String accN = data[1];
                    String name = data[2];
                    String add = data[3];
                    String gen = data[4];
                    String bd = data[5];
                    String age = data[6];
                    String type = data[7];
                    String status = data[8];
                    String term = data[9];


                    Customer x = new Customer();
                    x.setID(uid);
                    x.setAccNo(accN);
                    x.setName(name);
                    x.setAddress(add);
                    x.setGender(Customer.Gender.valueOf(gen));
                    x.setBirthDate(bd);
                    x.setAge(age);
                    x.setAccType(Customer.AccountType.valueOf(type));
                    x.setStatus(status);
                    x.setTerm(term);
                    customers.add(x);


                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }





    }

    /**
        Gets all customer object
     */
    @Override
    public ArrayList<Customer> all() {
        if (customers == null) customers = new ArrayList<>();
//        file = new File("customers.db");
//        readFile(file);

        return customers;
    }

    @Override
    public ArrayList<Customer> active() {
        if (customers == null ) customers = new ArrayList<>();
        Predicate<Customer> byStatus = teller -> "Active".equals(teller.getStatus());
        var result = customers.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Customer>) result;
    }

    @Override
    public ArrayList<Customer> dormant(){
        if (customers == null ) customers = new ArrayList<>();
        Predicate<Customer> byStatus = teller -> "Dormant".equals(teller.getStatus());
        var result = customers.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Customer>) result;
    }

    @Override
    public ArrayList<Customer> accounts(String acc){
        if (customers == null ) customers = new ArrayList<>();
        Predicate<Customer> byID = customer -> acc.equals(customer.getID());
        var result = customers.stream().filter(byID).collect(Collectors.toList());
        return (ArrayList<Customer>) result;
    }

    @Override
    public ArrayList<Customer> closed(){
        if (customers == null ) customers = new ArrayList<>();
        Predicate<Customer> byStatus = teller -> "Closed".equals(teller.getStatus());
        var result = customers.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Customer>) result;
    }

    @Override
    public ArrayList<Customer> inactive() {
        if (customers == null ) customers = new ArrayList<>();
        Predicate<Customer> byStatus = teller -> "Inactive".equals(teller.getStatus());
        var result = customers.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Customer>) result;
    }

    @Override
    public void update(Customer customer) {
        InputController i = new InputController();
        System.out.println("-=====================-");
        System.out.println(" Account Credentials!");
        System.out.println(" [1] Name      : " + customer.getName());
        System.out.println(" [2] Password  : " + customer.getPassword());
        System.out.println(" [3] BirthDate : " + customer.getBirthDate());
        System.out.println(" [4] Address   : " + customer.getAddress());
        System.out.println(" [5] Gender    : " + customer.getGender());
        System.out.println(" [6] Update All ");
        System.out.println("-=====================-");
        int choice = InputController.getInteger("Select to update credentials! (1 - 6 ) ");
        switch (choice){
            case 1:
                customer.setName(i.getStrInpt("Enter your name"));
                break;
            case 2:
                customer.setPassword(i.getStrInpt("Enter password"));
                break;
            case 3:
                customer.setBirthDate(i.getDateInpt());
                break;
            case 4:
                customer.setAddress(i.getStrInpt("Enter the address"));
                break;
            case 5:
                customer.setGender(i.getGender());
                break;
            case 6:
                loop: while(true) {
                    char yes = InputController.getChar("Are you sure you want to update all [y/n]?");
                    switch (Character.toUpperCase(yes)){
                         case'Y':
                            customer.setName(i.getStrInpt("Enter your name"));
                            customer.setPassword(i.getStrInpt("Enter password"));
                            customer.setBirthDate(i.getDateInpt());
                            customer.setAddress(i.getStrInpt("Enter the address"));
                            customer.setGender(i.getGender());
                            break;
                         case 'N':
                            break loop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                }
                break;
             default:
                System.out.println("Inavalid Input!");
                break;
        }
    }

    @Override
    public void store(Customer customer) {
        customer = customerWrapper(customer);
        if (customer == null){
            System.out.println("Operation was canceled!");
        }else{
            customers.add(customer);

        }
    }

    @Override
    public void delete(Customer customer) {

        ListIterator<Customer> it = customers.listIterator();
        while(it.hasNext()){
            Customer current = it.next();
            if ( (current.getAccNo() == customer.getAccNo())){
                current.setStatus("Closed");
                System.out.println("Account deleted successfully!");
            }
        }
    }

    public void activate(Customer customer){
        ListIterator<Customer> it = customers.listIterator();
        while(it.hasNext()){
            Customer current = it.next();
            if ( (current.getAccNo() == customer.getAccNo())){
                current.setStatus("Active");
                System.out.println("Account has been activated successfully!");
            }
        }
    }


    /**
     Searches a customer object
     */
    @Override
    public Customer find(int accNo) {
        for(int i = 0; i < customers.size(); i++){
            int x = Integer.parseInt(customers.get(i).getAccNo().trim());
            if (accNo == x){
                return customers.get(i);
            }
        }
        return null;
    }


    public Customer findID(String accNo) {
        for (Customer customer : customers) {

            if (Objects.equals(accNo, customer.getID())) {
                return customer;
            }

        }
        return null;
    }
    /**
     * Open Account Form Menu
     * @param name
     * @param birthdate
     * @param add
     * @param gender
     * @param type
     * @param uid
     */
    public void OpenAccountForm(String name, String birthdate, String add, String gender, String type,String uid) {
        System.out.println("-=====================-");
        System.out.println("   OPEN ACCOUNT FORM");
        System.out.println(" [1] Name        : " + name);
        System.out.println(" [2] Birthdate   : " + birthdate);
        System.out.println(" [3] Address     : " + add);
        System.out.println(" [4] Gender      : " + gender);
        System.out.println(" [5] Acc. Type   : " + type);
        System.out.println(" HAVE A UID ? -");
        System.out.println(" [6] If yes ---- : " + uid);
        System.out.println(" [7] Enter all   ");
        System.out.println(" [8] Confirm");
        System.out.println(" [9] Back");
        System.out.println("-=====================-");
    }

    /**
     * Wrapper for Customer
     * This will get the data for the Customer
     * before adding it in the container
     * @param customer
     * @return
     */
    public Customer customerWrapper(Customer customer){
        String name = "",bdate="",address="",gender="",type="",uid="";
        InputController inpt = new InputController();
        boolean hasID = false;
        main : while(true){
            if (!uid.isEmpty()){
                Customer c = findID(uid);
                if (c != null){
                    customer.setID(c.getID());
                    customer.setName(c.getName());
                    customer.setBirthDate(c.getBirthDate());
                    customer.setAddress(c.getAddress());
                    customer.setGender(c.getGender());
                    hasID = true;
                    uid = c.getID();
                    name = c.getName();
                    bdate = c.getBirthDate();
                    address = c.getAddress();
                    gender = String.valueOf(c.getGender());
                }
            }
            OpenAccountForm(name,bdate,address,gender,type,uid);
            int num = InputController.getInteger("Choose (1 - 8)");
            switch (num){
                case 1:
                    customer.setName(InputController.getPassInput("Enter Name"));
                    name = customer.getName();
                    break;
                case 2:
                    customer.setBirthDate(inpt.getDateInpt());
                    bdate = customer.getBirthDate();
                    break;
                case 3:
                    customer.setAddress(inpt.getStrInpt("Enter the address"));
                    address = customer.getAddress();
                    break;
                case 4:
                    customer.setGender(inpt.getGender());
                    gender = String.valueOf(customer.getGender());
                    break;
                case 5:
                    customer.setAccType(inpt.getAccType());
                    type = String.valueOf(customer.getAccType());
                    break;
                case 6:
                    lop:while(true){
                        char yes = InputController.getChar("Do you have an UID [Y/N]?");
                        switch (Character.toUpperCase(yes)){
                            case 'Y':
                                uid = String.valueOf(InputController.getInteger("Enter account no. "));
                                break lop;
                            case 'N':
                                break lop;
                            default:
                                System.out.println("Invalid Input!");
                                break;

                        }
                    }
                    break;
                case 7:
                    lop : while (true){
                        char yes = InputController.getChar("Are you sure you want to enter all [Y/N]?");
                        switch(Character.toUpperCase(yes)) {
                            case 'Y':
                                customer.setName(InputController.getPassInput("Enter Name"));
                                customer.setBirthDate(inpt.getDateInpt());
                                customer.setAddress(inpt.getStrInpt("Enter the address"));
                                customer.setGender(inpt.getGender());
                                customer.setAccType(inpt.getAccType());
                                name = customer.getName();
                                bdate = customer.getBirthDate();
                                address = customer.getAddress();
                                gender = String.valueOf(customer.getGender());
                                type = String.valueOf(customer.getAccType());
                                break lop;
                            case 'N':
                                break lop;
                            default:
                                System.out.println("Invalid Input!");
                                break;
                        }
                    }
                    break;
                case 8:
                   if (type == ""|| bdate =="" || name == "" || address == "" ||gender == ""){
                       System.out.println("One of the fields are missing!");
                       break;
                   }

                    if (uid == null || uid == ""){
                        customer.setID(Integer.toString(inpt.generateUID()));
                        customer.setAccNo(Integer.toString(inpt.generateAccNo()));
                        customer.setStatus("Active");
                        customer.setAge(inpt.calculateAge(customer.getBirthDate()));
                        return customer;
                    }else {
                        Customer c = findID(uid);
                        if (c != null) {
                            if (c.getName() != name || c.getAddress() != address || c.getBirthDate() != bdate || String.valueOf(c.getGender()) != gender){
                                System.out.println("Credentials mismatch!");
                                break;
                            }

                            if (c.getName().equals(customer.getName()) && c.getGender().equals(customer.getGender()) && c.getBirthDate().equals(customer.getBirthDate()) && c.getAddress().equals(customer.getAddress())) {
                                customer.setAccNo(Integer.toString(inpt.generateAccNo()));
                                customer.setStatus("Active");
                                customer.setAge(inpt.calculateAge(customer.getBirthDate()));
                                if (type != "" || type != null){
                                    return customer;
                                }else{
                                    System.out.println("Account type field is missing!");
                                    break;
                                }

                            } else {
                                System.out.println("Incorrect credentials!");
                                break;
                            }
                        } else {
                            System.out.println("UID not found!");
                            uid = "";
                            break;
                        }
                    }
                case 9:
                    return null;
                default:
                    System.out.println("Invalid Input!");
                    break;
            }
        }
//
    }


}
