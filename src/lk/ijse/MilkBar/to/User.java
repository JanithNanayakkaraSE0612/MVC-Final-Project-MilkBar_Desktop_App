package lk.ijse.MilkBar.to;


public class User {
      private String empID;
      private String userName;
      private String userPassword;
      private String Role;

    public User(String empID) {
        this.empID = empID;
    }

    public User(String empID, String userName, String userPassword, String Role) {
        this.empID=empID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.Role=Role;
    }

    public User() {
    }

    public User(String userName, String userPassword, String role) {
        this.userName = userName;
        this.userPassword = userPassword;
        Role = role;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
       this.Role = role;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
}
