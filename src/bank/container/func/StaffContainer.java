package bank.container.func;

import bank.container.IStaffContainer;
import bank.models.Staff;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StaffContainer implements IStaffContainer {
    private File file;








    private ArrayList<Staff> staffs = new ArrayList<>();




    public void StaffFile() throws IOException {
        InputController inpt = new InputController();
        all();
        File f = new File("staffs.txt");
        if(!f.exists()){
            System.out.println("File [staffs.txt] not found! ");
            f.createNewFile();
        }else{
            System.out.println("File [staffs.txt] detected!");
            try{
                BufferedReader staffFile = new BufferedReader(new FileReader("staffs.txt"));
                String input;
                while((input = staffFile.readLine()) != null){
                    String[] data = input.split("\\|");
                    String accN = data[0];
                    String name = data[1];
                    String add = data[2];
                    String gen = data[3];
                    String bd = data[4];
                    String age = data[5];
                    String role = data[6];
                    String status = data[7];
                    String pass = data[8];


                    Staff x = new Staff();
                    x.setAccNo(accN);
                    x.setName(name);
                    x.setAddress(add);
                    x.setGender(Staff.Gender.valueOf(gen));
                    x.setBirthDate(bd);
                    x.setAge(age);
                    x.setRole((String)role);
                    x.setStatus(status);
                    x.setPassword(pass);
                    staffs.add(x);


                }
            }catch (IOException e){
                e.printStackTrace();
            }

            if (checkFirst() == false){

                Staff admin = new Staff();
                admin.setAccNo(Integer.toString(inpt.generateAccNo()));
                admin.setName("admin");
                admin.setPassword("admin");
                admin.setRole("Manager");
                admin.setStatus("Active");
                admin.setAddress("Naga");
                admin.setBirthDate("1/11/1997");
                admin.setAge("21");
                admin.setGender(Staff.Gender.MALE);
                System.out.println(admin.getAccNo());
                staffs.add(admin);
            }

        }





    }

    public boolean checkFirst(){
        for(Staff staff : staffs){
            if ("Manager".equals(staff.getRole()))
                return true;
        }
        return false;
    }


    @Override
    public ArrayList<Staff> all() {
        if (staffs == null)
            staffs = new ArrayList<>();


            return staffs;

    }

    @Override
    public ArrayList<Staff> active() {
        if (staffs == null ) staffs = new ArrayList<>();
        Predicate<Staff> byStatus = teller -> "Active".equals(teller.getStatus());
        var result = staffs.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Staff>) result;
    }

    @Override
    public ArrayList<Staff> inactive() {
        Predicate<Staff> byStatus = teller -> "Inactive".equals(teller.getStatus());
        var result = staffs.stream().filter(byStatus).collect(Collectors.toList());
        return (ArrayList<Staff>) result;
    }

    @Override
    public void activate(Staff staff){
        ListIterator<Staff> it = staffs.listIterator();
        while(it.hasNext()){
            Staff current = it.next();
            if ( (current.getAccNo() == staff.getAccNo())){
                current.setStatus("Active");
                System.out.println("Account deleted successfully!");
            }
        }
    }

    @Override
    public Staff find(int staffID) {
        for (Iterator<Staff> iterator = staffs.iterator(); iterator.hasNext(); ) {
            Staff staff = iterator.next();
            int x = Integer.parseInt(staff.getAccNo().trim());
            if (staffID == x) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public void update(Staff staff) {
        InputController i = new InputController();

        System.out.println("-=====================-");
        System.out.println("  Staff Credentials!");
        System.out.println(" [1] Name      : " + staff.getName());
        System.out.println(" [2] Password  : " + staff.getPassword());
        System.out.println(" [3] Birthdate : " + staff.getBirthDate());
        System.out.println(" [4] Address   : " + staff.getAddress());
        System.out.println(" [5] Gender    : " + staff.getGender());
        System.out.println(" [6] Update all");
        System.out.println("-=====================-");
        int choice = InputController.getInteger("Select to update credentials! (1 - 5 ) ");
        switch (choice){
            case 1:
                staff.setName(i.getStrInpt("Enter your name"));
                System.out.println("Name has been updated!");
                break;
            case 2:
                staff.setPassword(i.getStrInpt("Enter password"));
                System.out.println("Password has been updated!");
                break;
            case 3:
                staff.setBirthDate(i.getDateInpt());
                System.out.println("Birthdate has been updated!");
                break;
            case 4:
                staff.setAddress(i.getStrInpt("Enter the address"));
                System.out.println("Address has been updated!");
                break;
            case 5:
                staff.setGender(i.getGenderr());
                System.out.println("Gender has been updated!");
                break;
            case 6:
               lop : while (true){
                    char yes = InputController.getChar("Are you sure you want to update all [y/n]?");
                    switch(Character.toUpperCase(yes)) {
                        case 'Y':
                            staff.setName(i.getStrInpt("Enter your name"));
                            staff.setPassword(i.getStrInpt("Enter password"));
                            staff.setBirthDate(i.getDateInpt());
                            staff.setAddress(i.getStrInpt("Enter the address"));
                            staff.setGender(i.getGenderr());
                            System.out.println("User information has been updated!");
                            break;
                        case 'N':
                            break lop;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                }
                break;
            default:
                System.out.println("Invalid Input");
                break;

        }

    }

    @Override
    public void store(Staff staff) {
        if (staffs.size() != 0){
            staff = staffwrapper(staff);
            if (staff == null){
                System.out.println("Operation was cancelled!");
            }else{
                staffs.add(staff);
                System.out.println("Teller created successfully! \n" + "Account Number : " + staff.getAccNo() );
            }
        }else{
            staffs.add(staff);
            System.out.println("Manager created successfully! \n" + "Account Number : " + staff.getAccNo() );
        }
    }

    @Override
    public void delete(Staff staff) {
        ListIterator<Staff> it = staffs.listIterator();
        if ("Manager".equals(staff.getRole())){
            System.out.println("Cannot remove Manager!");
            return;
        }
        while(it.hasNext()){
            Staff current = it.next();
            if ( (current.getAccNo() == staff.getAccNo())){
                current.setStatus("Inactive");
                System.out.println("Account deleted successfully!");
            }
        }
    }


    public void OpenAccountForm(String name,String password, String birthdate, String add, String gender) {
        System.out.println("-=====================-");
        System.out.println("   OPEN ACCOUNT FORM");
        System.out.println(" [1] Name        : " + name);
        System.out.println(" [2] Password    : " + password);
        System.out.println(" [3] Birthdate   : " + birthdate);
        System.out.println(" [4] Address     : " + add);
        System.out.println(" [5] Gender      : " + gender);
        System.out.println(" [6] Enter all   ");
        System.out.println(" [7] Confirm");
        System.out.println(" [8] Back");
        System.out.println("-=====================-");
    }

    private Staff staffwrapper(Staff staff){
        String name = "",bdate="",address="",gender="",password="";
        InputController inpt = new InputController();
        main : while(true){
            OpenAccountForm(name,password, bdate,address,gender);
            int num = InputController.getInteger("Choose (1 - 8)");
            switch (num){
                case 1:
                    staff.setName(InputController.getPassInput("Enter Name"));
                    name = staff.getName();
                    break;
                case 2:
                    staff.setPassword(inpt.getStrInpt("Enter password"));
                    password = staff.getPassword();
                    break;
                case 3:
                    staff.setBirthDate(inpt.getDateInpt());
                    bdate = staff.getBirthDate();

                    break;
                case 4:
                    staff.setAddress(inpt.getStrInpt("Enter the address"));
                    address = staff.getAddress();

                    break;
                case 5:
                    staff.setGender(inpt.getGenderr());
                    gender = String.valueOf(staff.getGender());
                    break;
                case 6:
                    lop : while (true){
                        char yes = InputController.getChar("Are you sure you want to enter all [Y/N]?");
                        switch(Character.toUpperCase(yes)) {
                            case 'Y':
                                staff.setName(InputController.getPassInput("Enter Name"));
                                staff.setPassword(inpt.getStrInpt("Enter password"));
                                staff.setBirthDate(inpt.getDateInpt());
                                staff.setAddress(inpt.getStrInpt("Enter the address"));
                                staff.setGender(inpt.getGenderr());
                                name = staff.getName();
                                password = staff.getPassword();
                                bdate = staff.getBirthDate();
                                address = staff.getAddress();
                                gender = String.valueOf(staff.getGender());
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
                    if (password == ""|| bdate =="" || name == "" || address == "" ||gender == ""){
                        System.out.println("One of the fields are missing!");
                        break;
                    }
                    staff.setAccNo(Integer.toString(inpt.generateAccNo()));
                    staff.setRole("Teller");
                    staff.setStatus("Active");
                    staff.setAge(inpt.calculateAge(staff.getBirthDate()));
                    return staff;
                case 8:
//                    System.out.println("Operation was canceled!");
                    return null;
                default:
                    System.out.println("Invalid Input!");
                    break;
            }
        }

//        InputController inpt = new InputController();
//        staff.setAccNo(Integer.toString(inpt.generateAccNo()));
//        staff.setName(inpt.getStrInpt("Enter your name"));
//        staff.setPassword(inpt.getStrInpt("Enter password"));
//        staff.setBirthDate(inpt.getDateInpt());
//        staff.setAddress(inpt.getStrInpt("Enter the address"));
//        staff.setGender(inpt.getGenderr());
//        staff.setRole("Teller");
//        staff.setStatus("Active");
//        staff.setAge(inpt.calculateAge(staff.getBirthDate()));
//        return staff;
    }

}
