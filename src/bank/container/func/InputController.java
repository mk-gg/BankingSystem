package bank.container.func;


import bank.models.Customer;
import bank.models.Staff;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Input Class
 *
 * Handles all inputs
 */

public class InputController {

    private Scanner inpt = new Scanner(System.in);
    private static ArrayList<Integer> listNo = new ArrayList<>();
    private static ArrayList<Integer> UID = new ArrayList<>();


    public static ObjectOutputStream objectOutputStream(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return new ObjectOutputStream(fileOutputStream);
    }

    public static ObjectInputStream objectInputStream(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return new ObjectInputStream(fileInputStream);
    }


    public static boolean check(int i ){
        for(int n : UID)
            if (i == n)
                return true;
        return false;
    }

    /**
     * Checks if the ID / Account No. is found
     * inside the list
     * @param i
     * @return
     */

    public boolean isFound(int i){
        for(int n : UID)
            if (i == n)
                return true;
        UID.add(i);
        return false;
    }

    public boolean isNoFound(int i){
        for(int n : listNo)
            if (i == n)
                return true;
        listNo.add(i);
        return false;
    }

    /**
     * Random Generation ID and Account No.
     * @return
     */

    public int generateUID(){
        Random random = new Random();
        int randomNumber = random.nextInt(10000 + 1 - 100) + 100;
        return isFound(randomNumber) ? generateAccNo() : randomNumber;
    }

    public int generateAccNo(){
        Random random = new Random();
        int randomNumber = random.nextInt(10000 + 1 - 100) + 100;
        return isNoFound(randomNumber) ? generateAccNo() : randomNumber;
    }

    public static String getPassInput(String prompt){
        System.out.print(prompt + " : ");
        return new Scanner(System.in).nextLine();
    }

    public String getStrInpt(String prompt){
        System.out.print(prompt + " : ");
        return inpt.nextLine();
    }

    public static int getTerm(String prompt){
        while (true){
            System.out.print(prompt + " : ");
            String choice = new Scanner(System.in).next();
            if (isint(choice)){
                if (Integer.parseInt(choice) == 1){
                    return 1;
                }else if (Integer.parseInt(choice) == 2){
                    return 2;
                }else if (Integer.parseInt(choice) == 3){
                   return 3;
                } else{
                    System.out.println("Incorrect Choice!");
                }
            }
        }
    }



    public boolean isInt(String n){
        Scanner scanner = new Scanner(n);
        boolean matches = scanner.hasNextInt();
        if(!matches){
            System.out.println("Invalid Input!");
            return false;
        }
        return matches;
    }

    /**
     * Input Gender choice
     * @return
     */
    public Staff.Gender getGenderr(){
        while (true){
            System.out.print("Select gender [1] Male : [2] Female : ");
            String choice = inpt.next();
            if (isInt(choice)){
                if (Integer.parseInt(choice) == 1){
                    return Staff.Gender.MALE;
                }else if (Integer.parseInt(choice) == 2){
                    return Staff.Gender.FEMALE;
                }else{
                    System.out.println("Incorrect Choice!");
                }
            }
        }
    }

    public Customer.Gender getGender(){
        while (true){
            System.out.print("Select gender [1] Male : [2] Female : ");
            String choice = inpt.next();
            if (isInt(choice)){
                if (Integer.parseInt(choice) == 1){
                    return Customer.Gender.MALE;
                }else if (Integer.parseInt(choice) == 2){
                    return Customer.Gender.FEMALE;
                }else{
                    System.out.println("Incorrect Choice!");
                }
            }
        }
    }

    public Customer.AccountType getAccType(){
        while (true){
            int choice = getInt("Select account type [1] Savings : [2] Credits ");
            if (choice == 1){
                return Customer.AccountType.SAVINGS;
            }else if (choice == 2){
                return Customer.AccountType.CREDITS;
            }else{
                System.out.println("Incorrect Choice!");
            }
        }
    }

    public boolean isValid(String dateStr) {
        String dateFormat = "MM/dd/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public boolean checkAge(String bdate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date now = new Date();
        String present = sdf.format(now);
        String parts[] = present.split("/");
        String partss[] = bdate.split("/");
        int age = Integer.parseInt(parts[2]) - Integer.parseInt(partss[2]);
        if (age >= 18 && age < 100){
            return true;
        }else{
            return false;
        }
    }

    public String calculateAge(String bdate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date now = new Date();
        String present = sdf.format(now);
        String parts[] = present.split("/");
        String partss[] = bdate.split("/");
        int age = Integer.parseInt(parts[2]) - Integer.parseInt(partss[2]);

        return Integer.toString(age);
    }

    public String checkDate(){
        System.out.print("Enter Birthdate [MM/DD/YYYY] - [11/2/2014] : ");
        String date = inpt.nextLine();
        boolean isDate = isValid(date);
        boolean isAge;
        boolean correct = false;
        if (isDate){
            isAge = checkAge(date);
            if (isAge){
                correct = true;
            }else{
                System.out.println("Invalid Age!");
            }
        }else{
            System.out.println("Invalid Date!");
        }

        return correct == true ? date : checkDate();
    }

    public String getDateInpt() {
        String date = checkDate();

        return date;
    }

    public static double getAmount(String prompt){
        System.out.print(prompt + " : ");
        String inpt = new Scanner(System.in).next();

        return isDouble(inpt) ? Double.parseDouble(inpt) : getAmount(prompt);

    }



    /**
     * Checks Data type
     * and gets data
     */
    public static boolean isint(String n){
        Scanner scanner = new Scanner(n);
        boolean matches = scanner.hasNextInt();
        if(!matches){
            System.out.println("Invalid Input!");
            return false;
        }
        return matches;
    }

    public static int getInt(String prompt){
        System.out.print(prompt + " : ");
        while (true){
            try{
                return new Scanner(System.in).nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("-- INVALID INPUT");
                System.out.print(prompt + " : ");
                new Scanner(System.in).next();
            }
        }
    }

    public static boolean isInteger(String n){
        Scanner scanner = new Scanner(n);
        boolean matches = scanner.hasNextInt();
        if(!matches){
            System.out.println("Invalid Input!");
            return false;
        }
        return matches;
    }

    public static boolean isDouble(String n){
        Scanner scanner =new Scanner(n);
        boolean matches = scanner.hasNextDouble();
        if (matches){
            if (Double.parseDouble(n) < 300){
                System.out.println("The amount is less than 300!");
                return false;
            }

            if (Double.parseDouble(n) > 20000){
                System.out.println("The amount is greather than 20,000!");
                return false;
            }

        }else{
            System.out.println("Invalid Input!");
        }

        return matches;

    }

    public static int getInteger(String prompt){
        System.out.print(prompt + " : ");
        String inpt = new Scanner(System.in).next();

        return isInteger(inpt) ? Integer.parseInt(inpt) : getInteger(prompt);
    }

    public static char getChar (String prompt){

        System.out.print(prompt + " : ");
        Scanner input = new Scanner(System.in);

        while (true){
            try{
                return input.next(".").charAt(0);
            }
            catch(InputMismatchException e){
                System.out.println("-- INVALID INPUT");
                System.out.print(prompt+ " : ");
                input.next();

            }
        }

    }


}
