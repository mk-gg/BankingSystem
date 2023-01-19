package bank.util;

/**
 * Auth class
 *
 * This is for the login and this will determine if
 * the user is admin or not
 */
public class Auth {
    private  static String accNo,name,type;

    public static String getAccNo() {
        return accNo;
    }

    public static void setAccNo(String accNo) {
        Auth.accNo = accNo;
    }

    public static  String getName() {
        return name;
    }

    public static void setName(String name) {
        Auth.name = name;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Auth.type = type;
    }
}
