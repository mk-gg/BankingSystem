package bank.manager;

import bank.container.func.StaffContainer;
import bank.models.Staff;
import bank.util.Auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * STAFF CONTROLLER
 */
public class StaffManager {
    private static StaffManager staffManager;
    private final StaffContainer staffContainer;
    private List<Staff> staffs;

    /**
     * STAFF CONTROLLER CONSTRUCTOR
     *
     * for initializing the STAFFCONTAINER class
     * and Reading the staff.txt file
     * @throws IOException
     */
    private StaffManager() throws IOException {
        this.staffContainer = new StaffContainer();
        staffContainer.StaffFile();
    }


    /**
     * Login Session for Staff
     *
     * this will determine whether the user is a
     * STAFF or TELLER
     *
     * @param accNo
     * @param pass
     * @return
     */
    public boolean login(String accNo, String pass) {
        staffs = staffContainer.all();
        for (Iterator<Staff> iterator = staffs.iterator(); iterator.hasNext(); ) {
            Staff i = iterator.next();
            Staff temp = i;
//            System.out.println(temp.getPassword());
            if (temp.getAccNo().equals(accNo) && temp.getPassword().equals(pass)) {
                Auth.setAccNo(accNo);
                Auth.setName(temp.getName());
                if (temp.getRole() == "Manager") {
                    Auth.setType("admin");
                }
                if (temp.getRole() == "Teller"){
                    Auth.setType("teller");
                }

                return true;
            }
        }
        return false;
    }



    public void store(Staff staff) {
        staffContainer.store(staff);
    }

    public ArrayList<Staff> getActiveStaffs(){
        return staffContainer.active();
    }

    public ArrayList<Staff> getInactiveStaffs(){
        return staffContainer.inactive();
    }

    public ArrayList<Staff> getStaffs() {
        return staffContainer.all();
    }

    public Staff findID(int id) {
        return staffContainer.find(id);
    }

    public void update(Staff staff) {
        staffContainer.update(staff);
    }

    public void delete(Staff staff) {
        if (staff.getStatus() == "Inactive"){
            System.out.println("Account has already been removed!");
            return;
        }else{
            staffContainer.delete(staff);
        }
    }

    public void activate(Staff staff) { staffContainer.activate(staff);}



    public static StaffManager getInstance() throws IOException{
        if (staffManager == null) staffManager = new StaffManager();
        return staffManager;
    }

    public void displayAccount(Staff staff){
        System.out.println("Account No   : " + staff.getAccNo());
        System.out.println("Name         : " + staff.getName());
        System.out.println("Address      : " + staff.getAddress());
        System.out.println("Gender       : " + staff.getGender());
        System.out.println("Birth date   : " + staff.getBirthDate());
        System.out.println("Age          : " + staff.getAge());
        System.out.println("Role         : " + staff.getRole());
        System.out.println("Status       : " + staff.getStatus());
    }

}
