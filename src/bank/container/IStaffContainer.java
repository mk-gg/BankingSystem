package bank.container;

import bank.models.Staff;

import java.util.ArrayList;

public interface IStaffContainer {
    ArrayList<Staff> all();

    ArrayList<Staff> active();

    ArrayList<Staff> inactive();

    Staff find(int staffID);

    void update(Staff staff);

    void store(Staff staff);

    void delete(Staff staff);

    void activate(Staff staff);

}
